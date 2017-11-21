import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SharkTest {
    @Test
    void Shark() throws Exception {
        /* Instantiates a shark object using the no-args constructor */
        Shark a = new Shark();
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", a.getClass().toString());
        /* Asserts that the commonName attribute is null */
        assertEquals(null, a.getCommonName());
        /* Asserts that the latinName attribute is null */
        assertEquals(null, a.getLatinName());

        /* Instantiates a shark object with a common name and Latin name */
        Shark b = new Shark("commonname", "latinname");
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", b.getClass().toString());
        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", b.getCommonName());
        /* Asserts that the latinName attribute has been assigned "latinname" */
        assertEquals("latinname", b.getLatinName());

        /* Instantiates a shark object using the constructor that takes a shark object */
        Shark c = new Shark(b);
        /* Asserts that the object is of the Shark class */
        assertEquals("class Shark", c.getClass().toString());
        /* Asserts that the commonName attribute has been assigned "commonname" */
        assertEquals("commonname", c.getCommonName());
        /* Asserts that the latinName attribute attribute has been assigned "latinname" */
        assertEquals("latinname", c.getLatinName());
        /* Asserts that the commonName attribute has the same value as the commonName attribute in the object passed */
        assertEquals(c.getCommonName(), b.getCommonName());
        /* Asserts that the latinName attribute has the same value as the latinName attribute in the object passed */
        assertEquals(c.getLatinName(), b.getLatinName());
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
}