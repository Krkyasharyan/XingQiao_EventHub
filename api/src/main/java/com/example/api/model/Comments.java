package com.example.api.model;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", length = 1000)
    private String text;

    @Column(name = "comment_date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private Events event;

    @ManyToOne
    private Users author;
}
