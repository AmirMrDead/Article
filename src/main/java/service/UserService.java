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
    }

    public void login(User user) throws SQLException {
        ResultSet resultSet = userRepository.load(user);
        if (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            System.out.println("Login done");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        }
    }

    public Article[] load(User user) throws SQLException {
        ResultSet resultSet = articleRepository.load(user.getId());
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

    public Article[] load() throws SQLException {
        ResultSet resultSet = articleRepository.load();
        Article[] articles = new Article[1000];
        int index = 0;
        while(resultSet.next()){
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

    public void addArticle(Article article, User user) throws SQLException {
        articleRepository.save(article, user.getId());
    }

    public void changePassword(User user, String password) throws SQLException {
        userRepository.changePassword(user, password);
    }


}
