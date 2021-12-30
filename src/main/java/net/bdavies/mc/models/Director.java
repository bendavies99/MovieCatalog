package net.bdavies.mc.models;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bdavies.mc.exceptions.DirectorHasDirectedMoviesException;

/**
 * Model Definition of the Director Object they will have multiple movies tied to them
 *
 * @see Movie
 * @author ben.davies
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Director
{
	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;

	@OneToMany(mappedBy = "director")
	@ToString.Exclude
	private List<Movie> moviesDirected;

	/**
	 * Run before a entity is deleted and persisted and
	 * ensure that the director is not used in any movies within the DB
	 *
	 * @throws DirectorHasDirectedMoviesException if the director has movies
	 */
	@PreRemove
	private void preRemove() {
		if (moviesDirected != null && !moviesDirected.isEmpty()) {
			throw new DirectorHasDirectedMoviesException();
		}
	}
}
