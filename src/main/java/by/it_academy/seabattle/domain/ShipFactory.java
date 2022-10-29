package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

final class ShipFactory {

    IntactShip intactShip(GameStates.Ship ship) {
        if (!ship.destroyedSquares().isEmpty()) {
            throw new SquareIsNotValid();
        }
        return intactShip(squares(ship.intactSquares()).iterator());
    }

    IntactShip intactShip(Collection<String> shipCoordinates) {
        return intactShip(
                shipCoordinates.stream()
                        .map(Square::of)
                        .sorted()
                        .iterator()
        );
    }

    Ship ship(GameStates.Ship ship) {
        Ship identity = intactShip(squares(ship.intactSquares(), ship.destroyedSquares()).iterator());
        return squares(ship.destroyedSquares()).reduce(identity, Ship::hit, (first, second) -> first);
    }

    IntactShip random() {
        return intactShip(
                Stream.iterate(Optional.of(Square.random()), randomDirectionOperator())
                        .limit(ThreadLocalRandom.current().nextLong(1, 5))
                        .flatMap(Optional::stream)
                        .sorted()
                        .iterator()
        );
    }

    private UnaryOperator<Optional<Square>> randomDirectionOperator() {
        Function<Square, Optional<Square>> function = randomDirectionFunction();
        return current -> current.flatMap(function);
    }

    private Function<Square, Optional<Square>> randomDirectionFunction() {
        switch (ThreadLocalRandom.current().nextInt(0, 4)) {
            case 0:
                return Square::left;
            case 1:
                return Square::right;
            case 2:
                return Square::up;
            case 3:
                return Square::down;
            default:
                return Optional::of;
        }
    }

    private IntactShip intactShip(Iterator<Square> iterator) {
        if (!iterator.hasNext()) {
            throw new ShipIsNotValid();
        }
        IntactShip ship = new OneSquareShip(iterator.next());
        while (iterator.hasNext()) {
            ship = ship.grow(iterator.next());
        }
        return ship;
    }

    @SafeVarargs
    private Stream<Square> squares(Collection<GameStates.Square>... squares) {
        return Arrays.stream(squares)
                .flatMap(Collection::stream)
                .map(square -> Square.of(square.column(), square.row()))
                .sorted();
    }
}
