package expensetracker.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.TransactionCategory;
import expensetracker.models.TransactionType;
import expensetracker.models.User;
import expensetracker.services.ExpenseService;

@WebMvcTest(controllers = ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    @DisplayName("GET /api/expenses/ should return all expenses")
    void allShouldReturnExpenses() throws Exception {
        User owner = new User(1L, "John", "Doe", "john.doe@example.com", null, null);
        Expense expense = new Expense(10L, "Laptop", owner, new BigDecimal("1200.00"),
                TransactionCategory.HOBBY, TransactionType.EXPENSE, null, null);

        when(expenseService.findAll()).thenReturn(List.of(expense));

        mockMvc.perform(get("/api/expenses/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Laptop"))
                .andExpect(jsonPath("$[0].amount").value(1200.00));

        verify(expenseService).findAll();
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    @DisplayName("GET /api/expenses/{id} should return not found when expense is missing")
    void getShouldReturnNotFoundWhenMissing() throws Exception {
        when(expenseService.find(20L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/expenses/{id}", 20L))
                .andExpect(status().isNotFound());

        verify(expenseService).find(20L);
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    @DisplayName("GET /api/expenses/{id} should return the expense when present")
    void getShouldReturnExpense() throws Exception {
        User owner = new User(2L, "Jane", "Smith", "jane.smith@example.com", null, null);
        Expense expense = new Expense(30L, "Groceries", owner, new BigDecimal("45.50"),
                TransactionCategory.FOOD, TransactionType.EXPENSE, null, null);

        when(expenseService.find(30L)).thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/expenses/{id}", 30L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Groceries")));

        verify(expenseService).find(30L);
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    @DisplayName("POST /api/expenses/ should create a new expense when service succeeds")
    void createShouldReturnCreated() throws Exception {
        ExpenseRequest request = new ExpenseRequest("Train ticket", new BigDecimal("15.00"),
                TransactionCategory.HOBBY, TransactionType.EXPENSE, 1L);

        when(expenseService.create(any(ExpenseRequest.class))).thenReturn(Optional.of(55L));

        mockMvc.perform(post("/api/expenses/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/expenses/55"))
                .andExpect(jsonPath("$.id").value(55));

        verify(expenseService).create(Mockito.any(ExpenseRequest.class));
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    @DisplayName("POST /api/expenses/ should return bad request when service fails to create")
    void createShouldReturnBadRequestWhenServiceFails() throws Exception {
        ExpenseRequest request = new ExpenseRequest("Gym", new BigDecimal("70.00"),
                TransactionCategory.SPORT, TransactionType.EXPENSE, 2L);

        when(expenseService.create(any(ExpenseRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/expenses/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(expenseService).create(Mockito.any(ExpenseRequest.class));
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    @DisplayName("DELETE /api/expenses/{id} should delegate to the service and return ok")
    void deleteShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/expenses/{id}", 77L))
                .andExpect(status().isOk());

        verify(expenseService).delete(77L);
        verifyNoMoreInteractions(expenseService);
    }
}
