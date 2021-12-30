package net.bdavies.mc.e2e;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.bdavies.mc.setup.MovieSetup;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:testing-integration.properties")
public class MovieRatingTests extends MovieSetup
{
	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp()
	{
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@SneakyThrows
	@Test
	public void create_a_movie_rating()
	{
		val movie = createMovie("Test Movie", firstDirector);
		Map<String, String> movieRating = new HashMap<>();
		movieRating.put("comment", "Test Movie is good");
		movieRating.put("ratingValue", "3.5");
		movieRating.put("movie", "/movies/" + movie.getId());
		val uri = URI.create("/movieRatings");
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(post(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(movieRating)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.ratingValue", Matchers.is(3.5))).andReturn();
	}

	@SneakyThrows
	@Test
	public void update_a_movie_rating()
	{
		val originalMovie = createMovie("Test Movie Non-Updated", firstDirector);
		val originalMovieRating = createMovieRating(originalMovie, 3.5f);

		Map<String, String> movieRating = new HashMap<>();
		movieRating.put("ratingValue", "4.5");
		val uri = URI.create("/movieRatings/" + originalMovieRating.getId());
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(patch(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(movieRating)))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.ratingValue", Matchers.is(4.5))).andReturn();
	}

	@SneakyThrows
	@Test
	public void delete_a_movie_rating()
	{
		val originalMovie = createMovie("Test Movie", firstDirector);
		val originalMovieRating = createMovieRating(originalMovie, 3.5f);
		val movieRatingBefore = ratingRepository.findById(originalMovieRating.getId()).orElse(null);
		assertNotNull(movieRatingBefore);

		val uri = URI.create("/movieRatings/" + originalMovieRating.getId());
		mvc.perform(delete(uri))
				.andExpect(status().isNoContent());

		val movieRating = ratingRepository.findById(originalMovieRating.getId()).orElse(null);
		assertNull(movieRating);
	}
}
