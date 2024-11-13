#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <string.h>

#define SHM_SIZE 1024

int main() {
    // Generate unique key
    key_t key = ftok("shmfile", 65);
    if (key == -1) {
        perror("ftok");
        exit(1);
    }

    // Create shared memory segment
    int shmid = shmget(key, SHM_SIZE, IPC_CREAT | 0666);
    if (shmid == -1) {
        perror("shmget");
        exit(1);
    }

    // Attach to shared memory
    char *data = (char*)shmat(shmid, NULL, 0);
    if (data == (char*)-1) {
        perror("shmat");
        exit(1);
    }

    printf("Enter the message: ");
    fgets(data, SHM_SIZE, stdin);  // Fixed SAM_SIZE to SHM_SIZE

    if (shmdt(data) == -1) {
        perror("shmdt");
        exit(1);
    }

    printf("Message written to shared memory.\n");  // Fixed printf and string
     
    return 0;
}