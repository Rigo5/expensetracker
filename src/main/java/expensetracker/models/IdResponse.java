package expensetracker.models;

import jakarta.validation.constraints.NotNull;

public record IdResponse(
		@NotNull Long id
		) 
{}
