package gamma.builder;

public class FluentBuilder {

    public static void main(String[] args) {

        Person p1 = new PersonBuilder()
        .withName("Danilo")
        .withAge(10)
        .build();

        Employee p2 = new EmployeeBuilder()
        .withName("Olinad")
        .withAge(100)
        .withRole("Dev")
        .build();

        System.out.println(p1);
        System.out.println(p2);

    }
}

class Person {

    String name;
    int age;

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}

class Employee extends Person {

    String role;

    @Override
    public String toString() {
        return "Employee [name=" + name + ", age=" + age + ", role=" + role + "]";
    }
    
}

class PersonBuilder<T extends PersonBuilder<T>> {

    protected Person p = new Person();

    T withName(String name) {
        p.name = name;
        return self();
    }

    T withAge(int age) {
        p.age = age;
        return self();
    }

    /**
     * Aqui tá o segredo.
     * 
     * os filhos podem sobrepor esse método
     */
    protected T self() {
        return (T) this;
    }

    Person build() {
        return p;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {

    EmployeeBuilder() {
        super.p = new Employee();
    }
    
    EmployeeBuilder withRole(String role) {
        ((Employee) p).role = role;
        return self();
    }

    @Override
    protected EmployeeBuilder self() {
        return this;
    }

    @Override
    Employee build() {
        return (Employee) super.p;
    }
}