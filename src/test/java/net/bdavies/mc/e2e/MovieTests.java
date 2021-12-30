package net.bdavies.mc.e2e;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
public class MovieTests extends MovieSetup
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
	public void get_all_movies()
	{
		createMovie("Test Movie", firstDirector);
		createMovie("Test Movie2", secondDirector);
		val uri = URI.create("/movies");
		val result = mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(jsonPath("$._embedded.movies", Matchers.hasSize(2)))
				.andExpect(jsonPath("$._embedded.movies[0].title", Matchers.is("Test Movie"))).andReturn();
	}

	@SneakyThrows
	@Test
	public void get_a_movie()
	{
		val movie = createMovie("Test Movie", firstDirector);
		createMovie("Test Movie2", secondDirector);
		val uri = URI.create("/movies/" + movie.getId());
		val result = mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(jsonPath("$.title", Matchers.is("Test Movie"))).andReturn();
	}

	@SneakyThrows
	@Test
	public void create_a_movie()
	{
		Map<String, String> movie = new HashMap<>();
		movie.put("title", "Test Movie");
		movie.put("director", "/directors/" + firstDirector.getId());
		val uri = URI.create("/movies");
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(post(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(movie)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.title", Matchers.is("Test Movie"))).andReturn();
	}

	@SneakyThrows
	@Test
	public void update_a_movie()
	{
		val originalMovie = createMovie("Test Movie Non-Updated", firstDirector);
		Map<String, String> movie = new HashMap<>();
		movie.put("title", "Test Movie");
		val uri = URI.create("/movies/" + originalMovie.getId());
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(patch(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(movie)))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.title", Matchers.is("Test Movie"))).andReturn();
	}

	@SneakyThrows
	@Test
	public void delete_a_movie()
	{
		val originalMovie = createMovie("Test Movie", firstDirector);
		val movieBefore = movieRepository.findById(originalMovie.getId()).orElse(null);
		assertNotNull(movieBefore);

		val uri = URI.create("/movies/" + originalMovie.getId());
		mvc.perform(delete(uri))
				.andExpect(status().isNoContent());

		val movie = movieRepository.findById(originalMovie.getId()).orElse(null);
		assertNull(movie);
	}
}
