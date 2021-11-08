package project.movie4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Movie4youApplication {

	public static void main(String[] args) {
		SpringApplication.run(Movie4youApplication.class, args);
	}

}
