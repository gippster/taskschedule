package com.example.taskschedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskscheduleApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskscheduleApplication.class, args);
	}
}
