class HelloWorld {

    public static void main (String args[]) throws InterruptedException {
        ExecuteMe executeMe = new ExecuteMe();
        Thread innerThread = new Thread(executeMe);
        innerThread.start();

        System.out.println("main thread sleeping at" + System.currentTimeMillis());
        Thread.sleep(5000);
        innerThread.interrupt();
        System.out.println("main thread exiting at " + System.currentTimeMillis());
    }

    static class ExecuteMe implements Runnable {
        public void run() {
            try {
                System.out.println("inner thread goes to sleep at" + System.currentTimeMillis());
                Thread.sleep(1000*1000);
            } catch (InterruptedException ie) {
                System.out.println("inner thread interrupted at" + System.currentTimeMillis());
            }
        }
    }
}

/* output -> Main thread sleeping at 
            innerThread goes to sleep at
            main thread exiting at
            inner thread interrupted at /* */
