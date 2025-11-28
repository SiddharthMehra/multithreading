

class Demonstration {
    public static void main (String args[]) {
        final ReadWriteLock rwl = new ReadWriteLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("attempting to acquire lock t1" + System.currentTimeMillis());
                    rwl.acquireWriteLock();
                    System.out.println("lock acquired for t1" + System.currentTimeMillis());

                    Thread.sleep(2000);
                    rwl.releaseWriteLock();
                }
                catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("attempting to acquire lock t2" + System.currentTimeMillis());
                    rwl.acquireWriteLock();
                    System.out.println("lock acquired for t1" + System.currentTimeMillis());

                    Thread.sleep(2000);
                    rwl.releaseWriteLock();
                }
                catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("attempting to acquire read lock ");
                    rwl.acquireReadLock();
                } catch (InterruptedException ie) {
                    // ignore or handle
                }
            }
        });

        Thread tReader2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("read lock about to release");
                    rwl.releaseReadLock();
                } catch (InterruptedException ie) {
                    // ignore or handle
                }
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();
    }
}

  class ReadWriteLock {
    boolean isWriteLocked = false;
    int readers = 0;

    public synchronized void acquireReadLock() throws InterruptedException  {
            while (isWriteLocked) {
                wait();
            }
            readers++;
    }

    public synchronized void releaseReadLock() {
        if (readers == 0) {
            throw new IllegalStateException("no read lock held");
        }
        readers--;
        notify();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
            while (isWriteLocked || readers!=0) {
                wait();
            }
            isWriteLocked = true;
    }

    public synchronized void releaseWriteLock()  {
        isWriteLocked = false;
        notify();
    }
  }

        