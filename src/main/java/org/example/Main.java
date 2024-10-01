package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.example.model.Position;
import org.example.service.MatrixBuilder;
import org.example.service.PathFinder;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("Enter the sequence of characters for the matrix and press Enter:");
        String matrixString = scanner.nextLine();

        Future<Map<Character, List<Position>>> futureCharMap = executorService.submit(() -> {
            MatrixBuilder matrixBuilder = new MatrixBuilder(matrixString);
            return matrixBuilder.getGlobalCharMap();
        });

        System.out.println("Enter the word to search in the matrix and press Enter:");
        String word = scanner.nextLine();

        try {
            Map<Character, List<Position>> globalCharMap = futureCharMap.get();
            PathFinder pathFinder = new PathFinder(globalCharMap);
            String path = pathFinder.findPathForWord(word);
            System.out.println(path);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}