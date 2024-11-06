import java.util.Scanner;

public class FirstFit {
    // Function to implement the First Fit algorithm
    static void firstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
        // Array to store the allocation status of each process
        int allocation[] = new int[n];

        // Initialize allocation array with -1 indicating not allocated
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        // Iterate through each process to find the first fit block
        for (int i = 0; i < n; i++) {
            // Iterate through each block to find the first block that fits the process
            for (int j = 0; j < m; j++) {
                // Check if the block size is greater than or equal to the process size
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remblockSize[i] = blockSize[j];
                    break; // Break out of the loop once the first fit block is found
                }
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

        // Call the First Fit algorithm function
        firstFit(blockSize, m, processSize, n, remblockSize);
    }
}
