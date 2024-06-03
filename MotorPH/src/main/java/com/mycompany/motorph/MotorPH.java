/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristine Parpan
 */
public class MotorPH {
    
    // Constant Variables
    private static final String EMPLOYEE_DETAILS_CSV = System.getProperty("user.dir") + "/src/main/resources/employee_details.csv";
    private static final String ATTENDANCE_CSV = System.getProperty("user.dir") + "/src/main/resources/attendance.csv";

    // Global Variables
    private static Employee employee;
    // private static Attendance attendance;
    // private static SalaryDeduction salaryDeduction;
    
    public static BufferedReader employeeDetailsReader = null;
    public static BufferedReader attendanceReader = null;
    // public static String employee = "";
    // public static int employeeNumber = 0;
    // public static double hourlyRate = 0;
    public static int year = 0;
    public static boolean isYearInRecord = false;
    public static int monthNumber = 0;
    public static int monthTotalWeeks = 0;
    public static String month = "";
    public static double totalHoursWorked = 0;
    // public static double employee.getBasicSalary() = 0;
    public static List<AbstractMap.SimpleEntry<Integer, List<Attendance>>> weeklyAttendances;
    
    public static void main(String[] args) {
        while (true) {
            resetData();
            System.out.println("**************************************************");
            System.out.println("** Welcome to MotorPH's Employee Salary System **");
            
            readCSVFiles();
            getEmployee();
            
            System.out.println("");
            System.out.println("**************** Employee Details ****************");
            System.out.println("");
            
            if (employee != null) {
                viewEmployeeProfile();
                
                System.out.println("");
                System.out.println("*************** Salary Computation ***************");
                System.out.println("");

                getYear();
                if (year > (Year.now().getValue() - 5) && year <= Year.now().getValue()) {
                    getMonthNumber();
                    if (monthNumber >= 1 && monthNumber <= 12) {

                        month = new DateFormatSymbols().getMonths()[monthNumber - 1];
                        System.out.println("Entered Month: " + month);
                        System.out.println("Total Number of Weeks: " + monthTotalWeeks);
                        
                        getWeeklyAttendance();
                        if (!weeklyAttendances.isEmpty()) {
                            calculateSalary();
                        }
                        else {
                            System.out.println("No attendance record for " + month + " " + year);
                        }
                    }
                    else {
                        System.out.println("Invalid Month Number. Please Try Again.");
                    }
                }
                else {
                    System.out.println("Invalid Year. Please Try Again.");
                }
            }
            else {
                System.out.println("Employee Not Found. Please Try Again.");
            }
            
            System.out.println("");
            System.out.println("***************** End of Session *****************");
            System.out.println("**************************************************");
            System.out.println("");
        }
    }
           
    // Method used for reseting data used in the system
    public static void resetData () {
        employeeDetailsReader = null;
        attendanceReader = null;
        // employee = "";
        // employeeNumber = 0;
        // hourlyRate = 0;
        year = 0;
        isYearInRecord = false;
        monthNumber = 0;
        monthTotalWeeks = 0;
        month = "";
        totalHoursWorked = 0;
        // employee.getBasicSalary() = 0;
        weeklyAttendances = null;
    }
    
    // Method used for reading CSV files
    private static void readCSVFiles() {
        try {
            employeeDetailsReader = new BufferedReader(new FileReader(EMPLOYEE_DETAILS_CSV));
            attendanceReader = new BufferedReader(new FileReader(ATTENDANCE_CSV));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MotorPH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Method used for getting Employee
    public static void getEmployee () {
        System.out.print("Please Enter Employee Number: ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            int employeeNumber = Integer.parseInt(inputReader.readLine());
            String employeeDetailsRow = "";
            
            // read each row of the CSV file
            while ((employeeDetailsRow = employeeDetailsReader.readLine()) != null) {
                // replace commas inside string
                String employeeDetails = employeeDetailsRow.replaceAll(",(?!(([^\"]*\"){2})*[^\"]*$)", ";x;");
                String[] splitEmployeeDetails = employeeDetails.split(",");

                // check if employee number matches row data
                if (Integer.parseInt(splitEmployeeDetails[0]) == employeeNumber) {
                    String employeeName = cleanString(splitEmployeeDetails[2] + " " + splitEmployeeDetails[1]);
                    String birthday = cleanString(splitEmployeeDetails[3]);
                    double hourlyRate = Double.parseDouble(cleanString(splitEmployeeDetails[splitEmployeeDetails.length - 1]));
                    double basicSalary = Double.parseDouble(cleanString(splitEmployeeDetails[splitEmployeeDetails.length - 6]));
                    
                    // Instantiate Employee Object
                    employee = new Employee(employeeNumber, employeeName, birthday, hourlyRate, basicSalary);
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    // Method used for View Employee Profile Process
    public static void viewEmployeeProfile () {
        // display employee details
        System.out.println("Employee Number: " + employee.getEmployeeNumber());
        System.out.println("Employee Name: " + employee.getEmployeeName());
        System.out.println("Employee Birthday: " + employee.getBirthday());
        // display salary and hourly rate
        System.out.println("Basic Salary: " + employee.getBasicSalary());
        System.out.println("Hourly Rate: " + employee.getHourlyRate());
    }
    
    // Method used for getting Year from user input
    public static void getYear () {
        System.out.print("Enter Attendance Year: ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
           year = Integer.parseInt(inputReader.readLine());
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    // Method used for getting Month Number from user input
    public static void getMonthNumber () {
        System.out.print("Enter Month Number For Salary Computation (1-12): ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
           monthNumber = Integer.parseInt(inputReader.readLine());
           Calendar calendar = Calendar.getInstance();
           calendar.set(Calendar.YEAR, year);
           calendar.set(Calendar.MONTH, monthNumber - 1);
           calendar.set(Calendar.DAY_OF_MONTH, 1);
           // get total number of weeks of the selected month
           monthTotalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
           
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    // Method used for calculating employee salary
    public static void calculateSalary () {
        System.out.println("------------------------------");
        
        // loop through total number of weeks to display Salary Computation
        for (int a = 1; a <= monthTotalWeeks; a++) {
            // initialize variables to be used for computation
            double weeklyHoursWorked = 0;
            boolean isRecordAdded = false;
            
            // loop through list of record of weekly attendance by specific employee
            for (int i = 0; i < weeklyAttendances.size(); i++) {
                // check if week number is equal to current loop number
                // if true, display the calculated salary for that week number
                if (weeklyAttendances.get(i).getKey().equals(a)) {
                    isRecordAdded = true;
                    weeklyHoursWorked = calculateHoursWorked(weeklyAttendances.get(i), weeklyHoursWorked);
                    System.out.println("Week # " + weeklyAttendances.get(i).getKey() + " Total Hours: " + String.format("%.2f", weeklyHoursWorked));

                    calculateGrossWage (weeklyHoursWorked, weeklyAttendances.get(i).getKey());
                    calculateNetWage (weeklyHoursWorked, weeklyAttendances.get(i).getKey());

                    System.out.println("------------------------------");
                }
            }
            
            // if no record added for the specific week, display calculations with 0 hours worked
            if (isRecordAdded == false) {
                System.out.println("Week # " + a + " Total Hours: 0.0");
                calculateGrossWage (0.0, a);
                calculateNetWage (0.0, a);
                System.out.println("------------------------------");
            }
        }
    }
    
    // Method used for calculating hours per week depending on the month number provided
    public static double calculateHoursWorked (AbstractMap.SimpleEntry<Integer, List<Attendance>> weeklyAttendance, double weeklyHoursWorked) {
        // loop through employee weekly attendance record
        for (int a = 0; a < weeklyAttendance.getValue().size(); a++) {
            double hoursWorkedPerDay = getHoursWorkedPerDay(weeklyAttendance.getValue().get(a));
            weeklyHoursWorked += hoursWorkedPerDay;
        }
        return weeklyHoursWorked;
    }
    
    // Method used for calculating the Weekly Gross Wage
    public static void calculateGrossWage (double weeklyHoursWorked, int week) {
        double grossWage = weeklyHoursWorked * employee.getHourlyRate();
        
        System.out.println("Week # " + week + " Gross Wage: " + String.format("%.2f", grossWage));
    }
    
    // Method used for calculating the Weekly Net Wage
    public static void calculateNetWage (double weeklyHoursWorked, int week) {
        double grossWage = Double.parseDouble(String.format("%.2f", (weeklyHoursWorked * employee.getHourlyRate())));
        
        double sssContribution = Double.parseDouble(String.format("%.2f", getSSSContribution()));
        double philHealthContribution = Double.parseDouble(String.format("%.2f", getPhilHealthContribution()));
        double pagibigContribution = Double.parseDouble(String.format("%.2f", getPagibigContribution()));
        
        double witholdingTax = Double.parseDouble(String.format("%.2f", (getWitholdingTax(getSSSContribution(), getPhilHealthContribution(), getPagibigContribution()))));
        
        SalaryDeduction salaryDeduction = new SalaryDeduction(sssContribution, philHealthContribution, pagibigContribution, witholdingTax);
        
        double netWage = grossWage - (salaryDeduction.getSSSContribution() / monthTotalWeeks) - (salaryDeduction.getPhilHealthContribution() / monthTotalWeeks) - (salaryDeduction.getPagibigContribution() / monthTotalWeeks) - (salaryDeduction.getWitholdingTax() / monthTotalWeeks);
                
        // System.out.println("Weekly SSS Contrib: " + sssContribution);
        // System.out.println("Weekly PhilHealth Contrib: " + philHealthContribution);
        // System.out.println("Weekly pagibig Contrib: " + pagibigContribution);
        // System.out.println("Weekly Witholding Tax: " + witholdingTax);
        System.out.println("Week # " + week + " Net Wage: " + String.format("%.2f", (netWage)));
    }
    
    
    // Method used for getting weekly attendance depending on the selected month
    public static void getWeeklyAttendance () {  
        // Initialize variables need for this method
        weeklyAttendances = new ArrayList<AbstractMap.SimpleEntry<Integer, List<Attendance>>>();
        String attendanceRow = "";
        List<String> allEmployeeAttendance = new ArrayList<String>();
        List<String> monthlyEmployeeAttendance = new ArrayList<String>();
        
        try {
            // loop through attendance csv
            while ((attendanceRow = attendanceReader.readLine()) != null) {
                String[] splitAttendance = attendanceRow.split(",");
                
                // check if data row is equal to the entered employee number
                // if true, add row to attendances variable
                if (Integer.parseInt(splitAttendance[0]) == employee.getEmployeeNumber()) {
                    allEmployeeAttendance.add(attendanceRow);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MotorPH.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Get all attendances with same month and year
        for (int i = 0; i < allEmployeeAttendance.size(); i++) {
            String date = allEmployeeAttendance.get(i).split(",")[1];
            int dateMonth = Integer.parseInt(date.split("/")[0]);
            int dateYear = Integer.parseInt(date.split("/")[2]);
                    
            if(dateMonth == monthNumber && dateYear == year) {
                monthlyEmployeeAttendance.add(allEmployeeAttendance.get(i));
            }
        }
        
        // Get all attendances per week
        for (int i = 0; i < monthlyEmployeeAttendance.size(); i++) {
            String date = monthlyEmployeeAttendance.get(i).split(",")[1];
            // get week number of the date variable to use as key for the weeklyAttendances list
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(date.split("/")[2]));
            calendar.set(Calendar.MONTH,Integer.parseInt(date.split("/")[0]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(date.split("/")[1]));
            int week = calendar.get(calendar.WEEK_OF_MONTH);
            // System.out.println("Date: " + date);
            // System.out.println("Week: " + week);
            
            // check if weeklyAttendances list is empty
            // if true, add the first monthly attendance record into the weeklyAttendances list and use the week # as key
            // otherwise, check if the record already exist in the weeklyAttendances List
            if (weeklyAttendances.isEmpty()) {
                String timeIn = monthlyEmployeeAttendance.get(i).split(",")[2];
                String timeOut = monthlyEmployeeAttendance.get(i).split(",")[3];
                
                List<Attendance> weeklyAttendance = new ArrayList<Attendance>();
                Attendance attendance = new Attendance(year, monthNumber, Integer.parseInt(date.split("/")[1]), timeIn, timeOut);
                
                weeklyAttendance.add(attendance);
                
                weeklyAttendances.add(new AbstractMap.SimpleEntry<Integer, List<Attendance>>(week, weeklyAttendance));
            }
            else {
                // initialize marker if attendence record is already added in the list
                boolean isRecordAdded = false;
                
                // loop through the weeklyAttendances list and check if record already exists in the list
                for (int a = 0; a < weeklyAttendances.size(); a++) {
                    // check if week number from the current attendance date is equal to the week number (key) of the current data in the weeklyAttendances list
                    // if true, check if the date in the current attendance record already exists in the current data of the weeklyAttendances list
                    if (weeklyAttendances.get(a).getKey() == week) {
                        for (int b = 0; b < weeklyAttendances.get(a).getValue().size(); b++) {
                            int attendanceDate = weeklyAttendances.get(a).getValue().get(b).getDate();
                            int attendanceMonth = weeklyAttendances.get(a).getValue().get(b).getMonth();
                            int attendanceYear = weeklyAttendances.get(a).getValue().get(b).getYear();
                            String attDate = attendanceMonth + "/" + attendanceDate + "/" + attendanceYear;
                            
                            // set isRecordAdded if date already exists in the weeklyAttendances list 
                            if (attDate.equals(date)) {
                                isRecordAdded = true;
                            }
                        }
                        
                        // check if isRecordAdded is false
                        // if false, add the current attendance record in the weekly attendances list with the same week # (key)
                        if (isRecordAdded == false) {
                            String timeIn = monthlyEmployeeAttendance.get(i).split(",")[2];
                            String timeOut = monthlyEmployeeAttendance.get(i).split(",")[3];

                            Attendance attendance = new Attendance(year, monthNumber, Integer.parseInt(date.split("/")[1]), timeIn, timeOut);

                            weeklyAttendances.get(a).getValue().add(attendance);
                            isRecordAdded = true;
                        }
                    }
                }
                
                // check if isRecordAdded is false
                // if false, add the current attendance record in the weekly attendances list and use the week # as key
                if (isRecordAdded == false) {
                    String timeIn = monthlyEmployeeAttendance.get(i).split(",")[2];
                    String timeOut = monthlyEmployeeAttendance.get(i).split(",")[3];

                    List<Attendance> weeklyAttendance = new ArrayList<Attendance>();
                    Attendance attendance = new Attendance(year, monthNumber, Integer.parseInt(date.split("/")[1]), timeIn, timeOut);

                    weeklyAttendance.add(attendance);
                
                    weeklyAttendances.add(new AbstractMap.SimpleEntry<Integer, List<Attendance>>(week, weeklyAttendance));
                }
            }
        }
    }
    
    // Method used for getting hours worked per day
    public static double getHoursWorkedPerDay (Attendance attendance) {
        String attDate = attendance.getMonth() + "/" + attendance.getDate() + "/" + attendance.getYear();
        
        // Convert String date and time to LocalDateTime
        LocalDateTime localDateTimeIn = getDateTime(attDate, attendance.getTimeIn());
        LocalDateTime localDateTimeOut = getDateTime(attDate, attendance.getTimeOut());
        
        // Set Grace Period as indicated in the requirements
        LocalTime gracePeriod = LocalTime.of(8, 11);
        // Check if time in is beyond grace period
        if (localDateTimeIn.toLocalTime().isBefore(gracePeriod)){
            // reset time in to 8AM to remove deduction
            localDateTimeIn = getDateTime(attDate, "08:00");
        }
        
        // Calculate Time Difference between timein and timeout
        long timeDiff = localDateTimeIn.toLocalTime().until(localDateTimeOut.toLocalTime(), ChronoUnit.MINUTES);
        double timeDiffDouble = Double.parseDouble(Long.toString(timeDiff)) / 60;
        
        if (timeDiffDouble <= 0) {
            // return zero for invalid data
            return 0.00;
        }
        else {
            // return total hours worked - 1 hour of breaktime
            timeDiffDouble = Double.parseDouble(String.format("%.2f", timeDiffDouble -1));
            
            return timeDiffDouble;
        }
    }
    
    // Method used for converting String date and String time
    public static LocalDateTime getDateTime (String date, String time) {
        String[] splitDate = date.split("/");
        String[] splitTime = time.split(":");
        
        // Convert String date to LocalDate
        LocalDate localDate = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]));
        // Convert String time to LocalTime
        LocalTime localTime = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
        // Combine date and time
        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
        
        return dateTime;
    }
    
    
    // Method used for removing unnecessary characters in a string
    public static String cleanString (String input) {
    
        input = input.replaceAll(";x;", "");
        input = input.replaceAll("\"", "");
        
        return input;
    }

    // Method used for getting SSS Contribution
    // hard coded values are based on the Google Sheet Provided
    public static double getSSSContribution() {
        if (employee.getBasicSalary() < 3250) {
            return 135.00;
        }
        else if (employee.getBasicSalary() >= 3250 && employee.getBasicSalary() <= 3750) {
            return 157.50;
        }
        else if (employee.getBasicSalary() >= 3750 && employee.getBasicSalary() <= 4250) {
            return 180.00;
        }
        else if (employee.getBasicSalary() >= 4250 && employee.getBasicSalary() <= 4750) {
            return 202.50;
        }
        else if (employee.getBasicSalary() >= 4750 && employee.getBasicSalary() <= 5250) {
            return 225.00;
        }
        else if (employee.getBasicSalary() >= 5250 && employee.getBasicSalary() <= 5750) {
            return 247.50;
        }
        else if (employee.getBasicSalary() >= 5750 && employee.getBasicSalary() <= 6250) {
            return 270.00;
        }
        else if (employee.getBasicSalary() >= 6250 && employee.getBasicSalary() <= 6750) {
            return 292.50;
        }
        else if (employee.getBasicSalary() >= 6750 && employee.getBasicSalary() <= 7250) {
            return 315.00;
        }
        else if (employee.getBasicSalary() >= 7250 && employee.getBasicSalary() <= 7750) {
            return 337.50;
        }
        else if (employee.getBasicSalary() >= 7750 && employee.getBasicSalary() <= 8250) {
            return 360.00;
        }
        else if (employee.getBasicSalary() >= 8250 && employee.getBasicSalary() <= 8750) {
            return 382.50;
        }
        else if (employee.getBasicSalary() >= 8750 && employee.getBasicSalary() <= 9250) {
            return 405.00;
        }
        else if (employee.getBasicSalary() >= 9250 && employee.getBasicSalary() <= 9750) {
            return 427.50;
        }
        else if (employee.getBasicSalary() >= 9750 && employee.getBasicSalary() <= 10250) {
            return 450.00;
        }
        else if (employee.getBasicSalary() >= 10250 && employee.getBasicSalary() <= 10750) {
            return 472.50;
        }
        else if (employee.getBasicSalary() >= 10750 && employee.getBasicSalary() <= 11250) {
            return 495.00;
        }
        else if (employee.getBasicSalary() >= 11250 && employee.getBasicSalary() <= 11750) {
            return 517.50;
        }
        else if (employee.getBasicSalary() >= 11750 && employee.getBasicSalary() <= 12250) {
            return 540.00;
        }
        else if (employee.getBasicSalary() >= 12250 && employee.getBasicSalary() <= 12750) {
            return 562.50;
        }
        else if (employee.getBasicSalary() >= 12750 && employee.getBasicSalary() <= 13250) {
            return 585.00;
        }
        else if (employee.getBasicSalary() >= 13250 && employee.getBasicSalary() <= 13750) {
            return 607.50;
        }
        else if (employee.getBasicSalary() >= 13750 && employee.getBasicSalary() <= 14250) {
            return 630.00;
        }
        else if (employee.getBasicSalary() >= 14250 && employee.getBasicSalary() <= 14750) {
            return 652.50;
        }
        else if (employee.getBasicSalary() >= 14750 && employee.getBasicSalary() <= 15250) {
            return 675.00;
        }
        else if (employee.getBasicSalary() >= 15250 && employee.getBasicSalary() <= 15750) {
            return 697.50;
        }
        else if (employee.getBasicSalary() >= 15750 && employee.getBasicSalary() <= 16250) {
            return 720.00;
        }
        else if (employee.getBasicSalary() >= 16250 && employee.getBasicSalary() <= 16750) {
            return 742.50;
        }
        else if (employee.getBasicSalary() >= 16750 && employee.getBasicSalary() <= 17250) {
            return 765.00;
        }
        else if (employee.getBasicSalary() >= 17250 && employee.getBasicSalary() <= 17750) {
            return 787.50;
        }
        else if (employee.getBasicSalary() >= 17750 && employee.getBasicSalary() <= 18250) {
            return 810.00;
        }
        else if (employee.getBasicSalary() >= 18250 && employee.getBasicSalary() <= 18750) {
            return 832.50;
        }
        else if (employee.getBasicSalary() >= 18750 && employee.getBasicSalary() <= 19250) {
            return 855.00;
        }
        else if (employee.getBasicSalary() >= 19250 && employee.getBasicSalary() <= 19750) {
            return 877.50;
        }
        else if (employee.getBasicSalary() >= 19750 && employee.getBasicSalary() <= 20250) {
            return 900.00;
        }
        else if (employee.getBasicSalary() >= 20250 && employee.getBasicSalary() <= 20750) {
            return 922.50;
        }
        else if (employee.getBasicSalary() >= 20750 && employee.getBasicSalary() <= 21250) {
            return 945.00;
        }
        else if (employee.getBasicSalary() >= 21250 && employee.getBasicSalary() <= 21750) {
            return 967.50;
        }
        else if (employee.getBasicSalary() >= 21750 && employee.getBasicSalary() <= 22250) {
            return 990.00;
        }
        else if (employee.getBasicSalary() >= 22250 && employee.getBasicSalary() <= 22750) {
            return 1012.50;
        }
        else if (employee.getBasicSalary() >= 22750 && employee.getBasicSalary() <= 23250) {
            return 1035.00;
        }
        else if (employee.getBasicSalary() >= 23250 && employee.getBasicSalary() <= 23750) {
            return 1057.50;
        }
        else if (employee.getBasicSalary() >= 23750 && employee.getBasicSalary() <= 24250) {
            return 1080.00;
        }
        else if (employee.getBasicSalary() >= 24250 && employee.getBasicSalary() <= 24750) {
            return 1102.50;
        }
        else {
            return 1125.00;
        }
    }

    // Method used for getting PhilHealth Contribution
    // hard coded values are based on the Google Sheet Provided
    public static double getPhilHealthContribution() {
        if (employee.getBasicSalary() <= 10000.00) {
            return 150.00;
        }
        else if (employee.getBasicSalary() > 10000 && employee.getBasicSalary() < 60000) {
            return (employee.getBasicSalary() * 0.03) / 2;
        }
        else {
            return 900;
        }
    }

    // Method used for getting Pagibig Contribution
    // hard coded values are based on the Google Sheet Provided
    public static double getPagibigContribution() {
        if (employee.getBasicSalary() >= 1000 && employee.getBasicSalary() <= 1500) {
            return employee.getBasicSalary() * 0.01;
        }
        else if (employee.getBasicSalary() > 1500){
            if ((employee.getBasicSalary() * 0.02) <= 100) {
                return employee.getBasicSalary() * 0.02;
            }
            else {
                return 100;
            }
        }
        else {
            return 0.0;
        }
    }

    // Method used for getting Witholding Tax
    // hard coded values are based on the Google Sheet Provided
    public static double getWitholdingTax(double sss, double philHealth, double pagibig) {
        double deductedSalary = employee.getBasicSalary() - sss - philHealth - pagibig;
        
        if (deductedSalary <= 20832){
            return 0.0;
        }
        else if (deductedSalary >= 20833 && deductedSalary < 33333) {
            return (deductedSalary - 20833) * 0.2;
        }
        else if (deductedSalary >= 33333 && deductedSalary < 66667) {
            return ((deductedSalary - 33333) * 0.25) + 2500;
        }
        else if (deductedSalary >= 66667 && deductedSalary < 166667) {
            return ((deductedSalary - 66667) * 0.3) + 10833;
        }
        else if (deductedSalary >= 166667 && deductedSalary < 666667) {
            return ((deductedSalary - 166667) * 0.32) + 40833.33;
        }
        else {
            return ((deductedSalary - 666667) * 0.35) + 200833.33;
        }
    }
}
