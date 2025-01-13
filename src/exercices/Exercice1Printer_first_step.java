package exercices;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Exemple de code pour explorer la structure d'une classe (super-classes, interfaces, classes internes)
 * sans provoquer de ConcurrentModificationException.
 */
public class Exercice1Printer_first_step {

    /**
     * Gère la représentation textuelle d'une classe (ou interface) :
     * - Ajoute cls à visited
     * - Récupère super-classe, interfaces, classes internes
     * - Construit un "mini code source" (déclaration) sous forme de String
     * - Les nouvelles classes découvertes sont ajoutées à 'unbounds'
     */
    static private String classRepresentation(Class<?> cls,
                                              Set<Class<?>> visited,
                                              Set<Class<?>> unbounds) {
        // Chaîne de résultat
        String clsrep = "";

        // Retirer cls de unbounds (puisqu'on va la traiter)
        unbounds.remove(cls);

        // Marquer cls comme "visitée"
        visited.add(cls);

        // Récupérer les modificateurs (public, static, etc.)
        int modifiers = cls.getModifiers();

        // Décoder quelques modificateurs de base
        if (Modifier.isPublic(modifiers)) {
            clsrep += "public ";
        }
        // Pour 'static', on évite de le mettre si c'est une interface top-level, etc.
        // Ici on fait un choix simple : s'il est statique et (classe interne ou interface interne)
        if (Modifier.isStatic(modifiers)) {
            if (!cls.isInterface() || (cls.getDeclaringClass() != null)) {
                clsrep += "static ";
            }
        }

        // Classe ou interface ?
        String clskw = cls.isInterface() ? "interface" : "class";

        // Nom simple
        clsrep += clskw + " " + cls.getSimpleName() + " ";

        // 1) Gestion de la super-classe
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class && !cls.isInterface()) {
            clsrep += "extends " + superClass.getSimpleName() + " ";
            // Si on ne l'a pas encore visitée
            if (!visited.contains(superClass)) {
                unbounds.add(superClass);
            }
        }

        // 2) Gestion des interfaces
        Class<?>[] intfs = cls.getInterfaces();
        if (intfs.length > 0) {
            // Si c'est une interface : extends
            // Si c'est une classe : implements
            String keyword = cls.isInterface() ? "extends" : "implements";
            clsrep += keyword + " ";

            for (int i = 0; i < intfs.length; i++) {
                Class<?> intf = intfs[i];
                clsrep += intf.getSimpleName();
                if (i < intfs.length - 1) {
                    clsrep += ", ";
                }
                // Ajouter aux unbounds si pas encore visitée
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

    /**
     * Lance le processus de génération de la représentation de cls,
     * puis traite toutes les classes détectées au fur et à mesure,
     * en évitant la ConcurrentModificationException.
     */
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
            // À la fin de cette boucle, d'autres classes internes ou super-classes
            // peuvent avoir été ajoutées à unbounds. On réitère.
        }
    }
}
