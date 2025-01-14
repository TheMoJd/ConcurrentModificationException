package yumlElements;

/**
 * Cardinalité exprimée par un entier (ex: 1, 2, 3).
 * Sur le schéma, on voit "n : integer".
 */
public class YuIntCard extends YuCardinality {
    private int n;

    public YuIntCard(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
