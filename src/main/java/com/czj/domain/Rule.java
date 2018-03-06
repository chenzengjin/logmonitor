package com.czj.domain;


/**
 ---------------------------------------------------------+
 | rule  | CREATE TABLE `rule` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(100) NOT NULL,
 `description` varchar(500) DEFAULT NULL,
 `keyword` varchar(200) NOT NULL,
 `appId` int(11) NOT NULL,
 `isValid` char(1) DEFAULT '1',
 PRIMARY KEY (`id`),
 KEY `appId` (`appId`),
 CONSTRAINT `rule_ibfk_1` FOREIGN KEY (`appId`) REFERENCES `app` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 |
 +-------+-----------------------------------------------------
 * Created by 11273 on 2018-3-2.
 */
public class Rule {
    private int id;
    private String name;
    private String description;
    private String keyword;
    private int appId;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
