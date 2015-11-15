package jschedule.models.forms;

import jschedule.models.domain.News;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsForm {
    @NotNull
    @Size(min = 3, max = 140)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public News toNews() {
        return new News(content);
    }
}
