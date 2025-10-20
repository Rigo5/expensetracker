package expensetracker.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExpenseRequest(
        @NotBlank String description,
        @NotNull @DecimalMin(value = "0.000", inclusive = false) BigDecimal amount,
        @NotNull TransactionCategory category,
        @NotNull TransactionType type,
        @NotNull Long ownerId
) {}