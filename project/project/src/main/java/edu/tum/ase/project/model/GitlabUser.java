package edu.tum.ase.project.model;

// Used for mapping the response of the Gitlab API to an object when querying
// for a user a project should be shared with
public class GitlabUser {

    private int id;
    private String name;
    private String username;
    private String state;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getUsername(){
        return username;
    }

    public GitlabUser() {}
}
