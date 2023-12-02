package br.com.pedro.api.config;

import br.com.pedro.api.domain.User;
import br.com.pedro.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void Startup(){
        User u1 = new User(null, "Pedro", "pedro@email.com", "123");
        User u2 = new User(null, "Luiz", "luiz@email.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}
