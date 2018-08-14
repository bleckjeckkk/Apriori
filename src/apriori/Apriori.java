/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Apriori {

    static int level = 1;
    static Database db = new Database();
    static int minSupport = 2;
    static int confidence;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("APRIORI");
        System.out.print("Input number of transactions: ");
        int numOfTrans = scanner.nextInt();
        
        for(int i = 0; i < numOfTrans ; i++){
            System.out.println(" - - - - - ");
            ArrayList<String> items = new ArrayList<>();
            System.out.print("Input number of Items for transaction " + i + ": ");
            int numOfItems = scanner.nextInt();
            for(int j = 0 ; j < numOfItems; j++){
                System.out.print("Input item " + j + ": ");
                items.add((String) scanner.next());
            }
            db.addTransaction(new Transaction(items));
        }
        System.out.println("Input amounnt of support required ");
        minSupport = scanner.nextInt();
        
        System.out.println("Input amounnt of confidence required (in percentage)");
        confidence = scanner.nextInt();
        
        
        //printing the database
        System.out.println("---------------------------------------");
        System.out.println("Database: ");
        System.out.println(db.toString());
        
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
                //traverse the list again to add the other elements
                for(LRow roww : table.rows){
                    //clones values of the current row
                    LRow r = new LRow((ArrayList<String>)row.itemset.clone());
                    
                    //checks if the incoming element is already in r and the elements in r doesn't exceed the level
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
                        //check for uniqueness
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
            //getting support for the new rows
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
            
            //puts the table into the dictionary if there's only one element (row) left in the table
            if(lt.rows.size() <=1){
                putTableInDictionary(lt,d);                
            }
            //puts the table into the dictionary and generates the next tables
            else{
                putTableInDictionary(lt,d);   
                generateNextSets(lt,d);
            }
        }
    }
}
