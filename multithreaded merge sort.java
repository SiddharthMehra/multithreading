import java.util.Random;

class Demonstration {
    private static int size = 25;
    private static Random random = new Random(System.currentTimeMillis());
    private static int[] input = new int[size];

    static private void createTestData() {
        for (int i=0 i<size; i++) {
            input[i]=random.nextInt(10000);
        }
    }

    static private void printArray(int[] input) {
        System.out.println();
        for (int i=0; i<input.length; i++) {
            System.out.println(" " + input[i] + " ");
        }
        System.out.println();
    }

    public static void main (String args[]) {
        createTestData();
        System.out.println("unsorted array");
        printArray(input);
        long start = System.currentTimeMillis();
        (new MultithreadedMergeSort()).mergeSort(0, input.length-1, input);
        long end = System.currentTimeMillis();
        printArray(input);
    }


Class MultithreadedMergeSort() {
    private static int size = 25;
    private int[] input = new int[size];
    private int[] scratch = new int[size];

    void mergeSort(final int start, final int end, final int[] input) {
        if (start == end) {
            return;
        }

        final int mid = start + ((end-start)/2);
        
        Thread worker1 = new Thread(new Runnable() {
            public void run() {
                mergeSort(start, mid, input);
            }
        });

        Thread worker2 = new Thread(new Runnable() {
            public void run() {
                mergeSort(mid+1, end, input);
            }
        });

        worker1.start();
        worker2.start();

        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException ie) {

        }

        int i = start;
        int j = mid+1;
        int k;

        for (k=start; k<=end; k++) {
            scratch[k]=input[k];
        }

        k=start;
        while (k<=end) {
            if (i<=mid && j<=end) {
                input[k] = Math.min(scratch[i], scratch[j]);
                if (input[k] == scratch[i]) {
                    i++;
                } else {
                    j++;
                }

            } else if (i<=mid && j>end) {
                input[k]=scratch[i];
                i++;
            } else {
                input[k]=scratch[j];
                j++;
            }
            k++;
        }

    }
}