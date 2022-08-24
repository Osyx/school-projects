package server.model;

import javax.persistence.*;
import java.io.Serializable;



@NamedQueries({
        @NamedQuery(
                name = "deleteUser",
                query = "DELETE FROM users user " +
                        "WHERE user.username LIKE :username " +
                        "AND user.password LIKE :password"
        )
        ,
        @NamedQuery(
                name = "checkUser",
                query = "SELECT user FROM users user " +
                        "WHERE user.username LIKE :username",
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "loginUser",
                query = "SELECT user FROM users user " +
                        "WHERE user.username LIKE :username " +
                        "AND user.password LIKE :password",
                lockMode = LockModeType.OPTIMISTIC
        )
})


@Entity(name = "users")
public class User implements Serializable{
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Version
    @Column(name = "version")
    private int versionNum;

    public User() {}

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
