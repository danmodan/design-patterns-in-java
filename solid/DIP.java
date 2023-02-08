package solid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Dependency Inversion Principle
 * 
 * Abstrações não devem lidar com detalhes, o inverso, sim.
 * Módulos superiores não devem depender de módulos inferiores.
 * 
 * Sempre trabalhe com abstrações!
 */
public class DIP {

}

class Triple<L, M, R> {

    L left;
    M middle;
    R right;
    public Triple() {
    }
    public Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
}

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    String name;
}

/**
 * classe de baixo nível.
 * trabalha diretamente com uma Lista
 */
class Relationships implements RelationshipsBrowser {

    private List<Triple<Person, Relationship, Person>> relationships = new ArrayList<>();

    @Override
    public void addParentAndChild(Person parent, Person child) {
        relationships.add(new Triple<>(parent, Relationship.PARENT, child));
        relationships.add(new Triple<>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Triple<Person, Relationship, Person>> getRelationships() {
        return relationships;
    }
}

interface RelationshipsBrowser {

    void addParentAndChild(Person parent, Person child);
    List<Triple<Person, Relationship, Person>> getRelationships();
}

/**
 * classe de alto nível.
 * mas está quebrando o princípio de inversão de denpendência 
 * pois está lidando com classes de baixo nível.
 * 
 * E se tiver outro mecanismo de agrupamento de relacionamentos, 
 * que não armazene os objetos num lista mas em outro lugar?
 * 
 * Trabalhe sempre com abstrações/interfaces
 */
class Research {

    final List<Triple<Person, Relationship, Person>> relationships;

    //  ERRADO!!
    public Research(Relationships relationships) {
        this.relationships = relationships.getRelationships();
    }

    //  CERTO!!
    public Research(RelationshipsBrowser relationships) {
        this.relationships = relationships.getRelationships();
    }

    Stream<Person> filterChildsParent(String parentName) {
        return relationships
        .stream()
        .filter(triple -> triple.left.name.equals(parentName) && triple.middle == Relationship.PARENT)
        .map(triple -> triple.right);
    }
}