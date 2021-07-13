package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Employee {

    //declare the hashmap to hold the employee data
    static HashMap<String, List <Double>> Employee = new HashMap<>();




    public static void main(String[] args) throws IOException {
        //declaring my variables
        double empNum;
        double anualSalary = 0;
        String name = "";
        boolean flag = true;
        //scanner to get user input
        Scanner ac = new Scanner(System.in);
        TransferData();

        while(!(name.equals("ex"))){
            System.out.println("Enter the employee name, type ex to exit");
            name = ac.nextLine();
            if(name.equals("ex")){
                break;
            }
            System.out.println("Enter the employee number");
            empNum = ac.nextDouble();
            flag = true;
            while(flag){
                try{
                    System.out.println("Enter the employee salary");
                    anualSalary = ac.nextDouble();
                    if(anualSalary <0 || anualSalary == 0){
                        throw new MyError("salary should be greater than zero");
                    }
                    flag = false;
                    Employee.put(name, new ArrayList<>(Arrays.asList(empNum, anualSalary)));
                    ac.nextLine();
                }
                catch (InputMismatchException a){
                    System.out.println("You should input a number");
                    ac.nextLine();
                }
                catch (MyError message1){
                    System.out.println(message1.getMessage());
                }

            }

        }
        Display();
        SaveFile();
        System.out.println("data is saved in the text file");


    }

    public static void TransferData(){
        //creating a file object
        File tempFile = new File("MyEmployeeList.txt");
        //checking if file will exist or not
        boolean exists = tempFile.exists();
        //put data that was read from the file
        String input[];
        // try nd catch to see if the file exists
        try {
            //if the file does exist we will read the file
            if (exists) {
                Scanner ac1 = new Scanner(tempFile);
                while(ac1.hasNextLine()){
                    String data = ac1.nextLine();
                    input = data.split("\\s");
                    double EmpNum = Double.parseDouble(input[1]);
                    double EmpSal = Double.parseDouble(input[2]);
                    Employee.put(input[0], new ArrayList<>(Arrays.asList(EmpNum, EmpSal)));
                }
            }
        }
        // if file does not exist exception error
        catch (FileNotFoundException e){
            System.out.println("this file does not exist");
        }

    }
    public static void Display(){
        int count = Employee.size();
        System.out.println("there are currently " + count + " of employees in your records \n");
        for(Map.Entry<String,List<Double>>entry:Employee.entrySet()){
            System.out.println("Employee name " + "  \t" + entry.getKey());
            System.out.println("employee number " + "\t" + Employee.get(entry.getKey()).get(0));
            System.out.println("employee salary " + "\t" + Employee.get(entry.getKey()).get(1));
        }
    }

    public static void SaveFile() throws IOException {
        Path file = Paths.get("MyEmployeeList.txt");
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(file,APPEND,CREATE));
        BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(output));
        String data;
        try {
            for(Map.Entry<String,List<Double>>entry:Employee.entrySet()){
                data = entry.getKey() + " " + Employee.get(entry.getKey()).get(0) + " " + Employee.get(entry.getKey()).get(1) + "\n";
                Writer.write(data);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        Writer.close();
    }
}