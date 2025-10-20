package expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import expensetracker.models.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
