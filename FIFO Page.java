import java.util.*;

public class FIFO {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int noofpages, capacity, index = 0;
        int hit = 0, fault = 0;
        double faultRatio, hitRatio;

        // Prompt the user to enter the number of pages
        System.out.print("Enter the number of pages you want to enter: ");
        noofpages = sc.nextInt();
        int pages[] = new int[noofpages];

        // Read the page numbers from the user
        for (int i = 0; i < noofpages; i++) {
            pages[i] = sc.nextInt();
        }

        // Prompt the user to enter the capacity of frame
        System.out.print("Enter the capacity of frame: ");
        capacity = sc.nextInt();
        int frame[] = new int[capacity];
        int table[][] = new int[noofpages][capacity];

        // Initialize the frame array with -1 indicating empty frames
        for (int i = 0; i < capacity; i++) {
            frame[i] = -1;
        }

        System.out.println("\n----------------------------------------------------------------------");

        // FIFO Page Replacement Algorithm
        for (int i = 0; i < noofpages; i++) {
            int search = -1;

            // Check if the page is already in the frame
            for (int j = 0; j < capacity; j++) {
                if (frame[j] == pages[i]) {
                    search = j;
                    hit++;
                    System.out.printf("%4s", "H"); // Print 'H' for Hit
                    break;
                }
            }

            // If the page is not in the frame, replace a page with the new one
            if (search == -1) {
                frame[index] = pages[i];
                fault++;
                System.out.printf("%4s", "F"); // Print 'F' for Fault
                index++;

                // Wrap around the frame index if it reaches the capacity
                if (index == capacity) {
                    index = 0;
                }
            }

            // Copy the current frame state to the table for display
            System.arraycopy(frame, 0, table[i], 0, capacity);
        }

        System.out.println("\n----------------------------------------------------------------------");

        // Print the page replacement table
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < noofpages; j++)
                System.out.printf("%3d ", table[j][i]);
            System.out.println();
        }

        System.out.println("----------------------------------------------------------------------");

        // Calculate and print page fault and hit statistics
        faultRatio = ((double) fault / noofpages) * 100;
        hitRatio = ((double) hit / noofpages) * 100;
        System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit);
        System.out.printf("Hit Ratio:%.2f \nFault Ratio:%.2f ", hitRatio, faultRatio);
    }
}
