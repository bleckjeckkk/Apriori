/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Transaction {
    
    public int transNo;
    public ArrayList<String> items;
    public int numberOfItems = 0;
    
    Transaction(){
        items = new ArrayList<>();
    }
    
    Transaction(ArrayList<String> newArr){
        items = newArr;
        numberOfItems = newArr.size();
    }

    public void addItem(String s){
        items.add(s);
        numberOfItems++;
    }

    @Override
    public String toString(){
        String str = "Transaction " + transNo + "\n";
        for(String s : items){
            str = str + s + ",";
        }
        return str;
    }
}
