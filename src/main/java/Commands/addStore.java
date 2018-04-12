package Commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import structs.Store;


/**
 * Add a store to a Brand, which in itself is contained in a Market
 * TODO implement this for multiple stores
 * TODO cleanup args array, set contents to values for readabillity
 **/
public class addStore implements Command {


    @Override
    public boolean fire(TextChannel channel, String[] args) {
        try{

            if(args.length != 5) {
                channel.sendMessage("Format: !addStore <market> <brand> <store>").queue();
            }else if(!Bot.Main.getContainer().hasMarket(args[1])){
                channel.sendMessage("Market does not exist!").queue();
            }else if(!Bot.Main.getContainer().getMarket(args[1]).hasBrand(args[2])){
                channel.sendMessage("Brand does not exist in " + args[1] + "!");
            }else{
                Bot.Main.getContainer().getMarket(args[1]).getBrand(args[2]).addStore(new Store(args[3], args[4]));
                channel.sendMessage("Successfully added Store " + args[3] + " to Brand " + args[2] + " in Market " + args[1]).queue();
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}
