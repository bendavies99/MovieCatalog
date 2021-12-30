package net.bdavies.mc.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * A custom exception handler for the Spring Data REST Repositories
 * </p>
 * It will return a json object like:
 * {
 *   code: 400,
 *   message: "Something bad happened"
 * }
 * </p>
 * Another note is that all exceptions will be logged to the console too for logging monitoring services
 *
 * @see RepositoryRestExceptionHandler
 * @see ExceptionHandler
 *
 * @author ben.davies
 */
@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
@Slf4j
public class GenericExceptionHandler
{
	/**
	 * Handle the exception made by the routing handler
	 *
	 * @param e the exception to handle
	 * @return A response handle for spring boot to send back to the client
	 */
	@ExceptionHandler
	ResponseEntity<ExceptionObj> handle(Exception e) {
		log.error("Something went wrong", e);
		if (e instanceof RepositoryConstraintViolationException) {
			return handleValidationException((RepositoryConstraintViolationException) e);
		}
		return new ResponseEntity<>(new ExceptionObj(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle a validation exception and render a more read-able message to the user
	 *
	 * @param e the validation exception
	 * @return A response handle for spring boot to send back to the client
	 *
	 * @see RepositoryConstraintViolationException
	 */
	private ResponseEntity<ExceptionObj> handleValidationException(RepositoryConstraintViolationException e)
	{
		return new ResponseEntity<>(new ValidationExceptionObj(e.getErrors()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * JSON Exception POJO for jackson to turn into a string
	 *
	 * @see GenericExceptionHandler
	 * @author ben.davies
	 */
	@Data
	private static class ExceptionObj
	{
		private final int code = HttpStatus.BAD_REQUEST.value();
		private final String message;
	}

	/**
	 * JSON Validation Exception POJO for jackson to turn into a string
	 *
	 * @see ExceptionObj
	 * @see GenericExceptionHandler
	 * @author ben.davies
	 */
	@Getter
	private static class ValidationExceptionObj extends ExceptionObj
	{
		private final Map<String, String> errors;
		public ValidationExceptionObj(Errors errors) {
			super("Validation Failed");
			this.errors = new HashMap<>();
			errors.getFieldErrors().forEach(fe ->
					this.errors.put(fe.getField(), "RejectedValue: " + fe.getRejectedValue() + " Message: " + fe.getDefaultMessage()));
		}
	}
}
