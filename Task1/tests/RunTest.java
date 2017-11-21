import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class RunTest {
    /* ArrayLists are initialised */
    private ArrayList<String> rawData = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    /* A run object is instantiated */
    private Run run = new Run();

    void fillRawData(int action) {
        String[] latinNames = {"Carcharhinus amblyrhychos", "Galeocerdo cuvier", "Ginglymostoma cirratum", "Trianoden obesus"};

        /* Adds 10 strings of shark data to rawData */
        for (int i = 0; i < 10; i++) {
            /* randomNumber is assigned a number between 1 and 100 */
            int randomNumber = (int) (1 + (Math.random() * 100));
            /* data is assigned a string of shark data */
            String data = "Grey Reef Shark:latinname:length:100:6:4:Indian Ocean";

            if (action == 1) {
                /* Replaces "length" with a number that has been assigned to randomNumber */
                data = data.replace("length", Integer.toString(randomNumber));
            }
            else if (action == 2) {
                if (randomNumber % 2 == 0) {
                    /* Replaces "length" with "UNKNOWN" if randomNumber is even */
                    data = data.replace("length", "UNKNOWN");
                }
                else {
                    /* Replaces "length" with a number that has been assigned to randomNumber */
                    data = data.replace("length", Integer.toString(randomNumber));
                }
            }

            /* Adds the string of data to rawData */
            rawData.add(data);
        }
    }

    int countLines(String fileName) throws IOException {
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
    void importData() throws IOException {
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
        fillRawData(1);

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* Asserts that the size of rawData is equal to the size of sharks */
        assertEquals(rawData.size(), sharks.size());
    }

    @Test
    void createObject() {
        /* Initialises an array containing shark data */
        String[] sharkData = {"Grey Reef Shark", "Carcharhinus amblyrhychos", "255", "100", "6", "4", "Indian Ocean"};

        Shark shark = run.createObject(sharkData);

        /* Asserts that the object is of type Shark */
        assertEquals("class Shark", shark.getClass().toString());

        /* Asserts that the shark object properties have been correctly mutated */
        assertEquals("Grey Reef Shark", shark.getCommonName());
        assertEquals("Carcharhinus amblyrhychos", shark.getLatinName());
        assertEquals(255, shark.getSharkLength());
        assertEquals(100, shark.getMaxDepth());
        assertEquals(6, shark.getMaxOffspring());
        assertEquals(4, shark.getGlobalPresence());
        assertEquals("Indian Ocean", shark.getOceanicRegions());
    }

    @Test
    void formatData() {
        /* Initialises an array containing shark data */
        String[] sharkData = {"Grey Reef Shark", "Carcharhinus amblyrhychos", "255", "100", "6", "4", "Indian Ocean"};

        for (int i = 0; i < sharkData.length; i++) {
            /* Initialises a new string array with the same length as sharkData  */
            String[] sharkDataCopy = new String[sharkData.length];

            /* Copies the strings in sharkData to sharkDataCopy */
            for (int j = 0; j < sharkData.length; j++) {
                sharkDataCopy[j] = new String(sharkData[j]);
            }

            /* Assigns "UNKNOWN" to an index of sharkDataCopy */
            sharkDataCopy[i] = "UNKNOWN";
            /* Assigns an array containing formatted data to returnedArray */
            String[] returnedArray = run.formatData(sharkDataCopy);

            if (i < 2 || i == sharkData.length - 1) {
                /* Asserts that returnedArray[i] has not been assigned a new string */
                assertEquals("UNKNOWN", returnedArray[i]);
            }
            else {
                /* Asserts that returnedArray[i] has has been assigned with "-1" */
                assertEquals("-1", returnedArray[i]);
            }
        }
    }

    @Test
    void collectResults() {
        /* Fills rawData */
        fillRawData(1);

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* An ArrayList containing results is assigned to results */
        ArrayList<String> results = run.collectResults(run.getCopy(sharks), run.sortByLength(run.removeUnknown(sharks, 1)));

        /* Asserts that the size of results is 12 */
        assertEquals(12, results.size());
    }

    @Test
    void exportResults() throws IOException {
        /* Fills rawData */
        fillRawData(1);

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* An ArrayList containing a collection of results is assigned to results */
        ArrayList<String> results = run.collectResults(run.getCopy(sharks), run.sortByLength(run.removeUnknown(sharks, 1)));
        /* lengthOfResults is assigned the size of the ArrayList */
        int lengthOfResults = results.size();

        /* The results are exported to a text file */
        run.exportResults(results);

        /* totalNumberOfLines is assigned a number returned by countLines */
        int totalNumberOfLines = countLines("results.txt");

        /* Asserts that the total number of lines in results.txt is equal to the length of results + 2 */
        assertEquals(totalNumberOfLines, lengthOfResults + 2);
    }

    @Test
    void totalLetters() {
        /* Fills rawData */
        fillRawData(1);
        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* Assigns the expected total number of letters of all Latin names to expectedTotalNumberOfLetters */
        int expectedTotalNumberOfLetters = sharks.get(0).getLatinNameLength() * sharks.size();

        int totalNumberOfLetters = 0;
        /* Totals the number of letters of all Latin names for all sharks */
        for (int i = 0; i < sharks.size(); i++) {
            /* Adds the returned value of getLatinNameLength() to totalNumberOfLetters */
            totalNumberOfLetters += sharks.get(i).getLatinNameLength();
        }

        /* Asserts that the expected total number of letters is equal to the result */
        assertEquals(expectedTotalNumberOfLetters, totalNumberOfLetters);
    }

    @Test
    void totalEvenWords() {
        /* Initialises an ArrayList */
        ArrayList<String> rawData = new ArrayList<>();
        /* Shark data is added to rawData */
        rawData.add("Grey Reef Shark:Carcharhinus amblyrhychos:1:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Carcharhinus perezi:2:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Trianoden obesus:3:100:6:4:Indian Ocean");

        /* Assigns a set containing unique Latin words */
        Set<String> setOfLatinWords = run.removeDuplicates(run.objectifyData(rawData));

        /* expected is assigned 0 */
        int expected = 0;
        /* Evaluates the length of each Latin word and increments expected if the length is even */
        for (String s : setOfLatinWords) {
            if (s.length() % 2 == 0) {
                /* expected is incremented */
                expected++;
            }
        }

        /* Asserts that the expected amount of even words is equal to the number returned */
        assertEquals(expected, run.totalEvenWords(setOfLatinWords));
    }

    @Test
    void totalOddWords() {
        /* Initialises an ArrayList */
        ArrayList<String> rawData = new ArrayList<>();
        /* Shark data is added to rawData */
        rawData.add("Grey Reef Shark:Carcharhinus amblyrhychos:1:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Carcharhinus perezi:2:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Trianoden obesus:3:100:6:4:Indian Ocean");

        /* Assigns a set containing unique Latin words */
        Set<String> setOfLatinWords = run.removeDuplicates(run.objectifyData(rawData));

        /* expected is assigned 0 */
        int expected = 0;
        /* Evaluates the length of each Latin word and increments expected if the length is odd */
        for (String s : setOfLatinWords) {
            if (s.length() % 2 != 0) {
                /* expected is incremented */
                expected++;
            }
        }

        /* Asserts that the expected amount of odd words is equal to the number returned */
        assertEquals(expected, run.totalOddWords(setOfLatinWords));
    }

    @Test
    void sortByLength() {
        /* Fills rawData */
        fillRawData(1);

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);
        /* Initialises a shark array containing shark objects sorted by length */
        Shark[] sharksSorted = run.sortByLength(run.getCopy(sharks));

        /* counter is assigned 0 */
        int totalMatched = 0;
        /* Compares the common name and length of all shark objects in sharksSorted with the shark objects in the sharks ArrayList */
        for (int i = 0; i < sharksSorted.length; i++) {
            /* The common name and length of a shark object in sharksSorted is assigned to commonName and length */
            String commonName = sharksSorted[i].getCommonName();
            int length = sharksSorted[i].getSharkLength();

            /* Traverses through the sharks ArrayList until an object with the same common name is found */
            for (int j = 0; j < sharks.size(); j++) {
                if (sharks.get(j).getCommonName() == commonName) {
                    /* Asserts that the length of the current shark object in sharksSorted is equal to the object in sharks */
                    assertEquals(sharks.get(j).getSharkLength(), length);
                    /* totalMatched is incremented */
                    totalMatched++;

                    break;
                }
            }
        }

        /* Asserts that all objects in sharksSorted have been matched */
        assertEquals(totalMatched, sharks.size());

        /* Traverses through the sharksSorted array and checks that index i is greater than or equal to index i + 1 */
        for (int i = 0; i < sharksSorted.length - 1; i++) {
            /* sharkA is assigned the length of shark i */
            int sharkA = sharksSorted[i].getSharkLength();
            /* sharkB is assigned the length of shark i + 1 */
            int sharkB = sharksSorted[i + 1].getSharkLength();

            /* Asserts that sharkA is greater than or equal to sharkB */
            assertTrue(sharkA > sharkB || sharkA == sharkB);
        }
    }

    @Test
    void getCopy() {
        /* Instantiates a shark object and adds it to the sharks ArrayList */
        sharks.add(new Shark("Name1", "Name2"));

        /* Assigns a copy of the sharks ArrayList to sharksCopy */
        ArrayList<Shark> sharksCopy = run.getCopy(sharks);

        /* Asserts that the getCopy method returned an ArrayList containing the same object */
        assertEquals("Name1", sharksCopy.get(0).getCommonName());
        assertEquals("Name2", sharksCopy.get(0).getLatinName());

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

    @Test
    void removeDuplicates() {
        /* Initialises an ArrayList */
        ArrayList<String> rawData = new ArrayList<>();
        /* Shark data is added to rawData */
        rawData.add("Grey Reef Shark:Carcharhinus amblyrhychos:1:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Carcharhinus perezi:2:100:6:4:Indian Ocean");
        rawData.add("Grey Reef Shark:Trianoden obesus:3:100:6:4:Indian Ocean");

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* A set is initialised and populated with Latin names */
        Set<String> setOfLatinWords = run.removeDuplicates(sharks);

        /* Asserts that there are no duplicates in setOfLatinWords */
        assertEquals(5, setOfLatinWords.size());
    }

    @Test
    void removeUnknown() {
        /* Fills rawData */
        fillRawData(2);

        /* Assigns 0 to totalUnknown */
        int totalUnknown = 0;
        /* Increments totalUnknown for every data string containing "UNKNOWN" */
        for (int i = 0; i < rawData.size(); i++) {
            if (rawData.get(i).contains("UNKNOWN")) {
                /* totalUnknown is incremented */
                totalUnknown++;
            }
        }

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* Asserts that the returned array contains no shark objects with unknown shark lengths */
        assertEquals(sharks.size() - totalUnknown, run.removeUnknown(sharks, 1).size());
    }

    @Test
    void isUnknown() {
        /* Initialises an array containing 2 "unknown" strings in upper and lowercase */
        String[] s = {"UNKNOWN", "unknown"};

        /* Asserts that isUnknown returns true for the 2 strings in s */
        for (int i = 0; i < s.length; i++) {
            assertTrue(run.isUnknown(s[i]));
        }

        /* Asserts that isUnknown returns false for a string other than "unknown" and its case variations */
        assertFalse(run.isUnknown("test1"));
    }
}