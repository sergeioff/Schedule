package jschedule.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Group currentGroup;

    @NotNull
    @Min(0)
    private Long subGroup;

    @NotNull
    @Min(1)
    private Long week;

    @NotNull
    @Min(1)
    private Long day;

    @NotNull
    @Min(1)
    private Long numberInDay;

    @NotNull
    @ManyToOne
    private Subject subject;

    @NotNull
    @ManyToOne
    private TypeOfPair type;

    @NotNull
    @Size(min = 1, max = 20)
    private String room;

    public Pair() { }

    public Pair(Group currentGroup, Long subGroup, Long week, Long day, Long numberInDay, Subject subject, TypeOfPair type, String room) {
        this.currentGroup = currentGroup;
        this.subGroup = subGroup;
        this.week = week;
        this.day = day;
        this.numberInDay = numberInDay;
        this.subject = subject;
        this.type = type;
        this.room = room;
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

    public Long getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(Long subGroup) {
        this.subGroup = subGroup;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getNumberInDay() {
        return numberInDay;
    }

    public void setNumberInDay(Long numberInDay) {
        this.numberInDay = numberInDay;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public TypeOfPair getType() {
        return type;
    }

    public void setType(TypeOfPair type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
