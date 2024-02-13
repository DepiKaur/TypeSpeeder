package se.ju23.typespeeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.menu.MenuHandler;

@Component
public class MyMain implements CommandLineRunner {


    private MenuHandler menu ;

    @Override
    public void run(String... args) throws Exception {
        menu = new MenuHandler();
        menu.run();
    }
}
