package exercices;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


public class Exercice2 {

    public static String generateYumlFromClasses(Set<Class<?>> classes) {

        Set<String> definitions = new LinkedHashSet<>();
        Set<String> relations = new LinkedHashSet<>();

        for (Class<?> cls : classes) {
            String className = cls.getSimpleName();

            boolean isInterface = cls.isInterface();

            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && superClass != Object.class && classes.contains(superClass)) {
                // Notation yUML : [Sub]^-[Super]
                relations.add("[" + className + "]^-" + "[" + superClass.getSimpleName() + "]");
            }

            Class<?>[] intfs = cls.getInterfaces();
            for (Class<?> intf : intfs) {
                if (classes.contains(intf)) {
                    relations.add("[" + className + "]^.-" + "[" + intf.getSimpleName() + "]");
                }
            }


            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                // Type du champ
                Class<?> fieldType = f.getType();
                if (classes.contains(fieldType)) {

                    relations.add("[" + className + "] - [" + fieldType.getSimpleName() + "]");
                }
            }


            StringBuilder sb = new StringBuilder();
            sb.append("[").append(className);

            // On peut séparer en trois sections : Nom | Champs | Méthodes
            sb.append("|");

            // Champs publics
            // (ou tous les champs, si vous le souhaitez)
            for (Field f : fields) {
                if (Modifier.isPublic(f.getModifiers())) {
                    sb.append(f.getName()).append(":").append(f.getType().getSimpleName()).append("; ");
                }
            }
            sb.append("|");

            // (On peut récupérer les méthodes publiques, etc., si on veut)
            // ...

            sb.append("]");
            definitions.add(sb.toString());
        }


        StringBuilder result = new StringBuilder();

        // (A) Les définitions (optionnel)
        for (String def : definitions) {
            result.append(def).append("\n");
        }

        // (B) Les relations
        for (String rel : relations) {
            result.append(rel).append("\n");
        }

        return result.toString();
    }
}
