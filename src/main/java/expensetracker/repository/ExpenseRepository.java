package expensetracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import expensetracker.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	Page<Expense> findByOwnerId(Long ownerId, Pageable pageable);
}
