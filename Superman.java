public class Superman {
    private static volatile Superman superman; //superman is a static variable same for all threads; volatile ensures visibility between thread
    private Superman() { //private constructor

    }

    public static Superman getInstance() {
        if (superman == null) {
            synchronized(Superman.class) {
                if (superman == null) {
                    superman = new Superman();
                }
            }
        }
        return superman;
    }

    public void fly() {
        System.out.println("I am superman an I can fly!");
    }
}
