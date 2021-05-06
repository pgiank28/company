/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;

import java.util.Date;

public class EmployeeFactory{
    public Employee getEmployee(String id,Date d,double hours,double hourly_rate,double fixed_cost,String type){
        System.out.println(type);
        if(type.equalsIgnoreCase("i")){
            return new InternalEmployee(id,d,hours,hourly_rate,fixed_cost);
        }
        if(type.equalsIgnoreCase("o")){
            return new ExternalEmployee(id,d,hours,hourly_rate,fixed_cost);
        }
        
        throw new NullPointerException();
    }
}
