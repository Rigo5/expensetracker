package expensetracker.services;

import java.util.List;
import java.util.Optional;

import expensetracker.models.ExpenseRequest;
import expensetracker.models.User;

public interface UserService {
	public List<User> findAll(); 
	public Optional<User> find(Long id); 
	public Optional<Long> create(ExpenseRequest request);
	public Optional<Void> delete(Long id);
}
