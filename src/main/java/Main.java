import entity.Article;
import entity.User;
import service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);
    final static UserService userService = new UserService();

    public static void signUp() throws SQLException {
        User user = new User();
        String password;
        System.out.print("Enter username: ");
        user.setUsername(scanner.next());
        System.out.print("Enter national code: ");
        password = scanner.next();
        user.setNationalCode(password);
        user.setPassword(password);
        System.out.print("Enter birthday: ");
        user.setBirthday(scanner.next());
        userService.save(user);
    }

    public static void login() throws SQLException {
        User user = new User();
        System.out.print("Enter username: ");
        user.setUsername(scanner.next());
        System.out.print("Enter password: ");
        user.setPassword(scanner.next());
        userService.login(user);
    }

    public static void showAll() throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(userService.loadAllArticles(), 1000);
        for (int i = 0; i < articles.length; i++) {
            if (articles[i] != null)
                System.out.println(articles[i].showSummary());
            else break;
        }
    }

    public static void main(String[] args) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) Sign up");
            System.out.println("2) Login");
            System.out.println("3) Show All articles that have been published");
            System.out.println("4) Exit");
            command = scanner.next();
            if (Objects.equals(command, "1")) {
                signUp();
            } else if (Objects.equals(command, "2")) {
                login();
            } else if (Objects.equals(command, "3")) {
                showAll();
            } else if (Objects.equals(command, "4")) {
                break;
            }
        }

    }
}
