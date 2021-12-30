package net.bdavies.mc.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.val;
import net.bdavies.mc.models.Movie;
import net.bdavies.mc.repositories.DirectorRepository;

/**
 * Movie validator before it is created
 *
 * @see Validator
 * @see org.springframework.data.repository.CrudRepository#save(Object)
 *
 * @author ben.davies
 */
@Component("beforeCreateMovieValidator")
public class MovieValidator implements Validator
{
	@Autowired
	DirectorRepository repository;

	/**
	 * Ensure this validator runs for the Movie class
	 *
	 * @param clazz the class being validated
	 * @return true if it is the {@link Movie} class
	 */
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Movie.class.isAssignableFrom(clazz);
	}

	/**
	 * Ensure that the data for the director is valid
	 *
	 * Rules:
	 *  title: required
	 *  director: required
	 *
	 * @param target the new {@link Movie}
	 * @param errors the errors object to update if the validation fails
	 */
	@Override
	public void validate(Object target, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty");
		val m = (Movie)target;
		if (m.getDirector() == null) {
			errors.rejectValue("director", "director.nonExistent", "Director doesn't exist");
		}
	}
}
