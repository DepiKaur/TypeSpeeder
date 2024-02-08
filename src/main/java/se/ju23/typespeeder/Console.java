package se.ju23.typespeeder;

import org.springframework.stereotype.Component;

@Component
public class Console {
    public void print(String message){
        System.out.println(message);
    }
    public void print(String[] stringList){
        for(String s: stringList){
            print(s);
        }
    }
}
