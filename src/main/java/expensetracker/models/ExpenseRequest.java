package expensetracker.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor    
@AllArgsConstructor    
public class ExpenseRequest {

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "0.000", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private TransactionCategory category;

    @NotNull
    private TransactionType type;

    @NotNull
    private Long ownerId;
}