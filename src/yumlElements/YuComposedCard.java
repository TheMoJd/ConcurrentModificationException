package yumlElements;

/**
 * Cardinalité exprimée par un intervalle (ex: min..max)
 * Le diagramme mentionne "Yu ComposedCard" (ou "YuCompCard")
 * avec "min" et "max".
 */
public class YuComposedCard extends YuCardinality {
    private int min;
    private int max;

    public YuComposedCard(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
