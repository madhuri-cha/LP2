import java.util.Arrays;
import java.util.Scanner;

public class NextFit {

    // Function to implement the Next Fit algorithm
    static void NextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {

        // Array to store the allocation status of each process
        int allocation[] = new int[n];

        // Initialize allocation array with -1 indicating not allocated
        Arrays.fill(allocation, -1);

        // Variable to keep track of the current block index
        int j = 0;

        // Iterate through each process to find the next fit block
        for (int i = 0; i < n; i++) {
            int count = 0;

            // Iterate through the available blocks to find the next fit
            while (count < m) {
                count++;

                // Check if the current block can accommodate the process
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remblockSize[i] = blockSize[j];
                    break;
                }

                // Move to the next block in a circular manner
                j = (j + 1) % m;
                count += 1;
            }
        }

        // Display the allocation and remaining block size information
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");

            // If the process is allocated, print block number and remaining block size
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            } else {
                // If the process is not allocated, print "Not Allocated" and remaining block size
                System.out.print("Not Allocated" + "\t" + remblockSize[i]);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        int m, n, num;
        Scanner in = new Scanner(System.in);

        // Input the number of blocks
        System.out.print("Enter how many number of blocks you want to enter:");
        m = in.nextInt();
        int blockSize[] = new int[m];
        int remblockSize[] = new int[m];

        // Input the size of each block
        for (int i = 0; i < m; i++) {
            System.out.print("Enter Data " + (i + 1) + ":");
            num = in.nextInt();
            blockSize[i] = num;
        }

        // Input the number of processes
        System.out.print("Enter how many number of processes you want to enter:");
        n = in.nextInt();
        int processSize[] = new int[n];

        // Input the size of each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Data " + (i + 1) + ":");
            num = in.nextInt();
            processSize[i] = num;
        }

        // Call the Next Fit algorithm function
        NextFit(blockSize, m, processSize, n, remblockSize);
    }
}
