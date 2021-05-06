
package company;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.logging.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;  

public class Company {

    private static Logger LOGGER = Logger.getLogger("MyLogger");
    static ArrayList<Employee> internals;
    static ArrayList<Employee> externals;
    
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
        } catch (FileNotFoundException e){
            LOGGER.log(Level.SEVERE,e.getMessage());
            return;
        }
            
            //Operation 1.Print costs of a given day
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,22).getTime(),EmployeeType.I);
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,21).getTime(),EmployeeType.O);
        
            //Operation 2.Print in descending order of total cost
        s.ranking(internals,EmployeeType.I);
        s.ranking(externals, EmployeeType.O);
        
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
    
    public void ranking(ArrayList<Employee> emp,EmployeeType type){
        if(type==EmployeeType.I){
            ArrayList<Employee> inters = new ArrayList(emp);
            System.out.println("Internal employees ranking:");
            inters.sort((e1,e2)->(int)(e1.cost()-e2.cost()));
            Collections.reverse(inters);
            inters.forEach(x->System.out.println(x.getId()+" with cost "+x.cost()));
        
        }else{
            ArrayList<Employee> exters = new ArrayList(emp);
            System.out.println("External employees ranking:");
            exters.sort((e1,e2)->(int)(e1.cost()-e2.cost()));
            Collections.reverse(exters);
            exters.forEach(x->System.out.println(x.getId()+" with cost "+x.cost()));
        
        }
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
    }
    
}
