package datastructuresproject3;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @file HeapOperations.java
 * @description A program that get recommendations according to the target user and movies.
 * @assignment Data Structures Project 3: Movie Recommendation
 * @date 26.05.2023
 * @authors Beyza Yıldızlı @beyzayildizli10@gmail.com & Merve Öğ @merve.og@stu.fsm.edu.tr
 */
public class Operations<T extends Comparable<T>> {

    GenericCast g = new GenericCast();
    FileOperations fl = new FileOperations();

    String rateFile = "src/datastructuresproject3/main_data.csv";
    int rowNum = FileOperations.rowNumber(rateFile) - 1;
    String[][] movieMatrix = fl.movieMatrix();

    /*Collects and returns a list of userNumber max elements from the CosMaxHeap named m sorted by the given cosine similarity.*/
    public T[] closeUsers(int userNumber, CosMaxHeap m, T selectedId, Integer[][] targetMatrix) {
        T[] closeUsers = (T[]) new Comparable[userNumber];
        for (int i = 0; i < userNumber; i++) {
            closeUsers[i] = (T) m.deleteMax(selectedId, targetMatrix);
        }
        return closeUsers;
    }

    /*Collects movie IDs as many as movieNum for selectedUserMatrix and returns this list.*/
    public <T extends Comparable<T>> List<T> bestMovies(T[] selectedUserMatrix, int movieNum) {
        List<T> bestMovies = new ArrayList<>(movieNum);
        for (int i = 0; i < movieNum; i++) {
            bestMovies.add(null);
        }
        int temp = 0;
        for (int j = 0; j < movieNum; j++) {
            T max = selectedUserMatrix[0];
            for (int i = 0; i < selectedUserMatrix.length; i++) {
                if (selectedUserMatrix[i].compareTo(max) > 0) {
                    max = selectedUserMatrix[i];
                    bestMovies.set(j, (T) Integer.class.cast(i + 1));
                    temp = i;
                }
                if (i == selectedUserMatrix.length - 1) {
                    selectedUserMatrix[temp] = (T) Integer.class.cast(-1);
                }
            }
        }
        return bestMovies;
    }

    /*It collects the names and genres of movies with given IDs in a list and returns this list.*/
    public <T> List<String> movieNamesArray(List<T> movies) {
        List<String> bestMovies = new ArrayList<>(movies.size());
        for (int i = 0; i < movies.size(); i++) {

            T[] movieList = (T[]) fl.matrixToArray(movieMatrix, g.convertGenericToString(movies.get(i)));

            String movie = movieList[0] + " " + movieList[1];
            bestMovies.add(movie);
        }
        return bestMovies;
    }
}
