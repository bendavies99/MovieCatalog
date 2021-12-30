package net.bdavies.mc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Class of the Application this is where Spring will boostrap and
 * setup all of the internals
 *
 * @author ben.davies
 */
@SpringBootApplication
public class MovieCatalogApplication {

	/**
	 * Main Function of the application
	 *
	 * @param args The command line arguments supplied from the user
	 */
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
	}

}
