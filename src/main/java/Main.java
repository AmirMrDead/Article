import entity.Article;
import entity.User;
import service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(userService.login(user))
            showMenuForUser(user);
    }

    public static void showAllArticles() throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(userService.load(), 1000);
        for (Article article : articles) {
            if (article != null)
                System.out.println(article.showSummary());
            else break;
        }
    }

    public static void showAllArticles(User user) throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(userService.load(user), 1000);
        for (Article article : articles) {
            if (article != null)
                System.out.println(article);
            else break;
        }
        System.out.println("Press enter to continue");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    public static void addArticle(User user) throws SQLException {
        Article article = new Article();
        System.out.println("Enter title: ");
        article.setTitle(scanner.nextLine());
        System.out.println("Enter brief: ");
        article.setBrief(scanner.nextLine());
        System.out.println("Enter content: ");
        article.setContent(scanner.nextLine());
        System.out.println("be published ? (enter yes or no):  ");
        String publish = scanner.next();
        article.setIsPublished(Objects.equals(publish, "yes"));
        article.setUserId(user.getId());
        article.setCreateDate(Date.valueOf(java.time.LocalDate.now()));
        userService.addArticle(article, user);
    }

    public static void showMenuForUser(User user) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) My articles");
            System.out.println("2) add new article");
            System.out.println("3) edit article");
            System.out.println("4) change password");
            System.out.println("5) logout");
            command = scanner.next();
            if (Objects.equals(command, "1")) {
                showAllArticles(user);
            } else if (Objects.equals(command, "2")) {
                addArticle(user);
            } else if (Objects.equals(command, "3")) {

            } else if (Objects.equals(command, "4")) {
                break;
            } else if (Objects.equals(command, "5")) {
                break;
            }
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
                showAllArticles();
            } else if (Objects.equals(command, "4")) {
                break;
            }
        }
    }
}
