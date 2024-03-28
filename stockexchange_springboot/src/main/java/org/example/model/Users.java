package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
    @Column(name = "email", unique=true)
    private String email;





    public Users(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public Users(String id){
        this.id = id;
    }

    public Users() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public long getLastLogin() {
//        return lastLogin;
//    }
//
//    public void setLastLogin(long lastLogin) {
//        this.lastLogin = lastLogin;
//    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
