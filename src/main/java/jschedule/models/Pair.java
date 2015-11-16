package jschedule.models;

import javax.persistence.*;
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
    @Size(min = 1)
    private Integer subGroup;

    @NotNull
    @Size(min = 1)
    private Integer week;

    @NotNull
    @Size(min = 1)
    private Integer day;

    @NotNull
    @Size(min = 1)
    private Integer numInDay;

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

    public Pair(Group currentGroup, Integer subGroup, Integer week, Integer day, Integer numInDay, Subject subject, TypeOfPair type, String room) {
        this.currentGroup = currentGroup;
        this.subGroup = subGroup;
        this.week = week;
        this.day = day;
        this.numInDay = numInDay;
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

    public Integer getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(Integer subGroup) {
        this.subGroup = subGroup;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getNumInDay() {
        return numInDay;
    }

    public void setNumInDay(Integer numInDay) {
        this.numInDay = numInDay;
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
