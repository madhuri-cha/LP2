#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t m;
pthread_cond_t cv;
int turn;

void *printFirst(void *arg) {
  pthread_mutex_lock(&m);
  while (turn != 1) {
    pthread_cond_wait(&cv, &m);
  }
  printf("first\n");
  turn = 2;
  pthread_cond_signal(&cv);
  pthread_mutex_unlock(&m);
  return NULL;
}

void *printSecond(void *arg) {
  pthread_mutex_lock(&m);
  while (turn != 2) {
    pthread_cond_wait(&cv, &m);
  }
  printf("second\n");
  turn = 3;
  pthread_cond_signal(&cv);
  pthread_mutex_unlock(&m);
  return NULL;
}

void *printThird(void *arg) {
  pthread_mutex_lock(&m);
  while (turn != 3) {
    pthread_cond_wait(&cv, &m);
  }
  printf("third\n");
  pthread_mutex_unlock(&m);
  return NULL;
}

int main() {
  pthread_t thread1, thread2, thread3;
  pthread_mutex_init(&m, NULL);
  pthread_cond_init(&cv, NULL);
  turn = 1;
  pthread_create(&thread1, NULL, printFirst, NULL);
  pthread_create(&thread2, NULL, printSecond, NULL);
  pthread_create(&thread3, NULL, printThird, NULL);
  pthread_join(thread1, NULL);
  pthread_join(thread2, NULL);
  pthread_join(thread3, NULL);
  pthread_mutex_destroy(&m);
  pthread_cond_destroy(&cv);
  return 0;
}