import java.util.Scanner;

public class BestFit {
    // Function to implement the Best Fit algorithm
    static void bestFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        // Array to store the allocation status of each process
        int allocation[] = new int[n];

        // Initialize allocation array with -1 indicating not allocated
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        // Iterate through each process to find the best fit block
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;

            // Iterate through each block to find the best fit
            for (int j = 0; j < m; j++) {
                // Check if the block size is greater than or equal to the process size
                if (blockSize[j] >= processSize[i]) {
                    // If bestIdx is not set, or the current block is a better fit than the previously selected block
                    if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j]) {
                        bestIdx = j;
                    }
                }
            }

            // If a block is found, allocate the process to that block and update block size
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
                remblockSize[i] = blockSize[bestIdx];
            }
        }

        // Display the allocation and remaining block size information
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");

            // If the process is allocated, print block number and remaining block size
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            } else {
                // If the process is not allocated, print "Not Allocated" and remaining block size
                System.out.print("Not Allocated" + "\t" + remblockSize[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m, n, num;
        Scanner in = new Scanner(System.in);

        // Input the number of blocks
        System.out.print("Enter how many number of blocks you want to enter:");
        m = in.nextInt();
        int remblockSize[] = new int[m];
        int blockSize[] = new int[m];

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

        // Call the Best Fit algorithm function
        bestFit(blockSize, m, processSize, n, remblockSize);
    }
}
