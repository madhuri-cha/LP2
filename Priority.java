package clg;
import java.util.Scanner;

public class PN {
	// Package declaration for the class
	

	// Importing the Scanner class from java.util package to take input from the user
	
	// Class definition named 'proirity_nonpreemptive'
	

	    // Main method, the entry point of the program
	    public static void main(String[] args) {

	        // Creating a Scanner object to take input from the user
	        Scanner sc = new Scanner(System.in);

	        // Asking the user to enter the number of Processes
	        System.out.println("Enter the no of Processes:");

	        // Reading the number of processes from the user
	        int n = sc.nextInt();

	        // Arrays to store process details (priority, process ID, arrival time, burst time, etc.)
	        int priority[] = new int[n];
	        int process[] = new int[n];
	        int arrival[] = new int[n];
	        int burst[] = new int[n];
	        int completion[] = new int[n];
	        int turnaround[] = new int[n];
	        int waiting[] = new int[n];

	        // Array to keep track of whether a process is completed or not
	        boolean[] completed = new boolean[n];

	        // Variables to calculate average turnaround time and average waiting time
	        int avgtat = 0;
	        int avgwt = 0;

	        // Loop to take input for each process
	        for (int i = 0; i < n; i++) {
	            System.out.println("Enter the priority, arrival, and burst time for process p" + (i + 1) + ":");
	            process[i] = (i + 1);
	            System.out.println("Enter Priority Time:");
	            priority[i] = sc.nextInt();
	            System.out.println("Enter Arrival Time:");
	            arrival[i] = sc.nextInt();
	            System.out.println("Enter Burst Time:");
	            burst[i] = sc.nextInt();
	            completed[i] = false;
	        }

	        // Variables to keep track of time, completed processes, and the process index to execute
	        int time = 0;
	        int completedprocesses = 0;

	        // Loop to simulate the non-preemptive priority scheduling algorithm
	        while (completedprocesses < n) {

	            int index = -1;
	            int max_priority = Integer.MIN_VALUE;

	            // Finding the process with the highest priority among the non-completed processes
	            for (int i = 0; i < n; i++) {
	                if (!completed[i] && arrival[i] <= time && priority[i] > max_priority) {
	                    index = i;
	                    max_priority = priority[i];
	                }
	            }

	            // If no process is available to execute at the current time, time is incremented
	            if (index == -1) {
	                time++;
	            } else {
	                // If a process is found, update completion, turnaround, and waiting times
	                completion[index] = time + burst[index];
	                turnaround[index] = completion[index] - arrival[index];
	                waiting[index] = turnaround[index] - burst[index];
	                time = completion[index];
	                completed[index] = true;
	                completedprocesses++;
	            }
	        }

	        // Printing the header for the output table
	        System.out.println("priority\tProcess\tarrival\tburst\tcompletion\tturnaround\twaiting");

	        // Loop to print the details for each process
	        for (int i = 0; i < n; i++) {
	            System.out.println(priority[i] + "\t\t" + "p" + process[i] + "\t\t" + arrival[i] + "\t\t" +
	                    burst[i] + "\t\t" + completion[i] + "\t\t" + turnaround[i] + "\t\t" + waiting[i]);
	            avgtat += turnaround[i];
	            avgwt += waiting[i];
	        }

	        // Printing the average turnaround time and average waiting time
	        System.out.println("Average turnaround is:" + (avgtat / n));
	        System.out.println("Average waiting is:" + (avgwt / n));
	    }
	}


