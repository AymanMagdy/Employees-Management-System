/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeedbapp;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asoliman
 */
public class DBmanger {
    
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pst;
    private Vector<Employee> empVec = new Vector();
    private int flagNext = 0;

    
    public DBmanger() {
        try {
            startConnection();
            getEmployee();
            closeConnection();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // DONE and working..
    private void startConnection() throws ClassNotFoundException {
        
        try {
            // to start the connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://Your_DB_server/Your_DB_name","Your_DB_user_name","Your_DB_Password");
        } catch (SQLException e){
            e.printStackTrace();
        }
         
    }
    
    
    // Getting all the data..
    public void getEmployee() throws ClassNotFoundException{
        
        try {
            startConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from EMP_DATA;"); 
            
            while(rs.next()){
                Employee emp = new Employee();
                emp.setId(Integer.parseInt(rs.getString("id")));
                emp.setFname(rs.getString("fname"));
                emp.setMname(rs.getString("mname"));
                emp.setLname(rs.getString("lname"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                
                empVec.add(emp);
            }
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    // Done..
    public Employee getFirstEmployee() throws ClassNotFoundException{
        
        Employee firstEmployee = new Employee();
        getEmployee();
        firstEmployee = empVec.firstElement();
        flagNext = 0;
        return firstEmployee;
    }
    
    // Done..
    public Employee getLastEmployee() throws ClassNotFoundException{
        getEmployee();
        Employee lastEmployee = new Employee();
        lastEmployee = empVec.lastElement();
        flagNext = empVec.size()-1;
        return lastEmployee;
    }
    
    // Done..
    public Employee getNextEmployee(){
        
        Employee nextEmployee = new Employee();
        
        if(flagNext < empVec.size()-1)
            flagNext++;
        System.out.println("flagNext: " + flagNext);
        nextEmployee = empVec.get(flagNext);
        return nextEmployee;
    }
    
    // Done..
    public Employee getPrevEmployee(){
        Employee prevEmployee = new Employee();
        
        if(flagNext > 0)
            flagNext--;
        prevEmployee = empVec.get(flagNext);
        return prevEmployee;
    }
    
    // Done..
    public boolean deleteRow(Employee emp) throws ClassNotFoundException{
        
        try {
            startConnection();
            
            PreparedStatement pst = con.prepareStatement("DELETE FROM EMP_DATA WHERE id=?");
            pst.setString(1, Integer.toString(emp.getId()));
            pst.executeUpdate();
            empVec.remove(flagNext);
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
    }
    
    // Done..
    public boolean updateRow(Employee emp) throws ClassNotFoundException{
        try {
            startConnection();
            
            pst = con.prepareStatement("UPDATE EMP_DATA SET fname=? , mname=? , lname=? , email=? , phone=? WHERE id=?");
            pst.setString(1, emp.getFname());
            pst.setString(2, emp.getMname());
            pst.setString(3, emp.getLname());
            pst.setString(4, emp.getEmail());
            pst.setString(5, emp.getPhone());
            
            pst.setString(6, Integer.toString(emp.getId())); //  WHERE and send the ID;
            
            pst.executeUpdate();
            closeConnection();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
    }
    
    // Done..
    public boolean insertRow(Employee emp) throws ClassNotFoundException{
        try {
            startConnection();
            PreparedStatement pst = con.prepareStatement("INSERT INTO EMP_DATA (fname, mname, lname, email, phone) VALUES (?,?,?,?,?) ");
            
            pst.setString(1, emp.getFname());
            pst.setString(2, emp.getMname());
            pst.setString(3, emp.getLname());
            pst.setString(4, emp.getEmail());
            pst.setString(5, emp.getPhone());
            
            
            pst.executeUpdate();
            closeConnection();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    // Done..
    private void closeConnection(){
        try {
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBmanger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
