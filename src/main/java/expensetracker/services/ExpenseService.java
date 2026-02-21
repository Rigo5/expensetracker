package expensetracker.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.ExpenseResponse;

public interface ExpenseService {
	public Page<ExpenseResponse> findAll(Pageable pageable); 
	public Optional<ExpenseResponse> find(Long id); 
	public Optional<Long> create(ExpenseRequest request);
	public Optional<Expense> update(Long id, ExpenseRequest request);
	public Page<ExpenseResponse> findByUser(Long userId, Pageable pageable);
	public Optional<Void> delete(Long id);
}
