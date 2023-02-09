package gamma.factory;

/**
 * Obedece o Single Responsability Principle
 */
public class FactoryClass {
    
    public static void main(String[] args) {

        Point cartesianPoint = Point.Factory.newCartesianPoint(10, 20);
        Point polarPoint = Point.Factory.newPolarPoint(10, 180);
        System.out.println("Catesian: " + cartesianPoint);
        System.out.println("Polar: " + polarPoint);
    }


    private static class Point {

        public final double x, y;

        // se decidir que o construt deve constinuar privado e forçar o uso da classe Facotry,
        // a classe Facotry tem que ficar dentro dessa.
        // 
        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }

        // Factory Class
        private static class Factory {

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
}