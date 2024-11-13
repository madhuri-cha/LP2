#include <stdio.h>
#include <stdlib.h>
#include<unistd.h>
#include<semaphore.h>
#include<pthread.h>

# define BUFFER_SIZE 5

void* producer(void* thread);
void* consumer(void* thread);

sem_t full ,empty;
int buffer[BUFFER_SIZE];
int count =0 , in=0 , out=0;
pthread_mutex_t mutex;

int main(){
    int p , c;
    pthread_t producers[100],consumers[100];

    sem_init(&full,0,0);
    sem_init(&empty,0,BUFFER_SIZE);
    pthread_mutex_init(&mutex , NULL);

    printf("Enter the number of producers: ");
    scanf("%d", &p); 

    printf("Enter the number of consumers: ");
    scanf("%d", &c); 

    for(int i =0 ; i<p ; i++){
        pthread_create(&producers[i],NULL,producer,(void*)&i); // different producer and producers ;
    }
     for(int i =0 ; i<c ; i++){
        pthread_create(&consumers[i],NULL,consumer,(void*)&i);
    }
     for(int i =0 ; i<p ; i++){
        pthread_join(producers[i],NULL);
    }
      for(int i =0 ; i<c ; i++){
        pthread_join(consumers[i],NULL);
    }

    sem_destroy(&full);
    sem_destroy(&empty);
    pthread_mutex_destroy(&mutex);   
    
    return 0 ;
}
void* producer(void* thread){
  int t = * (int *) thread;
  while(1){
    sem_wait(&empty);
    pthread_mutex_lock(&mutex);
    if(count<BUFFER_SIZE){
    buffer[in]=rand()%100;
    printf("Producer %d produced: %d\n", t, buffer[in]);
    in=(in+1)%BUFFER_SIZE;
    count++;
    }
    else{
        printf("Buffer is full\n");
    }
  pthread_mutex_unlock(&mutex);
  sem_post(&full);
  sleep(1);  
  }
  pthread_exit(0);

}
void* consumer(void* thread){
  int t = * (int *) thread;
  while(1){
    sem_wait(&full);
    pthread_mutex_lock(&mutex);
    if(count>0){
   
    printf("Producer %d produced: %d\n", t, buffer[out]);
    out=(out+1)%BUFFER_SIZE;
    count--;
    }
    else{
        printf("Buffer is empty\n");
    }
  pthread_mutex_unlock(&mutex);
  sem_post(&empty);
   sleep(1); 
  }
  pthread_exit(0);

}