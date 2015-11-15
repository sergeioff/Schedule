package jschedule.models.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectForm {
    @NotNull
    @Size(min = 3, max = 40)
    private String name;

    @NotNull
    private Long teacherId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
