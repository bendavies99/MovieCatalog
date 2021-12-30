package net.bdavies.mc;

import java.util.Arrays;
import java.util.Random;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import lombok.val;
import net.bdavies.mc.models.Director;
import net.bdavies.mc.models.Movie;
import net.bdavies.mc.models.MovieRating;
import net.bdavies.mc.repositories.DirectorRepository;
import net.bdavies.mc.repositories.MovieRatingRepository;
import net.bdavies.mc.repositories.MovieRepository;

/**
 * Main Class of the Application this is where Spring will boostrap and
 * setup all of the internals
 *
 * @author ben.davies
 */
@SpringBootApplication
public class MovieCatalogApplication
{

	/**
	 * Main Function of the application
	 *
	 * @param args The command line arguments supplied from the user
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(MovieCatalogApplication.class, args);
	}

	/**
	 * Generate some sample data when running in dev mode
	 *
	 * @param movieRepository       the movie repository
	 * @param directorRepository    the director repository
	 * @param movieRatingRepository the move rating repository
	 * @return a application runner
	 */
	@Bean
	@Profile("dev")
	ApplicationRunner init(MovieRepository movieRepository, DirectorRepository directorRepository,
			MovieRatingRepository movieRatingRepository)
	{
		return args -> {
			String[] data = new String[] { "Movie One", "Movie Two", "Movie Three" };
			Director johnDoe = new Director();
			johnDoe.setFirstName("John");
			johnDoe.setLastName("Doe");
			johnDoe.setEmail("john.doe@example.com");
			directorRepository.save(johnDoe);
			val random = new Random();
			Arrays.stream(data).forEach(d -> {
				val movie = new Movie();
				movie.setTitle(d);
				movie.setDirector(johnDoe);
				movieRepository.save(movie);
				for (int i = 0; i < random.nextInt(8) + 2; i++)
				{
					val movieRating = new MovieRating();
					movieRating.setMovie(movie);
					movieRating.setComment("Test");
					movieRating.setRatingValue((float) (Math.round(random.nextFloat() * 3) + 2));
					movieRatingRepository.save(movieRating);
				}
			});
		};
	}
}
