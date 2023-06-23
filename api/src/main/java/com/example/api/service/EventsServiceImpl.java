package com.example.api.service;

import com.example.api.model.Comments;
import com.example.api.model.Events;
import com.example.api.model.Users;
import com.example.api.repository.EventsRepository;
import com.example.api.repository.UsersRepository;

import lombok.AllArgsConstructor;
import com.example.api.DTO.CommentDTO;

//import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
//import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import com.example.api.DTO.EventDTO;
import com.example.api.DTO.UserDTO;
import com.example.api.repository.CommentsRepository;

@Service
@AllArgsConstructor
public class EventsServiceImpl implements EventsService {
    
    private EventsRepository eventsRepository;
    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;
    private ModelMapper modelMapper;

    @Override
    public EventDTO createEvent(Long userId, EventDTO eventDTO) {
        Users organizer = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Events event = modelMapper.map(eventDTO, Events.class);
        event.setOrganizer(organizer);
        List<Users> participants = new ArrayList<>();
        participants.add(organizer);
        event.setParticipants(participants);
        Events savedEvent = eventsRepository.save(event);
        return modelMapper.map(savedEvent, EventDTO.class);
    }

    @Override
    public EventDTO addUserToEvent(Long userId, Long eventId) {
        Events event = eventsRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(event.getParticipants().contains(user)){
            throw new RuntimeException("User already in event");
        }
        event.getParticipants().add(user);
        Events updatedEvent = eventsRepository.save(event);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    @Override
    public EventDTO removeUserFromEvent(Long userId, Long eventId){
        Events event = eventsRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        event.getParticipants().remove(user);
        Events updatedEvent = eventsRepository.save(event);
        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    @Override
    public EventDTO getEventById(long id) {
        Optional<Events> optionalEvents = eventsRepository.findById(id);
        if (optionalEvents.isPresent()) {
            return modelMapper.map(optionalEvents.get(), EventDTO.class);
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public List<EventDTO> GetAllEvents() {
        List<Events> events = eventsRepository.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Events event : events) {
            eventDTOs.add(modelMapper.map(event, EventDTO.class));
        }
        return eventDTOs;
    }

    @Override
    public EventDTO addComment(Long eventId, Long userId, String comment) {
        Events event = eventsRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText(comment);
        commentDTO.setAuthor(modelMapper.map(user, UserDTO.class));
        commentDTO.setDateTime(java.time.LocalDateTime.now());

        Comments newComment = modelMapper.map(commentDTO, Comments.class);
        newComment.setEvent(event);  // set the event in the comment
        newComment.setAuthor(user);  // set the user in the comment
        
        Comments savedComment = commentsRepository.save(newComment); // save the comment 

        if (event.getComments() == null) {
            event.setComments(new ArrayList<>());
        }
        
        event.getComments().add(savedComment); // add the saved comment to the event

        Events updatedEvent = eventsRepository.save(event); // save the event
        return modelMapper.map(updatedEvent, EventDTO.class);
    }


    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Events existingEvent = eventsRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));

        existingEvent.setEventDate(null != eventDTO.getEventDate() ? eventDTO.getEventDate() : existingEvent.getEventDate());
        existingEvent.setEventTitle(null != eventDTO.getEventTitle() ? eventDTO.getEventTitle() : existingEvent.getEventTitle());
        existingEvent.setEventDescription(null != eventDTO.getEventDescription() ? eventDTO.getEventDescription() : existingEvent.getEventDescription());
        existingEvent.setEventLocation(null != eventDTO.getEventLocation() ? eventDTO.getEventLocation() : existingEvent.getEventLocation());
        existingEvent.setEventTime(null != eventDTO.getEventTime() ? eventDTO.getEventTime() : existingEvent.getEventTime());
        existingEvent.setEventGallery(null != eventDTO.getEventGallery() ? eventDTO.getEventGallery() : existingEvent.getEventGallery());
        
        Events updatedEvent = eventsRepository.save(existingEvent);

        return modelMapper.map(updatedEvent, EventDTO.class);
    }

    @Override
    public void deleteEvent(long id) {
        eventsRepository.deleteById(id);
    }
}
