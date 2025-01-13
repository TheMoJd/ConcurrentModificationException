package exercices;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;


public class Exercice1Printer_first_step {


    static private String classRepresentation(Class<?> cls,
                                              Set<Class<?>> visited,
                                              Set<Class<?>> unbounds) {
        String clsrep = "";

        unbounds.remove(cls);

        visited.add(cls);

        int modifiers = cls.getModifiers();

        if (Modifier.isPublic(modifiers)) {
            clsrep += "public ";
        }

        if (Modifier.isStatic(modifiers)) {
            if (!cls.isInterface() || (cls.getDeclaringClass() != null)) {
                clsrep += "static ";
            }
        }

        String clskw = cls.isInterface() ? "interface" : "class";

        clsrep += clskw + " " + cls.getSimpleName() + " ";

        // 1) Gestion de la super-classe
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class && !cls.isInterface()) {
            clsrep += "extends " + superClass.getSimpleName() + " ";
            if (!visited.contains(superClass)) {
                unbounds.add(superClass);
            }
        }

        // 2) Gestion des interfaces
        Class<?>[] intfs = cls.getInterfaces();
        if (intfs.length > 0) {

            String keyword = cls.isInterface() ? "extends" : "implements";
            clsrep += keyword + " ";

            for (int i = 0; i < intfs.length; i++) {
                Class<?> intf = intfs[i];
                clsrep += intf.getSimpleName();
                if (i < intfs.length - 1) {
                    clsrep += ", ";
                }
                if (!visited.contains(intf)) {
                    unbounds.add(intf);
                }
            }
            clsrep += " ";
        }

        // Ouverture de la déclaration
        clsrep += "{\n";

        // 3) Gestion des classes internes déclarées
        Class<?>[] declaredClasses = cls.getDeclaredClasses();
        for (Class<?> dcls : declaredClasses) {
            if (!visited.contains(dcls)) {
                unbounds.add(dcls);
            }
        }

        // Fermeture de la déclaration
        clsrep += "}\n\n";

        return clsrep;
    }


    public static void classRepresentation(Class<?> cls) {
        // Ensemble des classes détectées mais pas encore explorées
        Set<Class<?>> unbounds = new HashSet<>();
        // Ensemble des classes déjà explorées
        Set<Class<?>> visited = new HashSet<>();

        // 1) Premier appel
        String result = classRepresentation(cls, visited, unbounds);
        System.out.println(result);

        // 2) Tant qu'il reste des classes à explorer dans unbounds...
        while (!unbounds.isEmpty()) {
            // Copier dans un ensemble temporaire pour éviter la modification
            // en cours d'itération
            Set<Class<?>> toExplore = new HashSet<>(unbounds);
            // On vide unbounds pour reconstruire au fur et à mesure
            unbounds.clear();

            // Explorer toutes les classes dans toExplore
            for (Class<?> c : toExplore) {
                // Vérifier si la classe est dans le même package que Exercice1
                if (c.getPackage() == Exercice1.class.getPackage()) {
                    // Générer la représentation pour cette classe
                    result = classRepresentation(c, visited, unbounds);
                    System.out.println(result);
                }
            }
        }
    }
}
