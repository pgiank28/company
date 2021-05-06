
package company;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.logging.*;
import java.io.File;
import java.io.FileNotFoundException;
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
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,22).getTime(),new ArrayList(internals),"internal");
        s.totalCostPerDay(new GregorianCalendar(2017,Calendar.MAY,21).getTime(),new ArrayList(externals),"external");
        
            //Operation 2.Print in descending order of total cost
        s.ranking(new ArrayList(internals),"internals");
        s.ranking(new ArrayList(externals),"externals");
        
    }
    
    public void storeNewEmployee(String inputLine){
        String[] tokens=inputLine.split("\\t");
        String type=tokens[1];
        try{
            if(type.equalsIgnoreCase("I")){
                Employee e=new InternalEmployee(tokens[0],new SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]),Double.parseDouble(tokens[3]),Double.parseDouble(tokens[4]),Double.parseDouble(tokens[5]));
                internals.add(e);
            }else{
                if(type.equalsIgnoreCase("0")){
                    Employee e=new ExternalEmployee(tokens[0],new SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]),Double.parseDouble(tokens[3]),Double.parseDouble(tokens[4]),Double.parseDouble(tokens[5]));
                    externals.add(e);
                }else{
                    LOGGER.log(Level.WARNING,"Not supported employee type at line : "+inputLine);
                }
            }
        }catch(ParseException e){
            LOGGER.log(Level.WARNING,"Parsing not implemented for the line :"+inputLine);
        }
    }
    
    public void initEmployeeArrays(){
        this.internals=new ArrayList<Employee>();
        this.externals=new ArrayList<Employee>();
    }
    
    public void ranking(ArrayList<Employee> emp,String type){
        System.out.println("Ranking of "+type+" employees");
        emp.sort((e1,e2)->(int)(e1.cost()-e2.cost()));
        Collections.reverse(emp);
        emp.forEach(x->System.out.println(x.getId()+" with cost "+x.cost()));
    }
    
    public void totalCostPerDay(Date d,ArrayList<Employee> emp,String type){
        System.out.println("Total cost for the day:"+d.toString()+" of "+type+" employees");
        double cost=0;
        for(Employee e:emp){
            if(e.getDate().equals(d)){
                cost=cost+e.cost();
            }
        }
        System.out.println(cost);
    }
    
}
