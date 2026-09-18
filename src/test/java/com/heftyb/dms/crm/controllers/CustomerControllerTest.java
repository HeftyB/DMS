package com.heftyb.dms.crm.controllers;

import com.heftyb.dms.crm.Customer;
import com.heftyb.dms.crm.repositories.CustomerRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@WithMockUser
class CustomerControllerTest {

    private static final String REDIRECT_PREFIX = "/customers/customer?id=";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepo;

    @Test
    void createsCustomerFromForm() throws Exception {
        String location = mvc.perform(post("/customers/customer/create").with(csrf())
                        .param("firstName", "Dana")
                        .param("lastName", "Reyes")
                        .param("email", "dana@example.com")
                        .param("address.addressLine1", "100 Main St")
                        .param("address.city", "Jacksonville")
                        .param("address.state", "FL")
                        .param("address.zip.zip", "32202")
                        .param("contactInformation.primaryPhone.number", "9045550100"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().getRedirectedUrl();

        assertThat(location).startsWith(REDIRECT_PREFIX);
        long id = Long.parseLong(location.substring(REDIRECT_PREFIX.length()));

        assertThat(customerRepo.findById(id))
                .get()
                .extracting(Customer::getLastName)
                .isEqualTo("Reyes");
    }

    @Test
    void rejectsCustomerWithoutLastName() throws Exception {
        long before = customerRepo.count();

        mvc.perform(post("/customers/customer/create").with(csrf())
                        .param("firstName", "Dana"))
                .andExpect(status().isBadRequest());

        assertThat(customerRepo.count()).isEqualTo(before);
    }
}
