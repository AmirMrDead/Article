package service;

import entity.Article;
import entity.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class LoginUserMethods {

    public static void showAllArticles(User user) throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(ApplicationObjects.getUserService().load(user), 1000);
        if (articles[0].getId() == 0) {
            System.out.println("You have no article");
            pressEnter();
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
        ApplicationObjects.getScanner().nextLine();
        article.setTitle(ApplicationObjects.getScanner().nextLine());
        System.out.println("Enter brief: ");
        article.setBrief(ApplicationObjects.getScanner().nextLine());
        System.out.println("Enter content: ");
        article.setContent(ApplicationObjects.getScanner().nextLine());
        System.out.println("Be published ? (enter yes or no):  ");
        String publish = ApplicationObjects.getScanner().next();
        article.setIsPublished(Objects.equals(publish, "yes"));
        article.setUserId(user.getId());
        article.setCreateDate(Date.valueOf(java.time.LocalDate.now()));
        ApplicationObjects.getUserService().addArticle(article, user);
        System.out.println("Add article done!");
        pressEnter();
    }

    public static void editArticle(User user) throws SQLException {
        String command;
        while (true) {
            System.out.println("1) Edit article");
            System.out.println("2) Published or unpublished article");
            System.out.println("3) Exit");
            System.out.print("Enter your command: ");
            command = ApplicationObjects.getScanner().next();
            if (Objects.equals(command, "1")) {
                while (true) {
                    showAllArticles(user);
                    System.out.println("These are your articles");
                    System.out.println("Enter the id of the article you want to edit (Enter exit for exit): ");
                    command = ApplicationObjects.getScanner().next();
                    if (Objects.equals(command, "exit"))
                        break;
                    int id = Integer.parseInt(command);
                    Article article;
                    article = ApplicationObjects.getUserService().load(Integer.parseInt(command), user);
                    if (article == null) {
                        System.out.println("There is no article with this id for you");
                        pressEnter();
                        continue;
                    }
                    while(true){
                        System.out.println(article);
                        System.out.println("This is your article");
                        System.out.println("Which part do you want to change? (Enter exit for exit)");
                        command = ApplicationObjects.getScanner().next();
                        if (Objects.equals(command, "title")) {
                            System.out.print("Enter new title: ");
                            ApplicationObjects.getScanner().nextLine();
                            article.setTitle(ApplicationObjects.getScanner().nextLine());
                            ApplicationObjects.getUserService().edit(article, id, user);
                            System.out.println("Done! Your article has been updated");
                            pressEnter();
                        } else if (Objects.equals(command, "brief")) {
                            System.out.print("Enter new brief: ");
                            ApplicationObjects.getScanner().nextLine();
                            article.setBrief(ApplicationObjects.getScanner().nextLine());
                            ApplicationObjects.getUserService().edit(article, id, user);
                            System.out.println("Done! Your article has been updated");
                            pressEnter();
                        } else if (Objects.equals(command, "content")) {
                            System.out.print("Enter new content: ");
                            ApplicationObjects.getScanner().nextLine();
                            article.setContent(ApplicationObjects.getScanner().nextLine());
                            ApplicationObjects.getUserService().edit(article, id, user);
                            System.out.println("Done! Your article has been updated");
                            pressEnter();
                        } else if (Objects.equals(command, "isPublished")) {
                            System.out.print("Enter new title");
                            ApplicationObjects.getScanner().nextLine();
                            article.setTitle(ApplicationObjects.getScanner().nextLine());
                            ApplicationObjects.getUserService().edit(article, id, user);
                            System.out.println("Done! Your article has been updated");
                            pressEnter();
                        } else if (Objects.equals(command, "exit")) {
                            break;
                        } else {
                            System.out.println("Wrong command. try again");
                        }
                    }
                }
            } else if (Objects.equals(command, "2")) {
                while (true) {
                    showAllArticles(user);
                    System.out.println("These are your articles");
                    System.out.println("Enter the id of the article you want to published or unpublished (Enter exit for exit): ");
                    command = ApplicationObjects.getScanner().next();
                    if (Objects.equals(command, "exit"))
                        break;
                    int id = Integer.parseInt(command);
                    Article article;
                    article = ApplicationObjects.getUserService().load(Integer.parseInt(command), user);
                    if (article == null) {
                        System.out.println("There is no article with this id for you");
                        pressEnter();
                        continue;
                    }
                    ApplicationObjects.getUserService().edit(!article.getIsPublished(), id, user);
                    article.setIsPublished(!article.getIsPublished());
                    String published;
                    if (article.getIsPublished())
                        published = "published";
                    else
                        published = "unpublished";
                    System.out.println("Done! your article with id: " + id + " is " + published + " from now");
                    pressEnter();
                }
            } else if (Objects.equals(command, "3")) {
                break;
            }
        }
    }

    public static void changePassword(User user) throws SQLException {
        System.out.print("Enter new password: ");
        String password = ApplicationObjects.getScanner().next();
        ApplicationObjects.getUserService().changePassword(user, password);
        System.out.println("Change password done!");
        pressEnter();
    }

    private static void pressEnter() {
        System.out.println("Press enter to continue");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

}
