package com.affirm.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static float expectedYield(float default_likelihood, float loan_interest_rate, float amount, float facility_interest_rate) {

        float expected_yield;
        expected_yield = (float) (((1.00 - default_likelihood) * loan_interest_rate * amount) - (default_likelihood * amount) - (
                facility_interest_rate * amount));

        return expected_yield;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Main App");

        String testingDir = "large";
        String fileNameBanks = testingDir + "/banks.csv";
        String fileNameCovenants = testingDir + "/covenants.csv";
        String fileNameFacilities = testingDir + "/facilities.csv";
        String fileNameLoan = testingDir + "/loans.csv";

        String outputYield = (testingDir + "/yields.csv");
        String outputAssignments = (testingDir + "/assignments.csv");

        List<Bank> bankList = Bank.readBankFile(fileNameBanks);
        List<Covenant> covenantslist = Covenant.readCovenantFile(fileNameCovenants);
        List<Facilities> facilitiesList = Facilities.readFacilitiesFile(fileNameFacilities);
        List<loan> loanList = loan.readLoanFile(fileNameLoan);

        Collections.sort(facilitiesList,new Facilities.sortByInterest()); // Sort In Order of Interest Rates

        System.out.println();
        System.out.println();

        for (int j = 0; j < loanList.size(); j++) {
            final int loanIndex = j;
            loan currentLoan = loanList.get(j);

            for (int i = 0; i < facilitiesList.size() && (currentLoan.getFacilityId() == 0); i++) {
                final int facilitiesIndex = i;
                Facilities currentFacilities = facilitiesList.get(i);
                List<Covenant> matchingCovenantList;
                System.out.println("currentLoan.getId())= " + currentLoan.getId());
                System.out.println("currentFacilities.getFacilityId())= " + currentFacilities.getFacilityId());

                Integer bankID = currentFacilities.getBankId();
                Integer facilityId = currentFacilities.getFacilityId();
                float loanDefault = currentLoan.getDefaultLikelihood();
                if (currentLoan.getAmount() < currentFacilities.getCurrentAmount()) {
                    System.out.println("currentLoan.getId())= " + currentLoan.getId());
                }
                matchingCovenantList = covenantslist.stream()
                        .filter(s -> (s.getBankId() == bankID) && s.getFacilityId() == facilityId && !currentLoan.getState()
                                .equals(s.getBannedState()) && currentLoan.getAmount() <= currentFacilities.getCurrentAmount() && s
                                .checkMaxDefaultLikelihood(loanDefault)).collect(Collectors.toList());

                matchingCovenantList.forEach((checkloan) -> {

                    float expected_yield = App
                            .expectedYield(currentLoan.getDefaultLikelihood(), currentLoan.getInterestRate(), currentLoan.getAmount(),
                                    currentFacilities.getInterestRate());
                    if (expected_yield > 0) {
                        System.out.println("expected_yield= " + expected_yield + ", Fac ID " + currentFacilities.getFacilityId() + ", Bank ID "
                                + currentFacilities.getBankId() + ", Loan Id " + currentLoan.getId());
                        if (currentFacilities.setNewAmount(currentLoan.getAmount(), currentLoan.getId(), expected_yield)) {
                            facilitiesList.set(facilitiesIndex, currentFacilities);
                            currentLoan.setFacilityId(currentFacilities.getFacilityId());
                            loanList.set(loanIndex, currentLoan);
                        }

                    }
                });

            }
        }

        //Use try-with-resource to get auto-closeable writer instance
        Path path1 = Paths.get(outputAssignments);
        try (BufferedWriter writer1 = Files.newBufferedWriter(path1)) {
            for (int i = 0; i < loanList.size(); i++) {
                if (i == 0) {
                    writer1.write("loan_id,facility_id");
                    writer1.write("\r\n");
                }
                writer1.write(loanList.get(i).getId() + "," + loanList.get(i).getFacilityId());
                writer1.write("\r\n");
            }
        }

        Path path2 = Paths.get(outputYield);
        try (BufferedWriter writer2 = Files.newBufferedWriter(path2)) {
            for (int i = 0; i < facilitiesList.size(); i++) {
                if (i == 0) {
                    writer2.write("facility_id,expected_yield ");
                    writer2.write("\r\n");
                }
                writer2.write(facilitiesList.get(i).getFacilityId() + "," + facilitiesList.get(i).getExpectedYield());
                writer2.write("\r\n");
            }

        }
    }
}
