import exercices.Exercice1;

import java.util.HashSet;
import java.util.Set;

import static exercices.Exercice1Printer_first_step.classRepresentation;
import static exercices.Exercice2.generateYumlFromClasses;

class F {
    public int i;
    private int i2;
    public int getI() { return i; }
    public int getI2() { return i; }
}

public class Main {
    public static void main(String[] args) {
        classRepresentation(Exercice1.class);

        System.out.println("exo2 : ");

        Set<Class<?>> classes = new HashSet<>();
        classes.add(Exercice1.class);
        classes.add(F.class);
        // classes.add(AutreClasse.class);

        String yumlSpec = generateYumlFromClasses(classes);

        System.out.println("===== Spécification yUML =====");
        System.out.println(yumlSpec);


    }
}