/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.motorph;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristine Joy Parpan
 */
public class MotorPHTest {
    
    public MotorPHTest() {
    }

    @Test
    public void testCleanString() {
        String input = "1;x;000.00";
        String expected = "1000.00";
        String actual = MotorPH.cleanString(input);
        
        assertEquals(expected, actual);
    }

    @Test
    public void testGetHoursWorkedPerDay() {
        String timeIn = "08:00";
        String timeOut = "17:00";
        double expected = 8.00;
        
        Attendance attendance = new Attendance(2024, 3, 16, timeIn, timeOut);
        
        double actual = MotorPH.getHoursWorkedPerDay(attendance);
        
        assertEquals(expected, actual, 0.0);
    }
    
}
