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
import net.bdavies.mc.models.Director;
import net.bdavies.mc.repositories.DirectorRepository;
import net.bdavies.mc.setup.MovieSetup;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:testing-integration.properties")
public class DirectorTests
{
	@Autowired
	WebApplicationContext context;

	@Autowired
	DirectorRepository directorRepository;

	private MockMvc mvc;

	@Before
	public void setUp()
	{
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	private Director createDirector(String name) {
		val tokens = name.split(" ");
		val d = new Director();
		d.setFirstName(tokens[0]);
		d.setLastName(tokens[1]);
		d.setEmail(tokens[0] + "." + tokens[1] + "@test.com");
		directorRepository.save(d);
		return d;
	}

	@SneakyThrows
	@Test
	public void get_all_directors()
	{
		createDirector("Test Me");
		createDirector("Test Me2");
		val uri = URI.create("/directors");
		val result = mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(jsonPath("$._embedded.directors", Matchers.hasSize(2)))
				.andExpect(jsonPath("$._embedded.directors[0].firstName", Matchers.is("Test")))
				.andExpect(jsonPath("$._embedded.directors[0].lastName", Matchers.is("Me")))
				.andExpect(jsonPath("$._embedded.directors[0].email", Matchers.is("Test.Me@test.com")))
				.andReturn();
	}

	@SneakyThrows
	@Test
	public void get_a_director()
	{
		val director = createDirector("Test Me");
		createDirector("Test Me2");
		val uri = URI.create("/directors/" + director.getId());
		val result = mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(jsonPath("$.firstName", Matchers.is("Test")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Me")))
				.andExpect(jsonPath("$.email", Matchers.is("Test.Me@test.com")))
				.andReturn();
	}

	@SneakyThrows
	@Test
	public void create_a_director()
	{
		Map<String, String> director = new HashMap<>();
		director.put("firstName", "Test");
		director.put("lastName", "Me");
		director.put("email", "Test.Me@test.com");
		val uri = URI.create("/directors");
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(post(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(director)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.firstName", Matchers.is("Test")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Me")))
				.andExpect(jsonPath("$.email", Matchers.is("Test.Me@test.com")))
				.andReturn();
	}

	@SneakyThrows
	@Test
	public void update_a_director()
	{
		val originalDirector = createDirector("Test Mee");
		Map<String, String> director = new HashMap<>();
		director.put("lastName", "Me");
		val uri = URI.create("/directors/" + originalDirector.getId());
		val objectMapper = new ObjectMapper();
		val result = mvc.perform(patch(uri)
				.contentType("application/json")
				.accept("application/json")
				.characterEncoding(StandardCharsets.UTF_8)
				.content(objectMapper.writeValueAsString(director)))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(jsonPath("$.firstName", Matchers.is("Test")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Me")))
				.andExpect(jsonPath("$.email", Matchers.is("Test.Mee@test.com")))
				.andReturn();
	}

	@SneakyThrows
	@Test
	public void delete_a_director()
	{
		val originalDirector = createDirector("Test Me");
		val directorBefore = directorRepository.findById(originalDirector.getId()).orElse(null);
		assertNotNull(directorBefore);

		val uri = URI.create("/directors/" + originalDirector.getId());
		mvc.perform(delete(uri))
				.andExpect(status().isNoContent());

		val director = directorRepository.findById(originalDirector.getId()).orElse(null);
		assertNull(director);
	}
}
