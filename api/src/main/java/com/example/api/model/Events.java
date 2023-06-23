package com.example.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")

public class Events {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "event_title")
    private String eventTitle;

    @Column(name = "event_description", length = 2000)
    private String eventDescription;

    @Column(name = "event_date")
    private String eventDate;

    @Column(name = "event_time")
    private String eventTime;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "event_gallary", length = 1000)
    private String eventGallery;

    @ManyToOne
    private Users organizer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> participants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Comments> comments;
}
