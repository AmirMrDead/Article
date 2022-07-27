import entity.Article;
import entity.User;
import service.ShowMenuForGuess;
import service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        ShowMenuForGuess.showMenuForGuess();
    }
}
