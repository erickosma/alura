
public class Main {

    public static void main(String[] args) {
        ConsumidorDeString consumidorDeString = new ConsumidorDeString();
        consumidorDeString.ordenaString();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Executando um Runnable");
            }

        }).start();

        new Thread(() -> System.out.println("Executando um Runnable 2")).start();
    }
}
