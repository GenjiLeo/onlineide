package edu.tum.ase.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "project_source_file")
public class SourceFile {
    // Generate unique IDs for source files
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "file_id", nullable = false, unique = true)
    private String id;
    // Many files belong to one project and cannot belong to no project
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id" , nullable = false)
    private Project project;
    @Column(name = "file_name", nullable = false)
    private String name;
    // @Lob stands for "large object" and makes Postgres treat this as a string
    // of infinite length. Without it we would be limited to 255 characters
    @Lob
    @Column(name = "content")
    private String content;

    protected SourceFile() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public SourceFile(Project project, String name) {
        this.project = project;
        this.name = name;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Project getProject(){
        return project;
    }
}