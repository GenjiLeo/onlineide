package edu.tum.ase.springsecurerest.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "project_id")
    private String id;

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

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

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public boolean hasUserAccess(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUserAccess(String username) {
        return hasUserAccess(new User(username, null, null));
    }
}
