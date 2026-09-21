package com.heftyb.dms.vehicles.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void vinSearchAcceptsThePostTheFormSends() throws Exception {
        mvc.perform(post("/vehicles/search").with(csrf()).param("vin", "JH4TB2H26CC000000"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles"));
    }

    @Test
    void blankVinSearchRedirectsToTheVehicleList() throws Exception {
        mvc.perform(get("/vehicles/search").param("vin", ""))
                .andExpect(redirectedUrl("/vehicles"));
    }
}
