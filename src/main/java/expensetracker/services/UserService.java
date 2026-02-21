package expensetracker.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import expensetracker.models.User;

public interface UserService {
	public Page<User> findAll(Pageable pageable); 
	public Optional<User> find(Long id); 
	public Optional<Long> create(User request);
	public Optional<Void> delete(Long id);
}
