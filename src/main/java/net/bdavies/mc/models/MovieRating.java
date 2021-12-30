package net.bdavies.mc.models;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model definition for the MovieRating Object this ties closely to a movie model
 *
 * @author ben.davies
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "movieratings")
public class MovieRating
{
	@Id
	@GeneratedValue
	private long id;
	private float ratingValue;
	private String comment;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
}
