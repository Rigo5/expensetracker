package expensetracker.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import expensetracker.models.Expense;
import expensetracker.models.ExpenseRequest;
import expensetracker.models.IdResponse;
import expensetracker.services.ExpenseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	private ExpenseService expenseService; 
	
	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService; 
	}
	
	@GetMapping("/")
	public ResponseEntity<?> all(){
		List<Expense> expenses = expenseService.findAll();
		return ResponseEntity.ok(expenses);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id){
		Optional<Expense> expense = expenseService.find(id);
		if(expense.isEmpty()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(expense);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody @Valid ExpenseRequest request){
		Optional<Long> expenseId = expenseService.create(request); 
		if(expenseId.isEmpty()) return ResponseEntity.badRequest().build();
		return ResponseEntity
				.created(URI.create("/api/expenses/" + expenseId.get()))
				.body(new IdResponse(expenseId.get()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		expenseService.delete(id);
		return ResponseEntity.ok().build(); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ExpenseRequest request){
		Optional<Expense> expense = expenseService.update(id, request);
		if(expense.isEmpty()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(expense.get());
	}
}
