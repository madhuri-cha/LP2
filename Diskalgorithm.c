#include<stdio.h>
#include<stdlib.h>
#include<math.h>

int track, no_req, head, head1, choice;
int disk_req[100], finish[100];

void input() {
    printf("Enter no of tracks:\n");
    scanf("%d", &track);
    printf("Enter no of disk requests:\n");
    scanf("%d", &no_req);
    printf("Enter disk requests in fifo order:\n");
    for(int i = 0; i < no_req; i++) {
        scanf("%d", &disk_req[i]);
    }
    printf("Enter current head position:\n");
    scanf("%d", &head1);
}

void sstf() {
    head = head1;
    int distance = 0;
    int diff, index;
    
    
    printf("The SSTF sequence is: ");
    
    for(int i = 0; i < no_req; i++) {
        finish[i] = 0;
    }
    
    
    for(int j = 0; j < no_req; j++) {
        int min = 9999; 
        index = -1;
        
       
       
        for(int i = 0; i < no_req; i++) {
            if(finish[i] == 0) {  
                
                diff = abs(head - disk_req[i]);
                if(diff < min) {
                    min = diff;
                    index = i;
                }
            }
        }
        
        if(index != -1) {
            finish[index] = 1;
            distance += abs(head - disk_req[index]);
            head = disk_req[index];
            printf("%d", head);
            if(j < no_req - 1) printf(" -> ");
        }
    }
    printf("\nTotal distance traversed = %d\n", distance);
}
void sort(int arr[] , int n){
    for(int i =0 ; i<n-1 ; i++){
        for(int j =0 ; j<n-i-1 ;j++){
            if(arr[j]>arr[j+1]){
                int temp =arr[j];
                arr[j]=arr[j+1];
                arr[j+1]=temp;

            }
        }
    }
}
 void scan(){
  int distance = 0 ;
  int total_request[100];
  head=head1;
  int head_index ;
  int n =no_req;

  for(int i =0 ; i<no_req ;i++){
    total_request[i]=disk_req[i];
  }
  total_request[n]=head;
  n++;
   sort(total_request,n);  

   for(int i =0 ; i<no_req ;i++){
    if(total_request[i]==head){
        head_index=i;
        break;
    }
   }

    printf("\nSCAN Sequence is: ");

     printf("%d", head);

    for(int i = head_index+1 ; i<=no_req ; i++){
        printf("-> %d" , total_request[i]);
        distance+=abs(total_request[i]-total_request[i-1]);

    }
    if(total_request[n-1]!=track-1){
        printf("-> %d" ,track-1);
        distance+=abs(track-1 - total_request[n-1]);
    }
    for(int i = head_index-1 ; i>=0 ; i-- ){
         printf(" -> %d", total_request[i]);

         if(i==head_index-1){
            
             distance+=abs(track-1 - total_request[i]);
         }
         else{
            distance+=abs(total_request[i+1]-total_request[i]);
         }
    }
    printf("\nTotal Distance Traversed: %d\n", distance);
 }
  void clook(){
  int distance = 0 ;
  int total_request[100];
  head=head1;
  int head_index ;
  int n =no_req;

  for(int i =0 ; i<no_req ;i++){
    total_request[i]=disk_req[i];
  }
  total_request[n]=head;
  n++;
   sort(total_request,n);  

   for(int i =0 ; i<no_req ;i++){
    if(total_request[i]==head){
        head_index=i;
        break;
    }
   }

  printf("\nCLOOK Sequence is: ");
    printf("%d", head);

    for(int i = head_index+1 ; i<=no_req ; i++){
        printf("-> %d" , total_request[i]);
        distance+=abs(total_request[i]-total_request[i-1]);

    }

    if (head_index>0){
        printf(" -> %d", total_request[0]);
        distance+=abs(total_request[0]-total_request[n-1]);

        for(int i =1 ; i< head_index ; i++){
             printf(" -> %d", total_request[i]);
        distance+=abs(total_request[i]-total_request[i-1]);
        }

    }

    printf("\nTotal Distance Traversed: %d\n", distance);
 }



int main() {
    while(1) {
        printf("\n\n1. INPUT\n2. SSTF\n3. SCAN\n4. CLOOK\n5. EXIT\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);
        
        switch(choice) {
            case 1:
                input();
                break;
            case 2:
                sstf();
                break;
           case 3:
                scan();
                break;
            case 4:
                clook();
                break;    

            case 5:
                exit(0);
            default:
                printf("Invalid choice!\n");
        }
    }
    return 0;
}