// Importing the necessary Java utilities
package clg;
import java.util.*;

// Defining the Process class to represent a process in the RR scheduling
class Process {
    int processID;
    int arrival, burst, waiting, turnAround, remainingTime;
    int finish, completionTime;
}

// Main class for Round Robin (RR) Scheduling
public class robin {
    public static void main(String[] args) {
        // Initializing variables
        int n, sumBurst = 0, quantum, time;
        double avgWAT = 0, avgTAT = 0;

        // Creating a scanner for input
        Scanner sc = new Scanner(System.in);

        // Creating a queue for process execution order
        Queue<Integer> q = new LinkedList<>();

        // Printing RR Scheduling title and prompting user for the number of processes
        System.out.println("* RR Scheduling (Preemptive) *");
        System.out.print("Enter Number of Process: ");
        n = sc.nextInt();

        // Creating an array of Process objects to store process details
        Process[] p = new Process[n];

        // Taking input for each process
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].processID = i + 1;
            System.out.print("Enter the arrival time for P" + (i + 1) + ": ");
            p[i].arrival = sc.nextInt();
            System.out.print("Enter the burst time for P" + (i + 1) + ": ");
            p[i].burst = sc.nextInt();
            p[i].remainingTime = p[i].burst;
            p[i].finish = 0;
            sumBurst += p[i].burst;
            System.out.println();
        }

        // Taking input for time quantum
        System.out.print("\nEnter time quantum: ");
        quantum = sc.nextInt();

        // Sorting processes based on arrival time
        Process pTemp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p[i].arrival > p[j].arrival) {
                    pTemp = p[i];
                    p[i] = p[j];
                    p[j] = pTemp;
                }
            }
        }

        // Initializing the queue with the first process
        q.add(0);

        // Simulating RR scheduling
        for (time = p[0].arrival; time < sumBurst;) {
            Integer I = q.remove();
            int i = I.intValue();

            // Checking if the process will finish within the time quantum
            if (p[i].remainingTime <= quantum) {
                time += p[i].remainingTime;
                p[i].remainingTime = 0;
                p[i].finish = 1;
                p[i].completionTime = time;
                p[i].waiting = time - p[i].arrival - p[i].burst;
                p[i].turnAround = time - p[i].arrival;

                // Adding processes that have arrived and not finished to the queue
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if ((p[j].arrival <= time) && (p[j].finish != 1) && (!q.contains(J)))
                        q.add(j);
                }
            } else {
                // If process doesn't finish, reduce remaining time and add back to the queue
                time += quantum;
                p[i].remainingTime -= quantum;

                // Adding processes that have arrived and not finished to the queue
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if (p[j].arrival <= time && p[j].finish != 1 && i != j && (!q.contains(J)))
                        q.add(j);
                }
                q.add(i);
            }
        }

        // Printing the results of RR scheduling
        System.out.println("\n** RR Scheduling (Preemptive) **");
        System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println(("P" + (i + 1)) + "\t\t" + p[i].arrival + "ms\t\t" + p[i].burst + "ms\t\t" + p[i].completionTime + "ms\t\t\t" + p[i].turnAround + "ms\t\t\t" + p[i].waiting + "ms");
            avgWAT += p[i].waiting;
            avgTAT += p[i].turnAround;
        }

        // Printing average turn around time and waiting time
        System.out.println("\nAverage turn around time of processor: " + (avgTAT / n) + "ms\nAverage waiting time of processor: " + (avgWAT / n) + "ms");
    }
}
