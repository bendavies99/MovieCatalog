package net.bdavies.mc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import net.bdavies.mc.models.Director;

/**
 * The REST Repository for the Director model
 *
 * @see Director
 * @author ben.davies
 */
@RepositoryRestResource
public interface DirectorRepository extends CrudRepository<Director, Integer>
{
	/**
	 * Find a Director by email
	 *
	 * @param email the email to check
	 * @return a director based on the email or null if not found
	 */
	Director findByEmail(@Param("email") String email);
}
