package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.exception.SquareIsNotValid;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Square implements Comparable<Square> {

    private final char column;
    private final int row;

    private Square(char column, int row) {
        this.column = column;
        this.row = row;
    }

    static Square of(String coordinates) {
        if (coordinates.length() < 2 || coordinates.length() > 3) {
            throw new SquareIsNotValid();
        }
        try {
            return of(
                    Character.toLowerCase(coordinates.charAt(0)),
                    Integer.parseInt(coordinates.substring(1))
            );
        } catch (NumberFormatException e) {
            throw new SquareIsNotValid(e);
        }
    }

    static Square of(char column, int row) {
        if (column < 'a' || column > 'j') {
            throw new SquareIsNotValid();
        }
        if (row < 1 || row > 10) {
            throw new SquareIsNotValid();
        }
        return new Square(column, row);
    }

    static Square random() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return of((char) random.nextInt('a', 'j' + 1), random.nextInt(1, 11));
    }

    @Override
    public int compareTo(Square square) {
        int columnCompareResult = Character.compare(column, square.column);
        if (columnCompareResult == 0) {
            return Integer.compare(row, square.row);
        }
        return columnCompareResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Square square = (Square) object;
        return column == square.column &&
                row == square.row;
    }

    @Override
    public String toString() {
        return "Square{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }

    Set<Square> neighbours() {
        return Stream.of(
                        right(),
                        left(),
                        up(),
                        down(),
                        right().flatMap(Square::up),
                        right().flatMap(Square::down),
                        left().flatMap(Square::up),
                        left().flatMap(Square::down)
                )
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    boolean connectedHorizontally(Square square) {
        return Stream.of(right(), left())
                .flatMap(Optional::stream)
                .anyMatch(square::equals);
    }

    boolean connectedVertically(Square square) {
        return Stream.of(up(), down())
                .flatMap(Optional::stream)
                .anyMatch(square::equals);
    }

    GameStates.Square state() {
        return new GameStates.Square(column, row);
    }

    GridsQuery.SquareView view(GridsQuery.Status status) {
        return new GridsQuery.SquareView(column, row, status);
    }

    Optional<Square> right() {
        if (column == 'j') {
            return Optional.empty();
        }
        return Optional.of(Square.of((char) (column + 1), row));
    }

    Optional<Square> left() {
        if (column == 'a') {
            return Optional.empty();
        }
        return Optional.of(Square.of((char) (column - 1), row));
    }

    Optional<Square> up() {
        if (row == 1) {
            return Optional.empty();
        }
        return Optional.of(Square.of(column, row - 1));
    }

    Optional<Square> down() {
        if (row == 10) {
            return Optional.empty();
        }
        return Optional.of(Square.of(column, row + 1));
    }
}
