package gamma.creational.prototype;

import java.io.Serializable;

import utils.ObjectCloner;

public class SerializationDeepCopy {

    public static void main(String[] args) {

        Car c1 = new Car("wolks", "black");
        Car c2 = ObjectCloner.deepCopy(c1);
        c2.brand = "Ford";
        c2.color = "red";

        System.out.println(c1);
        System.out.println(c2);
    }

    static class Car implements Serializable {

        String brand;
        String color;

        public Car(String brand, String color) {
            this.brand = brand;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Car [brand=" + brand + ", color=" + color + "]";
        }
    }
}