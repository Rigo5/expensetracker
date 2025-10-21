package expensetracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import expensetracker.models.User;
import expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository; 
	}
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> find(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<Long> create(User request) {
		User user = userRepository.save(request); 
		return Optional.of(user.id());
	}

	@Override
	public Optional<Void> delete(Long id) {
		userRepository.deleteById(id);
		return Optional.empty();
	}

}
