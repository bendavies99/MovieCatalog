package net.bdavies.mc.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	@Override
	@RestResource(exported = false)
	Optional<MovieRating> findById(Integer id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	@Override
	@RestResource(exported = false)
	Iterable<MovieRating> findAll();

	/**
	 * Returns all instances of the type {@code T} with the given IDs.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 *
	 * @param ids must not be {@literal null} nor contain any {@literal null} values.
	 * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
	 *         {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
	 */
	@Override
	@RestResource(exported = false)
	Iterable<MovieRating> findAllById(Iterable<Integer> ids);
}
