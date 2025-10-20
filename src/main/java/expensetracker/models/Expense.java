package expensetracker.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record Expense(
        @Id
        @GeneratedValue
        Long id,

        String description,

        @ManyToOne
        @JoinColumn(name = "owner_id")
        User owner,

        @Column(nullable = false, precision = 15, scale = 2)
        BigDecimal amount,

        @Enumerated(EnumType.STRING)
        TransactionCategory category,

        @Enumerated(EnumType.STRING)
        TransactionType type,

        @CreationTimestamp
        LocalDate createDate,

        @UpdateTimestamp
        LocalDate updateDate
		) 
{}
