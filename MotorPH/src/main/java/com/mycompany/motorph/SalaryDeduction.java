/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

/**
 *
 * @author Kristine Parpan
 */
public class SalaryDeduction {
    
    private double sssContribution;
    private double philHealthContribution;
    private double pagibigContribution;
    private double witholdingTax;
    
    public SalaryDeduction (double sss, double philHealth, double pagibig, double wTax) {
        this.sssContribution = sss;
        this.philHealthContribution = philHealth;
        this.pagibigContribution = pagibig;
        this.witholdingTax = wTax;
    }
    
    public double getSSSContribution(){
        return this.sssContribution;
    }
    public void setSSSContribution(double sss){
        this.sssContribution = sss;
    }
    
    public double getPhilHealthContribution(){
        return this.philHealthContribution;
    }
    public void setPhilHealthContribution(double philHealth){
        this.philHealthContribution = philHealth;
    }
    
    public double getPagibigContribution(){
        return this.pagibigContribution;
    }
    public void setPagibigContribution(double pagibig){
        this.pagibigContribution = pagibig;
    }
    
    public double getWitholdingTax(){
        return this.witholdingTax;
    }
    public void setWitholdingTax(double wTax){
        this.witholdingTax = wTax;
    }
}
