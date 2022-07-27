package Check;

import service.ApplicationObjects;

import java.util.regex.Pattern;

public class Check {

    public static String checkDate(String date, String regex){
        if(Pattern.matches(regex,date)){
            return date;
        }
        else{
            while(true){
                System.out.print("Wrong input. Try again: ");
                date = ApplicationObjects.getScanner().next();
                if(Pattern.matches(regex,date)){
                    return date;
                }
            }
        }
    }

    public static void main(String[] args) {

    }

}