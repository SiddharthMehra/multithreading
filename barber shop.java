import java.util.concurrent.locks.ReentrantLock;
import java.util.HashSet;


class Demonstration {
    public static void main (String[] args) throws InterruptedException {
        BarbershopProblem.runTest();

    
    }
}

class BarbershopProblem {
    final int CHAIRS = 3;
    Semaphore waitForCustomerToEnter = new Semaphore(0);
    Semaphore waitForBarberToGetReady = new Semaphore(0);
    Semaphore waitForCustomerToLeave = new Semaphore(0);
    Semaphore waitForBarberToCutHair = new Semaphore(0);

    int waitingCustomers = 0;
    ReentrantLock lock = new ReentrantLock();
    int haircutsGiven = 0;

    void customerWalksIn() throws InterruptedException {
        lock.lock();

        if (waitingCustomers == CHAIRS) {
            System.out.println("customer walks out, all chairs occupied");
            lock.unlock();
            return;
        }

        waitingCustomers++;
        lock.unlock();

        waitForCustomerToEnter.release();
        waitForBarberToGetReady.acquire();

        lock.unlock();
        waitingCustomers--;
        lock.unlock();

        waitForBarberToCutHair.acquire();
        waitForCustomerToLeave.release();
    }

    void barber() throws InterruptedException {
        while (true) {
            waitForCustomerToEnter.acquire();
            waitForBarberToGetReady.release();
            haircutsGiven++;
            System.out.println("barber cutting hair");
            Thread.sleep(50);
            waitForBarberToCutHair.release();
            waitForCustomerToLeave.acquire();
        }
    }

    public static void runTest() throws InterruptedException {
        HashSet<Set> set = new HashSet<Thread>();

        final BarbershopProblem barbershopProblem = new BarbershopProblem();

        Thread barberThread = new Thread(new Runnable() {
            public void run() {
                try {
                    barbershopProblem.barber();
                } catch (InterruptedException ie) {

                }
            }
        });
        barberThread.start();
        
        for (int i=0; i<10; i++) {
            Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            barbershopProblem.customerWalksIn();
                        } catch (InterruptedException ie) {

                        }
                    }
            });
            set.add(t);
        }

        for (Thread t: set) {
            t.start();
            Thread.sleep(5);
        }
        barberThread.join();
    }
}