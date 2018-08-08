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
public class Database {
    ArrayList<Transaction> transactions;
    static int numOfTransactions = 0;
    
    Database(){
        transactions = new ArrayList<>();
    }
    
    public void addTransaction(Transaction t){
        t.transNo = numOfTransactions++;
        transactions.add(t);
    }
    
    @Override
    public String toString(){
        String str = "Items:\n";
        for(Transaction t : transactions){
            str = str + t.toString() + "\n";
        }
        return str;
    }
}
