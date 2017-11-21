public class Shark {
    private String commonName, latinName;

    /**
     * No-args Constructor.
     */
    public Shark() {
    }

    /**
     * Constructor that takes a common name and Latin name.
     */
    public Shark(String commonName, String latinName) {
        this.commonName = commonName;
        this.latinName = latinName;
    }

    /**
     * Constructor that takes a Shark object.
     */
    public Shark(Shark shark) {
        this(shark.commonName, shark.latinName);
    }

    /**
     * Sets the sharks common name.
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Sets the sharks Latin name.
     */
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    /**
     * Gets the sharks common name.
     */
    public String getCommonName() {
        return this.commonName;
    }

    /**
     * Gets the sharks Latin name.
     */
    public String getLatinName() {
        return this.latinName;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Common Name: " + commonName + "\nLatin Name: " + latinName;
    }
}