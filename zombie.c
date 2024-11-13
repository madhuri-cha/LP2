#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>


int arr[6];
void input() {
printf("Enter the 6 elements:\n");
for (int i = 0; i < 6; i++) {
scanf("%d", &arr[i]);
}
}
void bubblsort(int arr[], int n) {
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
void insertionsort(int arr[], int n) {
int k, j;
for (int i = 1; i < n; i++) {
k = arr[i];
j = i - 1;
while (j >= 0 && arr[j] > k) {
arr[j + 1] = arr[j];
j = j - 1;
}
arr[j + 1] = k;
}
}
void display() {
printf("Sorted array : ");
for (int i = 0; i < 6; i++) {
printf("%d ", arr[i]);
}
printf("\n");
}
void sorting() {
pid_t p = fork();
if (p < 0) {

printf("fork failed\n");
exit(1);
} else if (p == 0) {
printf("Hello from Child!\n");
bubblsort(arr, 6);
printf("Sorting in child through bubble sort.....\n");
display();
exit(0);
} else {
sleep(3);
printf("Hello from Parent!\n");
insertionsort(arr, 6);
printf("Sorting in parent through insertion sort.....\n");
display();
}
}
int main() {
input();
sorting();
return 0;
}