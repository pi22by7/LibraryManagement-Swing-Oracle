package models;

public class Students {

    public String uid;
    public String name;

    public Students(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student [uid=" + uid + ", name=" + name + "]";
    }
}
