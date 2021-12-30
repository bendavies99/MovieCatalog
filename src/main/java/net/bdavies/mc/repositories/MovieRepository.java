package net.bdavies.mc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import net.bdavies.mc.models.Movie;

/**
 * The REST Repository for the Movie model
 *
 * @see Movie
 * @author ben.davies
 */
@RepositoryRestResource
public interface MovieRepository extends CrudRepository<Movie, Integer>
{
}
