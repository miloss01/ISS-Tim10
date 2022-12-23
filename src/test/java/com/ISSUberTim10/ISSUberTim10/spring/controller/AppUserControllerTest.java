package com.ISSUberTim10.ISSUberTim10.spring.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.controller.AppUserController;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl.AppUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = AppUserController.class)
public class AppUserControllerTest {

    // Testovi ne prolaze zbog izuzetka:
    // java.lang.IllegalStateException: Failed to load ApplicationContext

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should block user with PUT request to endpoint - /api/user/{id}/block")
    public void shouldBlockUser() throws Exception {
        String responseMessage = "User successfully blocked";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);

        Mockito.when(appUserService.blockUser(1)).thenReturn(responseEntity);

        mockMvc.perform(put("/api/user/{id}/block", 1))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$.message").value(responseMessage));

    }

    @Test
    @DisplayName("Should get all users with GET request to endpoint - /api/user")
    public void getUsers() throws Exception {
        this.mockMvc
                .perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BlockResponse {
    private String responseMessage;
}
