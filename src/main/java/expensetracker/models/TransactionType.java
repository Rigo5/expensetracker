package expensetracker.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
	INCOME, EXPENSE;
	
	@JsonCreator
	public static TransactionType from(String value) {
		return Arrays.stream(values())
			.filter(v -> v.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Category not valid" + value));
	}
	
	@JsonValue
	public String toJson() {
		return name().toLowerCase();
	}
}