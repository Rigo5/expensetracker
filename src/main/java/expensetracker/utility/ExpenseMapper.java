package expensetracker.utility;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseResponse;

public class ExpenseMapper {
	
	public static ExpenseResponse mapToResponse(Expense expense) {
		ExpenseResponse response = new ExpenseResponse();
		response.setId(expense.getId());
		response.setCategory(expense.getCategory());
		response.setCreateDate(expense.getCreateDate());
		response.setAmount(expense.getAmount());
		response.setDescription(expense.getDescription());
		response.setOwner(expense.getOwner().getId());
		response.setType(expense.getType());
		response.setUpdateDate(expense.getUpdateDate());
		
		return response; 
	}
}
