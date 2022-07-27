package service;

import java.sql.SQLException;
import java.util.Objects;

public class ShowMenuForGuess {

    public static void showMenuForGuess() throws SQLException {
        String command;
        while (true) {
            System.out.println("1) Sign up");
            System.out.println("2) Login");
            System.out.println("3) Show All articles that have been published");
            System.out.println("4) Exit");
            command = ApplicationObjects.getScanner().next();
            if (Objects.equals(command, "1")) {
                GuessMethods.signUp();
            } else if (Objects.equals(command, "2")) {
                GuessMethods.login();
            } else if (Objects.equals(command, "3")) {
                GuessMethods.showAllArticles();
            } else if (Objects.equals(command, "4")) {
                break;
            }
        }
    }

}
