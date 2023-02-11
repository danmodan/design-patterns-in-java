package gamma.singleton;

import java.util.EnumMap;
import java.util.Map;

/**
 * flexibilidade de limitar a quantidade de instâncias
 * do objeto basedo num parâmetro.
 * 
 * E é lazy load.
 */
public class MultitonPattern {

    public static void main(String[] args) {

        var newInstance1 = Printer.getInstance(Fruit.APPLE);
        var newInstance2 = Printer.getInstance(Fruit.BANANA);
        var newInstance3 = Printer.getInstance(Fruit.BANANA);
        System.out.println("Mesma instância: " + (newInstance1 == newInstance2));
        System.out.println("Mesma instância: " + (newInstance2 == newInstance3));
    }
}

class Printer {

    private static int instanceCounter = 0;
    private static final Map<Fruit, Printer> map = new EnumMap<>(Fruit.class);

    private Printer() {
        instanceCounter++;
        System.out.println(instanceCounter + " instâncias criadas.");
    }

    static Printer getInstance(Fruit param) {

        if (map.containsKey(param)) {
            return map.get(param);
        }

        Printer newInstance = new Printer();
        map.put(param, newInstance);
        return newInstance;
    }
}

enum Fruit {
    APPLE,
    BANANA
}