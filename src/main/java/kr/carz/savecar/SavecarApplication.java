package kr.carz.savecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
public class SavecarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavecarApplication.class, args);
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();

		characterEncodingFilter.setEncoding("UTF-8");

		characterEncodingFilter.setForceEncoding(true);

		return characterEncodingFilter;

	}

}
