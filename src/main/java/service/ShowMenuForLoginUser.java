package service;

import entity.User;

import java.sql.SQLException;
import java.util.Objects;

public class ShowMenuForLoginUser {

    private ShowMenuForLoginUser() {
    }

    public static void showMenuForUser(User user) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) My articles");
            System.out.println("2) Add new article");
            System.out.println("3) Edit article");
            System.out.println("4) Change password");
            System.out.println("5) Logout");
            System.out.println("6) Delete my account");
            command = ApplicationObjects.getScanner().next();
            if (Objects.equals(command, "1")) {
                onePossibleArticle(user);
            } else if (Objects.equals(command, "2")) {
                LoginUserMethods.addArticle(user);
            } else if (Objects.equals(command, "3")) {
                LoginUserMethods.editArticle(user);
            } else if (Objects.equals(command, "4")) {
                LoginUserMethods.changePassword(user);
            } else if (Objects.equals(command, "5")) {
                break;
            }else if (Objects.equals(command, "6")) {
                LoginUserMethods.deleteUser(user);
                break;
            }else{
                System.out.println("Wrong input");
            }
        }
    }

    // check user has article or not
    private static void onePossibleArticle(User user) throws SQLException {
        if(LoginUserMethods.showAllArticles(user)){
            System.out.println("these are your articles");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        }
    }
}
