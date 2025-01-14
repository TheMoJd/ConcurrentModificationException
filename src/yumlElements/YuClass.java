package yumlElements;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une classe UML dans votre modèle
 */
public class YuClass extends YuElement {

    // Extrait du diagramme : "YuClass" a un attribut "name"
    private String name;

    // "super" : 0..1
    // => On utilise un attribut YuClass (null si pas de super-classe)
    private YuClass superClass;

    // "implements" : 0..*
    // => On représente par une liste ou un set
    private List<YuClass> interfaces = new ArrayList<>();

    // Constructeurs, getters, setters
    public YuClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public YuClass getSuperClass() {
        return superClass;
    }

    public void setSuperClass(YuClass superClass) {
        this.superClass = superClass;
    }

    public List<YuClass> getInterfaces() {
        return interfaces;
    }

    public void addInterface(YuClass intf) {
        this.interfaces.add(intf);
    }
}

