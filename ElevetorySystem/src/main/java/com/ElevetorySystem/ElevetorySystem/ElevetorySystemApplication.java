package com.ElevetorySystem.ElevetorySystem;

import com.ElevetorySystem.ElevetorySystem.dto.Request;
import com.ElevetorySystem.ElevetorySystem.enums.Direction;
import com.ElevetorySystem.ElevetorySystem.service.ElevatorService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ElevetorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElevetorySystemApplication.class, args);
    }

    @Bean
    ApplicationRunner demoRunner(ElevatorService service) {
        return args -> {
            System.out.println("--- Elevator System Booted ---");

            // Hallway requests
            service.handleHallCall(new Request(5, Direction.UP));
            service.handleHallCall(new Request(2, Direction.DOWN));

            // Delayed part alag thread me, taaki startup block na ho
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    // Elevator 1 me baitha banda floor 10 dabata hai
                    service.selectFloor(1, 10);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
            t.setDaemon(true);
            t.start();
        };
    }
}