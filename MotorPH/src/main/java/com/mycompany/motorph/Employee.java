/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

/**
 *
 * @author Kristine Parpan
 */
public class Employee {
    
    private int employeeNumber;
    private String employeeName;
    private String birthday;
    private double hourlyRate;
    private double basicSalary;
    
    public Employee (int empNumber, String empName, String bday, double hrlyRate, double bscSalary) {
        this.employeeNumber = empNumber;
        this.employeeName = empName;
        this.birthday = bday;
        this.hourlyRate = hrlyRate;
        this.basicSalary = bscSalary;
    }
    
    public int getEmployeeNumber() {
        return this.employeeNumber;
    }
    public void setEmployeeNumber(int empNumber) {
        this.employeeNumber = empNumber;
    }
    
    public String getEmployeeName() {
        return this.employeeName;
    }
    public void setEmployeeName(String empName) {
        this.employeeName = empName;
    }
    
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String bday) {
        this.birthday = bday;
    }
    
    public double getHourlyRate() {
        return this.hourlyRate;
    }
    public void setHourlyRate(double hrlyRate) {
        this.hourlyRate = hrlyRate;
    }
    
    public double getBasicSalary() {
        return this.basicSalary;
    }
    public void setBasicSalary(double bscSalary) {
        this.basicSalary = bscSalary;
    }
}
