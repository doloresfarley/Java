package com.affirm.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private Integer bank_id;
    private String bank_name;

    public Bank(String bank_id, String bank_name){
        this.bank_id = Integer.parseInt(bank_id);
        this.bank_name = bank_name;
    }
    public Bank getBank(){
        return this;
    }
    public Integer getBankId(){
        return this.bank_id;
    }
    public String getBankName(){
        return this.bank_name;
    }
    public static List<Bank> readBankFile(String BankFilename) {
        String line = "";
        String splitBy = ",";
        List<Bank> BankList = new ArrayList<Bank>();
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(BankFilename));
            System.out.println(br.readLine() ); // Skip first Line

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] BankLine = line.split(splitBy);    // use comma as separator
                System.out.println("Bank [bank_id=" + BankLine[0] + ", bank_name=" + BankLine[1] + "]");
                Bank current_Bank = new Bank(BankLine[0], BankLine[1]);
                BankList.add(current_Bank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BankList;
    }
}
