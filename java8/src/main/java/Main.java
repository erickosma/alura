import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        BigDecimal t = BigDecimal.valueOf(Long.parseLong("1,155"));
        System.out.println(t);
        System.exit(1);

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
