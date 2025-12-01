import java.util.concurrent.Semaphore;

class FooBar {
    private int n;

    Semaphore s = new Semaphore(0);
    Semaphore s2 = new Semaphore(1);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable foo) throws InterruptedException {
        for (int i=0; i<n; i++) {
            s2.acquire();
            printFoo.run();
            s.release();
        }
    }

    public void bar(Runnable bar) throws InterruptedException {
        for (int i=0; i<n; i++) {
            s.acquire();
            printBar.run();
            s2.release();
        }
    }
}