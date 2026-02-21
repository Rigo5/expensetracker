package expensetracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import expensetracker.exception.UserNotFoundException;
import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.ExpenseResponse;
import expensetracker.models.User;
import expensetracker.repository.ExpenseRepository;
import expensetracker.repository.UserRepository;
import expensetracker.utility.ExpenseMapper;

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
	public List<ExpenseResponse> findAll() {
		return expenseRepository.findAll()
					.stream()
					.map(ExpenseMapper::mapToResponse)
					.toList();
	}

	@Override
	public Optional<ExpenseResponse> find(Long id) {
		Optional<Expense> expense = expenseRepository.findById(id);
		if(expense.isPresent()) return Optional.of(ExpenseMapper.mapToResponse(expense.get()));
		return Optional.empty();
	}

	@Override
	public Optional<Long> create(ExpenseRequest request) {
		if(!userRepository.existsById(request.getOwnerId())) throw new UserNotFoundException("User id not found");
		
		User owner = userRepository.getReferenceById(request.getOwnerId());
		Expense expense = new Expense(
				request.getDescription(),
				owner,
				request.getAmount(),
				request.getCategory(),
				request.getType());
		expenseRepository.save(expense);
		return Optional.of(expense.getId());
	}

	@Override
	public Optional<Expense> update(Long id, ExpenseRequest request) {
		Optional<Expense> maybeExpense = expenseRepository.findById(id);
		if (maybeExpense.isEmpty()) {
			return Optional.empty();
		}

		if (!userRepository.existsById(request.getOwnerId())) {
			throw new UserNotFoundException("User id not found");
		}

		User owner = userRepository.getReferenceById(request.getOwnerId());
		Expense expense = maybeExpense.get();
		expense.setDescription(request.getDescription());
		expense.setOwner(owner);
		expense.setAmount(request.getAmount());
		expense.setCategory(request.getCategory());
		expense.setType(request.getType());

		Expense saved = expenseRepository.save(expense);
		return Optional.of(saved);
	}

	@Override
	public Optional<Void> delete(Long id) {
		expenseRepository.deleteById(id);
		return Optional.empty();
	}

}
