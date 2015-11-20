package schedule.models.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SettingsForm {
    @Min(1)
    private Integer startWeek;
    @Min(1)
    private Integer finalWeek;
    @Min(1)
    @Max(7)
    private Integer daysCount;
    @Min(1)
    private Integer subgroupsInGroup;
    @Min(1)
    private Integer pairsInDay;

    public SettingsForm() { }

    public SettingsForm(Integer startWeek, Integer finalWeek, Integer daysCount, Integer subgroupsInGroup,
                        Integer pairsInDay) {
        this.startWeek = startWeek;
        this.finalWeek = finalWeek;
        this.daysCount = daysCount;
        this.subgroupsInGroup = subgroupsInGroup;
        this.pairsInDay = pairsInDay;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getFinalWeek() {
        return finalWeek;
    }

    public void setFinalWeek(Integer finalWeek) {
        this.finalWeek = finalWeek;
    }

    public Integer getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount;
    }

    public Integer getSubgroupsInGroup() {
        return subgroupsInGroup;
    }

    public void setSubgroupsInGroup(Integer subgroupsInGroup) {
        this.subgroupsInGroup = subgroupsInGroup;
    }

    public Integer getPairsInDay() {
        return pairsInDay;
    }

    public void setPairsInDay(Integer pairsInDay) {
        this.pairsInDay = pairsInDay;
    }
}
