package jschedule.models.forms;

import jschedule.models.domain.Teacher;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeacherForm {
    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher toTeacher() {
        return new Teacher(name);
    }
}
