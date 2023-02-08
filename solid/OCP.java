package solid;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Open-Close Principle
 * 
 * Prefira herdar classes do que modificá-las
 */
public class OCP {

    public static void main(String[] args) {

        Product p1 = new Product("Maçã", Color.GREEN, Size.SMALL);
        Product p2 = new Product("Árvore", Color.GREEN, Size.LARGE);
        Product p3 = new Product("Casa", Color.BLUE, Size.LARGE);

        List<Product> products = List.of(p1, p2, p3);
        BadProductFilter bpf = new BadProductFilter();
        bpf.filterByColor(products, Color.GREEN)
                .forEach(p -> System.out.println(p.toString()));

        ProductFilter pf = new ProductFilter();
        pf.filter(products, new ColorSpecification(Color.BLUE))
                .forEach(p -> System.out.println(p.toString()));
    }
}

enum Color {
    RED, GREEN, BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE
}

class Product {

    final String name;
    final Color color;
    final Size size;

    Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    @Override
    public String toString() {
        return name + " is " + color;
    }
}

/**
 * Começou a quebrar o princípio.
 * 
 * antes, só tinha o filtro por color e agora precisou
 * modificar a classe para implementar o filtro por size
 */
class BadProductFilter {

    Stream<Product> filterByColor(Collection<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    Stream<Product> filterBySize(Collection<Product> products, Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    Stream<Product> filterByColorAndSize(Collection<Product> products, Color color, Size size) {
        return products.stream().filter(p -> p.color == color && p.size == size);
    }
}

interface Specification<T> {

    boolean isSatisfied(T t);
}

interface Filter<T> {

    Stream<T> filter(Collection<T> coll, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {

    private final Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product product) {
        return color == product.color;
    }
}

class SizeSpecification implements Specification<Product> {

    private final Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product product) {
        return size == product.size;
    }
}

class ProductFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(Collection<Product> coll, Specification<Product> spec) {
        return coll.stream().filter(spec::isSatisfied);
    }

}

class AndSpecification implements Specification<Product> {

    private final Specification<Product> first, second;

    public AndSpecification(Specification<Product> first, Specification<Product> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(Product p) {
        return first.isSatisfied(p) && second.isSatisfied(p);
    }
}