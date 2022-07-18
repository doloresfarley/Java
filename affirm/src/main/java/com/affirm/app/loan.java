package com.affirm.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class loan {
    private Integer id = 0;
    private float amount;
    private float interest_rate;
    private float default_likelihood;
    private String state;
    private Integer facilityId;

    public loan(String interest_rate,String amount, String loadid,   String default_likelihood, String state){

        this.amount = Float.valueOf(amount);
        this.interest_rate = Float.valueOf(interest_rate);
        this.id = Integer.parseInt(loadid);
        this.default_likelihood = Float.valueOf(default_likelihood);
        this.state = state;
        this.facilityId = 0;
    }
    public loan getLoan(){
        return this;
    }
    public Integer getId(){
        return this.id;
    }
    public Integer getFacilityId(){
        return this.facilityId;
    }
    public void setFacilityId(Integer facilityId){
         this.facilityId = facilityId;
    }

    public float getAmount(){
        return this.amount;
    }
    public float getDefaultLikelihood(){
        return this.default_likelihood;
    }
    public float getInterestRate(){
        return this.interest_rate;
    }
    public String getState(){
        return this.state;
    }
    public static List<loan> readLoanFile( String loanFilename){
        String line = "";
        String splitBy = ",";
        List<loan> loadList= new ArrayList<loan>();
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(loanFilename));
            System.out.println(br.readLine() ); // Skip first Line

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] loanLine = line.split(splitBy);    // use comma as separator
                System.out.println("Loan [interest_rate=" + loanLine[0] + ", amount=" + loanLine[1] + ", id=" + loanLine[2] + ", default_likelihood"
                        + "=" + loanLine[3] + ", state= " + loanLine[4] +"]");
                                       //loan(String interest_rate,String amount, String id,   String default_likelihood, String state){
                loan current_loan = new loan(loanLine[0] , loanLine[1], loanLine[2], loanLine[3] , loanLine[4]);
                loadList.add(current_loan);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return loadList;
    }

}
