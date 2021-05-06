/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;  

public class Company {

    /**
     * @param args the command line arguments
     
    */
    ArrayList<Employee> internals;
    ArrayList<Employee> externals;
    
    public static void main(String[] args) {
        
       //Prepare the company object
       Company s=new Company();
       s.initEmployeeArrays();
       
        //Reading input from file
       try {
            File myObj = new File("C:\\Users\\Andreas\\Documents\\NetBeansProjects\\company\\src\\company\\input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                s.storeNewEmployee(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        }
            
            //Operation 1.Print costs of a given day
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,22).getTime(),EmployeeType.I);
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,21).getTime(),EmployeeType.O);
        
            //Operation 2.Print in descending order of total cost
        System.out.println("Internal employees ranking:");
        for(employeesWithTotalCosts e:s.ranking(EmployeeType.I)){
            System.out.println("Employee ID: "+e.getId()+"  Cost:"+e.getCost());
        }
        
        System.out.println("External employees ranking:");
        for(employeesWithTotalCosts e:s.ranking(EmployeeType.O)){
            System.out.println("Employee ID: "+e.getId()+"  Cost:"+e.getCost());
        }
    }
    
    public void storeNewEmployee(String inputLine){
        String[] tokens=inputLine.split("\\t");
        EmployeeType type=tokens[1].equals("I")?EmployeeType.I:EmployeeType.O;
        try{
            Employee e=new Employee(tokens[0],new SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]),Double.parseDouble(tokens[3]),Double.parseDouble(tokens[4]),Double.parseDouble(tokens[5]),type);
            //The employees are either internal or external employees.We store them to the corresponding arraylist
            if(type==EmployeeType.I){
                this.internals.add(e);
            }else{
                this.externals.add(e);
            }
        }catch(ParseException e){
            System.out.println("Parse error");
            e.printStackTrace();
        }
    }
    
    public void initEmployeeArrays(){
        this.internals=new ArrayList<Employee>();
        this.externals=new ArrayList<Employee>();
    }
    
    public void totalCostPerDay(Date d,EmployeeType type){
        double cost=0;
        if(type==EmployeeType.I){
            for(Employee e:internals){
                if(e.getDate().equals(d)){
                    cost=cost+e.cost();
                }
            }
            System.out.println("Total cost of day "+d+" for internal employees:"+cost);
        }else{
            for(Employee e:externals){
                if(e.getDate().equals(d)){
                    cost=cost+e.cost();
                }
            }
            System.out.println("Total cost of day "+d+" for external employees:"+cost);
        }
        return;
    }
    
    public ArrayList<employeesWithTotalCosts> ranking(EmployeeType type){
        ArrayList<employeesWithTotalCosts> emp=new ArrayList<employeesWithTotalCosts>();
        if(type==EmployeeType.I){
            for(Employee e:internals){
                int i=0;
                for(employeesWithTotalCosts ecost:emp){
                    if(ecost.getId().equals(e.getId())){
                        ecost.addCost(e.cost());
                        emp.set(i,ecost);
                        System.out.println(i);
                        break;
                    }
                    i++;
                }
                emp.add(new employeesWithTotalCosts(e.getId(),e.cost()));
            }
            
        }
        else{
                for(Employee e:externals){
                for(employeesWithTotalCosts ecost:emp){
                    if(ecost.getId().equals(e.getId())){
                        ecost.addCost(e.cost());
                        continue;
                    }
                }
                emp.add(new employeesWithTotalCosts(e.getId(),e.cost()));
            }    
        }
        Collections.sort(emp);
        return emp;
    }
    
    private class employeesWithTotalCosts implements Comparable<employeesWithTotalCosts>{
        private String id;
        private double cost;
        
        private employeesWithTotalCosts(String id,double cost){
            this.id=id;
            this.cost=cost;
        }
        
        private void addCost(double cost){
            this.cost=this.cost+cost;
        }
        private double getCost(){
            return this.cost;
        }
        private String getId(){
            return id;
        }
        
        public int compareTo(employeesWithTotalCosts e){
            //Sorting in descending order
            return this.cost>e.cost?-1:1;
        }
    }
}
