package entity;

import list.ArticleList;

import java.sql.Date;

public class User {

    private int id;
    private String username;
    private String nationalCode;
    private Date birthday;
    private String password;
    private ArticleList articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArticleList getArticles() {
        return articles;
    }

    public void setArticles(ArticleList articles) {
        this.articles = articles;
    }
}
