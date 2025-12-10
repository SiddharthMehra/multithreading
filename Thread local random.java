import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

class Demonstration {
    public static void main (String args[]) throws Exception {
        performanceUsingRandom();
        performanceUsingThreadLocalRandom();
    }

    static void performanceUsingThreadLocalRandom() throws Exception {

        ExecutorService es = Executors.newFixedThreadPool(15);

        Runnable task = new Runnable() {

            @Override
            public void run() {
                for (int i=0; i<5000; i++) {
                    ThreadLocalRandom.current().nextInt();
                }
            }
        };

        int numThreads = 4;
        Future[] futures = new Future[numThreads];
        long start = System.currentTimeMillis();

        try {
            for (int i=0; i<numThreads; i++) {
                futures[i] = es.submit(task);

            }

            for (int i=0; i<numThreads; i++) {
                futures[i].get();
            }

            long executionTime = System.currentTimeMillis() - start;
            System.out.println("execution time using thread local random" + executionTime + "milliseconds");
        } finally {
            es.shutdown();
        }

    }

    static void performanceUsingRandom() throws Exception {
        Random random = new Random();
        ExecutorService es = Executors.newFixedThreadPool(15);

        Runnable task = new Runnable() {
            @Override
            public void run() {

                for (int i=0; i<5000; i++) {
                    random.nextInt();
                }
            }
        };

        int numThreads = 4;
        Future[] futures = new Future[numThreads];
        long start = System.currentTimeMillis();

        try {
            for (int i=0; i<numThreads; i++) {
                futures[i] = es.submit(task);
            }

            for (int i=0; i<numThreads; i++) {
                futures[i].get();
            }

            long executionTime = System.currentTimeMillis() - start;
            System.out.println("execution time" + executionTime + "milliseconds");
        } finally {
            es.shutdown();
        }

    }
}
