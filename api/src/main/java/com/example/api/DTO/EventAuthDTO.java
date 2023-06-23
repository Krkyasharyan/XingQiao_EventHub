package com.example.api.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventAuthDTO {
    private long id;
    private String eventTitle;
    private String eventDescription;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventGallery;
}
