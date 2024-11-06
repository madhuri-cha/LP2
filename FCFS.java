import java.util.*;

public class FCFS {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        float avgtat = 0, avgwt = 0;

        // Input: Number of processes
        System.out.println("*** First Come First Serve Scheduling ***");
        System.out.print("Enter Number of Processes: ");
        n = sc.nextInt();

        // Arrays to store process information
        String process[] = new String[n];
        int arrivalTime[] = new int[n];
        int burstTime[] = new int[n];
        int TAT[] = new int[n];
        int waitingTime[] = new int[n];
        int completionTime[] = new int[n];

        // Input: Arrival time and Burst time for each process
        for (int i = 0; i < n; i++) {
            process[i] = "P" + (i + 1);
            System.out.print("Enter Arrival Time for Processor " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter Burst Time for Processor " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }

        // Calculate completion time for each process
        completionTime[0] = burstTime[0];
        for (int i = 0; i < n - 1; i++) {
            completionTime[i + 1] = completionTime[i] + burstTime[i + 1];
        }

        // Display scheduling results
        System.out.println("*** First Come First Serve Scheduling ***");
        System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurn around time\tWaiting time");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            // Calculate Turnaround Time and Waiting Time
            TAT[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = TAT[i] - burstTime[i];

            // Accumulate values for average calculation
            avgtat += TAT[i];
            avgwt += waitingTime[i];

            // Display process details
            System.out.println(process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }

        // Calculate and display average Turnaround Time and Waiting Time
        System.out.println("Average Turnaround Time of Processor: " + (avgtat / n) + "ms");
        System.out.println("Average Waiting Time of Processor: " + (avgwt / n) + "ms");
    }
}
