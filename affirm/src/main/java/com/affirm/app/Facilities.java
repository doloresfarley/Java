package com.affirm.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Facilities {
    private Integer bank_id;
    private Integer facility_id;
    private float interest_rate;
    private float amount;
    private float currentAmount;
    private float expected_yield;
    private List<Integer> loadList;

    public Facilities(String amount,  String interest_rate, String facility_id, String bank_id ){


        this.interest_rate = Float.valueOf(interest_rate);
        this.amount =   Float.valueOf(amount);
        this.currentAmount =   Float.valueOf(amount);
        this.facility_id = Integer.parseInt(facility_id);
        this.bank_id = Integer.parseInt(bank_id);
        this.loadList = new ArrayList<Integer>();
        this.expected_yield = 0;
    }
    public Facilities getFacilities(){
        return this;
    }
    public float getInterestRate(){
        return this.interest_rate;
    }
    public void addLoan(Integer loadId, float expected_yield, float loanAmount ){
         this.loadList.add(loadId);
         this.expected_yield += expected_yield;
         this.currentAmount -= loanAmount;
    }
    public boolean setNewAmount(float loanAmount,Integer loadId, float expected_yield  ){
        if ( currentAmount >= loanAmount){
            this.addLoan( loadId,  expected_yield, loanAmount);
            return true;
        }
        else {
            return false;
        }
    }
    public float getExpectedYield(){
        return this.expected_yield;
    }

    public Integer getBankId(){
        return this.bank_id;
    }
    public Integer getFacilityId(){
        return this.facility_id;
    }
    public float getCurrentAmount(){
        return this.currentAmount;
    }

    public static List<Facilities> readFacilitiesFile( String FacilitiesFilename){
        String line = "";
        String splitBy = ",";
        List<Facilities> FacilitiesList= new ArrayList<Facilities>();
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(FacilitiesFilename));
            System.out.println(br.readLine() ); // Skip first Line

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] FacilitiesLine = line.split(splitBy);    // use comma as separator
                System.out.println("Facilities [amount=" + FacilitiesLine[0] + ", interest_rate=" + FacilitiesLine[1] + ", facility_id=" + FacilitiesLine[2] + ", bank_id=" + FacilitiesLine[3] +"]");
                Facilities current_Facilities= new Facilities(FacilitiesLine[0] , FacilitiesLine[1], FacilitiesLine[2], FacilitiesLine[3] );
                FacilitiesList.add(current_Facilities);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return FacilitiesList;
    }

    static class sortByInterest implements Comparator<Facilities> {

        @Override
        public int compare(Facilities f1, Facilities f2) {

            // for comparison
            return Float.valueOf(f1.getInterestRate()).compareTo(f2.getInterestRate());

        }
    }

}
