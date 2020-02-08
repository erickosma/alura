import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        //consumer();
        List<Curso> cursos = new ArrayList<Curso>();
        cursos.add(new Curso("Python", 45));
        cursos.add(new Curso("JavaScript", 150));
        cursos.add(new Curso("Java 8", 113));
        cursos.add(new Curso("C", 55));

        cursos.sort(Comparator.comparingInt(Curso::getAlunos));

        //stramCursos(cursos);

        //optional(cursos);

         cursos.stream()
                .mapToInt(Curso::getAlunos)
                .average()
                .ifPresent(System.out::println);
    }

    private static void optional(List<Curso> cursos) {
        Optional<Curso> optional = cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .findAny();

        optional.ifPresent(c -> System.out.println(c.getNome()));

        System.out.println("---------------");

        cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .findAny()
                .ifPresent(c -> System.out.println(c.getNome()));


        List<Curso> resultados = cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .collect(Collectors.toList());

        resultados.forEach( c ->  System.out.println(c.getAlunos()));


        Map mapa = cursos
                .stream()
                .filter(c -> c.getAlunos() > 100)
                .collect(Collectors.toMap(c -> c.getNome(), c -> c.getAlunos()));
    }

    private static void stramCursos(List<Curso> cursos) {
        cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .forEach(c -> System.out.println(c.getNome()));
        System.out.println("---------------");

        cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .map(Curso::getAlunos)
                .forEach(System.out::println);
        System.out.println("---------------");

        IntStream stream = cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .mapToInt(Curso::getAlunos);

        stream.forEach(System.out::println);

        System.out.println("---------------");
        int soma = cursos.stream()
                .filter(c -> c.getAlunos() > 100)
                .mapToInt(Curso::getAlunos)
                .sum();
        System.out.println(soma);
    }

    private static void consumer() {
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
