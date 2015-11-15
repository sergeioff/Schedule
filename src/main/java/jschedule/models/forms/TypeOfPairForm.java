package jschedule.models.forms;

import jschedule.models.domain.TypeOfPair;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TypeOfPairForm {
    @NotNull
    @Size(min = 1, max = 8)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfPair toTypeOfPair() {
        return new TypeOfPair(name);
    }
}
