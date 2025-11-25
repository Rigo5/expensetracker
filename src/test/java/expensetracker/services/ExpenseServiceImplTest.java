package expensetracker.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import expensetracker.exception.UserNotFoundException;
import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.TransactionCategory;
import expensetracker.models.TransactionType;
import expensetracker.models.User;
import expensetracker.repository.ExpenseRepository;
import expensetracker.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    @DisplayName("findAll should delegate to repository")
    void findAllShouldReturnRepositoryData() {
        Expense expense = new Expense(1L, "Book", null, new BigDecimal("10.00"),
                TransactionCategory.HOBBY, TransactionType.EXPENSE, null, null);
        when(expenseRepository.findAll()).thenReturn(List.of(expense));

        List<Expense> result = expenseService.findAll();

        assertThat(result).containsExactly(expense);
        verify(expenseRepository).findAll();
        verifyNoMoreInteractions(expenseRepository, userRepository);
    }

    @Test
    @DisplayName("find should return the repository result")
    void findShouldReturnOptionalFromRepository() {
        Expense expense = new Expense(2L, "Groceries", null, new BigDecimal("25.50"),
                TransactionCategory.FOOD, TransactionType.EXPENSE, null, null);
        when(expenseRepository.findById(2L)).thenReturn(Optional.of(expense));

        Optional<Expense> result = expenseService.find(2L);

        assertThat(result).containsSame(expense);
        verify(expenseRepository).findById(2L);
        verifyNoMoreInteractions(expenseRepository, userRepository);
    }

    @Test
    @DisplayName("create should build and persist a new expense when owner exists")
    void createShouldPersistExpenseWhenOwnerExists() {
        ExpenseRequest request = new ExpenseRequest("Laptop", new BigDecimal("1200.00"),
                TransactionCategory.HOBBY, TransactionType.EXPENSE, 5L);
        User owner = new User(5L, "John", "Doe", "john.doe@example.com", null, null);
        when(userRepository.existsById(5L)).thenReturn(true);
        when(userRepository.getReferenceById(5L)).thenReturn(owner);
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> {
            Expense saved = invocation.getArgument(0);
            saved.setId(99L);
            return saved;
        });

        Optional<Long> result = expenseService.create(request);

        assertThat(result).contains(99L);
        verify(userRepository).existsById(5L);
        verify(userRepository).getReferenceById(5L);
        verify(expenseRepository).save(any(Expense.class));
        verifyNoMoreInteractions(expenseRepository, userRepository);
    }

    @Test
    @DisplayName("create should throw when owner does not exist")
    void createShouldThrowWhenOwnerMissing() {
        ExpenseRequest request = new ExpenseRequest("Laptop", new BigDecimal("1200.00"),
                TransactionCategory.HOBBY, TransactionType.EXPENSE, 99L);
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> expenseService.create(request))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User id not found");

        verify(userRepository).existsById(99L);
        verifyNoInteractions(expenseRepository);
    }

    @Test
    @DisplayName("delete should delegate to repository")
    void deleteShouldCallRepository() {
        expenseService.delete(8L);

        verify(expenseRepository).deleteById(8L);
        verifyNoMoreInteractions(expenseRepository, userRepository);
    }
}
