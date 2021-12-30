package net.bdavies.mc.models;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Model Definition of the Movie Object it has a director and movie ratings
 *
 * @see Director
 * @see MovieRating
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

	@OneToMany(mappedBy = "movie")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ToString.Exclude
	private List<MovieRating> ratings;
}
