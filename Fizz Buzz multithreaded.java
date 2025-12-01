class FizzBuzz {
    private int n;
    private int currentNumber = 1;

    public FizzBuzz(int n) {
        this.n = n;
    }

    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while (currentNumber<=n) {
            if (currentNumber%3!=0 || currentNumber%5==0) {
                wait();
                continue;
            }
            printFizz.run();
            currentNumber+=1;
            notifyAll();
        }
    }

    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while (currentNumber<=n) {
            if (currentNumber % 5!=0 || currentNumber%3==0) {
                wait();
                continue;
            }
            printBuzz.run();
            currentNumber+=1;
            notifyAll();
        }
    }

    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (currentNumber<=n) {
            if (currentNumber%15!=0)  {
                wait();
                continue;
            }
            printFizzBuzz.run();
            currentNumber+=1;
            notifyAll();
        }
    }

    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while (currentNumber<=n) {
            if (currentNumber % 3==0 || currentNumber % 5 == 0) {
                wait();
                continue;
            }
            printNumber.accept(currentNumber);
            currentNumber+=1;
            notifyAll();
        }
    }
}