/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author admin
 */
public class Apriori {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database d = new Database();
        
        //the transactions
        String[] trans0 = {"1", "2", "3", "4"};  
        String[] trans1 = {"2", "3", "5"};  
        String[] trans2 = {"1", "2", "3", "5"};  
        String[] trans3 = {"2","5"}; 
        
        //"mapping" transactions to respective transaction objects
        Transaction t0 = new Transaction(new ArrayList<>(Arrays.asList(trans0)));
        Transaction t1 = new Transaction(new ArrayList<>(Arrays.asList(trans1)));
        Transaction t2 = new Transaction(new ArrayList<>(Arrays.asList(trans2)));
        Transaction t3 = new Transaction(new ArrayList<>(Arrays.asList(trans3)));
        
        //adding transactions to the "database"
        d.addTransaction(t0);
        d.addTransaction(t1);
        d.addTransaction(t2);
        d.addTransaction(t3);
        
        //printing the database
        System.out.println("---------------------------------------");
        System.out.println("Database: ");
        System.out.println(d.toString());
        
        ArrayList<String> uniqueElements = new ArrayList<>();
        //getting unique elements
        for(Transaction t : d.transactions){
            for(String s : t.items){
                if(!uniqueElements.contains(s)){
                    uniqueElements.add(s);
                }
            }
        }
        System.out.println("Unique Elements: ");
        uniqueElements.forEach((e) -> {
            System.out.println(e);
        });
    }
    
}
