package com.affirm.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Covenant {

    private Integer bank_id;
    private Integer facility_id;
    private float max_default_likelihood;
    private String banned_state;
    private boolean checkDefault = true;

    public Covenant(String facility_id, String max_default_likelihood, String bank_id, String banned_state) {
        this.bank_id = Integer.parseInt(bank_id);
        this.facility_id = Integer.parseInt(facility_id);
        if (max_default_likelihood.isEmpty() ) {
            this.max_default_likelihood = 0;
            this.checkDefault = false;
        }
        else
            this.max_default_likelihood = Float.valueOf(max_default_likelihood);
        this.banned_state = banned_state;
    }

    public Covenant getCovenant() {
        return this;
    }
    public Integer getBankId(){
        return this.bank_id;
    }

    public float getMaxDefaultLikelihood(){
        return this.max_default_likelihood;
    }
    public boolean checkMaxDefaultLikelihood(float default_likelihood){
      if ( this.checkDefault)
            return (default_likelihood <= this.max_default_likelihood);
        else
            return false;
    }

    public Integer getFacilityId(){
        return this.facility_id;
    }
    public String getBannedState(){
        return this.banned_state;
    }
    public static List<Covenant> readCovenantFile(String CovenantFilename) {
        String line = "";
        String splitBy = ",";
        List<Covenant> CovenantList = new ArrayList<Covenant>();
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(CovenantFilename));
            System.out.println(br.readLine() ); // Skip first Line
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] CovenantLine = line.split(splitBy);    // use comma as separator
                System.out.println("Covenant [facility_id=" + CovenantLine[0] + ", max_default_likelihood=" + CovenantLine[1] + ", bank_id=" + CovenantLine[2]
                        + ", banned_state=" + CovenantLine[3] + "]");
                Covenant current_Covenant = new Covenant(CovenantLine[0], CovenantLine[1], CovenantLine[2], CovenantLine[3]);
                CovenantList.add(current_Covenant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CovenantList;
    }

}
