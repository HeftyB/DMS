package com.heftyb.dms.users.controllers;

import com.heftyb.dms.users.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void anonymousUserIsSentToLogin() throws Exception {
        mvc.perform(post("/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isEmpty();
    }

    @Test
    @WithMockUser(roles = "USER")
    void nonAdminCannotCreateRole() throws Exception {
        mvc.perform(post("/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(status().isForbidden());

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateRole() throws Exception {
        mvc.perform(post("/roles/create").with(csrf())
                        .param("role", "porter")
                        .param("department", "SERVICE"))
                .andExpect(status().is3xxRedirection());

        assertThat(roleRepo.findByRoleIgnoreCase("porter")).isPresent();
    }
}
