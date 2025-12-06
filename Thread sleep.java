class SleepThreadExample {

    public static void main (String args[]) throws Exception {

        ExecuteMe executeMe = new ExecuteMe();
        Thread innerThread = new Thread(executeMe);
        innerThread.start();
        innerThread.join();
        System.out.println("main thread exiting");

    }

    static class ExecuteMe implements Runnable {
        public void run() {
            System.out.println("inner thread going to sleep");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }
        }
    }
}

// if we remove line 8, main thread may print its statement before inner thread is done executing.
// in this program, the inner thread sleeps for 1000ms, prints output then main thread exits only after inner thread is done processing 
