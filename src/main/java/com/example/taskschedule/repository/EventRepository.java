package com.example.taskschedule.repository;

import com.example.taskschedule.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrderByStartTimeAsc();
    List<Event> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
