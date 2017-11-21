public class Shark {
    private String commonName, latinName, oceanicRegions;
    private int latinNameLength, sharkLength, maxDepth, maxOffspring, globalPresence;

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

        setLatinNameLength(latinName);
    }

    /**
     * Constructor that takes a Shark object.
     */
    public Shark(Shark shark) {
        this(shark.commonName, shark.latinName);
        this.sharkLength = shark.sharkLength;
        this.maxDepth = shark.maxDepth;
        this.maxOffspring = shark.maxOffspring;
        this.globalPresence = shark.globalPresence;
        this.oceanicRegions = shark.oceanicRegions;
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
     * Sets the length of the sharks Latin name.
     */
    public void setLatinNameLength(String latinName) {
        if (isUnknown(latinName)) {
            this.latinNameLength = 0;
        }
        else {
            this.latinNameLength = latinName.replaceAll("\\s", "").length();
        }
    }

    /**
     * Sets the length of the shark.
     */
    public void setSharkLength(int sharkLength) {
        this.sharkLength = sharkLength;
    }

    /**
     * Sets the sharks maximum depth.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Sets the sharks maximum offspring.
     */
    public void setMaxOffspring(int maxOffspring) {
        this.maxOffspring = maxOffspring;
    }

    /**
     * Sets the sharks global presence.
     */
    public void setGlobalPresence(int globalPresence) {
        this.globalPresence = globalPresence;
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
     * Gets the sharks Latin name.
     */
    public String getLatinName() {
        return this.latinName;
    }

    /**
     * Gets the sharks oceanic regions.
     */
    public String getOceanicRegions() {
        return this.oceanicRegions;
    }

    /**
     * Gets the length of the sharks Latin name.
     */
    public int getLatinNameLength() {
        return this.latinNameLength;
    }

    /**
     * Gets the length of the shark.
     */
    public int getSharkLength() {
        return this.sharkLength;
    }

    /**
     * Gets the sharks maximum depth.
     */
    public int getMaxDepth() {
        return this.maxDepth;
    }

    /**
     * Gets the sharks maximum offspring.
     */
    public int getMaxOffspring() {
        return this.maxOffspring;
    }

    /**
     * Gets the sharks global presence.
     */
    public int getGlobalPresence() {
        return this.globalPresence;
    }

    /**
     * Returns a boolean value indicating whether or not the shark has an unknown Latin name.
     */
    public boolean hasUnknownLatinName() {
        return isUnknown(latinName);
    }

    /**
     * Returns a boolean value indicating whether or not the shark has an unknown length.
     */
    public boolean hasUnknownSharkLength() {
        return isUnknown(Integer.toString(sharkLength));
    }

    /**
     * Returns a boolean value indicating whether or not a string is equal or equivalent to "Unknown".
     */
    public boolean isUnknown(String s) {
        boolean flag = false;

        if (s.equals("-1") || s.equalsIgnoreCase("Unknown")) {
            flag = true;
        }

        return flag;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Common Name: " + commonName + "\nLatin Name: " + latinName
                + "\nLatin Name Length (excluding whitespace): " + latinNameLength +
                "\nShark Length: " + sharkLength + "\nMaximum Depth: " + maxDepth
                + "\nMaximum Offspring: " + maxOffspring + "\nGlobal Presence: " + globalPresence +
                "\nOceanic Regions: " + oceanicRegions;
    }
}