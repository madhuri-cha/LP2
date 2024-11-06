import java.util.*;

public class NonSJF {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    System.out.println("*** Shortest Job First Scheduling (Non Preemptive) ***");

    // Input: Number of processes
    System.out.print("Enter the number of processes: ");
    int n = sc.nextInt();

    // Arrays to store process details
    int process[] = new int[n];
    int arrivalTime[] = new int[n + 1];
    int burstTime[] = new int[n + 1];
    int completionTime[] = new int[n];
    int TAT[] = new int[n];
    int waitingTime[] = new int[n];
    int temp, k = 1, time = 0;
    int min = 0, sum = 0, compTotal = 0;
    float avgwt = 0, avgTAT = 0;

    // Input: Arrival time and Burst time for each process
    for (int i = 0; i < n; i++) {
      process[i] = (i + 1);
      System.out.print("Enter Arrival Time for Processor " + process[i] + ":");
      arrivalTime[i] = sc.nextInt();
      System.out.print("Enter Burst Time for Processor " + process[i] + ": ");
      burstTime[i] = sc.nextInt();
    }

    // Sorting processes based on arrival time
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (arrivalTime[i] < arrivalTime[j]) {
          // Swap process, arrivalTime, and burstTime
          temp = process[j];
          process[j] = process[i];
          process[i] = temp;
          temp = arrivalTime[j];
          arrivalTime[j] = arrivalTime[i];
          arrivalTime[i] = temp;
          temp = burstTime[j];
          burstTime[j] = burstTime[i];
          burstTime[i] = temp;
        }
      }
    }

    // Schedule processes based on Shortest Job First algorithm
    for (int j = 0; j < n; j++) {
      time = time + burstTime[j];
      min = burstTime[k];
      for (int i = k; i < n; i++) {
        if (time >= arrivalTime[i] && burstTime[i] < min) {
          // Swap process, arrivalTime, and burstTime
          temp = process[k];
          process[k] = process[i];
          process[i] = temp;
          temp = arrivalTime[k];
          arrivalTime[k] = arrivalTime[i];
          arrivalTime[i] = temp;
          temp = burstTime[k];
          burstTime[k] = burstTime[i];
          burstTime[i] = temp;
        }
      }
      k++;
    }

    // Calculate waiting time, turnaround time, and completion time for each process
    waitingTime[0] = 0;
    for (int i = 1; i < n; i++) {
      sum = sum + burstTime[i - 1];
      waitingTime[i] = sum - arrivalTime[i];
      avgwt += waitingTime[i];
    }

    for (int i = 0; i < n; i++) {
      compTotal = compTotal + burstTime[i];
      completionTime[i] = compTotal;
      TAT[i] = compTotal - arrivalTime[i];
      avgTAT += TAT[i];
    }

    // Display scheduling results
    System.out.println("\n*** Shortest Job First Scheduling (Non Preemptive) ***");
    System.out.println("Processor\tArrival time\tBurst time\tCompletion Time\tTurnaround time\tWaiting time");
    System.out.println(
        "----------------------------------------------------------------------------------------------------------");
    for (int i = 0; i < n; i++) {
      System.out.println("P" + process[i] + "\t\t" + arrivalTime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
          + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
    }

    // Calculate and display average Waiting Time and Turnaround Time
    avgTAT /= n;
    avgwt /= n;
    System.out.println("\nAverage Turnaround Time is " + avgTAT);
    System.out.println("Average Waiting Time is " + avgwt);
  }
}
