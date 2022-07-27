package service;

import repository.ArticleRepository;
import repository.UserRepository;

import java.util.Scanner;

public class ApplicationObjects {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final UserRepository userRepository = new UserRepository();
    private final ArticleRepository articleRepository = new ArticleRepository();

    public Scanner getScanner() {
        return scanner;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }
}
