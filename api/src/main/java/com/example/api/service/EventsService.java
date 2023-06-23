package com.example.api.service;

import java.util.List;
import com.example.api.DTO.EventDTO;

public interface EventsService {
    EventDTO createEvent(Long userId, EventDTO eventDTO);

    EventDTO addUserToEvent(Long userId, Long eventId);

    EventDTO getEventById(long id);

    List<EventDTO> GetAllEvents();

    EventDTO updateEvent(Long id, EventDTO eventDTO);

    void deleteEvent(long id);
    
    EventDTO addComment(Long eventId, Long userId, String comment);

    EventDTO removeUserFromEvent(Long userId, Long eventId);
}
