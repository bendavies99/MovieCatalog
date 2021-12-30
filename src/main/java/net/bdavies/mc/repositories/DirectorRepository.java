package net.bdavies.mc.repositories;

import org.springframework.data.repository.CrudRepository;
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
}
