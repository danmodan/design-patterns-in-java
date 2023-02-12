package gamma.structural.adapter;

/**
 * Fazer objetos incompatíveis
 * trabalharem entre si.
 * 
 * Construa a interface que você quer
 * a partir da interface que você tem.
 */
public class Adapter {

    public static void main(String[] args) {

        RoundHole roundHole = new RoundHole(10);

        RoundPeg roundPeg = new RoundPeg();
        roundPeg.setRadius(11);

        SquarePeg squarePeg = new SquarePeg();
        squarePeg.setSide(12);

        System.out.println(roundHole.fits(roundPeg));
        System.out.println(roundHole.fits(new SquarePegAdapter(squarePeg)));
    }
}

// Esse cliente só trabalha com o RoundPeg
class RoundHole {

    private int radius;

    RoundHole(int radius) {
        this.radius = radius;
    }

    boolean fits(RoundPeg roundPed) {
        return this.radius >= roundPed.getRadius();
    }
}

class RoundPeg {

    private int radius;

    void setRadius(int radius) {
        this.radius = radius;
    }

    int getRadius() {
        return radius;
    }
}

class SquarePeg {

    private int side;

    void setSide(int side) {
        this.side = side;
    }

    int getSide() {
        return side;
    }
}

class SquarePegAdapter extends RoundPeg {

    SquarePeg squarePeg;

    SquarePegAdapter(SquarePeg squarePeg) {
        this.squarePeg = squarePeg;
    }

    @Override
    void setRadius(int radius) {
        squarePeg.setSide((int) (radius * Math.sqrt(2)));
    }

    @Override
    int getRadius() {
        return (int) (squarePeg.getSide() * Math.sqrt(2) / 2);
    }
}