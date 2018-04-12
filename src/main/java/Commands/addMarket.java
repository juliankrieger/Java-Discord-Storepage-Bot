package Commands;

import net.dv8tion.jda.client.exceptions.VerificationLevelException;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import structs.Market;


/**
 * Add a Market to the Storelist/the Container. If successfull, set lastSuccessfullChange to the Message ID.
 * TODO handle exceptions better
 * TODO handle adding Multiple Markets
 * TODO remove success message
 * TODO cleanup args array, set contents to values for readabillity
 * **/
public class addMarket implements Command {
    @Override
    public boolean fire(TextChannel channel, String[] args) {
        try {

            //TODO implement adding multiple Markets
            if(args.length != 2){
                channel.sendMessage("Format: !addMarket <market>");
            }

            Bot.Main.getContainer().addMarket(new Market(args[1])); //Add a new Market to the Container
            channel.sendMessage("Successfully added Market " + args[1]).queue(); //And queue the Sucess Message
            return true;

        }catch(InsufficientPermissionException | VerificationLevelException e){
            e.printStackTrace();

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}
