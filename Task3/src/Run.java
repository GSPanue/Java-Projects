import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Run {
    public static void main(String[] args) {
        /* An ArrayList containing strings is assigned to rawData */
        ArrayList<String> rawData = importData();
        /* An ArrayList containing shark objects is assigned to sharks */
        ArrayList<Shark> sharks = objectifyData(rawData);

        /* A message requesting the user to enter a search string is output to the console */
        System.out.print("Enter search string for Latin names: > ");
        /* A string obtained via console input is assigned to term */
        String term = new Scanner(System.in).next();

        /* The users input is output to the console */
        System.out.println("You entered string \"" + term + "\".\n");

        /* An ArrayList containing sharks with Latin names that match the search term are assigned to searchResults */
        ArrayList<Shark> searchResults = search(term, getCopy(sharks));

        /* The results are output to the console */
        display(term, searchResults);
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
     * Returns a string array containing a sharks common name and latin name.
     */
    public static String[] filterData(String[] data) {
        /* A string array is initialised */
        String[] filteredData = new String[2];

        /* A try catch block is used to assign data to filteredData */
        try {
            filteredData[0] = capitalise(data[0].trim());
            filteredData[1] = capitalise(data[1].trim());
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
        StringBuilder stringBuilder = new StringBuilder(data);

        /* Capitalises the first character in StringBuilder */
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));

        /* Capitalises the first character after a whitespace/dash/comma */
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == ' ' || stringBuilder.charAt(i) == '-') {
                /* Converts the letter at i + 1 to an uppercase letter */
                stringBuilder.setCharAt(i + 1, Character.toUpperCase(stringBuilder.charAt(i + 1)));
            }
        }

        return stringBuilder.toString();
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
     * Returns an ArrayList containing sharks with Latin names that match the term.
     */
    public static ArrayList<Shark> search(String term, ArrayList<Shark> sharks) {
        /* An ArrayList is initialised */
        ArrayList<Shark> tempArray = new ArrayList<>();

        /* Adds all sharks with Latin names that match the given term to tempArray */
        for (int i = 0; i < sharks.size(); i++) {
            /* The current shark object is added to tempArray if its Latin name contains the given term */
            if (sharks.get(i).getLatinName().toLowerCase().contains(term.toLowerCase())) {
                /* Adds a shark object to tempArray */
                tempArray.add(sharks.get(i));
            }
        }

        /* The program is exited if no matches are found */
        if (tempArray.isEmpty()) {
            /* A message indicating that no matches were found is output to the console */
            System.out.println("Your search - \"" + term + "\" - did not match any latin names.");
            /* The program is exited */
            System.exit(1);
        }

        return sortByCommonName(tempArray);
    }

    /**
     * Returns an ArrayList containing sharks that have been sorted by common name.
     */
    public static ArrayList<Shark> sortByCommonName(ArrayList<Shark> sharks) {
        /* A set is initialised */
        Set<Shark> tempSet = new TreeSet<>(new Comparator<Shark>() {
            @Override
            /* The compare method is overridden to compare the common name of two shark objects */
            public int compare(Shark sharkA, Shark sharkB) {
                return sharkA.getCommonName().compareTo(sharkB.getCommonName());
            }
        });

        /* All shark objects are added to tempSet and sorted by their common name */
        tempSet.addAll(sharks);

        return new ArrayList<>(tempSet);
    }

    /**
     * Displays the result(s).
     */
    public static void display(String term, ArrayList<Shark> searchResults) {
        /* A StringBuilder object is instantiated */
        StringBuilder stringBuilder = new StringBuilder();

        /* A message indicating that matches were found is output to the console */
        System.out.println("The following matches have been found:-");

        /* A for-each loop is used to output the results to the console */
        for (Shark s : searchResults) {
            /* Appends a sharks common name to stringBuilder */
            stringBuilder.append(s.getCommonName());
            stringBuilder.append(" - Latin name: ");
            /* Converts all occurrences of a given term in a sharks Latin name to uppercase and appends it to stringBuilder */
            stringBuilder.append(s.getLatinName().replaceAll("(?i)" + term, term.toUpperCase()));

            /* A result is output to the console */
            System.out.println(stringBuilder.toString());

            /* The StringBuilder object is reset */
            stringBuilder.setLength(0);
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