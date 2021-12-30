package net.bdavies.mc.exceptions;

/**
 * An Exception class for when trying to a delete a {@link net.bdavies.mc.models.Director}
 *
 * @author ben.davies
 */
public class DirectorHasDirectedMoviesException extends RuntimeException
{
	/**
	 * Override the default message and output a fixed message for the exception handler
	 *
	 * @return a message to inform the user that the director cannot be deleted
	 *
	 * @see GenericExceptionHandler
	 */
	@Override
	public String getMessage()
	{
		return "Cannot delete director due to having movies already directed";
	}
}
