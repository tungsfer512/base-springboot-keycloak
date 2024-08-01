package vn.base.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import vn.base.app.utils.CustomLogger;

@Configuration
public class PostConstructCode {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void starterCode01() {
        CustomLogger.info("Post Construct");
    }
}
