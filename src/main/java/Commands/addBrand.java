package Commands;

import net.dv8tion.jda.client.exceptions.VerificationLevelException;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import structs.Brand;

/**
 * Add a Brand to a Market. If successfull, set lastSuccessfullChange to the Message ID sent.
 * //TODO implement adding multiple markets
 * TODO cleanup args array, set contents to values for readabillity
 **/
public class addBrand implements Command {
    @Override
    public boolean fire(TextChannel channel, String[] args) {
        try {

            if (args.length != 3) {
                channel.sendMessage("Format: !addBrand <market> <brand>").queue();

            } else if (!Bot.Main.getContainer().hasMarket(args[1])) {
                channel.sendMessage("Market does not exist!").queue();

            } else {

                Bot.Main.getContainer().getMarket(args[1]).addBrand(new Brand(args[2])); //add a new Brand to the Market
                channel.sendMessage(("Successfully added Brand " + args[2] + " to Market " + args[1])).queue(); //print Success message
                return true;
            }
        }catch(InsufficientPermissionException | VerificationLevelException e){
            e.printStackTrace();

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}
