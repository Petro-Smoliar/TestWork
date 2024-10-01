package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.model.Position;

public class PathFinder {
    private final Map<Character, List<Position>> globalCharMap;

    public PathFinder(Map<Character, List<Position>> globalCharMap) {
        this.globalCharMap = globalCharMap;
    }

    public String findPathForWord(String word) {
        if (!areAllCharsPresent(word)) {
            return "Not found word";
        }
        List<Position> path = new ArrayList<>();
        List<Position> startPositions = globalCharMap.get(word.charAt(0));

        if (startPositions == null) {
            return "Not found word";
        }

        for (Position startPosition : startPositions) {
            if (searchPath(startPosition, word, 1, path)) {
                return formatPath(path);
            }
        }
        return "Not found word";
    }

    private boolean searchPath(Position currentPosition, String word, int index, List<Position> path) {
        path.add(currentPosition);

        if (index == word.length()) {
            return true;
        }

        char nextChar = word.charAt(index);
        Map<Character, List<Position>> neighbors = currentPosition.getAdjacentPositions();

        List<Position> nextPositions = neighbors.get(nextChar);

        if (nextPositions != null) {
            for (Position nextPosition : nextPositions) {
                if (!path.contains(nextPosition)) {
                    if (searchPath(nextPosition, word, index + 1, path)) {
                        return true;
                    }
                }
            }
        }

        path.remove(currentPosition);
        return false;
    }

    private boolean areAllCharsPresent(String word) {
        for (char c : word.toCharArray()) {
            if (!globalCharMap.containsKey(c)) {
                return false;
            }
        }
        return true;
    }

    private String formatPath(List<Position> path) {
        return String.join("->", path.stream()
                .map(Position::toString)
                .toArray(String[]::new));
    }
}