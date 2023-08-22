package datastructuresproject3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @file FileOperations.java
 * @description A program that get recommendations according to the target user and movies.
 * @assignment Data Structures Project 3: Movie Recommendation
 * @date 26.05.2023
 * @authors Beyza Yıldızlı @beyzayildizli10@gmail.com & Merve Öğ @merve.og@stu.fsm.edu.tr
 */
public class FileOperations<T extends Comparable> {

    GenericCast g = new GenericCast();

    //filled the heap with the ids from the main_data.csv file
    public void fillHeapWithUserId(Integer selectedId, CosMaxHeap<Integer> m, Integer[][] targetMatrix) throws IOException {
        String rateFile = "src/datastructuresproject3/main_data.csv";
        List<String> firstColumnValues = readFirstColumn(rateFile);
        for (int i = 0; i < firstColumnValues.size(); i++) {
            m.insert(Integer.parseInt(firstColumnValues.get(i)), selectedId, targetMatrix);
            
        }
    }

    /*skips the first line of the given csv file and collects the value in the first column for all other lines in a list.*/
    public static List<String> readFirstColumn(String filePath) {
        List<String> firstColumnValues = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // A variable to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip first row
                }
                String[] values = line.split(","); 

                if (values.length > 0) {
                    String firstColumnValue = values[0].trim(); // Retrieves the value of the first column and clears leading and trailing spaces
                    firstColumnValues.add(firstColumnValue); // Adds the first column value to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstColumnValues;
    }

    //Returns the number of row of the csv file whose path is given.
    public static int rowNumber(String csvPath) {
        int rowCount = 0;
        try ( BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    
    //Returns the number of columns of the csv file whose path is given.
    public static int columnNumber(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line = br.readLine();
            if (line != null) {
                String[] columns = line.split(",");
                return columns.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //It finds the id value given in the matrix and returns the row after the id.
    public <T> T[] matrixToArray(T[][] matrix, T id) {
        T[] emptyArray = null;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0].equals(id)) {
                emptyArray = (T[]) Array.newInstance(id.getClass(), matrix[i].length - 1);
                for (int j = 0; j < emptyArray.length; j++) {
                    emptyArray[j] = matrix[i][j + 1];
                }
            }
        }
        return emptyArray;
    }

    public String[] matrixToArray(String[][] matrix, String id) {
        String[] emptyArray = null;
        for (String[] row : matrix) {
            if (row[0].equals(id)) {
                emptyArray = new String[row.length - 1];
                for (int j = 0; j < emptyArray.length; j++) {
                    emptyArray[j] = row[j + 1];
                }
            }
        }
        return emptyArray;
    }

    //We keep the target_user.csv file in the 2D matrix:
    public Integer[][] targetUserMatrix() {
        String targetFile = "src/datastructuresproject3/target_user.csv";
        return csvTo2DMatrix(targetFile, Integer.class);
    }

    //We keep the main_data.csv file in the 2D matrix:
    public Integer[][] rateMatrix() {
        String rateFile = "src/datastructuresproject3/main_data.csv";
        return csvTo2DMatrix(rateFile, Integer.class);
    }

    //We keep the movies.csv file in 2D matrix:
    public String[][] movieMatrix() {
        String movieFile = "src/datastructuresproject3/movies.csv";
        return csvTo2DMatrix(movieFile);
    }

    /*Keeps the csv file given the file path in the 2D matrix, skipping the first line, and returns this matrix*/
    public <T extends Number> T[][] csvTo2DMatrix(String filePath, Class<T> tClass) {
        List<T[]> rows = new ArrayList<>();

        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; 
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; 
                }

                String[] values = line.split(",");
                T[] row = createTypedArray(values.length, tClass);

                for (int i = 0; i < values.length; i++) {
                    row[i] = g.convertToType(values[i], tClass);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numRows = rows.size();
        int numCols = rows.get(0).length;

        T[][] matrix = createTypedArray2D(numRows, numCols, tClass);

        for (int i = 0; i < numRows; i++) {
            matrix[i] = rows.get(i);
        }

        return matrix;
    }

   /*Skips the first line of the csv file with the path given, keeps the comma separated data in the 2D matrix and returns this matrix.
    When separating the data by commas, if there is a part taken with quotation marks,
    it is the comma in the name of the movie and it does not use the commas in this part as a separator.*/
    public String[][] csvTo2DMatrix(String filePath) {
    List<String[]> rows = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            List<String> values = new ArrayList<>();
            StringBuilder sb = new StringBuilder();

            boolean withinQuotes = false;

            for (char c : line.toCharArray()) {
                if (c == '"') {
                    withinQuotes = !withinQuotes;
                } else if (c == ',' && !withinQuotes) {
                    values.add(sb.toString().trim());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }

            values.add(sb.toString().trim());
            rows.add(values.toArray(new String[0]));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    int numRows = rows.size();
    int numCols = rows.get(0).length;

    String[][] matrix = new String[numRows][numCols];

    for (int i = 0; i < numRows; i++) {
        matrix[i] = rows.get(i);
    }

    return matrix;
}

    //Used to create a returned one-dimensional array of the requested type.
    private static <T> T[] createTypedArray(int length, Class<T> tClass) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) java.lang.reflect.Array.newInstance(tClass, length);
        return array;
    }
    
    //It is used to create a returned 2-dimensional array of the requested type.
    private static <T extends Number> T[][] createTypedArray2D(int rows, int cols, Class<T> tClass) {
        @SuppressWarnings("unchecked")
        T[][] array = (T[][]) java.lang.reflect.Array.newInstance(tClass, rows, cols);
        return array;
    }

    
}
