package Commands;

import net.dv8tion.jda.core.entities.TextChannel;
import structs.Container;

public interface ContainerCommand {
    public boolean fire(TextChannel channel, String[] args, Container container);
}
