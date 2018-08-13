/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class LTable {
    ArrayList<LRow> rows = new ArrayList<>();
    int level = 1;
    
    LTable(){
        
    }
    
    public ArrayList<LRow> getRows() {return rows;}
    
    public int getSize() {return rows.size();}
    
    public void dropElements(int minSupport){
        rows.removeIf((r) -> 
           r.support < minSupport 
        );
    }
    
    public void showRows(){
        System.out.print("Itemset \t\t Support\n");
        for(LRow r : rows){
            r.showRows();
        }
    }
}
