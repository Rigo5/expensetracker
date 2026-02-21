package expensetracker.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import expensetracker.models.User;
import expensetracker.services.UserService;
import expensetracker.config.SecurityConfig;

@WebMvcTest(controllers = UserController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "app.auth.username=testuser",
        "app.auth.password=testpass"
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean 
    private UserService userService;

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpass";

    @Test
    @DisplayName("GET /api/users/ should return all users")
    void allShouldReturnUsers() throws Exception {
        User user = new User(1L, "Alice", "Johnson", "alice@example.com", null, null, "test");

        when(userService.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/users/").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));

        verify(userService).findAll(PageRequest.of(0, 10));
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("GET /api/users/{id} should return the user when present")
    void getShouldReturnUser() throws Exception {
        User user = new User(2L, "Bob", "Williams", "bob@example.com", null, null, "test");

        when(userService.find(2L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", 2L).with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("bob@example.com"));

        verify(userService).find(2L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("GET /api/users/{id} should return not found when missing")
    void getShouldReturnNotFoundWhenMissing() throws Exception {
        when(userService.find(5L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", 5L).with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());

        verify(userService).find(5L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("POST /api/users/ should return created when service provides an id")
    void postShouldReturnCreated() throws Exception {
        User request = new User(null, "Carol", "Smith", "carol@example.com", null, null, "test");

        when(userService.create(any(User.class))).thenReturn(Optional.of(12L));

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users12"))
                .andExpect(jsonPath("$.id").value(12));

        verify(userService).create(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("DELETE /api/users/{id} should return no content")
    void deleteShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 3L).with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());

        verify(userService).delete(3L);
        verifyNoMoreInteractions(userService);
    }
}
