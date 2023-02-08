package gamma.builder;

import java.math.BigDecimal;

class FacetedBuilder {

    public static void main(String[] args) {

        Person p = new PersonBuilder()
        .name("Danilo")
        .address()
        .street("Rua")
        .zipCode("666")
        .job()
        .role("Dev")
        .salary(new BigDecimal("100"))
        .build();

        System.out.println(p);
    }
}

class Person {

    String name;
    Address address;
    Job job;

    @Override
    public String toString() {
        return "Person [address=" + address + ", job=" + job + "]";
    }
}

class Address {

    String street;
    String zipCode;

    @Override
    public String toString() {
        return "Address [street=" + street + ", zipCode=" + zipCode + "]";
    }
}

class Job {

    String role;
    BigDecimal salary;

    @Override
    public String toString() {
        return "Job [role=" + role + ", salary=" + salary + "]";
    }
}

class PersonBuilder {

    protected Person p = new Person();

    public Person build() {
        return p;
    }

    public AddressBuilder address() {
        return new AddressBuilder(p);
    }

    public JobBuilder job() {

        return new JobBuilder(p);
    }

    public PersonBuilder name(String name) {
        p.name = name;
        return this;
    }

    static class AddressBuilder extends PersonBuilder {

        public AddressBuilder(Person p) {

            this.p = p;
            p.address = new Address();
        }

        AddressBuilder street(String street) {
            p.address.street = street;
            return this;
        }

        AddressBuilder zipCode(String zipCode) {
            p.address.zipCode = zipCode;
            return this;
        }
    }

    static class JobBuilder extends PersonBuilder {

        public JobBuilder(Person p) {

            this.p = p;
            p.job = new Job();
        }

        JobBuilder role(String role) {
            p.job.role = role;
            return this;
        }

        JobBuilder salary(BigDecimal salary) {
            p.job.salary = salary;
            return this;
        }
    }
}