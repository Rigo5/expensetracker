package expensetracker.services;

import java.util.List;
import java.util.Optional;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.ExpenseResponse;

public interface ExpenseService {
	public List<ExpenseResponse> findAll(); 
	public Optional<ExpenseResponse> find(Long id); 
	public Optional<Long> create(ExpenseRequest request);
	public Optional<Expense> update(Long id, ExpenseRequest request);
	public Optional<Void> delete(Long id);
}
