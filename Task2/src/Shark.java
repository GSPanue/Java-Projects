public class Shark {
    private String commonName, oceanicRegions;

    /**
     * No-args Constructor.
     */
    public Shark() {
    }

    /**
     * Constructor that takes a common name and oceanic regions.
     */
    public Shark(String commonName, String oceanicRegions) {
        this.commonName = commonName;
        this.oceanicRegions = oceanicRegions;
    }

    /**
     * Constructor that takes a Shark object.
     */
    public Shark(Shark shark) {
        this(shark.commonName, shark.oceanicRegions);
    }

    /**
     * Sets the sharks common name.
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Sets the sharks oceanic regions.
     */
    public void setOceanicRegions(String oceanicRegions) {
        this.oceanicRegions = oceanicRegions;
    }

    /**
     * Gets the sharks common name.
     */
    public String getCommonName() {
        return this.commonName;
    }

    /**
     * Gets the sharks oceanic regions.
     */
    public String getOceanicRegions() {
        return this.oceanicRegions;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Common Name: " + commonName + "\nOceanic Regions: " + oceanicRegions;
    }
}