package jschedule.models;

public abstract class Settings {
    private static Integer startWeek = 1;
    private static Integer finalWeek = 14;
    private static Integer daysCount = 5;
    private static Integer subgroupsInGroup = 2;
    private static Integer pairsInDay = 4;

    public static void setSettings(int startWeek, int finalWeek, int daysCount, int subgroupsInGroup, int pairsInDay) {
        Settings.startWeek = startWeek;
        Settings.finalWeek = finalWeek;
        Settings.daysCount = daysCount;
        Settings.subgroupsInGroup = subgroupsInGroup;
        Settings.pairsInDay = pairsInDay;
    }

    public static Integer getStartWeek() {
        return startWeek;
    }

    public static void setStartWeek(Integer startWeek) {
        Settings.startWeek = startWeek;
    }

    public static Integer getFinalWeek() {
        return finalWeek;
    }

    public static void setFinalWeek(Integer finalWeek) {
        Settings.finalWeek = finalWeek;
    }

    public static Integer getDaysCount() {
        return daysCount;
    }

    public static void setDaysCount(Integer daysCount) {
        Settings.daysCount = daysCount;
    }

    public static Integer getSubgroupsInGroup() {
        return subgroupsInGroup;
    }

    public static void setSubgroupsInGroup(Integer subgroupsInGroup) {
        Settings.subgroupsInGroup = subgroupsInGroup;
    }

    public static Integer getPairsInDay() {
        return pairsInDay;
    }

    public static void setPairsInDay(Integer pairsInDay) {
        Settings.pairsInDay = pairsInDay;
    }
}
