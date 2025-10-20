package expensetracker.models;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "Users")
@Table(
        name = "Users",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"email"},
                name = "email_uniquess"
        )
)
public record User(
        @Id
        @GeneratedValue
        Long id,

        @Column(nullable = false)
        String name,

        @Column(nullable = false)
        String surname,

        @Column(nullable = false)
        String email,

        @CreationTimestamp
        LocalDate createDate,
        
        @UpdateTimestamp
        LocalDate updateDate,
        
        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        List<Expense> transactions
) {}