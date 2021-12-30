package net.bdavies.mc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
	/**
	 * Find by a director's name
	 *
	 * @param firstName The first name of the director
	 * @param lastName The last name of the director
	 * @return a list of movies that the director(s) has directed
	 *
	 * @see net.bdavies.mc.models.Director
	 */
	@Query("SELECT m FROM Movie m INNER JOIN Director d on m.director=d.id WHERE d.firstName = :firstName AND d.lastName = :lastName")
	List<Movie> findByDirectorName(@Param("firstName") String firstName, @Param("lastName") String lastName);

	/**
	 * Find movies from a director's email
	 *
	 * @param email The email of the director
	 * @return a list of movies that the director has directed
	 *
	 * @see net.bdavies.mc.models.Director
	 */
	@Query("SELECT m FROM Movie m INNER JOIN Director d on m.director=d.id WHERE d.email = :email")
	List<Movie> findByDirectorEmail(@Param("email") String email);

	/**
	 * Find movies from a director's id
	 *
	 * @param id The id of the director
	 * @return a list of movies that the director has directed
	 *
	 * @see net.bdavies.mc.models.Director
	 */
	@Query("SELECT m FROM Movie m INNER JOIN Director d on m.director=d.id WHERE d.id = :id")
	List<Movie> findByDirectorId(@Param("id") int id);

	/**
	 * Find Movies that are above a specified rating which is the average of all rating matching for that one movie
	 *
	 * @param amount the amount which can be a floating point number e.g. 3.5
	 * @return a list of movies above the specified rating
	 *
	 * @see net.bdavies.mc.models.MovieRating
	 */
	@Query(value = "select m.* from movies m inner join movieratings mr ON mr.movie_id=m.id group by m.id having avg(mr.rating_value) > ?1",
			nativeQuery = true)
	List<Movie> findIfRatingIsAbove(@Param("amount") float amount);
}
