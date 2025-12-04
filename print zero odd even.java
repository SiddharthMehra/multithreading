import java.util.concurrent.*;

class PrintNumberSeries {
    private int n;
    private Semaphore zeroSem, oddSem, evenSem;
    
    public PrintNumberSeries(int n) {
        this.n = n;
        zeroSem = new Semaphore(1); // initally permit for printing only zero
        oddSem = new Semaphore(0);
        evenSem = new Semaphore(0);

    }

    public void printZero() {
        for (int i=0; i<n; i++) {
            try {
                zeroSem.acquire();
            } catch (Exception e) {

            }
            System.out.println("0");
            (i%2==0 ? oddSem : evenSem).release();

        }
    }

    public void printEven() {
        for (int i=2; i<=n; i+=2) {
            try {
                evenSem.acquire();
            } catch (Exception e) {

            }

            System.out.println("i");
            zeroSem.release();
        }
    }

    public void printOdd() {
        for (int i=1; i<=n; i+=2) {
            try {
                oddSem.acquire();
            } catch (Exception e) {

            }

            System.out.println("i")
            zeroSem.release();
        }
    }
}

class PrintNumberSeriesThread extends Thread {
    PrintNumberSeries zoe;
    String method;

    public PrintNumberSeriesThread(PrintNumberSeries zoe, String method) {
        this.zoe = zoe;
        this.method = method;
    }

    public void run() {
        if ("zero".equals(method)) {
            try {
                zoe.printZero();
            } catch (Exception e) {

            }
        }
        else if ("even".equals(method)) {
            try {
                zoe.printEven();
            } catch (Exception e) {

            }
        } else if ("odd".equals(method)) {
            try {
                zoe.printOdd();
            } catch (Exception e) {

            }
        }
    }
}

public class Main {
    public static void main (String args[]) {
        PrintNumberSeries = new PrintNumberSeries(5);

        Thread t1 = new PrintNumberSeriesThread(zoe, "zero");
        Thread t2 = new PrintNumberSeriesThread(zoe, "even");
        Thread t3 = new PrintNumberSeriesThread(zoe, "odd");

        t2.start();
        t1.start();
        t3.start();
    }
}

