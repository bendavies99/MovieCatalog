package net.bdavies.mc.models;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@OneToMany
	@ToString.Exclude
	private List<Movie> moviesDirected;

	@PreRemove
	private void preRemove() {
		if (!moviesDirected.isEmpty()) {
			throw new RuntimeException();
		}
	}
}
