package edu.tum.ase.frontendservice.models;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Project implements Serializable {
    private String id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    public Project() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
