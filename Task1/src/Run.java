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

        /* An ArrayList containing a collection of results is assigned to results */
        ArrayList<String> results = collectResults(getCopy(sharks), sortByLength(removeUnknown(sharks, 1)));
        /* The results are exported */
        exportResults(results);
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

        /* knownSharkLengths is assigned 0 */
        int knownSharkLengths = 0;

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

                    /* knownSharkLengths is incremented by 1 if the length is known */
                    if (!currentLine.split(":")[2].trim().equalsIgnoreCase("UNKNOWN")) {
                        /* Increments knownSharkLengths by 1 */
                        knownSharkLengths++;
                    }
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
            /* Displays an error message and exits the program if less than 3 sharks have a known length */
            else if (knownSharkLengths < 3) {
                System.out.print("Error: Insufficient number of known shark lengths - a minimum of 3 is required");
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
            sharkArray.add(createObject(data));
        }

        return sharkArray;
    }

    /**
     * Returns a shark object.
     */
    public static Shark createObject(String[] data) {
        /* Assigns an array of formatted strings to data */
        data = formatData(data);

        /* Instantiates a shark object */
        Shark shark = new Shark(data[0], data[1]);

        /* A try catch block is used to mutate various properties in the shark object and catch an exception */
        try {
            /* Setter methods are used to mutate the objects properties */
            shark.setSharkLength(Integer.parseInt(data[2]));
            shark.setMaxDepth(Integer.parseInt(data[3]));
            shark.setMaxOffspring(Integer.parseInt(data[4]));
            shark.setGlobalPresence(Integer.parseInt(data[5]));
            shark.setOceanicRegions(data[6]);
        }
        /* Catches NumberFormatException */
        catch (NumberFormatException e) {
            /* Prints the error and exits the program */
            System.out.print("Error: " + e);
            System.exit(1);
        }

        return shark;
    }

    /**
     * Returns an array of formatted strings.
     */
    public static String[] formatData(String[] data) {
        /* Eliminates leading and trailing spaces for every string in data */
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].trim();
        }

        /* Replaces all "unknown" strings that will be converted to an integer with "-1" */
        for (int i = 2; i < data.length - 1; i++) {
            /* Replaces an element in index i with "-1" if isUnknown returns true */
            if (isUnknown(data[i])) {
                data[i] = "-1";
            }
        }

        return data;
    }

    /**
     * Returns an ArrayList containing results.
     */
    public static ArrayList<String> collectResults(ArrayList<Shark> sharks, Shark[] sortedSharks) {
        /* An ArrayList is initialised */
        ArrayList<String> results = new ArrayList<>();

        /* Adds the three largest sharks to results */
        results.add("--- Three largest sharks ---");
        for (int i = 0; i < 3; i++) {
            /* A string containing a sharks common name and length is added to results */
            results.add(sortedSharks[i].getCommonName()
                    + ", Length = " + sortedSharks[i].getSharkLength() + " centimetres");
        }

        /* Adds the three largest sharks to results */
        results.add("\r\n--- Three smallest sharks ---");
        /* An array containing the last 3 shark objects in sortedSharks is assigned to tempArray */
        Shark[] tempArray = Arrays.copyOfRange(sortedSharks, sortedSharks.length - 3, sortedSharks.length);
        for (int i = 0; i < tempArray.length; i++) {
            /* A string containing a sharks common name and length is added to results */
            results.add(tempArray[i].getCommonName()
                    + ", Length = " + tempArray[i].getSharkLength() + " centimetres");
        }

        /* Adds all totals to results */
        results.add("\r\n--------------------------");
        results.add("Total number of letters in all Latin names = " + totalLetters(sharks));
        results.add("Total number of unique even words in Latin names = " + totalEvenWords(removeDuplicates(sharks)));
        results.add("Total number of unique odd words in Latin names = " + totalOddWords(removeDuplicates(sharks)));

        return results;
    }

    /**
     * Exports the results.
     */
    public static void exportResults(ArrayList<String> results) {
        /* A PrintWriter object is declared */
        PrintWriter writer = null;

        /* A try catch finally block is used to export and display the results */
        try {
            /* A PrintWriter object is instantiated */
            writer = new PrintWriter("results.txt", "UTF-8");

            /* Results are exported and displayed */
            for (int i = 0; i < results.size(); i++) {
                /* Prints result to console */
                System.out.println(results.get(0));

                /* Writes result to file */
                if (results.size() == 1) {
                    /* Writes to current line if results.size() is equal to 1 */
                    writer.print(results.get(0));
                }
                else {
                    /* Writes to the current line and moves the cursor to a new line */
                    writer.println(results.get(0));
                }

                /* Removes result from the results ArrayList */
                results.remove(0);
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
     * Returns the total amount of letters in all Latin names.
     */
    public static int totalLetters(ArrayList<Shark> sharks) {
        /* totalNumberOfLetters is assigned 0 */
        int totalNumberOfLetters = 0;

        /* Totals the number of letters of all Latin names for all sharks */
        for (int i = 0; i < sharks.size(); i++) {
            /* Adds the returned value of getLatinNameLength() to totalNumberOfLetters */
            totalNumberOfLetters += sharks.get(i).getLatinNameLength();
        }

        return totalNumberOfLetters;
    }

    /**
     * Returns the total number of unique Latin words that have an even number of letters.
     */
    public static int totalEvenWords(Set<String> setOfLatinWords) {
        /* totalNumberOfEvenWords is assigned 0 */
        int totalNumberOfEvenWords = 0;

        /* Evaluates the length of each Latin word and increments totalNumberOfEvenWords */
        for (String e : setOfLatinWords) {
            if (e.length() % 2 == 0)
            /* Increment totalNumberOfEvenWords if the Latin words length divided by 2 has no remainder */
                totalNumberOfEvenWords++;
        }

        return totalNumberOfEvenWords;
    }

    /**
     * Returns the total number of unique Latin words that have an odd number of letters.
     */
    public static int totalOddWords(Set<String> setOfLatinWords) {
        /* totalNumberOfOddWords is assigned 0 */
        int totalNumberOfOddWords = 0;

        /* Evaluates the length of each Latin word and increments totalNumberOfOddWords */
        for (String e : setOfLatinWords) {
            if (e.length() % 2 != 0)
            /* Increment totalNumberOfOddWords if the Latin words length divided by 2 has a remainder */
                totalNumberOfOddWords++;
        }

        return totalNumberOfOddWords;
    }

    /**
     * Returns an array of shark objects that have been sorted by their length in descending order.
     */
    public static Shark[] sortByLength(ArrayList<Shark> sharks) {
        /* An ArrayList is converted to an array and assigned to tempArray */
        Shark[] tempArray = new Shark[sharks.size()];
        tempArray = sharks.toArray(tempArray);

        /* The array is traversed from index 0 to tempArray.length - 1 to sort the entire array */
        for (int i = 0; i < tempArray.length - 1; i++) {
            /* currentMax is assigned an object in tempArray[i] */
            Shark currentMax = tempArray[i];
            /* currentMaxIndex is assigned i*/
            int currentMaxIndex = i;

            /* Locates the largest shark length between position i + 1 and tempArray.length */
            for (int j = i + 1; j < tempArray.length; j++) {
                /* Checks if the length of a shark in index j is greater than currentMax's shark length */
                if (currentMax.getSharkLength() < tempArray[j].getSharkLength()) {
                    /* If true, currentMax and currentMaxIndex is re-assigned with an object in tempArray[j] and j */
                    currentMax = tempArray[j];
                    currentMaxIndex = j;
                }
            }

            /* Swaps tempArray[i] with tempArray[currentMaxIndex] if currentMaxIndex isn't equal to i */
            if (currentMaxIndex != i) {
                tempArray[currentMaxIndex] = tempArray[i];
                tempArray[i] = currentMax;
            }
        }

        return tempArray;
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

    /**
     * Returns a set containing unique Latin words.
     */
    public static Set<String> removeDuplicates(ArrayList<Shark> sharks) {
        /* A set is initialised. This will be populated with unique Latin words */
        Set<String> setOfLatinWords = new LinkedHashSet<>();

        /* Adds all Latin words to setOfLatinWords */
        for (int i = 0; i < sharks.size(); i++) {
            /* Ignore all shark objects that have an unknown Latin name */
            if (!sharks.get(i).hasUnknownLatinName()) {
                /* Split the latin name by using whitespace as the delimiter and store in tempArray */
                String[] tempArray = sharks.get(i).getLatinName().split("\\s+");

                /* Add all elements in tempArray to setOfLatinWords */
                for (int j = 0; j < tempArray.length; j++) {
                    setOfLatinWords.add(tempArray[j]);
                }
            }
        }

        return setOfLatinWords;
    }

    /**
     * Removes an object from an ArrayList if a attribute is "Unknown" and returns the ArrayList.
     */
    public static ArrayList<Shark> removeUnknown(ArrayList<Shark> sharks, int action) {
        /* Action 1 checks if an object has an unknown shark length */
        if (action == 1) {
            /* The array is traversed from index 0 to sharks.size() so that all unknown shark lengths are removed */
            for (int i = 0; i < sharks.size(); i++) {
                /* Removes i from the given ArrayList if hasUnknownSharkLength returns true */
                if (sharks.get(i).hasUnknownSharkLength()) {
                    sharks.remove(i);
                    i--;
                }
            }
        }

        return sharks;
    }

    /**
     * Returns a boolean value indicating whether or not a given string is equal to "unknown".
     */
    public static boolean isUnknown(String data) {
        /* Compares data to "UNKNOWN" and ignores case considerations */
        return (data.equalsIgnoreCase("UNKNOWN")) ? true : false;
    }
}
