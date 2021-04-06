package edu.tum.ase.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    // Generate unique IDs
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "project_id")
    private String id;

    // Force project names to be unique (as given by the specification)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // List of source files belonging to this project, delete all source files
    // of this project if it is removed (cascade)
    // One project maps to many files
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<SourceFile> sourceFiles;

    // Collection of users that are allowed to access this project
    @ElementCollection
    @CollectionTable(name = "project_project_users", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "contributors")
    private Set<String> contributors = new HashSet<>();

    protected Project() {
    // no-args constructor required by JPA spec
    // this one is protected since it shouldn't be used directly
    }

    public Project(String name, String owner) {
        this.name = name;
        contributors.add(owner);
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // JsonIgnore makes the generator that generates a JSON response from the
    // return object ignore this attribute. Otherwise we would have a project
    // contain a list of source files with have a project which has a list
    // of source files etc. (infinite loop)
    @JsonIgnore
    public List<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public void addSourceFile(SourceFile sourceFile) {
        sourceFiles.add(sourceFile);
    }

    public void addContributor(String username) {
        contributors.add(username);
    }

    public Set<String> getContributers() {
        return contributors;
    }

    // Used for authorization checks
    public boolean hasAccess(String username) {
        return contributors.contains(username);
    }
}