package CommandListener;

import Bot.Main;
import FileWriter.FileWriter;
import net.dv8tion.jda.client.exceptions.VerificationLevelException;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import structs.Container;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import structs.Brand;
import structs.Market;
import structs.Store;

import java.io.IOException;
import java.util.List;

/**
 * A custom command listener, which extends ListenerAdapter and overrides the Method onMessageReceived
 * TODO clear all todos
 * TODO Remove command methods and replace with objects
 * TODO implement removeStore, removeBrand, removeMarket
 * TODO implement a parser to parse a store list
 */
public class CommandListener extends ListenerAdapter {

    /**
     * gets the ID of the container Message.
     * TODO This has to be saved into a config file later
     *
     */
    private long containerMessageID = 0;
    private Container lastChangedContainer = null;

    /**
     * Overrides the onMessageReceived function of ListenerAdapter. This allows us to get e's Message, which
     * in our case should start with ! and should be followed with a legit command.
     * TODO cleanup the args array for readabillity
     * @param  e
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent e){

        this.setLastChangedContainer(); //Set the last changed container

        if(e.getMessage().getContentRaw().startsWith("!")){ //get the Content of the MREs Message, check if it starts with the token !

            String args[] = e.getMessage().getContentRaw() //Get the Raw Content
                    .replaceFirst("!" , "") //Replace ! with null
                    .split(" "); //and split it into different parts, around the " " token

            if (args[0].equals("addMarket")) { //self explanatory

                addMarket(e.getChannel(), args);

            }else if(args[0].equals("addBrand")) {
                addBrand(e.getChannel(), args);

            }else if(args[0].equals("addStore")){
                addStore(e.getChannel(), args);

            }else if(args[0].equals("print")){
                 printContainer(e.getChannel());

            }else if(args[0].equals("checkOnline")){ //EW
               checkOnline(e.getChannel());

            }else if (args[0].equals("undo")){
                undo(e.getChannel());

            }else if (args[0].equals("save")){
                safeContainer();
                FileWriter.saveMessageID(this.containerMessageID);

            } else if (args[0].equals("load")) {
                loadContainer();
                this.containerMessageID = FileWriter.loadMessageID();
                printContainer(e.getChannel());

            }else if (args[0].equals("cleanup")){
                cleanup(e.getTextChannel(), args);
            }


        }
    }


    //Group: Change the Storelist

    /**
     * Add a Market to the Storelist/the Container. If successfull, set lastSuccessfullChange to the Message ID.
     * TODO handle exceptions better
     * TODO handle adding Multiple Markets
     * TODO remove success message
     * TODO cleanup args array, set contents to values for readabillity
     * @param channel
     * @param args
     */
    public void addMarket(MessageChannel channel, String[] args){
        try {

            //TODO implement adding multiple Markets
            if(args.length != 2){
                channel.sendMessage("Format: !addMarket <market>");
            }

            Bot.Main.getContainer().addMarket(new Market(args[1])); //Add a new Market to the Container
            printContainer(channel); //Print it
            channel.sendMessage("Successfully added Market " + args[1]).queue(); //And queue the Sucess Message

        }catch(InsufficientPermissionException  | VerificationLevelException e){
            e.printStackTrace();

        }catch(Exception ex){
            ex.printStackTrace();
        }



    }

    /**
     * Add a Brand to a Market. If successfull, set lastSuccessfullChange to the Message ID sent.
     * //TODO implement adding multiple markets
     * TODO cleanup args array, set contents to values for readabillity
     * @param channel
     * @param args
     */
    public void addBrand(MessageChannel channel, String[] args){

        try {

            if (args.length != 3) {
                channel.sendMessage("Format: !addBrand <market> <brand>").queue();

            } else if (!Bot.Main.getContainer().hasMarket(args[1])) {
                channel.sendMessage("Market does not exist!").queue();

            } else {

                Bot.Main.getContainer().getMarket(args[1]).addBrand(new Brand(args[2])); //add a new Brand to the Market
                printContainer(channel); //print it
                channel.sendMessage(("Successfully added Brand " + args[2] + " to Market " + args[1])).queue(); //print Success message
            }
        }catch(InsufficientPermissionException  | VerificationLevelException e){
            e.printStackTrace();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Add a store to a Brand, which in itself is contained in a Market
     * TODO implement this for multiple stores
     * TODO cleanup args array, set contents to values for readabillity
     * @param channel
     * @param args
     */
    public void addStore(MessageChannel channel, String [] args){
        try{

            if(args.length != 5) {
                channel.sendMessage("Format: !addStore <market> <brand> <store>").queue();
            }else if(!Bot.Main.getContainer().hasMarket(args[1])){
                channel.sendMessage("Market does not exist!").queue();
            }else if(!Bot.Main.getContainer().getMarket(args[1]).hasBrand(args[2])){
                channel.sendMessage("Brand does not exist in " + args[1] + "!");
            }else{
                Bot.Main.getContainer().getMarket(args[1]).getBrand(args[2]).addStore(new Store(args[3], args[4]));
                printContainer(channel);
                channel.sendMessage("Successfully added Store " + args[3] + " to Brand " + args[2] + " in Market " + args[1]).queue();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Undo the last change.
     * @param channel
     */
    public void undo(MessageChannel channel){
        try {
            if (this.lastChangedContainer == null) {
                channel.sendMessage("Nothing to undo.").queue();
            } else {
                Main.setContainer(lastChangedContainer); //Set mains Container to the last Changed
                printContainer(channel); //print it
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Group: Print and check the storelist.


    /**
     * Print the Container
     * @param channel
     */
    public void printContainer(MessageChannel channel){

        try {
            if (this.containerMessageID == 0) { //If containerMessageID is 0, theres no container printed yet

                //This block Sends a Message to the channel, containing the toString of Mains container, waits until
                //that aciton is complete, and sets containerMessageID to that exact Long
                this.containerMessageID = channel.sendMessage(Bot.Main.getContainer().toString()).complete().getIdLong(); //Blocks the current thread until the action is complete

            } else { //If containerMessageID has a concrete Value, edit the message as to not create clutter
                channel.editMessageById(this.containerMessageID, Bot.Main.getContainer().toString()).queue();

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Check if the stores are online
    public void checkOnline(MessageChannel channel){

        int counter = 0;
        int totalstores = 0;

        for(Market m : Main.getContainer().getMarketList()){
            for(Brand b : m.getBrandList()){
                for(Store s : b.getStoreList()){
                    if(!s.isOnline()){
                        s.crossOut();
                        printContainer(channel);
                    }else{
                        counter++;
                    }

                    totalstores++;

                }
            }
        }

        channel.sendMessage(counter + " of " + totalstores + " online.");
    }


    public void setLastChangedContainer(){
        this.lastChangedContainer = Main.getContainer().clone();
    }

    public void loadContainer(){
        Main.setContainer(FileWriter.loadContainer());

    }

    public void safeContainer(){
            FileWriter.saveContainer(Main.getContainer());

    }

    public void cleanup(TextChannel channel, String[] args){

        int num = 0;

        try{
            num = Integer.parseInt(args[1]);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            List<Message> msgLst = channel.getHistory().retrievePast(num).complete();
            for(Message m : msgLst){
                if(m.getIdLong() == this.containerMessageID){
                    msgLst.remove(m);
                }
            }
            channel.deleteMessages(msgLst).complete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
