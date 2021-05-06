/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;
import java.util.Date;

public class InternalEmployee extends Employee{
    
    public InternalEmployee(String id,Date d,double hours,double hourly_rate,double fixed_cost){
        super(id,d,hours,hourly_rate,fixed_cost);
    }
}
