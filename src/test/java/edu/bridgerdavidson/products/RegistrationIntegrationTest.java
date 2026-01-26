package edu.bridgerdavidson.products;

import edu.bridgerdavidson.products.data.UsersRepository;
import edu.bridgerdavidson.products.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies:
 * - Registration persists users
 * - Passwords are encoded (not stored as plain text)
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationIntegrationTest {

    @Autowired MockMvc mvc;
    @Autowired UsersRepository usersRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void registration_persistsUser_and_encodesPassword() throws Exception {
        // IMPORTANT: update this endpoint to match your app
        // Common ones: "/register", "/users/register", "/api/register"
        String registerPath = "/register";

        String username = "newuser";
        String rawPassword = "Password123!";

        mvc.perform(post(registerPath)
                        .with(csrf())
                        .param("username", username)
                        .param("password", rawPassword)
                        .param("email", "newuser@example.com"))
                .andExpect(status().is3xxRedirection());

        UserEntity saved = usersRepository.findByUsername(username);
        assertThat(saved).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("newuser@example.com"); // add this line if UserEntity has getEmail()
        assertThat(saved.getPassword()).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, saved.getPassword())).isTrue();

    }
}
