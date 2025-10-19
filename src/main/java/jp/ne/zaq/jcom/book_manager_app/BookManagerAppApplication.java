package jp.ne.zaq.jcom.book_manager_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookManagerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagerAppApplication.class, args);
	}

}
