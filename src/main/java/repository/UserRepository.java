package repository;

import config.DBConfig;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {


    public void save(User user) throws SQLException {
        final String query = """ 
                insert into users(username, national_code, birthday, password) 
                values (?,?,?,?);
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getNationalCode());
        preparedStatement.setDate(3, user.getBirthday());
        preparedStatement.setString(4, user.getNationalCode());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet load(User user) throws SQLException {
        final String query = """
                select * from users where  national_code = ? and password = ?;
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getNationalCode());
        preparedStatement.setString(2, user.getPassword());
        return preparedStatement.executeQuery();

    }

    public void changePassword(User user, String password) throws SQLException {
        final String query = """
                update users
                set password = ?
                where id = ?
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setInt(2, user.getId());
    }

}
