package org.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.model.Position;

public class MatrixBuilder {
    private final Position[][] matrix;
    private final int size;
    private final Map<Character, List<Position>> globalCharMap;

    public MatrixBuilder(String line) {
        this.size = (int) Math.ceil(Math.sqrt(line.length()));
        this.matrix = new Position[size][size];
        this.globalCharMap = new HashMap<>();
        initializePositions(line);
        linkNeighbors();
    }

    private void initializePositions(String line) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (index < line.length()) {
                    char symbol = line.charAt(index);
                    Position position = new Position(symbol, i, j);
                    globalCharMap.putIfAbsent(symbol, new ArrayList<>());
                    globalCharMap.get(symbol).add(position);
                    matrix[i][j] = position;
                    index++;
                }
            }
        }
    }

    private void linkNeighbors() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Position current = matrix[i][j];
                if (current == null) return;
                Map<Character, List<Position>> adjacentPositions = current.getAdjacentPositions();

                if (i > 0) addNeighbor(adjacentPositions, matrix[i - 1][j]);
                if (i < size - 1) addNeighbor(adjacentPositions, matrix[i + 1][j]);
                if (j > 0) addNeighbor(adjacentPositions, matrix[i][j - 1]);
                if (j < size - 1) addNeighbor(adjacentPositions, matrix[i][j + 1]);
            }
        }
    }

    public Map<Character, List<Position>> getGlobalCharMap() {
        return globalCharMap;
    }

    private void addNeighbor(Map<Character, List<Position>> adjacentPositions, Position position) {
        if (position == null) {
            return;
        }
        char symbol = position.getSymbol();
        if (!adjacentPositions.containsKey(symbol)) {
            adjacentPositions.put(symbol, new ArrayList<>());
        }
        adjacentPositions.get(symbol).add(position);
    }
}