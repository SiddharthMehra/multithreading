class Demonstration {
    public static void main (String args[]) throws Exception{
        SynchronousExecutor executor = new SynchronousExcecutor;
        executor.asynchronousExecution(() -> {
            System.out.println("i am done");
        });
        System.out.println("main thread exiting"); //first I am done is printed then main thread exiting making the execution synchronous
        
    }
}

interface Callback {
    public void done();

}

class SynchronousExcecutor extends Executor {
    @Override
    public void ansynchronousExecution(Callback callback) throws Exception {

        Object signal = new Object();
        final boolean[] isDone = new boolean[1];

        Callback cb = new Callback() {
            @Override
            public void done() {
                callback.done();
                synchronized (signal) {
                    signal.notify();
                    isDone[0]=true;
                }
            }
        };

        super.asynchronousExecution(cb);

        synchronized(signal) {
            while (!isDone[0]) {
                signal.wait();
            }
        }

    }
}

class Executor {

    public void asynchronousExecution(Callback callback) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {

            }
            callback.done();
        });
        t.start();

    }
}

