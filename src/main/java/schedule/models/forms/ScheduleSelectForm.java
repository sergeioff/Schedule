package schedule.models.forms;

import javax.validation.constraints.NotNull;

public class ScheduleSelectForm {
    @NotNull
    private Long selectedGroup;

    @NotNull
    private Long selectedSubgroup;

    @NotNull
    private Long selectedWeek;

    public ScheduleSelectForm() { }

    public ScheduleSelectForm(Long selectedGroup, Long selectedSubgroup, Long selectedWeek) {
        this.selectedGroup = selectedGroup;
        this.selectedSubgroup = selectedSubgroup;
        this.selectedWeek = selectedWeek;
    }

    public Long getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Long selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Long getSelectedSubgroup() {
        return selectedSubgroup;
    }

    public void setSelectedSubgroup(Long selectedSubgroup) {
        this.selectedSubgroup = selectedSubgroup;
    }

    public Long getSelectedWeek() {
        return selectedWeek;
    }

    public void setSelectedWeek(Long selectedWeek) {
        this.selectedWeek = selectedWeek;
    }
}
