package structs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brand implements Serializable{
    private String name;
    private List<Store> storeList;

    public Brand(String name){
        if(name.trim().length() == 0){
            throw new IllegalArgumentException("Brand name must not be empty!");
        }

        this.name = name;

        storeList = new ArrayList<>();
    }

    public Brand(String name, List<Store> storeList){
        this(name);

        this.storeList = storeList;
    }

    public Brand(String name, Store... stores){
        this(name, new ArrayList<>(Arrays.asList(stores)));
    }

    public String getName() {
        return name;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void addStore(Store s){
        this.storeList.add(s);
    }

    public void removeStore(String name){
        for(Store s : storeList){
            if(s.getName().equals(name)){
                storeList.remove(s);
            }
        }
    }

    public boolean hasStore(String name){
        for(Store s : storeList){
            if(s.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Store getStore(String name){
        for(Store s : storeList){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("--- " + this.name);
        for(Store s : this.storeList){
            sb.append("\n");
            sb.append(s.toString());
        }

        return sb.toString();
    }
}
