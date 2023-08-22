package datastructuresproject3;

/**
 * @param <T>
 * @file CosMaxHeap.java
 * @description A program that get recommendations according to the target user and movies.
 * @assignment Data Structures Project 3: Movie Recommendation
 * @date 26.05.2023
 * @authors Beyza Yıldızlı @beyzayildizli10@gmail.com & Merve Öğ @merve.og@stu.fsm.edu.tr
 */
public class CosMaxHeap<T extends Comparable<T>> {

    T[] heap;
    int size;

    FileOperations<Integer> f = new FileOperations();
    Integer[][] rateMatrix = f.rateMatrix();
    GenericCast g = new GenericCast();

    public CosMaxHeap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    //It is used to calculate the parent node of a given node in a max heap.
    int parent(int idx) {
        return (idx - 1) / 2;
    }

    // In an array, it replaces two elements whose index is given.
    void swap(int idx_1, int idx_2) {
        T temp = heap[idx_1];
        heap[idx_1] = heap[idx_2];
        heap[idx_2] = temp;
    }

    /*Retrieves the score matrix of the selected user using the given targetMatrix and selectedId information.
    It adds this score matrix to the heap by comparing the score matrix of the user with the newId id according to cosine smilarity.*/
    void insert(T newId, T selectedId, Integer[][] targetMatrix) {
        Integer selectedIdMatrix[] = (Integer[]) f.matrixToArray(targetMatrix, selectedId);
        if (size < heap.length) {
            heap[size] = newId;
            int current = size++;

            Integer[] heapMatrix = f.matrixToArray(rateMatrix, (Integer) heap[current]);
            Integer[] heapParentMatrix = f.matrixToArray(rateMatrix, (Integer) heap[parent(current)]);

            double a = cosineSimilarity(heapMatrix, selectedIdMatrix); // similarity_newId_selectedId 
            double b = cosineSimilarity(heapParentMatrix, selectedIdMatrix); // similarity_newIdsParent_selectedId 
            while (a > b) {
                swap(current, parent(current));
                current = parent(current);
                Integer[] heapMatrixx = f.matrixToArray(rateMatrix, (Integer) heap[current]);
                Integer[] heapParentMatrixx = f.matrixToArray(rateMatrix, (Integer) heap[parent(current)]);
                a = cosineSimilarity(heapMatrixx, selectedIdMatrix); // similarity_newId_selectedId
                b = cosineSimilarity(heapParentMatrixx, selectedIdMatrix); // similarity_newIdsParent_selectedId
            }
        } else {
            System.out.println("Heap is full");
        }
    }

    /*topDownHeapify is used to rearrange the heap after the element is deleted from the heap.
    Retrieves the score matrix of the selected user using the given targetMatrix and selectedId information.
    It rearranges the heap by comparing this score matrix with the score matrix of the user with the newId id according to cosine smilarity.*/
    void topDownHeapify(int idx, T selectedId, Integer[][] targetMatrix) {
        Integer selectedIdMatrix[] = (Integer[]) f.matrixToArray(targetMatrix, selectedId);

        int left = 2 * idx + 1;
        int right = 2 * idx + 2;
        int max = idx;

        Integer[] heapMaxMatrix = f.matrixToArray(rateMatrix, (Integer) heap[max]);
        double hMax = cosineSimilarity(heapMaxMatrix, selectedIdMatrix);

        if (left < size) {
            Integer[] heapLeftMatrix = f.matrixToArray(rateMatrix, (Integer) heap[left]);
            double hLeft = cosineSimilarity(heapLeftMatrix, selectedIdMatrix);
            if (hLeft > hMax) {
                max = left;
                hMax = hLeft; // update max value
            }
        }

        if (right < size) {
            Integer[] heapRightMatrix = f.matrixToArray(rateMatrix, (Integer) heap[right]);
            double hRight = cosineSimilarity(heapRightMatrix, selectedIdMatrix);
            if (hRight > hMax) {
                max = right;
            }
        }

        if (max != idx) {
            swap(max, idx);
            topDownHeapify(max, selectedId, targetMatrix);
        }
    }

    /*It deletes the root element of the heap and rearrange the heap by calling the topDownHeapify function.*/
    T deleteMax(T selectedId, Integer[][] targetMatrix) {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty. Cannot delete max element.");
        }
        T deletedElement = heap[0];
        heap[0] = heap[size - 1];
        size--;

        topDownHeapify(0, selectedId, targetMatrix);
        return deletedElement;
    }

    //It calculates the similarity between two different arrays by formulating it over the cosine function in trigonometry.
    public static <T extends Number> double cosineSimilarity(T[] vectorA, T[] vectorB) {
        // Vektörlerin uzunluğunu kontrol et
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vector length must be same!");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Calculates the dot product of vectors and the norms of each vector
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i].doubleValue() * vectorB[i].doubleValue();
            normA += Math.pow(vectorA[i].doubleValue(), 2);
            normB += Math.pow(vectorB[i].doubleValue(), 2);
        }

        normA = Math.sqrt(normA);
        normB = Math.sqrt(normB);

        // Calculates similarity using the cosine similarity formula
        double similarity;
        if (normA == 0.0 || normB == 0.0) {
            similarity = 0.0; // If the norm of a vector is zero, the similarity is zero.
        } else {
            similarity = dotProduct / (normA * normB);
        }
        return similarity;
    }

}
