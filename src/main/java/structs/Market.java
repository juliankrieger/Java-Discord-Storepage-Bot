package structs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Market implements Serializable {

    private List<Brand> brandList;
    private String name;

    public Market(String name){
        if(name.trim().length() == 0){
            throw new IllegalArgumentException("Market Name must not be empty!");
        }

        this.name = name;

        brandList = new ArrayList<>();
    }

    public Market(String name, List<Brand> brandList){
        this(name);

        this.brandList = brandList;

    }

    public String getName() {
        return name;
    }

    public List<Brand> getBrandList() {

        return brandList;
    }

    public Market(String name, Brand... brands){
        this(name);
        this.brandList = new ArrayList<Brand> (Arrays.asList(brands));
    }

    public void addBrand(Brand b){
        this.brandList.add(b);
    }

    public void removeBrand(String name){
        for(Brand b : brandList){
            if(b.getName().equals(name)){
                brandList.remove(b);
            }
        }
    }

    public boolean hasBrand(String s){
        for(Brand b : this.brandList){
            if(b.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public Brand getBrand(String s){
        for(Brand b : this.brandList){
            if(b.getName().equals(s)){
                return b;
            }
        }

        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("- " + this.name);
        for(Brand b : this.brandList){
            sb.append("\n");
            sb.append(b.toString());
        }

        return sb.toString();

    }
}
