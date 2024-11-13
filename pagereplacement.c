#include <stdio.h>
#include <stdbool.h>

#define MAX_FRAME_COUNT 10
#define MAX_PAGE_COUNT 100

int pagecount, framecount;
int pageReferences[MAX_PAGE_COUNT];
int frame[MAX_FRAME_COUNT];

void printframe(int frame[], int framecount) {
    for (int i = 0; i < framecount; i++) {
        if (frame[i] == -1) {
            printf("   ");  
        } else {
            printf("%d ", frame[i]);
        }
    }
    printf("| ");
}

int findIndex(int arr[], int n, int x) {
    for (int i = 0; i < n; i++) {
        if (arr[i] == x) {
            return i;
        }
    }
    return -1;
}

int fcfs() {
    int pageFault = 0;

    printf("FCFS Page Replacement Algorithm:\n");
    for (int i = 0; i < pagecount; i++) {
        int pageIndex = findIndex(frame, framecount, pageReferences[i]);
        if (pageIndex == -1) {  // Page fault occurs
            frame[pageFault % framecount] = pageReferences[i];
            pageFault++;
        }
        printframe(frame, framecount);
    }

    return pageFault;
}

int lruPageReplacement() {
    int pageFaults = 0;
    int time[MAX_FRAME_COUNT];

    printf("LRU Page Replacement Algorithm:\n");

    for (int i = 0; i < framecount; i++) {
        frame[i] = -1;
        time[i] = 0;
    }

    for (int i = 0; i < pagecount; i++) {
        int pageIndex = findIndex(frame, framecount, pageReferences[i]);
        
        if (pageIndex == -1) {  
            int emptyframe = findIndex(frame , framecount ,-1);
            if(emptyframe!= -1){
                 frame[emptyframe] = pageReferences[i];
                time[emptyframe] = i;
                pageFaults++;
 
            }
            else{

            int leastRecentlyUsedIndex = 0;
            
            
            
            // Find the least recently used page
            for (int j = 1; j < framecount; j++) {
                if (time[j] < time[leastRecentlyUsedIndex]) {
                    leastRecentlyUsedIndex = j;
                    
                }
            }
            
            // Replace the least recently used page with the new page
            frame[leastRecentlyUsedIndex] = pageReferences[i];
            time[leastRecentlyUsedIndex] = i;  // Update the time of the replaced frame
            pageFaults++;
        } 
        }
        else {
            // Update the time for the page already in memory
            time[pageIndex] = i;
        }

        printframe(frame, framecount);
    }
    return pageFaults;
}
int findNextUse(int page, int currentPos) {
    for (int i = currentPos + 1; i < pagecount; i++) {
        if (pageReferences[i] == page) {
            return i;
        }
    }
    return __INT_MAX__;  // Return INT_MAX if page won't be used again
}

int optimalPageReplacement() {
    int pageFaults = 0;
    
    printf("Optimal Page Replacement Algorithm:\n");

    // Initialize frames to -1
    for (int i = 0; i < framecount; i++) {
        frame[i] = -1;
    }

    for (int i = 0; i < pagecount; i++) {
        int pageIndex = findIndex(frame, framecount, pageReferences[i]);
        
        if (pageIndex == -1) {  // Page fault occurs
            // Check for empty frame
            int emptyFrame = findIndex(frame, framecount, -1);
            
            if (emptyFrame != -1) {
                // If empty frame available, use it
                frame[emptyFrame] = pageReferences[i];
                pageFaults++;
            } else {
                // No empty frame, need to replace a page
                int farthestUseIndex = 0;
                int farthestUse = findNextUse(frame[0], i);  // Initialize with first frame
                
                // Find the page that won't be used for the longest time
                for (int j = 1; j < framecount; j++) {
                    int nextUse = findNextUse(frame[j], i);
                    if (nextUse > farthestUse) {
                        farthestUse = nextUse;
                        farthestUseIndex = j;
                    }
                }
                
                // Replace the page that won't be used for the longest time
                frame[farthestUseIndex] = pageReferences[i];
                pageFaults++;
            }
        }
        
        printframe(frame, framecount);
    }
    
    return pageFaults;
}


int main() {
    printf("Enter the number of pages: ");
    scanf("%d", &pagecount);

    if (pagecount <= 0 || pagecount > MAX_PAGE_COUNT) {
        printf("Invalid number of pages. Please enter a value between 1 and %d.\n", MAX_PAGE_COUNT);
        return 1;
    }

    printf("Enter the number of frames: ");
    scanf("%d", &framecount);

    if (framecount <= 0 || framecount > MAX_FRAME_COUNT) {
        printf("Invalid number of frames. Please enter a value between 1 and %d.\n", MAX_FRAME_COUNT);
        return 1;
    }

    for (int i = 0; i < framecount; i++) {
        frame[i] = -1;
    }

    printf("Enter the page reference string (space-separated): ");
    for (int i = 0; i < pagecount; i++) {
        scanf("%d", &pageReferences[i]);
    }

    int pageFaultsFCFS = fcfs();
    printf("\nTotal Page Faults (FCFS): %d\n\n", pageFaultsFCFS);

     int pageFaultsLRU = lruPageReplacement();
 printf("\nTotal Page Faults (LRU): %d\n\n", pageFaultsLRU);

 int pageFaultsOptimal = optimalPageReplacement();
 printf("\nTotal Page Faults (Optimal): %d\n", pageFaultsOptimal);

    return 0;
}
