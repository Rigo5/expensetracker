package expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import expensetracker.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
