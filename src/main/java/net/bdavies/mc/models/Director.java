package net.bdavies.mc.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Director
{
	@Id
	@GeneratedValue
	private int id;

	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
}
