// 1. runnable interface

class Demonstration {
    public static void main (String args[]) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("hello");
            }
        });
        t.start();
    }
}

//separate class implementing the runnable interface

class Demonstration {
    public static void main (String args[]) {

        ExecuteMe executeMe = new ExecuteMe();
        Thread t = new Thread(executeMe);
        t.start();

    }
}

class ExecuteMe implements Runnable {
    public void run() {
        System.out.println("hello");
    }
}


//2. sublclassing the thread class

class Demonstration {
    public static void main (String args[]) throws Exception {

        ExecuteMe executeMe = new ExecuteMe();
        executeMe.start();
        executeMe.join();

    }
    }

class ExecuteMe extends Thread {

    @Override
    public void run() {
        System.out.println("I ran after extending thread class");
    }
}

// con of the second approach is that one is forced to extend thread class which limits code flexbility
