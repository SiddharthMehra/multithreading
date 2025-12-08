import java.util.concurrent.Executor;

class ThreadExecutorExample {

    public static void main (String args[]) {
        DumbExecutor myExecutor = new DumbExecutor();

        Mytask task = new Mytask();
        myExecutor.execute(task);

    }

    static class DumbExecutor implements Executor {

        public void execute(Runnable runnable) {

            Thread newThread = new Thread(runnable);
            newThread.start();
        }
    }

    static class Mytask implements Runnable {
        public void run() {
            System.out.println("myTask is running now");
        }
    }
}
