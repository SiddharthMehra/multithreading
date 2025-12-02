import java.util.concurrent.*;

class Demonstration {
    public static void main (String args[]) throws Exception {
        NonblockingStack<Integer> stack = new NonblockingStack<>();
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        int numThreads=2;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        long start = System.currentTimeMillis();
        Integer testValue = new Integer(51);

        try {
            for (int i=0; i<numThreads; i++) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<10000; i++) {
                            stack.push(testValue);
                        }
                        try {
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException ex) {
                            System.out.println("exception");
                        }

                        for (int i=0; i<10000; i++) {
                            stack.pop();
                        }
                    }
                });
            }
        }
        finally {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
        }

        System.out.println("number of elements in the stack" + stack.size());
    }
}
