package structs;

import structs.Market;

import java.io.Serializable;
import java.util.ArrayList;

public class Container implements Serializable, Cloneable {
    private ArrayList<Market> marketList = new ArrayList<>();

    public Container(){

    }

    public void addMarket(Market m){
        marketList.add(m);
    }

    public  boolean hasMarket(String s){
        for(Market market : marketList){
            if(s.equals( market.getName())){
                return true;
            }
        }
        return false;
    }

    public Market getMarket(String name){
        for(Market m : marketList){
            if(name.equals(m.getName())){
                return m;
            }
        }
        return null;
    }

    public  String toString(){
        StringBuilder sb = new StringBuilder();
        for(Market m : marketList){
            sb.append(m.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public ArrayList<Market> getMarketList() {
        return marketList;
    }

    private void setMarketList(ArrayList<Market> list){
        this.marketList = list;
    }

    @Override
    public Container clone(){
        Container cont = new Container();
        cont.setMarketList((ArrayList<Market>) this.getMarketList().clone());
        return cont;
    }
}
