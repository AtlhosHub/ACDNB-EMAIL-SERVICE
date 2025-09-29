package school.sptech.acdnbemailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AcdnbEmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcdnbEmailServiceApplication.class, args);
    }

}
