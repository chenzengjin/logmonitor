package com.czj.domain;

/**
 * create table app(
 id int AUTO_INCREMENT primary key,
 name varchar(100) unique not null,
 description varchar(500),
 logpath varchar(200),
 userId varchar(500),
 isValid char(1) default '1'
 );
 * Created by 11273 on 2018-2-26.
 */
public class App {
    private int id;
    private String name;
    private String description;
    private String logpath;
    private String userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logpath='" + logpath + '\'' +
                ", userId='" + userId + '\'' +
                ", isValid='" + isValid + '\'' +
                '}';
    }
}
