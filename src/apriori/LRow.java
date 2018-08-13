/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Dan
 */
public class LRow {
    ArrayList<String> itemset = new ArrayList<>();
    int support = 0;
    
    LRow(){
        
    }
    
    LRow(ArrayList<String> newList){
        itemset = newList;
    }
    
    LRow(String s){
        itemset.add(s);
    }
    
    LRow(String s, int sup){
        itemset.add(s);
        support = sup;
    }
    
    public void showRows(){
        System.out.print("{");
        for(String e : itemset){
            System.out.print(e + ",");
        }
        System.out.println("}\t\t\t" + support);
    }
    
    public void sortSet(){
        Collections.sort(itemset,new ElementComparator());
    }
}
