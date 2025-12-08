// thread pool implementation example

void receiveAndExecuteClientOrdersBest() {

    int expectedConcurrentOrders = 100;
    Executor executor = Executors.newFixedThreadPool(expectedConcurrentOrders);
    
    while (true) {
        final Order order = new Order();

        executor.execute(new Runnable() {

            public void run() {
                order.execute();
            }
        });
    }
}
