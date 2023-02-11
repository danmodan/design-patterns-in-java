package gamma.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

import utils.ObjectCloner;

/**
 * Existem objetos que fazem sentido terem apenas 1 instância na JVM
 * - Database repository
 * - Factories
 * 
 * Design que criar e garante a existência de apenas
 * uma instância do objeto no sistema
 * 
 * Se não for implementado direito, pode ser quebrado por:
 * - reflection
 * -serialização (dá pra consertar implementando Serializable eeeee implementando o método "Object readResolve() throws ObjectStreamException")
 */
public class Singleton {

    public static void main(String[] args) throws Exception {

        wrongSingletonNotSerializable();
        singletonSerializable();
        singletonLazyInitialization();
    }

    private static void singletonLazyInitialization() {
        LazyInitialization instance1 = LazyInitialization.Factory.getInstance();
        LazyInitialization instance2 = LazyInitialization.Factory.getInstance();

        System.out.println("Mesma instância LazyInitialization: " + (instance1 == instance2));
    }

    private static void singletonSerializable() {
        SingletonSerializable instance1 = SingletonSerializable.getSingleton();
        instance1.setProp(100);

        SingletonSerializable instance2 = ObjectCloner.deepCopy(instance1);

        instance2.setProp(200);

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println("Mesma instância SingletonSerializable: " + (instance1 == instance2));
    }

    private static void wrongSingletonNotSerializable() {
        SingletonNotSerializable instance1 = SingletonNotSerializable.getSingleton();
        instance1.setProp(100);

        SingletonNotSerializable instance2 = ObjectCloner.deepCopy(instance1);

        instance2.setProp(200);

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println("Mesma instância SingletonNotSerializable: " + (instance1 == instance2));
    }
}

/**
 * É threadsafe e mais rápido
 */
class InnerClassSingleton {

    private InnerClassSingleton() {}

    private static class Impl {

        private static final InnerClassSingleton UNIQUE_NISTANCE = new InnerClassSingleton();
        private Impl() {}
    }

    static InnerClassSingleton getInstance() {
        return Impl.UNIQUE_NISTANCE;
    }
}

// Double chek threadsafe 
// singleton
class LazyInitialization {

    private LazyInitialization() {}

    static class Factory {

        private Factory(){}

        private static LazyInitialization instance;

        static LazyInitialization getInstance() {

            if(instance != null) {
                return instance;
            }

            synchronized(Factory.class) {

                if(instance == null) {
                    instance = new LazyInitialization();
                }

                return instance;
            }
        }
    }
}

class SingletonNotSerializable implements Serializable {

    private static final SingletonNotSerializable UNIQUE_INSTANCE = new SingletonNotSerializable();

    private int prop;

    private SingletonNotSerializable() {
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "SingletonNotSerializable [prop=" + prop + "]";
    }

    static SingletonNotSerializable getSingleton() {

        return UNIQUE_INSTANCE;
    }
}

/**
 * Tem que implementar Serializable
 */
class SingletonSerializable implements Serializable {

    private static final SingletonSerializable UNIQUE_INSTANCE = new SingletonSerializable();

    private int prop;

    private SingletonSerializable() {
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "SingletonSerializable [prop=" + prop + "]";
    }

    static SingletonSerializable getSingleton() {

        return UNIQUE_INSTANCE;
    }

    /**
     * Tem que declarar o método readResolve !!!!!
     */
    Object readResolve() throws ObjectStreamException {
        return UNIQUE_INSTANCE;
    }
}