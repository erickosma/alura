import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;

public class ConsumidorDeString {
    public void ordenaString(){
        List<String> palavras = new ArrayList<>();
        palavras.add("alura online");
        palavras.add("casa do código");
        palavras.add("caelum");

        palavras.forEach(System.out::println);
        palavras.sort(Comparator.comparingInt(String::length));
        palavras.forEach(System.out::println);

        palavras.sort(Comparator.comparingInt(s -> length()));

    }
}