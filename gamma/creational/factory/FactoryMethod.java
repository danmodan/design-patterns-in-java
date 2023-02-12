package gamma.creational.factory;

/**
 * Construir/obter o objeto de uma única vez sem
 * Sem contato com seu construtor.
 * 
 * Permite diferentes nomes de métodos com os mesmos parâmetros,
 * coisa que o construtor não permite.
 * 
 * by-pass nas questões de heranças que um construtor deve obedecer.
 */
public class FactoryMethod {
    
    public static void main(String[] args) {
        
        Point cartesianPoint = Point.newCartesianPoint(10, 20);
        Point polarPoint = Point.newPolarPoint(10, 180);
        System.out.println("Catesian: " + cartesianPoint);
        System.out.println("Polar: " + polarPoint);
    }


    private static class Point {

        double x, y;

        // construtor com coordenadas cartesianas
        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        
        // construtor com medidas polares. 
        // Não é possível criar essa sobrecarga por causa do tipo dos argumentos
        // public Point (double rho, double beta) {
        //     x = rho * Math.cos(beta);
        //     y = rho * Math.sin(beta);
        // }

        // Factory methods
        // Fazer o construtor da classe privado.

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }


        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double beta) {
            double x = rho * Math.cos(beta);
            double y = rho * Math.sin(beta);
            return new Point(x, y);
        }
    }
}
