package com.example.api.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private long id;
    private String eventTitle;
    private String eventDescription;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventGallery;
    private UserDTO organizer;
    private List<CommentDTO> comments;
    private List<UserDTO> participants;
}
