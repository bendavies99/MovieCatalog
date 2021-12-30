package net.bdavies.mc.setup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.mc.models.Director;
import net.bdavies.mc.models.Movie;
import net.bdavies.mc.models.MovieRating;
import net.bdavies.mc.repositories.DirectorRepository;
import net.bdavies.mc.repositories.MovieRatingRepository;
import net.bdavies.mc.repositories.MovieRepository;

@Slf4j
public abstract class MovieSetup
{
	@Autowired
	protected MovieRepository movieRepository;

	@Autowired
	protected DirectorRepository directorRepository;

	@Autowired
	protected MovieRatingRepository ratingRepository;

	protected Director firstDirector;
	protected Director secondDirector;

	@Before
	public final void init()
	{
		log.info("Running Movie Setup");
		movieRepository.deleteAll();
		directorRepository.deleteAll();
		firstDirector = new Director();
		firstDirector.setFirstName("Test");
		firstDirector.setLastName("Me");
		firstDirector.setEmail("test.me@test.com");
		directorRepository.save(firstDirector);

		secondDirector = new Director();
		secondDirector.setFirstName("Test2");
		secondDirector.setLastName("Me2");
		secondDirector.setEmail("test2.me2@test.com");
		directorRepository.save(secondDirector);
	}

	protected MovieRating createMovieRating(Movie movie, float value) {
		val mr = new MovieRating();
		mr.setComment("Awesome!");
		mr.setRatingValue(value);
		mr.setMovie(movie);
		ratingRepository.save(mr);
		return mr;
	}

	protected Movie createMovie(String title, Director director) {
		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setDirector(director);
		movieRepository.save(movie);
		return movie;
	}
}
