package Commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import structs.Container;

public interface Command {
    public boolean fire(TextChannel channel, String[] args);

}
