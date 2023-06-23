package com.example.api.controller;

import com.example.api.service.EventsService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api.DTO.EventDTO;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/events")

@CrossOrigin(origins = "http://localhost:3000")
public class EventsController {

    private EventsService eventsService;

    @PostMapping("/create/{userId}")
    public EventDTO createEvent(@PathVariable Long userId, @RequestBody EventDTO eventDTO) {
        return eventsService.createEvent(userId, eventDTO);
    }

    @PostMapping("/addUser/{eventId}/{userId}")
    public EventDTO addUserToEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        return eventsService.addUserToEvent(userId, eventId);
    }

    @DeleteMapping("/removeUser/{eventId}/{userId}")
    public EventDTO removeUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        return eventsService.removeUserFromEvent(userId, eventId);
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEventss() {
        List<EventDTO> eventsDTO = eventsService.GetAllEvents();
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventsById(@PathVariable("id") long id) {
        EventDTO eventDTO = eventsService.getEventById(id);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvents(@PathVariable("id") long id, @RequestBody EventDTO eventDTO) {
        EventDTO updatedEventDTO = eventsService.updateEvent(id, eventDTO);
        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }
    @PutMapping("/addComment/{eventId}/{userId}")
    public ResponseEntity<EventDTO> addComment(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId, @RequestBody String comment) {
        EventDTO updatedEventDTO = eventsService.addComment(eventId, userId, comment);
        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvents(@PathVariable("id") long id) {
        eventsService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
