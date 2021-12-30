package net.bdavies.mc.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Before;
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
public class DirectorRepositoryTest
{

	@Autowired
	private DirectorRepository directorRepository;

	@SneakyThrows
	@Test
	public void can_search_director_by_email()
	{
		val directorOriginal = new Director();
		directorOriginal.setFirstName("John");
		directorOriginal.setLastName("Doe");
		directorOriginal.setEmail("john.doe@example.com");
		directorRepository.save(directorOriginal);

		val directorFound = directorRepository.findByEmail(directorOriginal.getEmail());
		assertThat(directorFound.getEmail()).isEqualTo(directorOriginal.getEmail());
		assertThat(directorFound.getId()).isEqualTo(directorOriginal.getId());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_first_name_empty_and_exception_is_thrown() {
		val director = new Director();
		director.setLastName("Test");
		director.setEmail("test@test.com");
		directorRepository.save(director);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_last_name_empty_and_exception_is_thrown() {
		val director = new Director();
		director.setFirstName("Test");
		director.setEmail("test@test.com");
		directorRepository.save(director);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_email_empty_and_exception_is_thrown() {
		val director = new Director();
		director.setFirstName("Test");
		director.setLastName("Test");
		directorRepository.save(director);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void validation_fails_when_email_not_unique_and_exception_is_thrown() {
		val director = new Director();
		director.setFirstName("Test");
		director.setLastName("Test");
		director.setEmail("Test@test.com");
		directorRepository.save(director);


		val badDirector = new Director();
		badDirector.setFirstName("New");
		badDirector.setLastName("Director");
		badDirector.setEmail("Test@test.com");
		directorRepository.save(badDirector);
		log.info("{}", directorRepository.findAll());
		assertThat(badDirector.getEmail()).isEqualTo(director.getEmail());
	}
}
