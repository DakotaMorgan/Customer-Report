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

public interface Encryptable
{
    public void encrypt();
    public String decrypt();
}
