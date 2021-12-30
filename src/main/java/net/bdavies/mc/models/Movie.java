package net.bdavies.mc.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model Definition of the Movie Object it has a director and movie ratings
 *
 * @see Director
 * @author ben.davies
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
@Data
public class Movie
{
	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = "director_id")
	private Director director;
}
