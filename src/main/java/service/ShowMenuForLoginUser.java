package service;

import entity.User;

import java.sql.SQLException;
import java.util.Objects;

public class ShowMenuForLoginUser {

    public static void showMenuForUser(User user) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) My articles");
            System.out.println("2) add new article");
            System.out.println("3) edit article");
            System.out.println("4) change password");
            System.out.println("5) logout");
            command = ApplicationObjects.getScanner().next();
            if (Objects.equals(command, "1")) {
                LoginUserMethods.showAllArticles(user);
            } else if (Objects.equals(command, "2")) {
                LoginUserMethods.addArticle(user);
            } else if (Objects.equals(command, "3")) {
                LoginUserMethods.editArticle(user);
            } else if (Objects.equals(command, "4")) {
                LoginUserMethods.changePassword(user);
            } else if (Objects.equals(command, "5")) {
                break;
            }
        }
    }

}
