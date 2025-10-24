package expensetracker.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private String message;
	private LocalDateTime timestamp; 
	private int status; 
	
	public ErrorResponse(String message, int status) {
		this.message = message; 
		this.status = status;
		this.timestamp = LocalDateTime.now(); 
	}
}
