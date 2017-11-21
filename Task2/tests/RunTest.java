import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;

class RunTest {
    /* ArrayLists are initialised */
    private ArrayList<String> rawData = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    /* A run object is instantiated */
    private Run run = new Run();

    private void fillRawData() {
        /* Adds 10 strings of shark data to rawData */
        for (int i = 0; i < 10; i++) {
            /* randomNumber is assigned a number between 1 and 10 */
            int randomNumber = (int) (1 + (Math.random() * 10));

            /* data is assigned a string of shark data */
            String data = "commonname:latinname:length:depth:maxoffspring:globalpresence:regions";

            /* sharkNameChar is assigned a character literal */
            char sharkNameChar = (char)('A' + i);
            /* Replaces "commonname" with the concatenation of sharkNameChar and " Shark" */
            data = data.replace("commonname", sharkNameChar + " Shark");

            /* A StringBuilder is initialised */
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < randomNumber; j++) {
                /* sharkRegionChar is assigned a character literal  */
                char sharkRegionChar = (char)('A' + j);

                /* Appends a region to stringBuilder  */
                if (randomNumber == 1 || j + 1 == randomNumber) {
                    stringBuilder.append(sharkRegionChar + " Region");
                }
                else {
                    stringBuilder.append(sharkRegionChar + " Region, ");
                }
            }

            /* Replaces "regions" with the string in stringBuilder */
            data = data.replace("regions", stringBuilder.toString());

            /* Adds the string of data to rawData */
            rawData.add(data);
        }
    }

    private int countRegions(ArrayList<Shark> sharks) {
        /* A set is initialised */
        Set<String> regions = new TreeSet<>();

        /* Adds all regions to the regions set */
        for (int i = 0; i < sharks.size(); i++) {
            /* Assigns an array containing oceanic regions for the current shark to currentRegions  */
            String[] currentSharkRegions = sharks.get(i).getOceanicRegions().split(",");

            /* Adds all regions in currentSharkRegions to the regions set */
            for (int j = 0; j < currentSharkRegions.length; j++) {
                regions.add(currentSharkRegions[j]);
            }
        }

        return regions.size();
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
        fillRawData();

        /* An ArrayList containing shark objects is assigned to sharks */
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

        /* Asserts that the second element of filteredData is the regions */
        assertEquals("Regions", filteredData[1]);
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
        assertEquals("class Shark", run.createObject(new String[]{"Common Name", "Regions"}).getClass().toString());
    }

    @Test
    void obtainRegions() {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList containing shark objects is assigned to sharks */
        ArrayList<Shark> sharks = run.objectifyData(rawData);

        /* regions is assigned a set containing all non-duplicated regions that is converted to an ArrayList */
        ArrayList<String> regions = new ArrayList<>(run.obtainRegions(sharks));

        /* Asserts that the returned set contains no duplicates */
        assertEquals(countRegions(sharks), regions.size());

        for (int i = 0; i < regions.size() - 1; i++) {
            /* Asserts that regions is sorted alphabetically */
            assertTrue(regions.get(i).charAt(0) < regions.get(i + 1).charAt(0));
        }
    }

    @Test
    void sortSharksByRegion() {
        /* Shark objects are instantiated */
        Shark sharkA = new Shark("A Shark", "A Region, B Region");
        Shark sharkB = new Shark("B Shark", "A Region, B Region, C Region");
        Shark sharkC = new Shark("C Shark", "A Region, B Region, D Region, C Region");

        /* The shark objects are added to the sharks ArrayList */
        sharks.add(sharkA);
        sharks.add(sharkB);
        sharks.add(sharkC);

        /* A set containing shark regions is assigned to regions */
        ArrayList<String> regions = new ArrayList<>(run.obtainRegions(sharks));
        /* An ArrayList containing sharks sorted by region is assigned to sharksSortedByRegion */
        ArrayList<String> sharksSortedByRegion = run.sortSharksByRegion(sharks, regions);

        /* Asserts that the size of regions is equal to the size of sharksSortedByRegion */
        assertEquals(regions.size(), sharksSortedByRegion.size());

        /* Checks if the sharks are associated with the correct region(s) */
        for (int i = 0; i < sharks.size(); i++) {
            /* currentSharkRegionCounter is assigned 0 */
            int currentSharkRegionCounter = 0;

            /* Traverses through sharksSortedByRegion */
            for (int j = 0; j < sharksSortedByRegion.size(); j++) {
                /* The current index of sharksSortedByRegion is split and assigned to currentSortedRegion */
                String[] currentSortedRegion = sharksSortedByRegion.get(j).replaceAll("[\\[\\]]", "").split(",");

                /* Traverses through currentSortedRegion */
                for (int k = 0; k < currentSortedRegion.length; k++) {
                    /* Checks if the current shark is found in the current region */
                    if (sharks.get(i).getCommonName().equals(currentSortedRegion[k].trim())) {
                        /* Asserts that the current sharks oceanic regions contains the current region */
                        assertTrue(sharks.get(i).getOceanicRegions().contains((char)('A' + j) + " Region"));

                        /* Increments currentSharkRegionCounter */
                        currentSharkRegionCounter++;
                    }
                }

                for (int m = 0; m < currentSortedRegion.length - 1; m++) {
                    /* Asserts that the sharks in the current region have been sorted alphabetically */
                    assertTrue(currentSortedRegion[m].trim().charAt(0) < currentSortedRegion[m + 1].trim().charAt(0));
                }
            }

            /* Asserts that the currentSharkRegionCounter is equal to the number of regions the current shark is in */
            assertEquals(currentSharkRegionCounter, sharks.get(i).getOceanicRegions().split(",").length);
        }
    }

    @Test
    void exportResults() throws IOException {
        /* Fills rawData */
        fillRawData();

        /* An ArrayList containing shark objects is assigned to sharks */
        sharks = run.objectifyData(rawData);

        /* A set containing shark regions is assigned to regions */
        ArrayList<String> regions = new ArrayList<>(run.obtainRegions(run.getCopy(sharks)));
        /* An ArrayList containing sharks sorted by region is assigned to sharksSortedByRegion */
        ArrayList<String> sharksSortedByRegion = run.sortSharksByRegion(run.getCopy(sharks), regions);

        /* totalNumberOfRegions is assigned the size of the regions ArrayList */
        int totalNumberOfRegions = regions.size();

        /* The results are exported to a text file */
        run.exportResults(sharksSortedByRegion, regions);

        /* totalNumberOfLines is assigned a number returned by countLines */
        int totalNumberOfLines = countLines("results.txt");

        /* Asserts that the total number of lines in results.txt is equal to the result of (totalNumberOfRegions * 2) - 1 */
        assertEquals(totalNumberOfLines, (totalNumberOfRegions * 2) - 1);
    }

    @Test
    void getCopy() {
        /* Instantiates a shark object and adds it to the sharks ArrayList */
        sharks.add(new Shark("Common Name", "A Region, B Region, C Region"));

        /* Assigns a copy of the sharks ArrayList to sharksCopy */
        ArrayList<Shark> sharksCopy = run.getCopy(sharks);

        /* Asserts that the getCopy method returned an ArrayList containing the same object */
        assertEquals(sharks.get(0).getCommonName(), sharksCopy.get(0).getCommonName());
        assertEquals(sharks.get(0).getOceanicRegions(), sharksCopy.get(0).getOceanicRegions());

        /* The object in sharksCopy is mutated using setter methods */
        sharksCopy.get(0).setCommonName("Changed1");
        sharksCopy.get(0).setOceanicRegions("Changed2");

        /* Asserts that the attributes have been mutated accordingly */
        assertEquals("Changed1", sharksCopy.get(0).getCommonName());
        assertEquals("Changed2", sharksCopy.get(0).getOceanicRegions());

        /* Asserts that the object in the sharks ArrayList has not been mutated */
        assertNotEquals(sharks.get(0).getCommonName(), sharksCopy.get(0).getCommonName());
        assertNotEquals(sharks.get(0).getOceanicRegions(), sharksCopy.get(0).getOceanicRegions());

        /* Removes the object from sharksCopy */
        sharksCopy.remove(0);

        /* Asserts that the object has been removed from sharksCopy */
        assertEquals(0, sharksCopy.size());

        /* Asserts that the object was not removed from the sharks ArrayList  */
        assertNotEquals(sharks.size(), sharksCopy.size());
    }
}