/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

public class Validator {

public static boolean isValidEmail(String email) {
    if (email == null)
        return false;

    int atPos = email.indexOf('@');
    if (atPos == -1)
        return false;

    // Must have 1 to 20 chars before @ to prevent excesive emails
    if (atPos < 1 || atPos > 20)
        return false;

    // Allow @gmail.com in any case possibl
    if (!email.toLowerCase().endsWith("@gmail.com"))
        return false;

    // No spaces allowed on emails 
    if (email.contains(" "))
        return false;

    // First char will always be a letter
    char firstChar = email.charAt(0);
    if (!Character.isLetter(firstChar))
        return false;

    // Only letters and digits can be used to make email no symbols for good.
    String localPart = email.substring(0, atPos);
    for (char c : localPart.toCharArray()) {
        if (!Character.isLetterOrDigit(c))
            return false;
    }

    return true;
}


    // Check if the password good to work or not
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 5) 
            return false;

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        // This will check the number of uppcase in the password
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
        }

        // This wil lcheck the digits that was inputed in the password
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }

        // checks symbols that inputed from password
        int symbolCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
                symbolCount++;
            }
        }

       
        if (!hasSymbol && symbolCount < 1) {
            return false;
        }

        if (hasUpper && hasDigit && hasSymbol) {
            return true;
        }

        return false;
    }
}
