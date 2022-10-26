package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.CoordinatesIsNotValid;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Cell(char row, int column) implements Comparable<Cell> {

    Cell(GameStates.Cell cell) {
        this(cell.row(), cell.column());
    }

    static Optional<Cell> of(char row, int column) {
        if (row < 'a' || row > 'j') {
            return Optional.empty();
        }
        if (column < 1 || column > 10) {
            return Optional.empty();
        }
        return Optional.of(new Cell(row, column));
    }

    static Cell of(String coordinates) {
        if (coordinates.length() != 2) {
            throw new CoordinatesIsNotValid();
        }
        return of(Character.toLowerCase(coordinates.charAt(0)), Character.digit(coordinates.charAt(1), 10))
                .orElseThrow(CoordinatesIsNotValid::new);
    }

    @Override
    public int compareTo(Cell cell) {
        int rowComparison = Character.compare(row, cell.row());
        if (rowComparison != 0) {
            return rowComparison;
        }
        return Integer.compare(column, cell.column());
    }

    Set<Cell> neighboursAndThis() {
        return Stream.of(
                        Optional.of(this),
                        left(),
                        right(),
                        up(),
                        down(),
                        up().flatMap(Cell::left),
                        up().flatMap(Cell::right),
                        down().flatMap(Cell::left),
                        down().flatMap(Cell::right)
                )
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    GameStates.Cell state() {
        return new GameStates.Cell(row, column);
    }

    BoardsQuery.CellView view(BoardsQuery.Status status) {
        return new BoardsQuery.CellView(row, column, status);
    }

    Optional<Relation> relation(Cell cell) {
        return Arrays.stream(Relation.values())
                .filter(relation -> relation.between(this, cell))
                .findAny();
    }

    Optional<Cell> right() {
        if (column == 10) {
            return Optional.empty();
        }
        return Optional.of(new Cell(row, column + 1));
    }

    Optional<Cell> left() {
        if (column == 1) {
            return Optional.empty();
        }
        return Optional.of(new Cell(row, column - 1));
    }

    Optional<Cell> up() {
        if (row == 'a') {
            return Optional.empty();
        }
        return Optional.of(new Cell((char) (row - 1), column));
    }

    Optional<Cell> down() {
        if (row == 'j') {
            return Optional.empty();
        }
        return Optional.of(new Cell((char) (row + 1), column));
    }

    public enum Relation {

        DOWN(Cell::down), UP(Cell::up), LEFT(Cell::left), RIGHT(Cell::right);

        private final Function<Cell, Optional<Cell>> possibleRelatedCellFunction;

        Relation(Function<Cell, Optional<Cell>> possibleRelatedCellFunction) {
            this.possibleRelatedCellFunction = possibleRelatedCellFunction;
        }

        boolean between(Cell start, Cell target) {
            return possibleRelatedCellFunction.apply(start)
                    .map(cell -> cell.equals(target))
                    .orElse(Boolean.FALSE);
        }
    }
}
