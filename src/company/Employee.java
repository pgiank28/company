/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;
import java.util.Date;

enum EmployeeType{
    I,O
}

public class Employee{
    private String id;
    private Date date;
    private double hours;
    private double hourly_rate;
    private double fixed_cost;
    private EmployeeType type;
    
    public Employee(String id,Date d,double hours,double hourly_rate,double fixed_cost,EmployeeType type){
        this.id=id;
        this.date=d;
        this.hours=hours;
        this.hourly_rate=hourly_rate;
        this.fixed_cost=fixed_cost;
        this.type=type;
    }
    
    public double cost(){
        return (hours*hourly_rate)+fixed_cost;
    }
    
    public Date getDate(){
        return date;
    }
    public String getId(){
        return id;
    }
    
}
