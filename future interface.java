// create two tasks. first poll to check if task is completed, in second we cancel the task

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Demonstration {

    static ExecutorService threadPool = Executors.newSingleThreadExecutor();
    
    public static void main (String args[]) throws Exception {
        System.out.println(pollingStatusAndCancelTask(10));
        threadPool.shutdown();
    }

    static int pollingStatusAndCancelTask(final int n) throws Exception {

        int result = -1;

        Callable<Integer> sumTask1 = new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(10);

                int sum = 0;
                for (int i=1; i<=n; i++) {
                    sum+=i;
                }
                return sum;
            }
        };

        Callable<Void> randomTask = new Callable<Void>() {

            public Void call() throws Exception {
                Thread.sleep(3600*1000); //sleep for an hour
                return null;
            }
        };

        Future<Integer> f1 = threadPool.submit(sumTask1);
        Future<Void> f2 = threadPool.submit(randomTask);

        try {
            f2.cancel(true);

            while (!f1.isDone()) {
                System.out.println("waiting for first task to complete");
            }

            result = f1.get();
        }
         catch (ExecutionException e) {
            System.out.println("something went wrong");
         }

         System.out.println("second task cancelled" + f2.isCancelled());

         return result;
    }
}

/*since our thread pool consists of a single thread and the first task sleeps a bit before it starts executing,
 we can assume that the second task will be cancelled /* */

