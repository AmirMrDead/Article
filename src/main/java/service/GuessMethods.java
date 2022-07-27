package service;

import Check.Check;
import entity.Article;
import entity.User;

import java.sql.SQLException;
import java.util.Arrays;

public class GuessMethods {

    private GuessMethods() {
    }

    public static void signUp() throws SQLException {
        User user = new User();
        String password;
        System.out.print("Enter username: ");
        user.setUsername(Check.checkSignUp(ApplicationObjects.getScanner().next() , ""));
        System.out.print("Enter national code: ");
        password = Check.checkSignUp(ApplicationObjects.getScanner().next(),"^\\d{10}$");
        user.setNationalCode(password);
        user.setPassword(password);
        System.out.print("Enter birthday: ");
        String date = Check.checkSignUp(ApplicationObjects.getScanner().next()
                ,"^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        user.setBirthday(date);
        if (!ApplicationObjects.getUserService().checkUserExist(user)) {
            ApplicationObjects.getUserService().save(user);
            ApplicationObjects.getUserService().login(user);
            System.out.println("Registration was successful");
            System.out.println("Press enter to continue");
            pressEnter();
            ShowMenuForLoginUser.showMenuForUser(user);
        } else {
            System.out.println("This user exists. try login or signup with new national code and username");
            System.out.println("Press enter to continue");
            pressEnter();
        }
    }

    public static void login() throws SQLException {
        User user = new User();
        System.out.print("Enter username: ");
        user.setUsername(ApplicationObjects.getScanner().next());
        System.out.print("Enter password: ");
        user.setPassword(ApplicationObjects.getScanner().next());
        if (ApplicationObjects.getUserService().login(user)) {
            System.out.println("Login done");
            System.out.println("Press enter to continue");
            pressEnter();
            ShowMenuForLoginUser.showMenuForUser(user);
        } else {
            System.out.println("your username or password is incorrect");
            System.out.println("Press enter to continue");
            pressEnter();
        }
    }

    public static void showAllArticles() throws SQLException {
        String command;
        Article[] articles;
        articles = Arrays.copyOf(ApplicationObjects.getUserService().load(), 1000);
        if (articles[0].getId() == 0) {
            System.out.println("We have no published articles");
            System.out.println("Press enter to continue");
            pressEnter();
        } else {
            while (true) {
                int id;
                for (Article article : articles) {
                    if (article != null)
                        System.out.println(article.showSummary());
                    else break;
                }
                System.out.println("This is a summary of our published articles");
                System.out.println("Enter the id of the article you want to see in detail (Enter 0 for exit)");
                id = Integer.parseInt(ApplicationObjects.getScanner().next());
                if (id == 0)
                    break;
                Article article = ApplicationObjects.getUserService().load(id);
                if (article == null) {
                    System.out.println("There is no article with this id that has been published");
                    System.out.println("Press enter to continue");
                    pressEnter();
                    continue;
                }
                System.out.println(article);
                System.out.println("Press enter to continue");
                pressEnter();
            }
        }
    }

    private static void pressEnter() {
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

}
