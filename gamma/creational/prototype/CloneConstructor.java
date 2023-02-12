package gamma.creational.prototype;

import java.util.Arrays;

/**
 * A partir de um objeto, clona-lo e amplia-lo/melhora-lo.
 * 
 * deep cloning e não shallow (raso) cloning.
 * 
 * A interface Cloneable é usada para shallow (raso) cloning.
 */
public class CloneConstructor {

    public static void main(String[] args) throws Exception {

        antiPattern();
        prototypePattern();
    }

    private static void antiPattern() throws CloneNotSupportedException {
        Person p1 = new Person(
            new String[]{"João", "Silva"},
            new Address("Rua Europa", 10));

        // anti pattern usar o método clone
        Person p2 = p1.clone();
        p2.fullName[0] = "Anti_Patter";
        p2.address.number = 666;

        System.out.println(p1);
        System.out.println(p2);
    }

    private static void prototypePattern() throws CloneNotSupportedException {
        Person p1 = new Person(
            new String[]{"João", "Silva"},
            new Address("Rua Europa", 10));

        // anti pattern usar o método clone
        Person p2 = new Person(p1);
        p2.fullName[0] = "Maria";
        p2.address.number = 777;

        System.out.println(p1);
        System.out.println(p2);
    }

    static class Address implements Cloneable {

        String street;
        int number;

        public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }

        public Address(Address other) {
            this.street = other.street;
            this.number = other.number;
        }

        @Override
        public Address clone() throws CloneNotSupportedException {
            return new Address(street, number);
        }

        @Override
        public String toString() {
            return "Address [street=" + street + ", number=" + number + "]";
        }
    }

    static class Person implements Cloneable {

        String[] fullName;
        Address address;

        public Person(String[] fullName, Address address) {
            this.fullName = fullName;
            this.address = address;
        }

        public Person(Person other) {

            this.fullName = other.fullName.clone();
            this.address = new Address(other.address);
        }

        @Override
        public Person clone() throws CloneNotSupportedException {
            return new Person(
                fullName.clone(),
                address.clone());
        }

        @Override
        public String toString() {
            return "Person [fullName=" + Arrays.toString(fullName) + ", address=" + address + "]";
        }
    }
}
