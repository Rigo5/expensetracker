package expensetracker.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    Long id;

    String description;

    Long owner;

    BigDecimal amount;

    TransactionCategory category;

    TransactionType type;

    LocalDate createDate;

    LocalDate updateDate;
    
    
	public ExpenseResponse(String description, 
			Long owner,
			BigDecimal amount,
			TransactionCategory category,
			TransactionType type) {
		
		this(null, description, owner, amount, category, type, null, null);
	}
	
	
}