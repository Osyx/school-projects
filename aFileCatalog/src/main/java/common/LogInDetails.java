package common;

import java.io.Serializable;



public class LogInDetails implements Serializable {
    private final String username;
    private final String password;

    public LogInDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

}
