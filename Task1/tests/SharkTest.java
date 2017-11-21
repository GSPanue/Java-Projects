import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SharkTest {
    @Test
    void Shark() {
        /* Instantiates a shark object using the no-args constructor */
        Shark a = new Shark();
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", a.getClass().toString());
        /* Asserts that all class attributes are either null or 0 */
        assertEquals(null, a.getCommonName());
        assertEquals(null, a.getLatinName());
        assertEquals(null, a.getOceanicRegions());
        assertEquals(0, a.getLatinNameLength());
        assertEquals(0, a.getSharkLength());
        assertEquals(0, a.getMaxDepth());
        assertEquals(0, a.getMaxOffspring());
        assertEquals(0, a.getGlobalPresence());

        /* Instantiates a shark object with a common name and latin name */
        Shark b = new Shark("commonname", "latinname");
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", b.getClass().toString());
        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", b.getCommonName());
        /* Asserts that the latinName attribute has been assigned "latinname" */
        assertEquals("latinname", b.getLatinName());

        /* Attributes in object b are mutated */
        b.setOceanicRegions("region");
        b.setSharkLength(1);
        b.setMaxDepth(1);
        b.setMaxOffspring(1);
        b.setGlobalPresence(1);

        /* Instantiates a shark object using the constructor that takes a shark object */
        Shark c = new Shark(b);
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", c.getClass().toString());
        /* Asserts that the commonName attribute has been assigned the correct value */
        assertEquals(b.getCommonName(), c.getCommonName());
        /* Asserts that the latinName attribute has been assigned the correct value */
        assertEquals(b.getLatinName(), c.getLatinName());
        /* Asserts that the oceanicRegions attribute has been assigned the correct value */
        assertEquals(b.getOceanicRegions(), c.getOceanicRegions());
        /* Asserts that the latinNameLength attribute has been assigned the correct value */
        assertEquals(b.getLatinName().length(), c.getLatinNameLength());
        /* Asserts that the sharkLength attribute has been assigned the correct value */
        assertEquals(b.getSharkLength(), c.getSharkLength());
        /* Asserts that the maxDepth attribute has been assigned the correct value */
        assertEquals(b.getMaxDepth(), c.getMaxDepth());
        /* Asserts that the maxOffspring attribute has been assigned the correct value */
        assertEquals(b.getMaxOffspring(), c.getMaxOffspring());
        /* Asserts that the globalPresence attribute has been assigned the correct value */
        assertEquals(b.getGlobalPresence(), c.getGlobalPresence());
    }

    @Test
    void setCommonName() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The commonName attribute is assigned "commonname" */
        a.setCommonName("commonname");

        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", a.getCommonName());
    }

    @Test
    void setLatinName() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The latinName attribute is assigned "latinname" */
        a.setLatinName("latinname");

        /* Asserts that the latinName attribute has been assigned "latinname" */
        assertEquals("latinname", a.getLatinName());
    }

    @Test
    void setOceanicRegions() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The oceanicRegions attribute is assigned "region" */
        a.setOceanicRegions("region");

        /* Asserts that the oceanicRegions attribute has been assigned "region" */
        assertEquals("region", a.getOceanicRegions());
    }

    @Test
    void setLatinNameLength() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The latinNameLength attribute is assigned the length of "latinname" */
        a.setLatinNameLength("latinname");

        /* Asserts that the latinNameLength attribute has been assigned the correct length of "latinname" */
        assertEquals(9, a.getLatinNameLength());

        /* The latinNameLength attribute is assigned the length of "UNKNOWN", which is zero */
        a.setLatinNameLength("UNKNOWN");

        /* Asserts that the latinNameLength attribute has been assigned 0 */
        assertEquals(0, a.getLatinNameLength());
    }

    @Test
    void setSharkLength() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The sharkLength attribute is assigned 1 */
        a.setSharkLength(1);

        /* Asserts that the sharkLength attribute has been assigned 1 */
        assertEquals(1, a.getSharkLength());
    }

    @Test
    void setMaxDepth() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The maxDepth attribute is assigned 1 */
        a.setMaxDepth(1);

        /* Asserts that the maxDepth attribute has been assigned 1 */
        assertEquals(1, a.getMaxDepth());
    }

    @Test
    void setMaxOffspring() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The maxOffspring attribute is assigned 1 */
        a.setMaxOffspring(1);

        /* Asserts that the maxOffspring attribute has been assigned 1 */
        assertEquals(1, a.getMaxOffspring());
    }

    @Test
    void setGlobalPresence() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The globalPresence attribute is assigned 1 */
        a.setGlobalPresence(1);

        /* Asserts that the globalPresence attribute has been assigned 1 */
        assertEquals(1, a.getGlobalPresence());
    }

    @Test
    void getCommonName() {
        /* Instantiates a shark object */
        Shark a = new Shark("commonname", "latinname");

        /* Asserts that getCommonName returns the value assigned to the commonName attribute */
        assertEquals("commonname", a.getCommonName());
    }

    @Test
    void getLatinName() {
        /* Instantiates a shark object */
        Shark a = new Shark("commonname", "latinname");

        /* Asserts that getLatinName returns the value assigned to the latinName attribute */
        assertEquals("latinname", a.getLatinName());
    }

    @Test
    void getOceanicRegions() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The oceanicRegions attribute is assigned "region" */
        a.setOceanicRegions("region");

        /* Asserts that getOceanicRegions returns the value assigned to the oceanicRegions attribute */
        assertEquals("region", a.getOceanicRegions());
    }

    @Test
    void getLatinNameLength() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The latinNameLength attribute is assigned the length of "latinname" */
        a.setLatinNameLength("latinname");

        /* Asserts that getLatinNameLength returns the value assigned to the latinNameLength attribute */
        assertEquals(9, a.getLatinNameLength());
    }

    @Test
    void getSharkLength() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The sharkLength attribute is assigned 1 */
        a.setSharkLength(1);

        /* Asserts that getSharkLength returns the value assigned to the sharkLength attribute */
        assertEquals(1, a.getSharkLength());
    }

    @Test
    void getMaxDepth() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The maxDepth attribute is assigned 1 */
        a.setMaxDepth(1);

        /* Asserts that getMaxDepth returns the value assigned to the maxDepth attribute */
        assertEquals(1, a.getMaxDepth());
    }

    @Test
    void getMaxOffspring() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The maxOffspring attribute is assigned 1 */
        a.setMaxOffspring(1);

        /* Asserts that getMaxOffspring returns the value assigned to the maxOffspring attribute */
        assertEquals(1, a.getMaxOffspring());
    }

    @Test
    void getGlobalPresence() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The globalPresence attribute is assigned 1 */
        a.setGlobalPresence(1);

        /* Asserts that getGlobalPresence returns the value assigned to the globalPresence attribute */
        assertEquals(1, a.getGlobalPresence());
    }

    @Test
    void hasUnknownLatinName() {
        /* Instantiates a shark object */
        Shark a = new Shark("commonname", "unknown");

        /* Asserts that true is returned when the latinName attribute is assigned "unknown" */
        assertTrue(a.hasUnknownLatinName());
    }

    @Test
    void hasUnknownSharkLength() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* The sharkLength attribute is assigned -1 */
        a.setSharkLength(-1);

        /* Asserts that true is returned when the sharkLength attribute is assigned -1 */
        assertTrue(a.hasUnknownSharkLength());
    }

    @Test
    void isUnknown() {
        /* Instantiates a shark object */
        Shark a = new Shark();

        /* Asserts that true is returned for any letter case variation of the word "unknown", and "-1" */
        assertTrue(a.isUnknown("unknown"));
        assertTrue(a.isUnknown("UNKNOWN"));
        assertTrue(a.isUnknown("UnKnOwN"));
        assertTrue(a.isUnknown("-1"));
    }
}