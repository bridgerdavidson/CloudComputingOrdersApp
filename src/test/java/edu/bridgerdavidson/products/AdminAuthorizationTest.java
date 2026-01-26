package edu.bridgerdavidson.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminAuthorizationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    RequestMappingHandlerMapping handlerMapping;

    @Test
    void adminUsersEndpoint_isActuallyMapped() {
        boolean found = handlerMapping.getHandlerMethods()
                .keySet()
                .stream()
                .anyMatch(info -> info.getPatternValues().contains("/admin/users"));

        assertThat(found)
                .as("Expected a controller mapping for GET /admin/users, but none was found")
                .isTrue();
    }

    @Test
    void adminRoutes_redirectWhenUnauthenticated() throws Exception {
        mvc.perform(get("/admin/users"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status).isBetween(300, 399);
                    assertThat(result.getResponse().getRedirectedUrl()).isEqualTo("/login");
                });
    }

    @Test
    void adminUsers_canPassSecurity_evenIfTemplateMissing() throws Exception {
        mvc.perform(get("/admin/users").with(user("admin").roles("ADMIN")))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();

                    assertThat(status < 300 || status > 399)
                            .as("Expected NOT a redirect (3xx), but got: " + status)
                            .isTrue();

                    // Accept either:
                    // 200 -> template exists
                    // 5xx -> controller executed but template missing (common before HTML is done)
                    assertThat(status == 200 || (status >= 500 && status < 600))
                            .as("Expected 200 (template exists) or 5xx (template missing), got: " + status)
                            .isTrue();
                });
    }
}
