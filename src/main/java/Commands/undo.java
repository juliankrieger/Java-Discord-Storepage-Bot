package Commands;

import Bot.Main;
import net.dv8tion.jda.core.entities.TextChannel;
import structs.Container;

public class undo implements ContainerCommand {


    @Override
    public boolean fire(TextChannel channel, String[] args, Container container) {
        if(container == null){
            return false;
        }else{
            Main.setContainer(container);
            return true;
        }
    }
}
