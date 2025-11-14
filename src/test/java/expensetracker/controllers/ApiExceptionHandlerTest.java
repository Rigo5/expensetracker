package expensetracker.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import expensetracker.models.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    @DisplayName("DataIntegrityViolationException should return bad request response")
    void handlerDataIntegrityShouldReturnBadRequest() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Duplicate email");

        var response = handler.handlerDataIntegrity(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        assertThat(((ErrorResponse) response.getBody()).getMessage()).isEqualTo("Duplicate email");
    }

    @Test
    @DisplayName("ConstraintViolationException should return bad request response")
    void handlerConstraintViolationShouldReturnBadRequest() {
        ConstraintViolationException exception = new ConstraintViolationException("Invalid amount", null);

        var response = handler.handlerConstraintViolation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        assertThat(((ErrorResponse) response.getBody()).getMessage()).isEqualTo("Invalid amount");
    }

    @Test
    @DisplayName("Generic exception should return internal server error response with generic message")
    void handlerGeneralExceptionShouldReturnInternalServerError() {
        Exception exception = new Exception("Unexpected failure");

        var response = handler.handlerGeneralException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isInstanceOf(ErrorResponse.class);
        assertThat(((ErrorResponse) response.getBody()).getMessage()).isEqualTo("Internal server error");
    }
}
