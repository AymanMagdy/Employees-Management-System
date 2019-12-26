/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeedbapp;

/**
 *
 * @author asoliman
 */
public class EmployeeDBApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
      DBmanger test = new DBmanger();
      Employee first = test.getFirstEmployee();
      Employee next = test.getNextEmployee();
      Employee nextn = test.getNextEmployee();
      
      System.out.println(first.getId() + "\n" + next.getId() + "\n" + nextn.getId());
        
        
        
        
    }
    
}
