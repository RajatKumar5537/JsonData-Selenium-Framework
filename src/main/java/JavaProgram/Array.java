package JavaProgram;

import java.util.ArrayList;
import java.util.List;

public class Array {

		
		/*
		 * check if string is palindrome: race a car
		 */
	
		 public static void main(String[] args) {
		        String input = "race a car";

		        boolean isPalindrome = isPalindrome(input);
		        
		        if (isPalindrome) {
		            System.out.println(input + " is a palindrome");
		        } else {
		            System.out.println(input + " is not a palindrome");
		        }
		    }

		    // Corrected the method to be outside the main method and fixed issues
		    public static boolean isPalindrome(String str) {
		        // Remove non-alphanumeric characters and convert to lowercase
		        str = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

		        int left = 0;
		        int right = str.length() - 1;
		        
		        // Compare characters from both ends
		        while (left < right) {
		            if (str.charAt(left) != str.charAt(right)) {
		                return false;
		            }
		            left++;
		            right--;
		        }
		        
		        return true;
		    }
	}
