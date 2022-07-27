package service;

import entity.Article;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // to add and save user
    public void save(User user) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getUserRepository().load(user);
        if (!resultSet.next()) {
            ApplicationObjects.getUserRepository().save(user);
        }
        resultSet.close();
    }

    // to login and set id for user
    public boolean login(User user) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getUserRepository().load(user);
        if (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            resultSet.close();
            return true;
        }
        resultSet.close();
        return false;
    }

    // to load all articles for one user
    public Article[] load(User user) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getArticleRepository().loadAllArticle(user.getId());
        Article[] articles = new Article[1000];
        int index = 0;
        while (resultSet.next()) {
            Article article = new Article();
            if (resultSet.getInt("user_id") == user.getId()) {
                getArticle(resultSet, article);
                article.setUserId(user.getId());
                articles[index] = article;
                index++;
            }
        }
        resultSet.close();
        return articles;
    }

    // to load all published articles for guess
    public Article[] load() throws SQLException {
        ResultSet resultSet = ApplicationObjects.getArticleRepository().loadAllArticle();
        Article[] articles = new Article[1000];
        int index = 0;
        while (resultSet.next()) {
            Article article = new Article();
            getArticle(resultSet, article);
            articles[index] = article;
            index++;
        }
        resultSet.close();
        return articles;
    }

    // to load one article for user
    public Article load(int id, User user) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getArticleRepository().loadOneArticleForUser(id);
        Article article = new Article();
        if (resultSet.next() && user.getId() == resultSet.getInt("user_id")) {
            getArticle(resultSet, article);
            resultSet.close();
            return article;
        } else {
            resultSet.close();
            return null;
        }
    }

    // to load article
    public Article load(int id) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getArticleRepository().loadOneArticleForGuess(id);
        Article article = new Article();
        if (resultSet.next()) {
            getArticle(resultSet, article);
            resultSet.close();
            return article;
        } else {
            resultSet.close();
            return null;
        }
    }

    // to edit published or unpublished
    public void edit(boolean isPublished, int id, User user) throws SQLException {
        ApplicationObjects.getArticleRepository().edit(isPublished, id);
    }

    // to edit article
    public void edit(Article article, int id, User user) throws SQLException {
        ApplicationObjects.getArticleRepository().edit(article, id);
    }

    public void addArticle(Article article, User user) throws SQLException {
        ApplicationObjects.getArticleRepository().save(article, user.getId());
    }

    public void changePassword(User user, String password) throws SQLException {
        ApplicationObjects.getUserRepository().changePassword(user, password);
    }

    public boolean checkUserExist(User user) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getUserRepository().checkUserExist(user);
        return resultSet.next();
    }

    public String forgotPassword(String username, String nationalCode,String birthday) throws SQLException {
        ResultSet resultSet = ApplicationObjects.getUserRepository().load(username,nationalCode,birthday);
        if(resultSet.next()){
            return resultSet.getString("password");
        }
        else{
            return null;
        }
    }

    public boolean deleteOneArticle(User user, int id) throws SQLException{
        ResultSet resultSet = ApplicationObjects.getArticleRepository().loadOneArticleForUser(id);
        if (resultSet.next() && user.getId() == resultSet.getInt("user_id")) {
            ApplicationObjects.getArticleRepository().deleteOne(id);
            resultSet.close();
            return true;
        } else {
            resultSet.close();
            return false;
        }
    }

    public void deleteUser(User user) throws SQLException{
       ApplicationObjects.getUserRepository().deleteUser(user);
    }

    public void deleteAllArticle(User user) throws SQLException{
            ApplicationObjects.getArticleRepository().deleteAll(user.getId());
    }

    // set article
    private void getArticle(ResultSet resultSet, Article article) throws SQLException {
        article.setId(resultSet.getInt("id"));
        article.setTitle(resultSet.getString("title"));
        article.setBrief(resultSet.getString("brief"));
        article.setContent(resultSet.getString("content"));
        article.setCreateDate(resultSet.getDate("create_date"));
        article.setIsPublished(resultSet.getBoolean("is_published"));
        article.setUserId(resultSet.getInt("user_id"));
    }

}
