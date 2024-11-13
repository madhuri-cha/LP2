#include <stdio.h>
#include <stdbool.h>

#define MAX_PROCESSES 10
#define MAX_RESOURCES 10


int CheckSafe(int n, int m, int alloc[MAX_PROCESSES][MAX_RESOURCES], int max[MAX_PROCESSES][MAX_RESOURCES], int avail[MAX_RESOURCES]) {
    
    int ans[MAX_PROCESSES], finish[MAX_PROCESSES] = {0};
    int work[MAX_RESOURCES];

    // Initialize work with available resources
    for (int j = 0; j < m; j++) {
        work[j] = avail[j];
    }

    int count = 0;
    while (count < n) {
        bool found = false;
        for (int i = 0; i < n; i++) {
            if (!finish[i]) {
                // Check if the current process's needs can be satisfied
                bool canAllocate = true;
                for (int j = 0; j < m; j++) {
                    if (max[i][j] - alloc[i][j] > work[j]) {
                        canAllocate = false;
                        break;
                    }
                }

                // If all resources can be allocated to this process
                if (canAllocate) {
                    // Assume this process finishes, add its allocated resources to work
                    for (int j = 0; j < m; j++) {
                        work[j] += alloc[i][j];
                    }
                    ans[count++] = i;
                    finish[i] = true;
                    found = true;
                }
            }
        }

        // If no process was found in this cycle, system is unsafe
        if (!found) {
            printf("The system is not in a safe state\n");
            return 0;
        }
    }

    // Print safe sequence
    printf("Following is the SAFE Sequence: ");
    for (int i = 0; i < n - 1; i++) {
        printf("P%d -> ", ans[i]);
    }
    printf("P%d\n", ans[n - 1]);
    return 1;
}

int ResourceRequest(int n, int m, int alloc[MAX_PROCESSES][MAX_RESOURCES], int max[MAX_PROCESSES][MAX_RESOURCES], int avail[MAX_RESOURCES], int process) {
    int Req[MAX_RESOURCES];

    printf("Enter the resource request for process P%d:\n", process);
    for (int i = 0; i < m; i++) {
        scanf("%d", &Req[i]);
    }

    // Check if request is valid
    for (int j = 0; j < m; j++) {
        if (Req[j] > max[process][j] - alloc[process][j]) {
            printf("Request exceeds process needs. Request denied.\n");
            return 0;
        }
        if (Req[j] > avail[j]) {
            printf("Requested resources are not available. Request denied.\n");
            return 0;
        }
    }

    // Tentatively allocate the resources
    for (int j = 0; j < m; j++) {
        avail[j] -= Req[j];
        alloc[process][j] += Req[j];
    }

    // Check if the new allocation results in a safe state
    if (CheckSafe(n, m, alloc, max, avail)) {
        printf("RESOURCE REQUEST GRANTED\n");
		printf("Updated allocation matrix:\n");
       for (int i = 0; i < n; i++){
          for (int j = 0; j < m; j++){
            printf("%d ", alloc[i][j]);
       }
printf("\n");
}
        return 1;
    } else {
        // If not safe, roll back allocation
        for (int j = 0; j < m; j++) {
            avail[j] += Req[j]; 
            alloc[process][j] -= Req[j];
        }
        printf("Resource request denied. The request may not be safe.\n");
        return 0;
    }
}

int main() {
    int n, m;
    int alloc[MAX_PROCESSES][MAX_RESOURCES];
    int max[MAX_PROCESSES][MAX_RESOURCES];
    int avail[MAX_RESOURCES];

    printf("\nEnter the number of processes and the number of resources:\n");
    scanf("%d%d", &n, &m);

    printf("\nEnter available resources\n");
    for (int j = 0; j < m; j++) {
        scanf("%d", &avail[j]);
    }

    printf("\nEnter the Allocated Matrix:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            printf("Enter for P%d, Resource R%d: ", i, j);
            scanf("%d", &alloc[i][j]);
        }
    }

    printf("\nEnter the Max Matrix:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            printf("Enter for P%d, Resource R%d: ", i, j);
            scanf("%d", &max[i][j]);
        }
    }

    CheckSafe(n, m, alloc, max, avail);

    while (1) {
        int process;
        printf("Enter the process that wants a resource request (0 to %d or -1 to exit): ", n - 1);
        scanf("%d", &process);

        if (process == -1) {
            break;
        }

        if (process < 0 || process >= n) {
            printf("Invalid process number.\n");
            continue;
        }

        if (ResourceRequest(n, m, alloc, max, avail, process)) {
            printf("Resource request granted.\n");
        } else {
            printf("Resource request denied.\n");
        }
    }

    return 0;
}
