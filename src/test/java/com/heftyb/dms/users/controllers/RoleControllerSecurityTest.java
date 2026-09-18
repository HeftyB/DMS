package com.heftyb.dms.users.controllers;

import com.heftyb.dms.users.Role;
import com.heftyb.dms.users.RoleDepartment;
import com.heftyb.dms.users.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RoleControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private RoleController roleController;

    @Test
    void anonymousUserIsSentToLogin() throws Exception {
        mvc.perform(post("/admin/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isEmpty();
    }

    @Test
    @WithMockUser(roles = "USER")
    void nonAdminCannotCreateRole() throws Exception {
        mvc.perform(post("/admin/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(status().isForbidden());

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isEmpty();
    }

    @Test
    @WithMockUser(roles = "USER")
    void controllerRejectsNonAdminIndependentlyOfUrlRules() {
        assertThatThrownBy(() -> roleController.createNewRole("porter", RoleDepartment.SERVICE))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateRole() throws Exception {
        mvc.perform(post("/admin/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(redirectedUrl("/admin"));

        assertThat(roleRepo.findByRoleIgnoreCase("porter"))
                .get()
                .extracting(Role::getDepartment)
                .isEqualTo(RoleDepartment.SERVICE);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanDeleteRole() throws Exception {
        Role porter = roleRepo.save(new Role("porter"));

        mvc.perform(post("/admin/roles/delete").with(csrf())
                        .param("roleId", String.valueOf(porter.getId())))
                .andExpect(redirectedUrl("/admin"));

        assertThat(roleRepo.findById(porter.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void unknownDepartmentIsRejected() throws Exception {
        mvc.perform(post("/admin/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "NOT_A_DEPARTMENT"))
                .andExpect(status().isBadRequest());

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isEmpty();
    }
}
