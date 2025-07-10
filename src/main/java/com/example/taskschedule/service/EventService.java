package com.example.taskschedule.service;

import com.example.taskschedule.model.Event;
import com.example.taskschedule.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JavaMailSender emailSender;

    public List<Event> getAllEvents() {
        return eventRepository.findByOrderByStartTimeAsc();
    }

    public Event addEvent(Event event) {
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    public Event updateEvent(Long id, Event event) {
        event.setId(id);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 9 * * ?") // Запуск каждый день в 9 утра
    public void sendScheduledEventNotifications() {
        System.out.println("Запуск метода");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);

        // Используем правильные временные рамки для поиска событий в ближайшие 24 часа
        List<Event> events = eventRepository.findByStartTimeBetween(now, tomorrow);
        System.out.println("Найдено событий: " + events.size());
        for (Event event : events) {
            sendEventNotification(event);
        }
    }

    private void sendEventNotification(Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Напоминание о предстоящем событии");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = event.getStartTime().format(formatter);

        message.setText("Напоминание: У вас запланировано событие: " + event.getName() + " на " + formattedDateTime);
        emailSender.send(message);
    }
}


