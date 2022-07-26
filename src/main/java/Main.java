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
        if (userService.login(user))
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

    public static void editArticle(User user) throws SQLException {
        String command;
        System.out.println("1) Edit article");
        System.out.println("2) Published or unpublished article");
        System.out.println("3) Exit");
        while (true) {
            command = scanner.next();
            if (Objects.equals(command, "1")) {
                showAllArticles(user);
                System.out.println("These are your articles");
                System.out.println("Enter the id of the article you want to edit: ");
                command = scanner.next();
                int id = Integer.parseInt(command);
                System.out.println(id);
                Article article;
                article = userService.load(Integer.parseInt(command), user);
                System.out.println(article);
                System.out.println("this is your article");
                System.out.println("Which part do you want to change? ");
                command = scanner.next();
                if (Objects.equals(command, "title")) {
                    System.out.print("Enter new title");
                    scanner.nextLine();
                    String temp = scanner.nextLine();
                    article.setTitle(temp);
                    System.out.println(article);
                    userService.edit(article, id, user);
                } else if (Objects.equals(command, "brief")) {
                    System.out.print("Enter new brief");
                    article.setBrief(scanner.nextLine());
                    userService.edit(article, id, user);
                } else if (Objects.equals(command, "content")) {
                    System.out.print("Enter new content");
                    article.setContent(scanner.nextLine());
                    userService.edit(article, id, user);
                } else if (Objects.equals(command, "isPublished")) {
                    System.out.print("Enter new title");
                    article.setTitle(scanner.nextLine());
                    userService.edit(article, id, user);
                }
            } else if (Objects.equals(command, "2")) {
                addArticle(user);
            } else if (Objects.equals(command, "3")) {
                break;
            }
        }
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
                editArticle(user);
            } else if (Objects.equals(command, "4")) {

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
