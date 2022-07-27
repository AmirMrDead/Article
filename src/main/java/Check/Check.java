package Check;

import service.ApplicationObjects;

import java.util.Objects;
import java.util.regex.Pattern;

public class Check {

    public static String check(String input, String regex){
        if(Objects.equals(regex, "")){
            if((input.length() < 3 || input.length() > 10) || Character.isDigit(input.charAt(0)) ) {
                while(true){
                    System.out.println("too short. Its size should be at least 3 and at most 10 and cant start with digit");
                    input = ApplicationObjects.getScanner().next();
                    if(input.length() > 3 && input.length() < 10){
                        return input;
                    }
                }
            }
        }
        if(Pattern.matches(regex,input)){
            return input;
        }
        else{
            while(true){
                System.out.print("Wrong input. Try again: ");
                input = ApplicationObjects.getScanner().next();
                if(Pattern.matches(regex,input)){
                    return input;
                }
            }
        }
    }

    public static void main(String[] args) {

    }

}