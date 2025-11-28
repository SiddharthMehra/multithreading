/*  
 * A barrier allows multiple threads to meet at a point 
 * before anyone moves ahead.
 */

 class Demonstration {
    public static void main(String[] args) {
        try {
            barrier.runTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class barrier {
    int count = 0;
    int released = 0;
    int totalThreads;

    public barrier(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public static void runTest() throws InterruptedException {
        final barrier b = new barrier(3);

        Thread p1 = new Thread(() -> {
            try {
                System.out.println("Thread1");
                b.await();

                System.out.println("Thread1");
                b.await();

                System.out.println("Thread1");
                b.await();
            } catch (InterruptedException ignored) {}
        });

        Thread p2 = new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println("Thread2");
                b.await();

                Thread.sleep(500);
                System.out.println("Thread2");
                b.await();

                Thread.sleep(500);
                System.out.println("Thread2");
                b.await();
            } catch (InterruptedException ignored) {}
        });

        Thread p3 = new Thread(() -> {
            try {
                Thread.sleep(1500);
                System.out.println("Thread3");
                b.await();

                Thread.sleep(1500);
                System.out.println("Thread3");
                b.await();

                Thread.sleep(1500);
                System.out.println("Thread3");
                b.await();
            } catch (InterruptedException ignored) {}
        });

        p1.start();
        p2.start();
        p3.start();

        p1.join();
        p2.join();
        p3.join();
    }

    public synchronized void await() throws InterruptedException {
        // First phase
        while (count == totalThreads) { //protects barrier from the next wave of threads 
            wait();
        }

        count++;

        if (count == totalThreads) {
            released = totalThreads;
            notifyAll();
        } else {
            while (count < totalThreads) {
                wait();
            }
        }

        // Second phase: letting them go one by one
        released--;

        if (released == 0) {
            count = 0;     // reset barrier → reusable
            notifyAll();
        }
    }
}
