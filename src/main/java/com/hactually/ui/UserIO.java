package com.hactually.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserIO {
    Scanner scanner = new Scanner(System.in);

    /**
     * Prints a message to standard out
     * @param message The message to be printed
     */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Reads a String from standard input
     * @param message The message to prompt the user for input
     * @return The input from the user
     */
    public String readString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Ensures the user selects an integer
     * @param prompt A string to be printed to indicate what the user should input
     * @return An integer choice from the uer
     */
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(prompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Reads a double from the user, with a minimum value
     * @param prompt The message to prompt the user for input
     * @param minimumArea The minimum value accepted
     * @return The String value of the valid input
     */
    public String readDoubleAsString(String prompt, double minimumArea) {
        double result = -10.0;
        do {
            try {
                result = Double.parseDouble(readString(prompt));
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid double :)");
            }
        } while (result < minimumArea);

        return String.valueOf(result);
    }
}
