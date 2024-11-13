#include <stdio.h>

#define MAX_PROCESSES 100



// Function to input process data
void inputProcessData(int pid[], int burst[], int arr[][1], int n) {
    printf("Enter the burst times and arrival times:\n");
    for (int i = 0; i < n; i++) {
        pid[i] = i + 1; // Assign process ID
        printf("P%d Burst Time: ", i + 1);
        scanf("%d", &burst[i]); // Read burst time
        printf("P%d Arrival Time: ", i + 1);
        scanf("%d", &arr[i][0]); // Read arrival time
    }
}

// Function to sort processes by arrival time using bubble sort
void sortByArrivalTime(int pid[], int burst[], int arr[][1], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j][0] > arr[j + 1][0]) { // Compare arrival times
                // Swap burst times
                int tempBT = burst[j];
                burst[j] = burst[j + 1];
                burst[j + 1] = tempBT;

                // Swap arrival times
                int tempAT = arr[j][0];
                arr[j][0] = arr[j + 1][0];
                arr[j + 1][0] = tempAT;

                // Swap process IDs
                int tempPID = pid[j];
                pid[j] = pid[j + 1];
                pid[j + 1] = tempPID;
            }
        }
    }
}

// Function to calculate waiting and turnaround times
void calculateTimes(int pid[], int burst[], int arr[][1], int wt[], int tt[], int n, float *totalWT, float *totalTT) {
    int currentTime = 0; // Track current time
    int completedProcesses = 0; // Track completed processes
    int completed[MAX_PROCESSES] = {0}; // Completion status of each process

    while (completedProcesses < n) {
        int idx = -1;
        int minBT = 9999;

        // Select the process with the shortest burst time that has arrived
        for (int i = 0; i < n; i++) {
            if (arr[i][0] <= currentTime && burst[i] < minBT && completed[i] == 0) {
                minBT = burst[i];
                idx = i;
            }
        }

        // If no process has arrived, increment the time
        if (idx == -1) {
            currentTime++;
            continue;
        }

        // Process the selected process
        wt[idx] = currentTime - arr[idx][0]; // Calculate waiting time
        tt[idx] = wt[idx] + burst[idx]; // Calculate turnaround time

        *totalWT += wt[idx]; // Add waiting time to total
        *totalTT += tt[idx]; // Add turnaround time to total

        currentTime += burst[idx]; // Update current time
        completed[idx] = 1; // Mark process as completed
        completedProcesses++; // Increment completed process count
    }
}



// Function to print process details and averages
void printProcessDetails(int pid[], int arr[][1], int burst[], int wt[], int tt[], int n, float avgWT, float avgTT) {
    printf("\nP     AT     BT     WT     TT\n");
    for (int i = 0; i < n; i++) {
        printf("P%d     %d      %d      %d      %d\n", pid[i], arr[i][0], burst[i], wt[i], tt[i]);
    }
    printf("Average Waiting Time = %f\n", avgWT);
    printf("Average Turnaround Time = %f\n", avgTT);
}

int main() {
    int n; // Number of processes
    int pid[MAX_PROCESSES];         // Process IDs
    int burst[MAX_PROCESSES];       // Burst times
    int arr[MAX_PROCESSES][1];      // Arrival times
    int wt[MAX_PROCESSES];          // Waiting times
    int tt[MAX_PROCESSES];          // Turnaround times
    float totalWT = 0, totalTT = 0; // Total waiting and turnaround times

    // Input the number of processes and process data
    printf("Enter the number of processes: ");
    scanf("%d", &n);
    inputProcessData(pid, burst, arr, n);

    // Sort processes by arrival time
    sortByArrivalTime(pid, burst, arr, n);

    // Calculate waiting and turnaround times
    calculateTimes(pid, burst, arr, wt, tt, n, &totalWT, &totalTT);

    // Calculate averages
    float avg_waiting_time = totalWT / n;
    float avg_turnaround_time = totalTT / n;

    // Print details
    printProcessDetails(pid, arr, burst, wt, tt, n, avg_waiting_time, avg_turnaround_time);

    return 0;
}









// Function prototypes
/*void inputProcessData(int pid[], int burst[], int arr[][1], int n);
void sortByArrivalTime(int pid[], int burst[], int arr[][1], int n);
void calculateTimes(int pid[], int burst[], int arr[][1], int wt[], int tt[], int n, float *totalWT, float *totalTT);
void printProcessDetails(int pid[], int arr[][1], int burst[], int wt[], int tt[], int n, float avgWT, float avgTT);*/

