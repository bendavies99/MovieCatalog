package net.bdavies.mc.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.mc.models.Director;
import net.bdavies.mc.models.Movie;
import net.bdavies.mc.models.MovieRating;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:testing-integration.properties")
public class MovieRatingRepositoryTest
{

	@Autowired
	private MovieRatingRepository movieRatingRepository;

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_movie_empty_and_exception_is_thrown() {
		val mr = new MovieRating();
		mr.setComment("Test");
		mr.setRatingValue(3.5f);
		movieRatingRepository.save(mr);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_rating_empty_and_exception_is_thrown() {
		val director = new Director();
		director.setFirstName("d");
		director.setLastName("sd");
		director.setEmail("sd@csc.c");
		directorRepository.save(director);

		val movie = new Movie();
		movie.setTitle("Test2");
		movie.setDirector(director);
		movieRepository.save(movie);

		val mr = new MovieRating();
		mr.setComment("Test");
		mr.setMovie(movie);
		movieRatingRepository.save(mr);
	}

}
