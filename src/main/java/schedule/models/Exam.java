package schedule.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Group currentGroup;

    @NotNull
    @ManyToOne
    private Subject subject;

    @NotNull
    @Size(min = 1, max = 20)
    private String room;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    public Exam() { }

    public Exam(Group currentGroup, Subject subject, String room, LocalDateTime dateTime) {
        this.currentGroup = currentGroup;
        this.subject = subject;
        this.room = room;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormattedTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFormattedDate() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
