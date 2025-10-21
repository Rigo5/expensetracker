package expensetracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.User;
import expensetracker.repository.ExpenseRepository;
import expensetracker.repository.UserRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{

	private final ExpenseRepository expenseRepository; 
	private final UserRepository userRepository; 
	
	@Autowired
	public ExpenseServiceImpl(ExpenseRepository expenseRepo, UserRepository userRepo) {
		this.expenseRepository = expenseRepo;
		this.userRepository = userRepo;
	}
	
	@Override
	public List<Expense> findAll() {
		return expenseRepository.findAll();
	}

	@Override
	public Optional<Expense> find(Long id) {
		return expenseRepository.findById(id);
	}

	@Override
	public Optional<Long> create(ExpenseRequest request) {
		if(!userRepository.existsById(request.ownerId())) return Optional.empty(); 
		
		User owner = userRepository.getReferenceById(request.ownerId());
		Expense expense = new Expense(
				request.description(),
				owner,
				request.amount(),
				request.category(),
				request.type());
		expenseRepository.save(expense);
		return Optional.of(expense.id());
	}

	@Override
	public Optional<Void> delete(Long id) {
		expenseRepository.deleteById(id);
		return Optional.empty();
	}

}
