import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class SortingAlgorithms {

    public static void print(int[] arr){
        for(int i : arr){
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }


    public static int[] insertionSort(int[] arr){
        int comparisons = 0;
        int exchanges = 0;
        int[] ret = new int[2];

        for (int i = 1; i < arr.length; i++){
            comparisons++;
            for (int j = i; j > 0; j--){
                comparisons++;
                comparisons++;
                if (arr[j - 1] > arr[j]){
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    exchanges++;
                }
            }
            comparisons++;
        }
        comparisons++;

        ret[0] = comparisons;
        ret[1] = exchanges;

        return ret;
    }


    public static int[] partition(int[] arr, int first, int last, int comparisons, int exchanges){ // int type
        int[] ret = new int[3]; // change

        // System.out.println("num exchange: " + exchanges);
        int bigger = first;

        for (int i = first + 1; i <= last; i++){ // i will be the smaller item that you switch with the bigger item
            comparisons++;
            comparisons++;
            if(arr[i] < arr[first]){ // if i is less than pivot then do the switch
                bigger++; // increment bigger into the non sorted zone of bigger nums
                exchanges++;
                int temp = arr[i];
                arr[i] = arr[bigger];
                arr[bigger] = temp;
            }
        }
        comparisons++;
        exchanges++;
        int temp = arr[first]; // switch the pivot with the number that is next to the numbers that are bigger than the pivots
        arr[first] = arr[bigger];
        arr[bigger] = temp;


        ret[2] = exchanges;
        ret[1] = comparisons;
        ret[0] = bigger;

        return ret; // return bigger;
    }


    public static int[] quickSort(int[] arr, int first, int last, int comparisons, int exchanges){
        int[] info = new int[3];
        info[1] = comparisons;
        info[2] = exchanges;

        info[1]++; // info[1] = comparisons
        if(first < last){

            info = partition(arr, first, last, info[1], info[2]);
            int bound = info[0];

            info = quickSort(arr, first, bound - 1, info[1], info[2]);
            info = quickSort(arr, bound + 1, last, info[1], info[2]);
        }


        return info;
    }


    public static int[] merge(int[] arr, int first, int last, int comparisons, int exchanges){
        int[] info = new int[2];

        int midpoint = (first + last)/2;

        int[] arrL = new int[midpoint - first + 1];
        int[] arrR = new int[last - midpoint];


        for (int i = 0; i < arrL.length ; i++){ // copy values into the left array
            comparisons++;
            arrL[i] = arr[first + i];
        }
        comparisons++;

        for (int j = 0; j < arrR.length; j++){ // copy values into the right array
            comparisons++;
            arrR[j] = arr[midpoint + 1 + j];
        }
        comparisons++;

        int i = 0;
        int j = 0;
        int k = first;

        while (i < arrL.length && j < arrR.length) { // starts overwriting the original array with the sorted values of arrL and arrR
            comparisons++;
            comparisons++;

            comparisons++;
            if (arrL[i] < arrR[j]) {
                exchanges++;
                arr[k] = arrL[i];
                i++;
            }
            else {
                exchanges++;
                arr[k] = arrR[j];
                j++;
            }
            k++;
        }
        comparisons++;


        while (i < arrL.length) { // if left array is not empty then copy the elements
            comparisons++;
            exchanges++;
            arr[k] = arrL[i];
            i++;
            k++;
        }
        comparisons++;

        while (j < arrR.length) { // if right array is not empty then copy the elements
            comparisons++;
            exchanges++;
            arr[k] = arrR[j];
            j++;
            k++;
        }
        comparisons++;

        info[0] = comparisons;
        info[1] = exchanges;
        return info;
    }


    public static int[] mergeSort(int[] arr, int first, int last, int comparisons, int exchanges){
        int[] info = new int[2];
        comparisons++;
        info[0] = comparisons;
        info[1] = exchanges;

        if(last - first <= 1){
            return info;
        }


        info = mergeSort(arr, first, (first + last)/2, info[0], info[1]); // mergeSort left side of array
        info = mergeSort(arr, (first + last)/2 + 1, last, info[0], info[1]); // mergeSort right side of array
        info = merge(arr, first, last, info[0], info[1]);

        return info;
    }


    public static int[] randArray(int size){
        Random rand = new Random();
        int[] arr = new int[size];
        for(int i = 0; i < arr.length; i++){
            arr[i] = rand.nextInt(size);
        }
        return arr;
    }



    public static void main(String[] args) throws IOException {

        int sampleSize = 10;

        FileWriter csv = new FileWriter("D:\\University\\Spring 2021\\Data Structures\\Assignments\\Assignment 9\\sortingalgorithms.csv");
        csv.append(",Time,Comparisons,Exchanges");

        for (int i = 64; i <= 1024; i = i*2) {
            csv.append("\n");
            csv.append("arr size: " + i + "\n");
            for (int j = 0; j < sampleSize; j++) {
                csv.append("Insertion,");



                int[] arr1 = randArray(i);
                int[] info = new int[3];
                long startTime = System.nanoTime();

                startTime = System.nanoTime();
                info = insertionSort(arr1);
                csv.append(String.valueOf(System.nanoTime() - startTime) + ",");
                csv.append(String.valueOf(info[0]) + ",");
                csv.append(String.valueOf(info[1]) + "\n");

            }
            csv.append("\n\n");

            for (int j = 0; j < sampleSize; j++){
                csv.append("Quick,");


                int[] arr1 = randArray(i);
                int[] info = new int[3];
                long startTime = System.nanoTime();


                info[1] = 0; // comparisons
                info[2] = 0; // comparisons
                startTime = System.nanoTime();
                info = quickSort(arr1, 0, arr1.length - 1, 0, 0);
                csv.append(String.valueOf(System.nanoTime() - startTime) + ",");
                csv.append(String.valueOf(info[1]) + ",");
                csv.append(String.valueOf(info[2]) + "\n");
            }
            csv.append("\n\n");

            for (int j = 0; j < sampleSize; j++) {
                csv.append("Merge,");


                int[] arr1 = randArray(i);
                int[] info = new int[3];
                long startTime = System.nanoTime();


                info[0] = 0;
                info[1] = 0;
                startTime = System.nanoTime();
                info = mergeSort(arr1, 0, arr1.length - 1, info[0], info[1]);
                //time.add(System.nanoTime() - startTime);
                csv.append(String.valueOf(System.nanoTime() - startTime) + ",");
                csv.append(String.valueOf(info[0]) + ",");
                csv.append(String.valueOf(info[1]) + "\n");
            }
            csv.append("\n\n");
        }
        csv.close();
    }


}
