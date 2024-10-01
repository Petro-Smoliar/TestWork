package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
    private final char symbol;
    private final int row;
    private final int col;
    private final Map<Character, List<Position>> adjacentPositions;

    public Position(char symbol, int row, int col) {
        this.symbol = symbol;
        this.row = row;
        this.col = col;
        this.adjacentPositions = new HashMap<>();
    }

    public Map<Character, List<Position>> getAdjacentPositions() {
        return adjacentPositions;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "[" + row + "," + col + "]";
    }
}
