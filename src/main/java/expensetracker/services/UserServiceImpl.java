package expensetracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import expensetracker.models.User;
import expensetracker.models.UserPrincipal;
import expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	private final UserRepository userRepository;
	private final PasswordEncoder econder; 
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository; 
		this.econder = encoder; 
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> find(Long id) {
		return userRepository.findById(id); 
	}

	@Override
	public Optional<Long> create(User request) {
		request.setPassword(econder.encode(request.getPassword()));
		User user = userRepository.save(request); 
		return Optional.of(user.getId());
	}

	@Override
	public Optional<Void> delete(Long id) {
		userRepository.deleteById(id);
		return Optional.empty();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found"); 
		}
		
		return new UserPrincipal(user);
	}

}
