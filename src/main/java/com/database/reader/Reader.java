package com.database.reader;

import com.database.employee.Employee;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Reader {
    private Logger logger = LogManager.getLogger("SpartaDatabaseProject");
    private HashSet<Employee> filteredData;
    private List<Employee> duplicateData;
    private List<String> corruptDataLines;
    private int duplicateDataCounter;
    private int corruptedDataCounter;


    public void readCSV(File fileName) {
        duplicateDataCounter = 0;
        corruptedDataCounter = 0;
        corruptDataLines = new ArrayList<>();
        filteredData = new HashSet<>();
        duplicateData = new ArrayList<>();
        try {
            Scanner reader = new Scanner(fileName);
            //discard the first line
            if (reader.hasNextLine())
                reader.nextLine();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if(Employee.isValid(line)){
                    // adds to filtered data and checking if duplicates exist
                    if(!filteredData.add(createEmployee(line))){
                        duplicateData.add(createEmployee(line));
                        duplicateDataCounter++;
                    }
                }
                else{
                    // not counting empty lines as corrupt entry
                    if(!line.equals("")){
                        corruptDataLines.add(line);
                        corruptedDataCounter++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File does not exist");
//            e.printStackTrace();
            //TODO ADD LOGGER HERE
            logger.log(Level.ERROR, "IOException Thrown", e);
        }
    }


    public HashSet<Employee> getFilteredData() {
        return filteredData;
    }

    public List<Employee> getDuplicateData() {
        return duplicateData;
    }


    public int getCorruptedDataCounter() {
        return corruptedDataCounter;
    }

    public int getDuplicateDataCounter() {
        return duplicateDataCounter;
    }

    private Employee createEmployee(String line) {
        return new Employee(line);
    }

    public List<String> getCorruptDataLines(){
        return corruptDataLines;
    }
}
