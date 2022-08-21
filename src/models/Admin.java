package models;
public class Admin {
    String id;
    String password;

    public Admin(String id, String password)
    {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}
