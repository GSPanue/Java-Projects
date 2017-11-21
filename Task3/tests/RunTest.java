import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RunTest {
    /* ArrayLists are initialised */
    private ArrayList<String> rawData = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    /* A run object is instantiated */
    private Run run = new Run();

    private void fillRawData() {
        String[] latinNames = {"String A", "String B", "String C"};

        /* Adds 10 strings of shark data to rawData */
        for (int i = 0; i < 10; i++) {
            String data = "commonname:latinname:length:depth:maxoffspring:globalpresence:regions";

            data = data.replace("commonname", "Common Name " + (char)('A' + i));
            data = data.replace("latinname", latinNames[(int)(Math.random() * 3)]);

            rawData.add(data);
        }
    }

    private int countLines(String fileName) throws IOException {
        /* dataPath is assigned a file path */
        String dataPath = System.getProperty("user.dir") + File.separator + fileName;
        /* totalNumberOfLines is assigned 0 */
        int totalNumberOfLines = 0;

        /* A LineNumberReader object is declared */
        LineNumberReader reader = null;

        try {
            /* A LineNumberReader object is instantiated */
            reader = new LineNumberReader(new FileReader(dataPath));

            /* A while loop is initialised until readLine() returns null */
            while (reader.readLine() != null) {
                /* totalNumberOfLines is incremented by 1 */
                totalNumberOfLines++;
            }
        }
        /* Catches FileNotFoundException */
        catch (FileNotFoundException e) {
            /* Prints the stack trace of the error */
            e.printStackTrace();
        }
        finally {
            /* The reader is closed */
            reader.close();
        }

        return totalNumberOfLines;
    }

    @Test
    void importData() throws Exception {
        /* totalNumberOfLines is assigned a number returned by countLines */
        int totalNumberOfLines = countLines("shark-data.txt");

        /* Assigns an ArrayList containing shark data to rawData */
        rawData = run.importData();

        /* Asserts that the total number of lines in the file is equal to the size of rawData */
        assertEquals(totalNumberOfLines, rawData.size());
    }

    @Test
    void objectifyData() {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList of shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* Asserts that the size of rawData is equal to the size of sharks */
        assertEquals(rawData.size(), sharks.size());
    }

    @Test
    void filterData() {
        /* An array containing split strings using a delimiter is assigned to data */
        String[] data = "commonname:latinname:length:depth:maxoffspring:globalpresence:regions".split(":");
        /* An array containing strings is assigned to filteredData */
        String[] filteredData = run.filterData(data);

        /* Asserts that the returned array has a length of 2 */
        assertEquals(2, filteredData.length);

        /* Asserts that the first element of filteredData is the common name */
        assertEquals("Commonname", filteredData[0]);

        /* Asserts that the second element of filteredData is the Latin name */
        assertEquals("Latinname", filteredData[1]);
    }

    @Test
    void capitalise() {
        /* Asserts that the first character of a string is capitalised */
        assertEquals("String", run.capitalise("string"));
        /* Asserts that the first character and character after a whitespace are capitalised */
        assertEquals("String String", run.capitalise("string string"));
        /* Asserts that the first character, character after a whitespace and character after a dash are capitalised */
        assertEquals("String, String, S-Tring", run.capitalise("string, string, s-tring"));
    }

    @Test
    void createObject() {
        /* Asserts that the object returned is of type Shark */
        assertEquals("class Shark", run.createObject(new String[]{"Common Name", "Latin Name"}).getClass().toString());
    }

    @Test
    void search() {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* Asserts that the ArrayList returned by search is of length 10 */
        assertEquals(10, run.search("string", sharks).size());

        /* term is assigned a character between A - C */
        char term = (char)('A' + Math.random() * 3);

        /* expectedSize is assigned 0 */
        int expectedSize = 0;
        /* Traverses through the sharks ArrayList and increments expectedSize for every Latin name containing the generated term */
        for (int i = 0; i < sharks.size(); i++) {
            /* Checks if the current sharks Latin name contains the term */
            if (sharks.get(i).getLatinName().contains(Character.toString(term))) {
                /* expectedSize is incremented by 1 */
                expectedSize++;
            }
        }

        /* An ArrayList containing sharks with Latin names that match the search term are assigned to searchResults */
        ArrayList<Shark> searchResults = run.search(Character.toString(term), sharks);

        /* Asserts that the expectedSize is equal to the size of the searchResults ArrayList */
        assertEquals(expectedSize, searchResults.size());

        /* Traverses through the searchResults ArrayList and checks if every sharks Latin name contains the generated term */
        for (int i = 0; i < searchResults.size(); i++) {
            /* Asserts that the current sharks Latin name contains the term */
            assertTrue(searchResults.get(i).getLatinName().contains(Character.toString(term)));
        }
    }

    @Test
    void sortByCommonName() {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList containing shark objects sorted by their common name is assigned to sharks */
        sharks = run.sortByCommonName(run.objectifyData(rawData));

        /* Traverses through the sharks ArrayList and checks if all sharks have been sorted by their common name */
        for (int i = 0; i < sharks.size() - 1; i++) {
            /* The last character of shark i is assigned to sharkA */
            char sharkA = sharks.get(i).getCommonName().charAt(sharks.get(i).getCommonName().length() - 1);
            /* The last character of shark i + 1 is assigned to sharkB */
            char sharkB = sharks.get(i + 1).getCommonName().charAt(sharks.get(i + 1).getCommonName().length() - 1);

            /* Asserts that the sharkA is less than or equal to sharkB */
            assertTrue(sharkA < sharkB || sharkA == sharkB);
        }
    }

    @Test
    void display() {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList containing shark objects sorted by their common name is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* term is assigned a character between A - C */
        char term = (char)('A' + Math.random() * 3);

        /* An ArrayList containing sharks with Latin names that match the search term are assigned to searchResults */
        ArrayList<Shark> searchResults = run.sortByCommonName(run.search(Character.toString(term), sharks));

        /* A StringBuilder object is instantiated */
        StringBuilder stringBuilder = new StringBuilder();

        /* counter is assigned 0 */
        int counter = 0;

        for (Shark s : searchResults) {
            /* Appends a sharks common name to stringBuilder */
            stringBuilder.append(s.getCommonName());
            stringBuilder.append(" - Latin name: ");
            /* Converts all occurrences of a given term in a sharks Latin name to uppercase and appends it to stringBuilder */
            stringBuilder.append(s.getLatinName().replaceAll("(?i)" + term, Character.toString(term).toUpperCase()));

            /* The StringBuilder object is reset */
            stringBuilder.setLength(0);

            /* Asserts that the length of stringBuilder is 0 */
            assertTrue(stringBuilder.length() == 0);

            /* counter is incremented by 1 */
            counter++;
        }

        /* Asserts that the size of searchResults is equal to counter */
        assertEquals(searchResults.size(), counter);
    }

    @Test
    void getCopy() {
        /* Instantiates a shark object and adds it to the sharks ArrayList */
        sharks.add(new Shark("Common Name", "Latin Name"));

        /* Assigns a copy of the sharks ArrayList to sharksCopy */
        ArrayList<Shark> sharksCopy = run.getCopy(sharks);

        /* Asserts that the getCopy method returned an ArrayList containing the same object */
        assertEquals(sharks.get(0).getCommonName(), sharksCopy.get(0).getCommonName());
        assertEquals(sharks.get(0).getLatinName(), sharksCopy.get(0).getLatinName());

        /* The object in sharksCopy is mutated using setter methods */
        sharksCopy.get(0).setCommonName("Changed1");
        sharksCopy.get(0).setLatinName("Changed2");

        /* Asserts that the attributes have been mutated accordingly */
        assertEquals("Changed1", sharksCopy.get(0).getCommonName());
        assertEquals("Changed2", sharksCopy.get(0).getLatinName());

        /* Asserts that the object in the sharks ArrayList has not been mutated */
        assertNotEquals(sharks.get(0).getCommonName(), sharksCopy.get(0).getCommonName());
        assertNotEquals(sharks.get(0).getLatinName(), sharksCopy.get(0).getLatinName());

        /* Removes the object from sharksCopy */
        sharksCopy.remove(0);

        /* Asserts that the object has been removed from sharksCopy */
        assertEquals(0, sharksCopy.size());

        /* Asserts that the object was not removed from the sharks ArrayList  */
        assertNotEquals(sharks.size(), sharksCopy.size());
    }
}