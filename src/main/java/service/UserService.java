package service;

import config.DBConfig;
import entity.Article;
import entity.User;
import repository.ArticleRepository;
import repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private UserRepository userRepository = new UserRepository();
    private ArticleRepository articleRepository = new ArticleRepository();

    public void save(User user) throws SQLException {
        ResultSet resultSet = userRepository.load(user);
        if(!resultSet.next()){
            userRepository.save(user);
        }
    }

    public void login(User user) throws SQLException {
        ResultSet resultSet = userRepository.load(user);
        if (resultSet.next()) {
            System.out.println("Login done");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        }
    }

    public Article[] load(User user) throws SQLException {
        ResultSet resultSet = articleRepository.load();
        Article article = new Article();
        Article[] articles = new Article[1000];
        int index = 0;
        while (resultSet.next()) {
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

    public void edit(boolean isPublished, int id, User user) throws SQLException {
        ResultSet resultSet = articleRepository.load(id);
        if (resultSet.next()) {
            if (resultSet.getInt("user_id") == user.getId()) {
                articleRepository.edit(isPublished, id);
            }
        }
        resultSet.close();
    }

    public void edit(Article article, int id, User user) throws SQLException {
        ResultSet resultSet = articleRepository.load(id);
        if (resultSet.next()) {
            if (resultSet.getInt("user_id") == user.getId()) {
                articleRepository.edit(article, id);
            }
        }
        resultSet.close();
    }
}
