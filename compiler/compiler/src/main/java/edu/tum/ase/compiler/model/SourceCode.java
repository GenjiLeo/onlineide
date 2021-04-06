package edu.tum.ase.compiler.model;

public class SourceCode {
private String content;
private String name;
private String stdout;
private String stderr;
private boolean compilable = false;

public SourceCode() {

}

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public boolean getCompilable() {
        return compilable;
    }

    public void setCompilable(boolean compilable) {
        this.compilable = compilable;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

}