import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Run {
    public static void main(String[] args) {
        /* An ArrayList containing strings is assigned to rawData */
        ArrayList<String> rawData = importData();
        /* An ArrayList containing shark objects is assigned to sharks */
        ArrayList<Shark> sharks = objectifyData(rawData);

        /* An ArrayList containing shark regions is assigned to regions */
        ArrayList<String> regions = new ArrayList<>(obtainRegions(getCopy(sharks)));
        /* An ArrayList containing sharks sorted by region is assigned to sharksSortedByRegion */
        ArrayList<String> sharksSortedByRegion = sortSharksByRegion(getCopy(sharks), regions);

        /* The results are exported */
        exportResults(sharksSortedByRegion, regions);
    }

    /**
     * Imports data from shark-data.txt and returns an ArrayList containing the data.
     */
    public static ArrayList<String> importData() {
        /* An ArrayList is initialised */
        ArrayList<String> tempArray = new ArrayList<>();
        /* dataPath is assigned the path to shark-data.txt */
        String dataPath = System.getProperty("user.dir") + File.separator + "shark-data.txt";

        /* A scanner object is declared */
        Scanner reader = null;

        /* A try catch block is used to import data from the path specified in dataPath and catch an exception */
        try {
            /* A scanner object is instantiated */
            reader = new Scanner(new File(dataPath));

            /* A while loop is initiated until there isn't another line in the input of the reader */
            while (reader.hasNextLine()) {
                /* A string of data from shark-data.txt is assigned to currentLine */
                String currentLine = reader.nextLine();

                /* Assigns the total number of delimiters in currentLine */
                int totalNumberOfDelimiters = currentLine.length() - currentLine.replace(":", "").length();

                /* Adds a non-empty and correctly delimited string to tempArray */
                if (!currentLine.isEmpty() && !currentLine.matches("^\\s+$") && totalNumberOfDelimiters == 6) {
                    tempArray.add(currentLine);
                }
            }
        }
        /* Catches FileNotFoundException */
        catch (FileNotFoundException e) {
            /* Prints the error and exits the program */
            System.out.print("Error: " + e);
            System.exit(1);
        }
        finally {
            /* Closes the reader */
            if (reader != null) {
                /* The reader is closed */
                reader.close();
            }

            /* Displays an error message and exits the program if tempArray is empty */
            if (tempArray.isEmpty()) {
                System.out.print("Error: " + dataPath + " is empty");
                System.exit(1);
            }
        }

        return tempArray;
    }

    /**
     * Returns an ArrayList containing shark objects.
     */
    public static ArrayList<Shark> objectifyData(ArrayList<String> rawData) {
        /* An ArrayList is initialised */
        ArrayList<Shark> sharkArray = new ArrayList<>();

        /* Each element in rawData is formatted and added to sharkArray */
        for (int i = 0; i < rawData.size(); i++) {
            /* Splits an element using ":" as a delimiter and stores the array of strings in data */
            String[] data = rawData.get(i).split(":");

            /* Adds the instantiated shark object to sharkArray */
            sharkArray.add(createObject(filterData(data)));
        }

        return sharkArray;
    }

    /**
     * Returns a string array containing a sharks common name and its oceanic regions.
     */
    public static String[] filterData(String[] data) {
        /* A string array is initialised */
        String[] filteredData = new String[2];

        /* A try catch block is used to assign data to filteredData */
        try {
            filteredData[0] = capitalise(data[0].trim());
            filteredData[1] = capitalise(data[6].trim());
        }
        /* Catches ArrayIndexOutOfBoundsException */
        catch (ArrayIndexOutOfBoundsException e) {
            /* Prints the error and exits the program */
            System.out.print("Error: " + e);
            System.exit(1);
        }

        return filteredData;
    }

    /**
     * Returns a string that has an uppercase character at the start and after every whitespace/dash/comma.
     */
    public static String capitalise(String data) {
        /* Instantiates a StringBuilder object */
        StringBuilder sb = new StringBuilder(data);

        /* Capitalises the first character in StringBuilder */
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

        /* Capitalises the first character after a whitespace/dash/comma */
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ' || sb.charAt(i) == '-') {
                /* Converts the letter at i + 1 to an uppercase letter */
                sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
            }
        }

        return sb.toString();
    }

    /**
     * Returns a shark object.
     */
    public static Shark createObject(String[] data) {
        /* Instantiates a shark object */
        Shark shark = new Shark(data[0], data[1]);

        return shark;
    }

    /**
     * Returns a Set containing all shark regions in alphabetical order.
     */
    public static Set<String> obtainRegions(ArrayList<Shark> sharks) {
        /* A set is initialised */
        Set<String> regions = new TreeSet<>();

        /* Oceanic regions are added to the set */
        for (int i = 0; i < sharks.size(); i++) {
            /* An array of strings split using a delimiter is assigned to data */
            String[] data = sharks.get(i).getOceanicRegions().split(",");

            /* Every element in data is added to the set */
            for (int j = 0; j < data.length; j++) {
                /* Adds data to regions */
                regions.add(data[j].trim());
            }
        }

        return regions;
    }

    /**
     * Returns an ArrayList containing sharks sorted by region in alphabetical order for all regions.
     */
    public static ArrayList<String> sortSharksByRegion(ArrayList<Shark> sharks, ArrayList<String> regions) {
        /* An ArrayList is initialised */
        ArrayList<String> sharksSortedByRegion = new ArrayList<>();

        /* Adds a string of sorted shark names to sharksSortedByRegion */
        for (int i = 0; i < regions.size(); i++) {
            /* A set is initialised */
            Set<String> tempSet = new TreeSet<>();

            /* Traverses through an ArrayList of sharks */
            for (int j = 0; j < sharks.size(); j++) {
                /* An array of oceanic regions that a shark resides in is assigned to currentSharkRegions */
                String[] currentSharkRegions = sharks.get(j).getOceanicRegions().split(",");

                /* Adds a sharks common name to tempSet if currentSharkRegions contains a string equal to the current region */
                for (int k = 0; k < currentSharkRegions.length; k++) {
                    if (currentSharkRegions[k].trim().equals(regions.get(i))) {
                        /* A sharks common name is added to tempSet */
                        tempSet.add(sharks.get(j).getCommonName());
                    }
                }
            }

            /* A string representation of tempSet is added to sharksSortedByRegion */
            sharksSortedByRegion.add(tempSet.toString());
        }

        return sharksSortedByRegion;
    }

    /**
     * Exports the results.
     */
    public static void exportResults(ArrayList<String> sharksSortedByRegion, ArrayList<String> regions) {
        /* A PrintWriter object is declared */
        PrintWriter writer = null;

        /* A try catch finally block is used to export and display the results */
        try {
            /* A PrintWriter object is instantiated */
            writer = new PrintWriter("results.txt", "UTF-8");

            /* Results are exported and displayed */
            for (int i = 0; i < sharksSortedByRegion.size() && i < regions.size(); i++) {
                if (i < sharksSortedByRegion.size() - 1) {
                    /* Prints result to console and inserts a newline */
                    System.out.println(regions.get(0) + " -> " + sharksSortedByRegion.get(0) + "\r\n");
                    /* Writes result to file and inserts a newline */
                    writer.println(regions.get(0) + " -> " + sharksSortedByRegion.get(0) + "\r\n");
                }
                else {
                    /* Prints result to console */
                    System.out.println(regions.get(0) + " -> " + sharksSortedByRegion.get(0));
                    /* Writes result to file */
                    writer.print(regions.get(0) + " -> " + sharksSortedByRegion.get(0));
                }

                /* Removes the printed and written result from the results ArrayList */
                sharksSortedByRegion.remove(0);
                regions.remove(0);

                /* Decrements i */
                i--;
            }
        }
        catch (FileNotFoundException e) {
            /* Prints the error */
            System.out.print("Error: " + e);
        }
        catch (UnsupportedEncodingException e) {
            /* Prints the error */
            System.out.println("Error: " + e);
        }
        finally {
            /* Closes the writer */
            if (writer != null) {
                /* The writer is closed */
                writer.close();
            }
        }
    }

    /**
     * Returns a defensive copy of a given shark ArrayList.
     */
    public static ArrayList<Shark> getCopy(ArrayList<Shark> sharks) {
        /* An ArrayList is initialised */
        ArrayList<Shark> sharksCopy = new ArrayList<>(sharks.size());

        /* Instantiates a copy of the current object in the given ArrayList and adds it to a new ArrayList */
        for (Shark shark : sharks) {
            sharksCopy.add(new Shark(shark));
        }

        return sharksCopy;
    }
}
