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

/** 
 * Declare class as final
 */
public final class Customer implements Encryptable
{
    /** 
     * Declare class variables
     */
    private String customerID;
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String username;
    private float bankBalance;
    private String password;
    private boolean passwordIsEncrypted = false;
    
    private static final char[] key = {'A','B','C','D','E','F','G','H','I','J','K','L'
        ,'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f'
        ,'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
        ,'0','1','2','3','4','5','6','7','8','9'};
        
    private static final char[] cipher = {'s','I','p','N','u','Q','2','o','Y','6','X','S'
        ,'V','7','L','b','l','t','c','g','a','v','w','R','n','3','J','C','U','T','K','G'
        ,'5','4','9','P','0','M','y','k','d','E','r','8','B','W','i','A','H','F','D','j'
        ,'1','h','z','O','x','Z','m','e','q','f'};

    /**
     * Constructor for objects of class Customer, set values to blank if invalid range
     * Encrypt password on instantiation
     */
    public Customer(String customerID, String lastName, String firstName, 
    String emailAddress, String username, float bankBalance, String password)
    {
        if (customerID.length() == 6){
            this.customerID = customerID;
        }
        else{
            this.customerID = "";
        }
        
        if (lastName.length() > 0){
            this.lastName = lastName;
        }
        else{
            this.lastName = "";
        }
        
        if (firstName.length() > 0){
            this.firstName = firstName;
        }
        else{
            this.firstName = "";
        }
        
        if (emailAddress.length() > 0){
            this.emailAddress = emailAddress;
        }
        else{
            this.emailAddress = "";
        }
        
        if (username.length() > 0){
            this.username = username;
        }
        else{
            this.username = "";
        }
        
        if (bankBalance > 0){
            this.bankBalance = bankBalance;
        }
        else{
            this.bankBalance = 0;
        }
        
        if (password.length() > 0){
            this.password = password;
            passwordIsEncrypted = false;
            encrypt();
        }
        else{
            this.password = "";
        }
    }
    
    /**
     * Accessors
     * I have included accessors for all data needed in the CustomerList driver,
     * in addition to accessors that may be needed by other classes or drivers.
     * The password returned should always be the encrypted version of the password.
     * All methods except toString declared as final.
     */
    public final String getCustomerID(){
        return customerID;
    }
    
    public final String getFirstName(){
        return firstName;
    }
    
    public final String getLastName(){
        return lastName;
    }
    
    public final String getPassword(){
        return password;
    }
    
    public final String getEmailAddress(){
        return emailAddress;
    }
    
    public final String getUsername(){
        return username;
    }
    
    public final float getBankBalance(){
        return bankBalance;
    }
    
    /**
     * Mutators
     * I have included mutators for all data that may need to be modified.
     * The password should always be encrypted when set. SetPassword could be modified
     * to only accept new password requirements. Add amount to bankBalance if resulting 
     * ammount is not negative.
     */
    public final void setCustomerID(String customerID){
        if (customerID.length() == 6){
            this.customerID = customerID;
        }
    }
    
    public final void setFirstName(String firstName){
        if (firstName.length() > 0){
            this.firstName = firstName;
        }
    }
    
    public final void setLastName(String lastName){
        if (lastName.length() > 0){
            this.lastName = lastName;
        }
    }
    
    public final void setUsername(String username){
        if (username.length() > 0){
            this.username = username;
        }
    }
    
    public final void setEmailAddress(String emailAddress){
        if (emailAddress.length() > 0){
            this.emailAddress = emailAddress;
        }
    }
    
    public final void setPassword(String password){
        if (password.length() > 0){
            this.password = password;
            passwordIsEncrypted = false;
            encrypt();
        }
    }
    
    public final void adjustBalance(float ammount){
        if ((bankBalance + ammount) >= 0){
            bankBalance += ammount;
        }
    }
    
    @Override
    public final void encrypt()
    {
        /** 
         * Replace each character in string with cipher[] character equivalent
         */
        if (!passwordIsEncrypted){
            String masked = "";
            
            for (int i = 0; i < password.length(); i++){
                for (int j = 0; j < cipher.length; j++){
                    if (password.charAt(i) == key[j]){
                        masked = masked + (cipher[j]);
                    }
                }
            }
            password = masked;
            passwordIsEncrypted = true;
        }
    }
    @Override
    public final String decrypt()
    {
        /** 
         * Replace each character in string with key[] character equivalent
         */
        if (passwordIsEncrypted){
            String unmasked = "";
            
            for (int i = 0; i < password.length(); i++){
                for (int j = 0; j < cipher.length; j++){
                    if (password.charAt(i) == cipher[j]){
                        unmasked = unmasked + (key[j]);
                    }
                }
            }
            return unmasked;
        }
        else{
            return "";
        }
    }
    
    /** 
     * Output formatted string containing customer information
     * Password and bankBalance omitted
     */
    public String toString(String customerID, String lastName, String firstName, 
    String emailAddress, String userName, float bankBalance)
    {
        return "\nCustomer ID: " + customerID + " Last Name: " + lastName + " First Name: "
        + firstName + "Email Address: " + emailAddress + "Username: " + userName;
    }
}
