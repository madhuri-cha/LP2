#include <stdio.h>

int main () {
    int mat[100][6]; // Store process ID, burst time, arrival time, waiting time, turnaround time, and completion status
    int n;
    float totalWT = 0, totalTT = 0;

    // Input the number of processes
    printf("Enter the number of processes: ");
    scanf("%d", &n);

    // Input burst time and arrival time
    printf("Enter the burst times and arrival times:\n");
    for (int i = 0; i < n; i++) {
        mat[i][0] = i + 1; // Process ID
        printf("P%d Burst Time: ", i + 1);
        scanf("%d", &mat[i][1]); // Burst time
        printf("P%d Arrival Time: ", i + 1);
        scanf("%d", &mat[i][2]); // Arrival time
        mat[i][5] = 0; // Mark all processes as incomplete (0 means not completed)
    }

    // Sorting by arrival time first
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (mat[j][2] > mat[j + 1][2]) {
                // Swap burst times
                int tempBT = mat[j][1];
                mat[j][1] = mat[j + 1][1];
                mat[j + 1][1] = tempBT;

                // Swap arrival times
                int tempAT = mat[j][2];
                mat[j][2] = mat[j + 1][2];
                mat[j + 1][2] = tempAT;

                // Swap process IDs
                int tempPID = mat[j][0];
                mat[j][0] = mat[j + 1][0];
                mat[j + 1][0] = tempPID;
            }
        }
    }

    int currentTime = 0; // Track the current time
    int completedProcesses = 0; // Count of processes completed
  
   

    while (completedProcesses < n) {
        int idx = -1;
        int minBT = 9999; // To find the process with the shortest burst time that has arrived

        // Select the process that has arrived and has the shortest burst time
        for (int i = 0; i < n; i++) {
            if (mat[i][2] <= currentTime && mat[i][1] < minBT && mat[i][5] == 0) { // Check if process has arrived and is not completed
                minBT = mat[i][1];
                idx = i;
            }
        }

        // If no process has arrived yet, increment the current time
        if (idx == -1) {
            currentTime++;
            continue;
        }

        // Process the selected process
        mat[idx][3] = currentTime - mat[idx][2]; // Waiting time = current time - arrival time
        mat[idx][4] = mat[idx][3] + mat[idx][1]; // Turnaround time = waiting time + burst time

        totalWT += mat[idx][3];
        totalTT += mat[idx][4];

        currentTime += mat[idx][1]; // Increment current time by the burst time of the selected process
        mat[idx][5] = 1; // Mark process as completed
        completedProcesses++;
    }

    // Calculate average waiting time and turnaround time
    float avg_waiting_time = totalWT / n;
    float avg_turnaround_time = totalTT / n;

    // Print process details
    printf("\nP     AT     BT     WT     TT\n");
    for (int i = 0; i < n; i++) {
        printf("P%d     %d      %d      %d      %d\n", mat[i][0], mat[i][2], mat[i][1], mat[i][3], mat[i][4]);
    }

    // Print average waiting time and turnaround time
    printf("Average Waiting Time = %f\n", avg_waiting_time);
    printf("Average Turnaround Time = %f\n", avg_turnaround_time);

    return 0;
}
