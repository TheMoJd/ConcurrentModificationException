package yumlElements;

/**
 * Représente une association binaire (bi-association),
 * spécialisée ensuite en agrégation, composition, etc.
 */
public class YuBiAssoc extends YuAssoc {
    // Dans le schéma : "startCard" et "endCard" (YuCardinality)
    private YuCardinality startCard;
    private YuCardinality endCard;

    public YuCardinality getStartCard() {
        return startCard;
    }

    public void setStartCard(YuCardinality startCard) {
        this.startCard = startCard;
    }

    public YuCardinality getEndCard() {
        return endCard;
    }

    public void setEndCard(YuCardinality endCard) {
        this.endCard = endCard;
    }
}
