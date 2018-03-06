package com.czj.domain;

/**
 * //用户表
 create table user(
 id int AUTO_INCREMENT primary key,
 name varchar(50) not null,
 phone varchar(11) not null,
 email varchar(50) not null,
 isValid char(1) default '1'
 );
 * Created by 11273 on 2018-2-26.
 */
public class User {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String isValid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isValid='" + isValid + '\'' +
                '}';
    }
}
