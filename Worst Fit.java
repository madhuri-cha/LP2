import java.util.Scanner;

public class WorstFit {

    // Function to implement the Worst Fit algorithm
    static void worstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {

        // Array to store the allocation status of each process
        int allocation[] = new int[n];

        // Initialize allocation array with -1 indicating not allocated
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        // Iterate through each process to find the worst fit block
        for (int i = 0; i < n; i++) {
            int wstIdx = -1;

            // Iterate through each block to find the worst fit block
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (wstIdx == -1)
                        wstIdx = j;
                    else if (blockSize[wstIdx] < blockSize[j])
                        wstIdx = j;
                }
            }

            // If a worst fit block is found, allocate the process to that block
            if (wstIdx != -1) {
                allocation[i] = wstIdx;
                blockSize[wstIdx] -= processSize[i];
                remblockSize[i] = blockSize[wstIdx];
            }
        }

        // Display the allocation and remaining block size information
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");

            // If the process is allocated, print block number and remaining block size
            if (allocation[i] != -1)
                System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
            else
                // If the process is not allocated, print "Not Allocated" and remaining block size
                System.out.print("Not Allocated" + "\t" + remblockSize[i]);
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

        // Call the Worst Fit algorithm function
        worstFit(blockSize, m, processSize, n, remblockSize);
    }
}
