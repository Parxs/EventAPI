package at.ac.uibk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "at.ac.uibk" })
public class EventApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventApiApplication.class, args);
	}
}
