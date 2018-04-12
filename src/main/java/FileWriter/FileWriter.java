package FileWriter;

import SaveFiles.outMarker;
import structs.Container;

import java.io.*;

public class FileWriter {

    private static String listLocation = "../../resources/main/storelist.txt";
    private static String messageIDLocation = "../../resources/main/messageID.txt";

    public static void saveContainer(Container container) {

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(outMarker.class.getClassLoader().getResource(listLocation).getFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(container);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Container loadContainer() {

        Container container = null;

        try{
            FileInputStream fis = new FileInputStream(outMarker.class.getClassLoader().getResource(listLocation).getFile());
            ObjectInputStream ois = new ObjectInputStream(fis);
            container =  (Container) ois.readObject();
        }catch (IOException e){
            if(!e.getClass().equals(EOFException.class)){
                e.printStackTrace();
            }
        }finally {
            if(container == null){
                System.out.println("Container is null");
            }
            return container;
        }

    }

    public static void saveMessageID(long id) {
        try {
            FileOutputStream fos = new FileOutputStream(outMarker.class.getClassLoader().getResource(messageIDLocation).getFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(id);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static long loadMessageID() {

        long id = 0;

        try{
            FileInputStream fis = new FileInputStream(outMarker.class.getClassLoader().getResource(messageIDLocation).getFile());
            ObjectInputStream ois = new ObjectInputStream(fis);
            id = (long) ois.readObject();
        }catch (EOFException e){

        }finally {
            return id;
        }

    }
}
