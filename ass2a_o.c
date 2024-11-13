/*
A. Implement the C program in which main program accepts the integers to be sorted. 
Main program uses the FORK system call to create a new process called a child process. 
Parent process sorts the integers using sorting algorithm and waits for child process using WAIT system 
call to sort the integers using any sorting algorithm. Also demonstrate zombie and orphan states
*/
//orphan state

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

void bubble_sort(int arr[], int start, int end) 
{
	for(int i=start; i<end; i++) {
		for(int j=i+1; j<end; j++) {
			if(arr[i] > arr[j]) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
	}
	
	printf("Sorted array using bubble sort from child process: ");
	for(int i=start; i<end; i++) {
		printf("%d ", arr[i]);
	}
	printf("\n");
}

void selection_sort(int arr[], int start, int end) {
	int min_idx, i; 

	for(i = start; i < end-1; i++) 
	{ 
		min_idx = i; 
		for(int j = i+1; j < end; j++) {
			if (arr[j] < arr[min_idx]) {
				min_idx = j;
			} 
		}
		  
		int temp = arr[min_idx];
		arr[min_idx] = arr[i];
		arr[i] = temp;
	} 
	
	printf("Sorted array using selection sort from parent process: ");
	for(int i=start; i<end; i++) {
		printf("%d ", arr[i]);
	}
	printf("\n");
	exit(0);
}

int main() 
{
    int size;

    printf("Enter size of array: ");
    scanf("%d", &size);

    int arr[size];

    printf("Enter %d numbers in array: ", size);
    for(int i=0; i<size; i++) {
        scanf("%d", &arr[i]);
    }

    int pid = fork();

    if(pid == 0) {
        printf("\n-------Hello from child process-------\n");
        printf("Childs parent process id: %d\n", getppid());

        sleep(3);
         
        printf("Child process id: %d\n", getpid());
        printf("Childs parent process id: %d\n", getppid());
        bubble_sort(arr, 0, size);
    }

    else if(pid != 0)
	 {
        printf("\n-------Hello from parent process-------\n");
        printf("Parent process id: %d\n", getpid());
        selection_sort(arr, 0, size);
    }
}
//Since exit(0) is used, the parent process exits without waiting for the child process to complete.