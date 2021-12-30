package net.bdavies.mc.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.mc.models.Director;
import net.bdavies.mc.repositories.DirectorRepository;

/**
 * Director validator before it is created
 *
 * @see Validator
 * @see org.springframework.data.repository.CrudRepository#save(Object)
 *
 * @author ben.davies
 */
@Slf4j
@Component("beforeCreateDirectorValidator")
public class DirectorValidator implements Validator
{
	@Autowired
	DirectorRepository repository;

	/**
	 * Ensure this validator runs for the Director class
	 *
	 * @param clazz the class being validated
	 * @return true if it is the {@link Director} class
	 */
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Director.class.isAssignableFrom(clazz);
	}

	/**
	 * Ensure that the data for the director is valid
	 *
	 * Rules:
	 *  firstName: required
	 *  lastName: required
	 *  email: required & unique
	 *
	 * @param target the new {@link Director}
	 * @param errors the errors object to update if the validation fails
	 */
	@Override
	public void validate(Object target, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
		val director = (Director)target;
		if (repository.findByEmail(director.getEmail()) != null) {
			errors.rejectValue("email", "email.nonUnique", "Email already exists for director");
		}
	}
}
