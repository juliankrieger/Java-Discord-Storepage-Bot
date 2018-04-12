package Bot;

import CommandListener.CommandListener;
import FileWriter.FileWriter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import structs.Container;
import structs.Market;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static Container container = new Container();

    public static void main(String[] args) throws LoginException, RateLimitedException{
        JDABuilder jda = new JDABuilder(AccountType.BOT);
        jda.setToken("NDIxOTg4NDUwMTQ5ODU5MzI4.DYVdTg.8ojPxQxLuY2nC23Ha5LPbjjbZ1A");
        jda.addEventListener(new CommandListener());
        jda.buildAsync();

        if(FileWriter.loadContainer() != null){
            container = FileWriter.loadContainer();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            FileWriter.saveContainer(container);
            System.out.print("END");

        }));
    }

    public static Container getContainer() {
        return container;
    }

    public static void setContainer(Container cont) {
        container = cont;
    }
}
