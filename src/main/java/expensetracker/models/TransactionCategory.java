package expensetracker.models;

import java.util.Arrays;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionCategory {
	
	INCOME, FOOD, PARTY, HOBBY, SPORT, STUDY, HOME, GIFTS, HEALTH, APPS, CAR;
	
	@JsonCreator
	public static TransactionCategory from(String value) {
		return Arrays.stream(values())
			.filter(v -> v.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(value));
	}
	
	@JsonValue
	public String toJson() {
		return name().toLowerCase();
	}
}
