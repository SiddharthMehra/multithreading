//daemon threads

class Demonstration {
    public static void main (String args[]) throws InterruptedException {
        ExecuteMe executeMe = new ExecuteMe();
        Thread innerThread = new Thread(executeMe);
        innerThread.setDaemon(true);
        innerThread.start();
    }
} 

class ExecuteMe implements Runnable {

    public void run() {
        while (true) {
            System.out.println("say hello");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {

            }
        }
    }
}

// as soon as the main thread exists after starting the inner thread, JVM also exits the spawned thread because it is marked as daemon


//make the following code change for spawned thread execution

Thread innerThread = new Thread(executeMe);
innerThread.start();
innerThread.join();
