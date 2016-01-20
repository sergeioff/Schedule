package schedule.models.forms;

import javax.validation.constraints.NotNull;

public class ExamsScheduleSelectForm {
    @NotNull
    private Long selectedGroup;

    public ExamsScheduleSelectForm() { }

    public ExamsScheduleSelectForm(Long selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Long getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Long selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
}
