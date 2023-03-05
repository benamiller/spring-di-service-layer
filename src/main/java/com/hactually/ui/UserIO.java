package com.hactually.ui;

import java.util.Scanner;

public class UserIO {
    Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }

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

    public String readDoubleAsString(String prompt, double minimumArea) {
        double result;
        do {
            result = Double.parseDouble(readString(prompt));
        } while (result < minimumArea);

        return String.valueOf(result);
    }
}
