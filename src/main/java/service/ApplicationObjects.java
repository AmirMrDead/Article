package service;

import repository.ArticleRepository;
import repository.UserRepository;

import java.util.Scanner;

public class ApplicationObjects {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final UserRepository userRepository = new UserRepository();
    private static final ArticleRepository articleRepository = new ArticleRepository();

    public static Scanner getScanner() {
        return scanner;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    private ApplicationObjects(){}
}
