package jschedule.models.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class GroupForm {
    @NotNull
    @Size(min = 2, max = 15)
    private String name;

    @NotNull
    private List<Long> subjectIds;
}
