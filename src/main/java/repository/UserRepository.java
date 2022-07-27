package repository;

import config.DBConfig;
import entity.User;

import java.sql.*;

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
                select * from users where username = ? and password = ?;
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        return preparedStatement.executeQuery();

    }

    // for forgot password
    public ResultSet load(String username, String nationalCode, String birthday) throws SQLException {
        final String query = """
                select * from users where username = ? and national_code = ? and birthday = ?;
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, nationalCode);
        preparedStatement.setDate(3, Date.valueOf(birthday));
        return preparedStatement.executeQuery();
    }

    public ResultSet checkUserExist(User user) throws SQLException {
        final String query = """
                select * from users where username = ? or national_code = ?;
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getNationalCode());
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
        preparedStatement.executeUpdate();
    }

    public void deleteUser(User user) throws SQLException {
        final String query = """
                delete from users
                where id = ?;
                """;
        PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,user.getId());
        preparedStatement.executeUpdate();
    }

}
