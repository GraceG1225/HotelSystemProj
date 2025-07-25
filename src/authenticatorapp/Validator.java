/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

public class Validator {

    // Check if the email is pass intructions
    public static boolean isValidEmail(String email) {
        return email != null && email.length() >= 14 && email.endsWith("@gmail.com") && email.substring(0, email.indexOf("@")).length() >= 4; 
    }

    
    // Check if the password good to work
    public static boolean isValidPassword(String password) {
          if (password == null || password.length() < 5) return false;
       boolean hasSymbol = false;
          boolean hasUpper = false;
        boolean hasDigit = false;
        

        // Loop to check password needed.
        for (char c : password.toCharArray()) {
                   if (Character.isUpperCase(c)) hasUpper = true;
             else if (!Character.isLetterOrDigit(c)) hasSymbol = true;
                   else if (Character.isDigit(c)) hasDigit = true;
           
        }
        return hasUpper && hasDigit && hasSymbol;
    }
}