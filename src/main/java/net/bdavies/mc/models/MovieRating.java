package net.bdavies.mc.models;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movieratings")
public class MovieRating
{
	@Id
	@GeneratedValue
	private int id;
	private float ratingValue;
	private String comment;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
}
