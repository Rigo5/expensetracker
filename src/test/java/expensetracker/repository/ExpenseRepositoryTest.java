package expensetracker.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import expensetracker.models.Expense;
import expensetracker.models.TransactionCategory;
import expensetracker.models.TransactionType;
import expensetracker.models.User;

@DataJpaTest
public class ExpenseRepositoryTest {
	
	@Autowired ExpenseRepository expenseRepo; 
	
	@Autowired UserRepository usersRepo; 
	
	private Expense expense; 
	
	public ExpenseRepositoryTest() {
		Optional<User> user = usersRepo.findById(1l);
		expense = new Expense(
				"test",
				user.get(),
				new BigDecimal(11.5),
				TransactionCategory.GIFTS,
				TransactionType.EXPENSE);
	}
	
	@Test
	public void saveAndFindById() {
		Expense saved = expenseRepo.save(expense); 
		assertNotNull(saved.getId());
		
		Optional<Expense> foundExpense = expenseRepo.findById(saved.getId()); 
		assertEquals(saved.getId(), foundExpense.get().getId());
	}
}
