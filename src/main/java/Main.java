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
        if (!userService.checkUserExist(user)) {
            userService.save(user);
            System.out.println("done successfully");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
            showMenuForUser(user);
        } else {
            System.out.println("this user exists. try login or signup with new national code and username");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        }
    }

    public static void login() throws SQLException {
        User user = new User();
        System.out.print("Enter username: ");
        user.setUsername(scanner.next());
        System.out.print("Enter password: ");
        user.setPassword(scanner.next());
        if (userService.login(user)) {
            System.out.println("Login done");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
            showMenuForUser(user);
        } else {
            System.out.println("your username or password is incorrect");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        }
    }

    public static void showAllArticles() throws SQLException {
        String command;
        Article[] articles;
        articles = Arrays.copyOf(userService.load(), 1000);
        if (articles[0].getId() == 0) {
            System.out.println("We have no published articles");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        } else {
            int id;
            for (Article article : articles) {
                if (article != null)
                    System.out.println(article.showSummary());
                else break;
            }
            /*System.out.println("This is a summary of our published articles");
            System.out.println("Enter the id of the article you want to see in detail");
            id = Integer.parseInt(scanner.next());
            Article article = userService.load(id,new User());
            System.out.println(article);*/
        }
    }

    public static void showAllArticles(User user) throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(userService.load(user), 1000);
        if (articles[0].getId() == 0) {
            System.out.println("you have no article");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (Exception ignored) {
            }
        } else {
            for (Article article : articles) {
                if (article != null)
                    System.out.println(article);
                else break;
            }
        }
    }

    public static void addArticle(User user) throws SQLException {
        Article article = new Article();
        System.out.println("Enter title: ");
        scanner.nextLine();
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
        System.out.println("add article done!");
        System.out.println("Press enter to continue");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    public static void editArticle(User user) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) Edit article");
            System.out.println("2) Published or unpublished article");
            System.out.println("3) Exit");
            System.out.print("Enter your command: ");
            command = scanner.next();
            if (Objects.equals(command, "1")) {
                while (true) {
                    showAllArticles(user);
                    System.out.println("These are your articles");
                    System.out.println("Enter the id of the article you want to edit (Enter exit for exit): ");
                    command = scanner.next();
                    if (Objects.equals(command, "exit"))
                        break;
                    int id = Integer.parseInt(command);
                    Article article;
                    article = userService.load(Integer.parseInt(command), user);
                    if (article == null) {
                        System.out.println("There is no article with this id for you");
                        continue;
                    }
                    System.out.println(article);
                    System.out.println("This is your article");
                    System.out.println("Which part do you want to change? (Enter exit for exit)");
                    command = scanner.next();
                    if (Objects.equals(command, "title")) {
                        System.out.print("Enter new title: ");
                        scanner.nextLine();
                        article.setTitle(scanner.nextLine());
                        userService.edit(article, id, user);
                        System.out.println("Done! Your article has been updated");
                    } else if (Objects.equals(command, "brief")) {
                        System.out.print("Enter new brief: ");
                        scanner.nextLine();
                        article.setBrief(scanner.nextLine());
                        userService.edit(article, id, user);
                        System.out.println("Done! Your article has been updated");
                    } else if (Objects.equals(command, "content")) {
                        System.out.print("Enter new content: ");
                        scanner.nextLine();
                        article.setContent(scanner.nextLine());
                        userService.edit(article, id, user);
                        System.out.println("Done! Your article has been updated");
                    } else if (Objects.equals(command, "isPublished")) {
                        System.out.print("Enter new title");
                        scanner.nextLine();
                        article.setTitle(scanner.nextLine());
                        userService.edit(article, id, user);
                        System.out.println("Done! Your article has been updated");
                    } else if (Objects.equals(command, "exit")) {
                        break;
                    } else {
                        System.out.println("Wrong command. try again");
                    }
                }
            } else if (Objects.equals(command, "2")) {
                while (true) {
                    showAllArticles(user);
                    System.out.println("These are your articles");
                    System.out.println("Enter the id of the article you want to published or unpublished (Enter exit for exit): ");
                    command = scanner.next();
                    if (Objects.equals(command, "exit"))
                        break;
                    int id = Integer.parseInt(command);
                    Article article;
                    article = userService.load(Integer.parseInt(command), user);
                    if (article == null) {
                        System.out.println("There is no article with this id for you");
                        continue;
                    }
                    userService.edit(!article.getIsPublished(), id, user);
                    article.setIsPublished(!article.getIsPublished());
                    String published;
                    if (article.getIsPublished())
                        published = "published";
                    else
                        published = "unpublished";
                    System.out.println("Done! your article with id: " + id + " is " + published + " from now");
                }
            } else if (Objects.equals(command, "3")) {
                break;
            }
        }
    }

    public static void changePassword(User user) throws SQLException {
        System.out.print("Enter new password: ");
        String password = scanner.next();
        userService.changePassword(user, password);
        System.out.println("change password done!");
        System.out.println("Press enter to continue");
        try {
            System.in.read();
        } catch (Exception ignored) {
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
                changePassword(user);
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
