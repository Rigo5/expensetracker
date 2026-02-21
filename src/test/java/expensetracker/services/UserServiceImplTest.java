package expensetracker.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import expensetracker.models.User;
import expensetracker.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

	@Test
	@DisplayName("findAll should return all users from repository")
	void findAllShouldReturnRepositoryUsers() {
		User user = new User(1L, "Alice", "Smith", "alice@example.com", null, null, "true");
        when(userRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(user)));

        Page<User> result = userService.findAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(user);
        verify(userRepository).findAll(PageRequest.of(0, 10));
        verifyNoMoreInteractions(userRepository);
	}

    @Test
    @DisplayName("find should return result from repository")
    void findShouldReturnOptionalFromRepository() {
        User user = new User(2L, "Bob", "Jones", "bob@example.com", null, null, "yoooo");
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.find(2L);

        assertThat(result).containsSame(user);
        verify(userRepository).findById(2L);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("create should save the user and return its id")
    void createShouldPersistUser() {
        User request = new User(null, "Carol", "Williams", "carol@example.com", null, null, "test");
        when(userRepository.save(request)).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        Optional<Long> result = userService.create(request);

        assertThat(result).contains(10L);
        verify(userRepository).save(request);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("delete should call repository")
    void deleteShouldInvokeRepository() {
        userService.delete(7L);

        verify(userRepository).deleteById(7L);
        verifyNoMoreInteractions(userRepository);
    }
}
