package solid;

/**
 * Interface Segregation Principle
 * 
 * Construa interfaces pequenas e granulares.
 * Interfaces com uma única responsabilidade para não propagar métodos vazios
 * nas implementações.
 */
public class ISP {

    public static void main(String[] args) {

    }
}

class Document {
}

/**
 * Aqui há a quebra do princípio de segregação de interface.
 * Essa interface é muito ampla.
 * Força seus clientes a fazer coisas que não deveriam ou não sabem fazer
 */
interface Machine {
    void print(Document d);

    void fax(Document d);

    void scan(Document d);
}

class MultiFunctionalPrinter implements Machine {

    @Override
    public void print(Document d) {
        // consegue/sabe imprimir
    }

    @Override
    public void fax(Document d) {
        // consegue/sabe passar fax
    }

    @Override
    public void scan(Document d) {
        // consegue/sabe scanear
    }
}

class BasicPrinter implements Machine {

    @Override
    public void print(Document d) {
        // consegue/sabe imprimir
    }

    @Override
    public void fax(Document d) {
        // ERRO!!!!!
    }

    @Override
    public void scan(Document d) {
        // ERRO!!!!!
    }
}

/**
 * A partir daqui obedece o princípio de segregação de interface
 */
interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

class OutraBasicPrinter implements Printer {

    @Override
    public void print(Document d) {
        // consegue/sabe imprimir e só isso.
    }
}

class OutraBasicScanner implements Scanner {

    @Override
    public void scan(Document d) {
        // consegue/sabe imprimir e só isso.
    }
}

interface MultiFuncionalMachine extends Printer, Scanner {
}

class MultiFuncionalDevice implements MultiFuncionalMachine {

    /**
     * Decorator Pattern
     */
    private final Printer printer;
    private final Scanner scanner;

    public MultiFuncionalDevice(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document d) {
        printer.print(d);
    }

    @Override
    public void scan(Document d) {
        scanner.scan(d);
    }
}