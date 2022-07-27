package service;

import Check.Check;
import entity.Article;
import entity.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class LoginUserMethods {

    private LoginUserMethods() {
    }

    public static boolean showAllArticles(User user) throws SQLException {
        Article[] articles;
        articles = Arrays.copyOf(ApplicationObjects.getUserService().load(user), 1000);
        if (articles[0] == null) {
            System.out.println("You have no article");
            pressEnter();
            return false;
        } else {
            for (Article article : articles) {
                if (article != null)
                    System.out.println(article);
                else break;
            }
//            pressEnter();
            return true;
        }
    }

    public static void addArticle(User user) throws SQLException {
        Article article = new Article();
        System.out.println(user.getId());
        System.out.println("Enter title: ");
        ApplicationObjects.getScanner().nextLine();
        article.setTitle(ApplicationObjects.getScanner().nextLine());
        System.out.println("Enter brief: ");
        article.setBrief(ApplicationObjects.getScanner().nextLine());
        System.out.println("Enter content: ");
        article.setContent(ApplicationObjects.getScanner().nextLine());
        System.out.println("Be published ? (enter yes or no):  ");
        String publish = ApplicationObjects.getScanner().next();
        Check.checkPublished(publish);
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
            showEditMenu();
            command = ApplicationObjects.getScanner().next();
            if (Objects.equals(command, "1")) {
                while (true) {
                    if (showAllUserArticles(user, "Enter the id of the article you want to edit (Enter exit for exit): "))
                        break;
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
                    while (true) {
                        showOneUserArticle(article);
                        command = ApplicationObjects.getScanner().next();
                        if (Objects.equals(command, "title")) {
                            updateArticle(user, id, article, "title");
                            pressEnter();
                        } else if (Objects.equals(command, "brief")) {
                            updateArticle(user, id, article, "brief");
                            pressEnter();
                        } else if (Objects.equals(command, "content")) {
                            updateArticle(user, id, article, "content");
                            pressEnter();
                        } else if (Objects.equals(command, "isPublished")) {
                            updateArticle(user, id, article, "isPublished");
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
                    if (showAllUserArticles(user, "Enter the id of the article you want to published or unpublished (Enter exit for exit): "))
                        break;
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
                while(true){
                    if(showAllUserArticles(user,"Enter the id of the article you want to delete (Enter exit for exit):"))
                        break;
                    String temp = ApplicationObjects.getScanner().next();
                    if(Objects.equals(temp, "exit"))
                        break;
                    int id = Integer.parseInt(temp);
                    if (ApplicationObjects.getUserService().deleteArticle(user, id)) {
                        System.out.println("Done! ");
                    } else {
                        System.out.println("There is no article with this id for you");
                    }
                    pressEnter();
                }
            } else if (Objects.equals(command, "4")) {
                break;
            }
        }
    }

    private static void showEditMenu() {
        System.out.println("1) Edit article");
        System.out.println("2) Published or unpublished article");
        System.out.println("3) Delete article");
        System.out.println("4) Exit");
        System.out.print("Enter your command: ");
    }

    private static void updateArticle(User user, int id, Article article, String command) throws SQLException {
        System.out.print("Enter new " + command + " : ");
        ApplicationObjects.getScanner().nextLine();
        if (Objects.equals(command, "title"))
            article.setTitle(ApplicationObjects.getScanner().nextLine());
        else if (Objects.equals(command, "content"))
            article.setContent(ApplicationObjects.getScanner().nextLine());
        else if (Objects.equals(command, "brief"))
            article.setBrief(ApplicationObjects.getScanner().nextLine());
        else if (Objects.equals(command, "isPublished")) {
            String publish = ApplicationObjects.getScanner().nextLine();
            Check.checkPublished(publish);
            if (Objects.equals(publish, "yes"))
                article.setIsPublished(true);
            else if (Objects.equals(publish, "no"))
                article.setIsPublished(false);
        }
        ApplicationObjects.getUserService().edit(article, id, user);
        System.out.println("Done! Your article has been updated");
    }

    private static void showOneUserArticle(Article article) {
        System.out.println(article);
        System.out.println("This is your article");
        System.out.println("Which part do you want to change? (Enter exit for exit)");
    }

    private static boolean showAllUserArticles(User user, String x) throws SQLException {
        if (showAllArticles(user)) {
            System.out.println("These are your articles");
            System.out.println(x);
            return false;
        } else return true;
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
