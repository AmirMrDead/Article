package repository;

import config.DBConfig;
import entity.Article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticleRepository {

    public void save(Article article, int id) throws SQLException {
        final String query = """
        insert into article (title, brief, content, create_date, is_published, user_id)
        values (?,?,?,?,?,?);
        """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1,article.getTitle());
        preparedStatement.setString(2,article.getBrief());
        preparedStatement.setString(3,article.getContent());
        preparedStatement.setDate(4,article.getCreateDate());
        preparedStatement.setBoolean(5,article.getIsPublished());
        preparedStatement.setInt(6,id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet load () throws SQLException {
        final String query = """
        select * from article;            
        """;
        Statement statement = DBConfig.getConnection().createStatement();
        statement.close();
        return statement.executeQuery(query);
    }
}
