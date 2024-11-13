#include <stdio.h>

int main () {
    int mat[100][7]; // Store process ID, burst time, arrival time, waiting time, turnaround time, and completion status
    int n;
    int quantum;
    float totalWT = 0, totalTT = 0;

    // Input the number of processes
    printf("Enter the number of processes: ");
    scanf("%d", &n);
    printf("Enter time quantum: ");
    scanf("%d", &quantum);


    // Input burst time and arrival time
    printf("Enter the burst times and arrival times:\n");
    for (int i = 0; i < n; i++) {
        mat[i][0] = i + 1; // Process ID
        printf("P%d Burst Time: ", i + 1);
        scanf("%d", &mat[i][1]); // Burst time
        printf("P%d Arrival Time: ", i + 1);
        scanf("%d", &mat[i][2]); // Arrival time
        mat[i][5] = mat[i][1];
        mat[i][6]=0;
    }

    // Sorting by arrival time first
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (mat[j][2] > mat[j + 1][2]) {
                
               for(int k = 0; k <= 6; k++) {
                    int temp = mat[j][k];
                    mat[j][k] = mat[j + 1][k];
                    mat[j + 1][k] = temp;
                }
            }
        }
    }

    int currentTime = 0; // Track the current time
    int completedProcesses = 0; // Count of processes completed
    int queue[100] , front=0 , rear=0;

    queue[rear++]=0;
    mat[0][6]=1;

    while(completedProcesses<n){
       if(front==rear){
        currentTime++;
         
       for(int i =0 ; i<n ; i++){
                if(mat[i][5]>0 && mat[i][6]==0 &&mat[i][2]<=currentTime){
                    queue[rear]=i;
                    rear=(rear+1)%100;
                    mat[i][6]=1;
                }
            }
            continue;

       }
         int currentprocess = queue[front];
        front = (front + 1) % 100;

        int exectuetime;
        if(mat[currentprocess][5]<quantum){
            exectuetime=mat[currentprocess][5];
        }else{
            exectuetime=quantum;
        }
       
       currentTime+=exectuetime;
       mat[currentprocess][5]-=exectuetime;

       for(int i =0 ; i<n ; i++){
                if(mat[i][5]>0 && mat[i][6]==0 &&mat[i][2]<=currentTime){
                    queue[rear]=i;
                    rear=(rear+1)%100;
                    mat[i][6]=1;
                }
            }

        if(mat[currentprocess][5]>0){
            queue[rear]=currentprocess;
            rear=(rear+1)%100;
        }  else{
            completedProcesses++;
            mat[currentprocess][4] = currentTime - mat[currentprocess][2]; 
            mat[currentprocess][3] = mat[currentprocess][4] - mat[currentprocess][1]; 
            totalWT += mat[currentprocess][3];
            totalTT += mat[currentprocess][4];
        }  



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
