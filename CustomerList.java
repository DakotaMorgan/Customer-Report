/**
(1) Name: Dakota Morgan  
(2) Date: 10/15/2019 
(3) Instructor: Ms Tucker 
(4) Class: CIT249 Java II  
(5) Purpose: Read from a csv file, instantiate each record to a customer object,
    and encrypt the password on instantiation.
    Determine how many customers would fail new password requirements.
        Password length is 10 or greater.
        Passwords contain both letters and numbers.
    Output customer information to a log file with the original password, encrypted 
    password, unencrypted password, and number of password exceptions generated.
**/

import java.io.*;
import java.util.Scanner;
import java.util.Calendar;

/** 
 * Declare class as final
 */
public final class CustomerList
{
    /** 
     * Declare class variables
     */
    private static Scanner fileScan, lineScan;
    
    private static BufferedReader inFile = null;
    private static PrintWriter outFile = null;
    
    private static final String heading1 = "ID\tFirst Name\tLast Name\tPassword From File\tEncrypted Password\tUnencrypted Password\tPassword Exceptions";
    private static final String heading2 = "________________________________________________________________________________________________________________________________________";
    private static boolean needToPrintHeadings = true;
    private static boolean hasLetter, hasNumber;
    private static String record;
    private static int customerRecordCount = 0;
    
    private static int tooShortandNotComplexInvalidPWCount = 0;
    private static int notComplexInvalidPWCount = 0;
    private static int tooShortInvalidPWCount = 0;
    
    private static String customerID, lastName, firstName, emailAddress, username, password;
    private static float bankBalance;
    
    private static InvalidPWException invalidPW;
    private static String errorMessage;

    public static void main (String[] args) throws  IOException, InvalidPWException
    {
        try
        {
            /** 
             * Create scanner and PrintWriter objects
             */
            fileScan = new Scanner(new File("Customer.csv"));
            outFile = new PrintWriter(new FileWriter("CustomerReport.txt"));
            
            /** 
             * Output started status to terminal
             */
            System.out.println("\n---PROCESSING In Progress...Please wait...\n");
            
            /** 
             * Add date to top of log
             * Start printing lines of data to log while there is a line left
             */
            outFile.println(Calendar.getInstance().getTime());
            outFile.println();

            while (fileScan.hasNext())
            {
                /** 
                 * Print heading if needed
                 */
                if (needToPrintHeadings){   
                    outFile.println(heading1);
                    outFile.println(heading2);
                    needToPrintHeadings = false;
                }
        
                /** 
                 * Read a line from the file
                 */
                record = fileScan.nextLine();
                
                /** 
                 * Set up a scanner object to parse the line read using , as the delimiter
                 */
                lineScan = new Scanner(record);
                lineScan.useDelimiter(",");
                
                /** 
                 * Read and set each customer value
                 */
                customerID = lineScan.next();
                lastName = lineScan.next();
                firstName = lineScan.next();
                emailAddress = lineScan.next();
                username = lineScan.next();
                bankBalance = lineScan.nextFloat();
                password = lineScan.next();
                
                /** 
                 * Set password checking variables to false for each record
                 */
                errorMessage = "";
                hasLetter = false;
                hasNumber = false;
                
                /** 
                 * Check if a password contains both letters and numbers
                 * Sets appropriate exception object, depending on if password is
                 * too short, not complex, or both, and increments count.
                 */
                try{
                    for(int i = 0; i < password.length(); i++){
                        if(Character.isLetter(password.charAt(i))){
                            hasLetter = true;
                        }
                        if(Character.isDigit(password.charAt(i))){
                            hasNumber = true;
                        }
                    }
                    if(password.length() < 10 && (!hasLetter || !hasNumber)){
                        tooShortandNotComplexInvalidPWCount++;
                        tooShortInvalidPWCount++;
                        notComplexInvalidPWCount++;
                        invalidPW = new InvalidPWException ("Too Short And Not Complex");
                        throw invalidPW;
                    }
                    
                    if(password.length() < 10){
                        tooShortInvalidPWCount++;
                        invalidPW = new InvalidPWException ("Too Short");
                        throw invalidPW;
                    }
                    
                    if(!hasLetter || !hasNumber){
                        notComplexInvalidPWCount++;
                        invalidPW = new InvalidPWException ("Not Complex");
                        throw invalidPW;
                    }
                }
                /** 
                 * Sets errorMessage to the exception message
                 */
                catch(InvalidPWException pwException){
                    errorMessage = pwException.getMessage();
                }
                /** 
                 * Assign the 7 parts of data from current record to Customer object
                 */
                Customer customer = new Customer(customerID, lastName, firstName
                , emailAddress, username, bankBalance, password);
                
                /** 
                 * Increment record count, output formatted log record, with errors
                 */
                customerRecordCount++;
                
                outFile.printf("%-8s%-16s%-16s%-24s%-24s%-24s%-24s%n", customer.getCustomerID()
                , customer.getFirstName(), customer.getLastName(), password
                , customer.getPassword(), customer.decrypt(), errorMessage);
            }//endWhile
            
            /** 
             * Add total records processed and number of password exceptions to end of log
             */
            outFile.println();
            outFile.println("Total records processed: " + Integer.toString(customerRecordCount));
            outFile.println("Passwords not complex: " + Integer.toString(notComplexInvalidPWCount));
            outFile.println("Passwords too short: " + Integer.toString(tooShortInvalidPWCount));
            outFile.println("Passwords too short AND not complex: " 
            + Integer.toString(tooShortandNotComplexInvalidPWCount));
            
            
            /** 
             * Output finished progress to terminal
             */
            System.out.println ("\n---PROCESSING COMPLETED---\n\n");
            System.out.println ("\n---Your report can be found in CustomerReport.txt\n\n");

        }
        
        finally
        {
            /** 
             * Close output file
             */
            if (outFile != null)
            {
                outFile.close();
            }
        }
    }//endMain
 }
