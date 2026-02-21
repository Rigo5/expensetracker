package expensetracker.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
