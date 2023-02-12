package gamma.creational.factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Usar do polimorfismo e herança para
 * abstrair a construção via Factory
 */
public class AbstractFactory {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            HotDrinkMachine machine = new HotDrinkMachine();
            var availableDrinks = new ArrayList<>(machine.getAvailableDrinks());
            System.out.println("Escolha um drink:");
            for(var i = 0; i < availableDrinks.size(); i++) {
                System.err.println(String.format("%d) %s", i, availableDrinks.get(i)));
            }
            String chosen = availableDrinks.get(scanner.nextInt());
            System.out.print("Informe a quantidade: ");
            int amount = scanner.nextInt();

            var drinkDone = machine.makeDrink(chosen, amount);

            System.out.println("Aqui está: " + chosen);
            drinkDone.consume();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface HotDrink {
        void consume();
    }

    public static class TeaDrink implements HotDrink {

        @Override
        public void consume() {
            System.out.println("Chá bom!");
        }
    }

    public static class CoffeDrink implements HotDrink {

        @Override
        public void consume() {
            System.out.println("Café bom!");
        }
    }

    public static interface HotDrinkFactory {
        HotDrink prepare(int amount);
    }

    public static class TeaDrinkFactory implements HotDrinkFactory {

        @Override
        public HotDrink prepare(int amount) {
            System.out.println("Esquente " + amount +"ml de água para o Chá bom!");
            return new TeaDrink();
        }
    }

    public static class CoffeDrinkFactory implements HotDrinkFactory {

        @Override
        public HotDrink prepare(int amount) {
            System.out.println("Esquente " + amount +"ml de água para o Café bom!");
            return new CoffeDrink();
        }
    }

    public static class HotDrinkMachine {

        private final Map<String, HotDrinkFactory> namedFactories;

        public HotDrinkMachine() {

            this.namedFactories = ReflectionUtils
                .getSubClasses(HotDrinkFactory.class)
                .stream()
                .collect(Collectors.toMap(
                    clazz -> clazz.getSimpleName().replace("Factory", ""), 
                    clazz -> {
                        try {
                            return clazz.getDeclaredConstructor().newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                ));
        }

        public Set<String> getAvailableDrinks() {
            return namedFactories.keySet();
        }

        public HotDrink makeDrink(String drinkName, int amount) {
            return namedFactories.get(drinkName).prepare(amount);
        }
    }

    // utils
    public static class ReflectionUtils {

        private ReflectionUtils() {}

        static <T> Set<Class<T>> getSubClasses(Class<T> superClass) {

            String packageName = superClass.getPackageName();
            String filesPath = packageName.replaceAll("[.]", "/");

            InputStream is = ClassLoader
                .getSystemClassLoader()
                .getResourceAsStream(filesPath);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                return br.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .filter(clazz -> 
                        (clazz.getSuperclass() != null && clazz.getSuperclass().equals(superClass)) ||
                        Arrays.asList(clazz.getInterfaces()).contains(superClass))
                    .collect(Collectors.toSet());
            } catch(Exception e) {

                e.printStackTrace();
            }

            return null;
        }

        private static Class getClass(String className, String packageName) {

            try {

                return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}