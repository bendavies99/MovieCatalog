package net.bdavies.mc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import net.bdavies.mc.models.MovieRating;

/**
 * The REST Repository for the MovieRating model
 *
 * @see MovieRating
 * @author ben.davies
 */
@RepositoryRestResource
public interface MovieRatingRepository extends CrudRepository<MovieRating, Integer>
{
}
