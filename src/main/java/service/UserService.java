package service;

import entity.Article;
import entity.User;
import repository.ArticleRepository;
import repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final ArticleRepository articleRepository = new ArticleRepository();

    public void save(User user) throws SQLException {
        ResultSet resultSet = userRepository.load(user);
        if (!resultSet.next()) {
            userRepository.save(user);
        }
        resultSet.close();
    }

    public boolean login(User user) throws SQLException {
        ResultSet resultSet = userRepository.load(user);
        if (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            resultSet.close();
            return true;
        }
        resultSet.close();
        return false;
    }

    public Article[] load(User user) throws SQLException {
        ResultSet resultSet = articleRepository.loadAllArticle(user.getId());
        Article[] articles = new Article[1000];
        int index = 0;
        while (resultSet.next()) {
            Article article = new Article();
            if (resultSet.getInt("user_id") == user.getId()) {
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setBrief(resultSet.getString("brief"));
                article.setContent(resultSet.getString("content"));
                article.setIsPublished(resultSet.getBoolean("is_published"));
                article.setUserId(user.getId());
                article.setCreateDate(resultSet.getDate("create_date"));
                articles[index] = article;
                index++;
            }
        }
        resultSet.close();
        return articles;
    }

    public Article[] load() throws SQLException {
        ResultSet resultSet = articleRepository.loadAllArticle();
        Article[] articles = new Article[1000];
        int index = 0;
        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setTitle(resultSet.getString("title"));
            article.setBrief(resultSet.getString("brief"));
            article.setContent(resultSet.getString("content"));
            article.setCreateDate(resultSet.getDate("create_date"));
            article.setIsPublished(resultSet.getBoolean("is_published"));
            article.setUserId(resultSet.getInt("user_id"));
            articles[index] = article;
            index++;
        }
        resultSet.close();
        return articles;
    }

    public Article load(int id, User user) throws SQLException {
        ResultSet resultSet = articleRepository.loadOneArticleForUser(id);
        Article article = new Article();
        if (resultSet.next() && user.getId() == resultSet.getInt("user_id")) {
            article.setId(resultSet.getInt("id"));
            article.setTitle(resultSet.getString("title"));
            article.setBrief(resultSet.getString("brief"));
            article.setContent(resultSet.getString("content"));
            article.setCreateDate(resultSet.getDate("create_date"));
            article.setIsPublished(resultSet.getBoolean("is_published"));
            article.setUserId(resultSet.getInt("user_id"));
            resultSet.close();
            return article;
        } else {
            resultSet.close();
            return null;
        }
    }

    public Article load(int id) throws SQLException {
        ResultSet resultSet = articleRepository.loadOneArticleForGuess(id);
        Article article = new Article();
        if (resultSet.next()) {
            article.setId(resultSet.getInt("id"));
            article.setTitle(resultSet.getString("title"));
            article.setBrief(resultSet.getString("brief"));
            article.setContent(resultSet.getString("content"));
            article.setCreateDate(resultSet.getDate("create_date"));
            article.setIsPublished(resultSet.getBoolean("is_published"));
            article.setUserId(resultSet.getInt("user_id"));
            resultSet.close();
            return article;
        } else {
            resultSet.close();
            return null;
        }
    }

    public void edit(boolean isPublished, int id, User user) throws SQLException {
        /*ResultSet resultSet = articleRepository.loadAllArticle(user.getId());
        if (resultSet.next()) {
            if (resultSet.getInt("user_id") == user.getId()) {
                articleRepository.edit(isPublished, id);
            }
        }*/
        articleRepository.edit(isPublished, id);
        //resultSet.close();
    }

    public void edit(Article article, int id, User user) throws SQLException {
        /*ResultSet resultSet = articleRepository.loadAllArticle(user.getId());
        if (resultSet.next()) {
            if (resultSet.getInt("user_id") == user.getId()) {
                articleRepository.edit(article, id);
            }
        }*/
        articleRepository.edit(article, id);
        //resultSet.close();
    }

    public void addArticle(Article article, User user) throws SQLException {
        articleRepository.save(article, user.getId());
    }

    public void changePassword(User user, String password) throws SQLException {
        userRepository.changePassword(user, password);
    }

    public boolean checkUserExist(User user) throws SQLException {
        ResultSet resultSet = userRepository.checkUserExist(user);
        return resultSet.next();
    }

}
