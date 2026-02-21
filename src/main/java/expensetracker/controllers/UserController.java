package expensetracker.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import expensetracker.models.IdResponse;
import expensetracker.models.User;
import expensetracker.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public ResponseEntity<?> all(@RequestParam(defaultValue = "0") int page,
								 @RequestParam(defaultValue = "10") int size){
		Page<User> users = userService.findAll(PageRequest.of(page, size));
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id){
		Optional<User> user = userService.find(id);
		if(user.isEmpty()) return ResponseEntity.notFound().build(); 
		return ResponseEntity.ok(user.get());
	}
	
	@PostMapping("/")
	public ResponseEntity<?> post(@RequestBody User user){
		Optional<Long> id = userService.create(user);
		return ResponseEntity.created(URI.create("/api/users" + id.get()))
				.body(new IdResponse(id.get()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity
				.noContent()
				.build();
	}
}
