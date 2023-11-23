package com.gl.epmservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class EpmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpmServiceApplication.class, args);
    }

}

@RestController
class EmpController {
    EmpRepo repo;

    public EmpController(EmpRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/emps")
    public List<Emp> all() {
        return repo.findAll();
    }

    @GetMapping("/emps/{id}")
    public Optional<Emp> one(@PathVariable long id) {
        return repo.findById(id);
    }
}

interface EmpRepo extends JpaRepository<Emp, Long> {
}

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
class Emp {
    @Id
    @GeneratedValue
    long id;
    String name;
}

@Configuration
class LoadBasicData {
    @Bean
    CommandLineRunner loadInitData(EmpRepo repo) {
        return args -> {
            List<Emp> emps = new ArrayList<>();
            emps.add(Emp.builder().name("Sam").build());
            emps.add(Emp.builder().name("Joy").build());
            emps.add(Emp.builder().name("Paul").build());
            emps.add(Emp.builder().name("Smith").build());
            emps.add(Emp.builder().name("Marry").build());
            emps.add(Emp.builder().name("Tim").build());

            repo.saveAll(emps);
        };
    }
}