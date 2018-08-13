/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author admin
 */
public class Apriori {

    static int level = 1;
    static Database db = new Database();
    static int minSupport = 2;
    
    public static void main(String[] args) {
        //START TODO: make a UI and automate
        
            //the transactions
            String[] trans0 = {"1", "3", "4"};  
            String[] trans1 = {"2", "3", "5"};  
            String[] trans2 = {"1", "2", "3", "5"};  
            String[] trans3 = {"2","5"};

            //"mapping" transactions to respective transaction objects
            Transaction t0 = new Transaction(new ArrayList<>(Arrays.asList(trans0)));
            Transaction t1 = new Transaction(new ArrayList<>(Arrays.asList(trans1)));
            Transaction t2 = new Transaction(new ArrayList<>(Arrays.asList(trans2)));
            Transaction t3 = new Transaction(new ArrayList<>(Arrays.asList(trans3)));

            //adding transactions to the "database"
            db.addTransaction(t0);
            db.addTransaction(t1);
            db.addTransaction(t2);
            db.addTransaction(t3);

            //printing the database
            System.out.println("---------------------------------------");
            System.out.println("Database: ");
            System.out.println(db.toString());

        //END TODO
        
        ArrayList<String> uniqueElements = new ArrayList<>();

        //getting unique elements
        for(Transaction t : db.transactions){
            for(String s : t.items){
                if(!uniqueElements.contains(s)){
                    uniqueElements.add(s);
                }
            }
        }
        
        sortSet(uniqueElements);
        
        Dictionary dictionary = new Hashtable();
        
        System.out.println("Unique Elements: ");
        uniqueElements.forEach((e) -> {
            System.out.println(e);
        });
        
        //get support for uniqueElements (first iteration) [L0]
        LTable l0 = getSupportFirstElements(uniqueElements, db);
        
        //drops elements that don't reach minSupport
        l0.dropElements(minSupport);
        
        //puts the "cleaned" table in the dictionary
        putTableInDictionary(l0,dictionary);
        
        //recursive generation for next possible sets
        generateNextSets(l0,dictionary);
        
        // Printing the dictionary
        Enumeration keys = dictionary.keys();
        System.out.println("---------------------------------------");
        System.out.println("Results");
        
        while (keys.hasMoreElements()) {
            int i = (int) keys.nextElement();
            LTable t = (LTable) dictionary.get(i);
            System.out.println("----- " + "L" + i + " -----");
            t.showRows();
        }
        
        
    }
    static void sortSet(ArrayList<String> set){
        Collections.sort(set,new ElementComparator());
    }
    
    static LTable getSupportFirstElements (ArrayList<String> elements, Database db){
        LTable lt = new LTable();
        
        //traverse the elements and add those in one LRow
        for(String s : elements){
            lt.rows.add(new LRow(s));
        }
        
        //get the support for elements
        for(LRow r : lt.rows){
            int sup = 0;
            for(Transaction t : db.transactions){
                for(String s : t.items){
                    if(r.itemset.contains(s)){
                        sup++;
                        break;
                    }
                }
            }
            r.support = sup;
        }
        
        return lt;
    }
    
    public static boolean putTableInDictionary(LTable table, Dictionary d){
        table.level = (Integer) level;
        d.put(level,table);
        level++;
        return true;
    }
    
    public static void generateNextSets(LTable table, Dictionary d){
        //if remaining LTable has only 1 element remaining
        if(table.getSize() <= 1){
            putTableInDictionary(table,d);
        }else{
            //make candidate table
            LTable lt = new LTable();
            
            //get candidates for next set
            //iterates over list
            for(LRow row : table.rows){
                //traverse the list again
                for(LRow roww : table.rows){
                    
                    LRow r = new LRow((ArrayList<String>)row.itemset.clone());
                    
                    for(String element : roww.itemset){
                        if(!r.itemset.contains(element) && r.itemset.size() < level){
                            r.itemset.add(element);
                        }
                    }
                    //if the size is equal to the current level
                    if(r.itemset.size() == level){
                        //checking for uniqueness
                        boolean unique = true;
                        r.sortSet();
                        for(LRow chkrow : lt.rows){
                            if(chkrow.itemset.equals(r.itemset)){
                                unique = false;
                                break;
                            }
                        }
                        if(unique){
                            lt.rows.add(r);
                        }
                    }
                }
            }
            
            //get support for next set
            
            //get the support for elements
            for(LRow r : lt.rows){
                int sup = 0;
                for(Transaction t : db.transactions){
                    if(t.items.containsAll(r.itemset)){
                        sup++;
                    }
                }
                r.support = sup;
            }
            
            //drop elements
            lt.dropElements(minSupport);
            
            putTableInDictionary(lt,d);
            generateNextSets(lt,d);
        }
    }
}
