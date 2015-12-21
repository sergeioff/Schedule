package schedule.models.forms;

import javax.validation.constraints.NotNull;

public class TeacherScheduleSelectForm {
    @NotNull
    private Long selectedTeacher;

    @NotNull
    private Long selectedWeek;

    public TeacherScheduleSelectForm() { }

    public TeacherScheduleSelectForm(Long selectedTeacher, Long selectedWeek) {
        this.selectedTeacher = selectedTeacher;
        this.selectedWeek = selectedWeek;
    }

    public Long getSelectedTeacher() {
        return selectedTeacher;
    }

    public void setSelectedTeacher(Long selectedTeacher) {
        this.selectedTeacher = selectedTeacher;
    }

    public Long getSelectedWeek() {
        return selectedWeek;
    }

    public void setSelectedWeek(Long selectedWeek) {
        this.selectedWeek = selectedWeek;
    }
}