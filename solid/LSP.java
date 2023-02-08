package solid;

/**
 * Liskov Substitution Principle
 * 
 * Tem que se garantir que ao trocar o usuo de um objeto por outro
 * de classes da mesma família, a aplicação não quebre.
 */
public class LSP {

    public static void main(String[] args) {
        
        Rectangle rec = new Rectangle();
        rec.setHeight(2);
        rec.setWidth(3);
        printRec(rec);

        Rectangle square = new Square(2);
        printRec(square);
    }

    /**
     * Aqui há a quebra do princípio de Liskov.
     * Não é possível usar ojbetos de subclasse de Rectangle
     * pois quebra o cálculo de print
     */
    static void printRec(Rectangle rectangle) {
        int width = rectangle.getWidth();
        rectangle.setHeight(10);
        System.out.println(
            String.format("Area rectangle: %d%nArea calc: %d", rectangle.getArea(), width * 10)
        );
    }
}

class Rectangle {

    protected int width, height;

    int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    int getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle [width=" + width + ", height=" + height + "]";
    }
}

class Square extends Rectangle {

    Square() { }

    Square(int side) {
        width = height = side;
    }

    @Override
    void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}