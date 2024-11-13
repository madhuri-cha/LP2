#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h> 

sem_t readCountAccess, databaseAccess;
int readCount = 0;

void *Reader(void *args) {
    int id = (int)(long) args;
    
    printf("Reader %d is trying to enter the database for reading.\n", id);
    sem_wait(&readCountAccess);
    if (++readCount == 1) {
        sem_wait(&databaseAccess);
    }
    sem_post(&readCountAccess);

    printf("Reader %d is reading the database.\n", id);
    sleep(1);  // Simulate reading

    sem_wait(&readCountAccess);
    if (--readCount == 0) {
        sem_post(&databaseAccess);
    }
    sem_post(&readCountAccess);

    printf("Reader %d is leaving the database.\n", id);
    return NULL;
}

void *Writer(void *args) {
    int id = (int)(long) args;
    
    printf("Writer %d is trying to enter the database for writing.\n", id);
    sem_wait(&databaseAccess);

    printf("Writer %d is writing to the database.\n", id);
    sleep(1);  // Simulate writing
    printf("Writer %d is leaving the database.\n", id);

    sem_post(&databaseAccess);
    return NULL;
}

int main() {
    int numReaders, numWriters;
    
    sem_init(&readCountAccess, 0, 1);
    sem_init(&databaseAccess, 0, 1);

    printf("Enter the number of Reader Threads: ");
    scanf("%d", &numReaders);
    printf("Enter the number of Writer Threads: ");
    scanf("%d", &numWriters);

    pthread_t readers[numReaders], writers[numWriters];

    for (int i = 0; i < numReaders; i++) {
        pthread_create(&readers[i], NULL, Reader, (void *)(long)i);
    }

    for (int i = 0; i < numWriters; i++) {
        pthread_create(&writers[i], NULL, Writer, (void *)(long)i);
    }

    for (int i = 0; i < numWriters; i++) {
        pthread_join(writers[i], NULL);
    }

    for (int i = 0; i < numReaders; i++) {
        pthread_join(readers[i], NULL);
    }

    sem_destroy(&readCountAccess);
    sem_destroy(&databaseAccess);

    return 0;
}
