import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Demonstration {
    
    static Random random = new Random(System.currentTimeMillis());

    public static void main(String args[]) throws Exception{

        completionServiceExample();
    }

    static void completionServiceExample() throws Exception {

        class TrivialTask implements Runnable {
            int n;

            public TrivialTask(int n) {
                this.n = n;
            }

            public void run() {
                try {
                    Thread.sleep(random.nextInt(101));
                    System.out.println(n*n);
                } catch (InterruptedException ie) {

                }
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<Integer>(threadPool);

        //submit 10 trivial tasks
        for (int i=0; i<10; i++) {
            service.submit(new TrivialTask(i), new Integer(i));
        }
        
        //wait for all tasks to get done
        int count = 10;
        while (count!=0) {
            Future<Integer> f = service.poll();
            if (f!=null) {
                System.out.println("Thread" + f.get() + "got done");
                count--;
            }
        }

        threadPool.shutdown();
    }
}
