package expensetracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	
	@GetMapping("/")
	public ResponseEntity<?> all(){
		return ResponseEntity.ok("cIAO"); 
	}
}
