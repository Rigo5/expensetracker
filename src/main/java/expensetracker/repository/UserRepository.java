package expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import expensetracker.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
