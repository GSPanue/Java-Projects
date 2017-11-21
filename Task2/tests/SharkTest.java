import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SharkTest {
    @Test
    void Shark() throws Exception {
        /* Instantiates a shark object using the no-args constructor */
        Shark a = new Shark();
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", a.getClass().toString());
        /* Asserts that the commonName attribute is null */
        assertEquals(null, a.getCommonName());
        /* Asserts that the oceanicRegions attribute is null */
        assertEquals(null, a.getOceanicRegions());

        /* Instantiates a shark object with a common name and region */
        Shark b = new Shark("commonname", "region");
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", b.getClass().toString());
        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", b.getCommonName());
        /* Asserts that the oceanicRegions attribute has been assigned "region" */
        assertEquals("region", b.getOceanicRegions());

        /* Instantiates a shark object using the constructor that takes a shark object */
        Shark c = new Shark(b);
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", c.getClass().toString());
        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", c.getCommonName());
        /* Asserts that the oceanicRegions attribute attribute has been assigned "region" */
        assertEquals("region", c.getOceanicRegions());
        /* Asserts that the commonName attribute has the same value as the commonName attribute in the object passed */
        assertEquals(c.getCommonName(), b.getCommonName());
        /* Asserts that the oceanicRegions attribute has the same value as the oceanicRegions attribute in the object passed */
        assertEquals(c.getOceanicRegions(), b.getOceanicRegions());
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
    void setOceanicRegions() {
        /* Instantiates a shark object */
        Shark a = new Shark();
        /* The oceanicRegions attribute is assigned "region" */
        a.setOceanicRegions("region");

        /* Asserts that the oceanicRegions attribute has been assigned "region" */
        assertEquals("region", a.getOceanicRegions());
    }

    @Test
    void getCommonName() {
        /* Instantiates a shark object */
        Shark a = new Shark("commonname", "region");

        /* Asserts that getCommonName returns the value assigned to the commonName attribute */
        assertEquals("commonname", a.getCommonName());
    }

    @Test
    void getOceanicRegions() {
        /* Instantiates a shark object */
        Shark a = new Shark("commonname", "region");

        /* Asserts that getOceanicRegions returns the value assigned to the oceanicRegions attribute */
        assertEquals("region", a.getOceanicRegions());
    }
}