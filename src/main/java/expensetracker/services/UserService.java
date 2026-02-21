package expensetracker.services;

import java.util.List;
import java.util.Optional;

import expensetracker.models.User;

//test
public interface UserService {
	public List<User> findAll(); 
	public Optional<User> find(Long id); 
	public Optional<Long> create(User request);
	public Optional<Void> delete(Long id);
}
