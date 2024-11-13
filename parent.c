#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<string.h>

void bubble_sort(int arr[],int n){
    for (int i = 0; i < n - 1; i++) {
       for (int j = 0; j < n - i - 1; j++) {
           if (arr[j] > arr[j + 1]) {
            int temp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = temp;
}
}
}
}
int main(){
    int arr[6]={23,12,45,6,14,9};
    char *argv[10];
     
     bubble_sort(arr,6);

    pid_t p =fork();

    if(p<0){
        perror("Fail to create");
        exit(EXIT_FAILURE);
    }
    else if(p==0){
        char *program ="./child" ;
        char num[10];

        argv[0]=program;
        for(int j =0 ; j<6;j++){
            sprintf(num ,"%d",arr[j]);
            argv[j+1]=strdup(num);
        }
        argv[7]=NULL;

        execve(program,argv,NULL);
         perror("Fail to create");
for (int j = 1; j <= 6; j++) {
    free(argv[j]);
}


        exit(EXIT_FAILURE);

    }
    else{
        wait(NULL);
        printf("Parent process exiting.\n");
    }


   
    return 0 ;
}
 /*gcc -o sort sort.c
 gcc -o child child.c
  ./sort