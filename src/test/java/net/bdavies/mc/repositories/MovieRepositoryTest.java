package net.bdavies.mc.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.mc.models.Movie;
import net.bdavies.mc.setup.MovieSetup;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:testing-integration.properties")
public class MovieRepositoryTest extends MovieSetup
{
	@SneakyThrows
	@Test
	public void can_search_movies_by_director_id()
	{
		val mov1 = createMovie("TestMovieOne", firstDirector);
		val mov2 = createMovie("TestMovieTwo", firstDirector);
		val mov3 = createMovie("TestMovieThree", secondDirector);

		val movies = movieRepository.findByDirectorId(firstDirector.getId());
		assertThat(movies).asList().hasSize(2);
		assertThat(movies).asList().contains(mov1);
		assertThat(movies).asList().contains(mov2);
		assertThat(movies).asList().doesNotContain(mov3);
	}

	@SneakyThrows
	@Test
	public void can_search_movies_by_director_email()
	{
		val mov1 = createMovie("TestMovieOne", firstDirector);
		val mov2 = createMovie("TestMovieTwo", firstDirector);
		val mov3 = createMovie("TestMovieThree", secondDirector);

		val movies = movieRepository.findByDirectorEmail(firstDirector.getEmail());
		assertThat(movies).asList().hasSize(2);
		assertThat(movies).asList().contains(mov1);
		assertThat(movies).asList().contains(mov2);
		assertThat(movies).asList().doesNotContain(mov3);
	}

	@Test
	public void can_search_for_ratings_above() {
		val movie = createMovie("GoodMovie", firstDirector);
		createMovieRating(movie, 4.0f);
		createMovieRating(movie, 4.1f);
		createMovieRating(movie, 4.2f); //Rating of 4.1 average

		val movies = movieRepository.findIfRatingIsAbove(4.0f);
		assertThat(movies).asList().hasSize(1);
		assertThat(movies).asList().contains(movie);

		val moviesTwo = movieRepository.findIfRatingIsAbove(4.2f);
		assertThat(moviesTwo).asList().hasSize(0);

		val moviesThree = movieRepository.findIfRatingIsAbove(4.1f);
		assertThat(moviesThree).asList().hasSize(0);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_title_empty_and_exception_is_thrown() {
		val movie = new Movie();
		movie.setDirector(firstDirector);
		movieRepository.save(movie);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_director_empty_and_exception_is_thrown() {
		val movie = new Movie();
		movie.setTitle("Test");
		movieRepository.save(movie);
	}
}
