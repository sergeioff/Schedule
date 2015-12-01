package schedule.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Entity
@Table(name = "Buzzers")
public class BuzzerOnPair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer pairNumber;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public BuzzerOnPair() {
        this.pairNumber = 1;
    }

    public BuzzerOnPair(Integer pairNumber, LocalTime startTime, LocalTime endTime) {
        this.pairNumber = pairNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPairNumber() {
        return pairNumber;
    }

    public void setPairNumber(Integer pairNumber) {
        this.pairNumber = pairNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
