package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class GridsCommand implements Command {

    private final GridsQuery query;

    public GridsCommand(GridsQuery query) {
        this.query = query;
    }

    @Override
    public String name() {
        return "grids";
    }

    @Override
    public String help() {
        return "Show current state of grids";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        return playerIdStorage.get()
                .map(this::grids)
                .orElse("No player ID found");
    }

    private String grids(UUID playerId) {
        try {
            return toString(playerId, query.currentGrids(playerId));
        } catch (GameWasNotFound e) {
            return "Game was not found";
        }
    }

    private String toString(UUID playerId, GridsQuery.Grids grids) {
        StringBuilder builder = new StringBuilder();
        if (grids.phase() == GridsQuery.Phase.SHIP_POSITIONING) {
            builder.append("Waiting for all ships positioned");
        }
        if (grids.phase() == GridsQuery.Phase.BATTLE) {
            builder.append("Battle is on");
        }
        if (grids.phase() == GridsQuery.Phase.OVER) {
            builder.append("Game is over");
        }
        builder.append(System.lineSeparator());
        if (grids.phase() != GridsQuery.Phase.SHIP_POSITIONING) {
            builder.append(
                    grids.winner()
                            .map(winner -> "Winner is " + winner)
                            .orElseGet(() -> nextTurnMessage(playerId, grids.turnOwnerId()))
            );
        }
        return builder.append(System.lineSeparator())
                .append("Your board:")
                .append(System.lineSeparator())
                .append(toString(grids.ownedSquares()))
                .append(System.lineSeparator())
                .append("Opponents board:")
                .append(System.lineSeparator())
                .append(toString(grids.opponentSquares()))
                .toString();
    }

    private Optional<GridsQuery.Status> findStatus(Set<GridsQuery.SquareView> squareViews, char column, int row) {
        return squareViews.stream()
                .filter(squareView -> squareView.column() == column)
                .filter(squareView -> squareView.row() == row)
                .map(GridsQuery.SquareView::status)
                .findAny();
    }

    private String nextTurnMessage(UUID playerId, UUID turnOwnerId) {
        if (turnOwnerId.equals(playerId)) {
            return "It is your turn";
        }
        return "It is opponent's turn";
    }

    private char toString(GridsQuery.Status status) {
        switch (status) {
            case SHIP_SEGMENT:
                return 'o';
            case DESTROYED_SHIP_SEGMENT:
                return '*';
            case EMPTY:
                return 'x';
            default:
                return ' ';
        }
    }

    private String toString(Set<GridsQuery.SquareView> squareViews) {
        StringBuilder builder = new StringBuilder("   ABCDEFGHIJ")
                .append(System.lineSeparator())
                .append("  -----------")
                .append(System.lineSeparator());
        for (int row = 1; row <= 10; row++) {
            if (row < 10) {
                builder.append(' ');
            }
            builder.append(row).append('|');
            for (char column = 'a'; column <= 'j'; column++) {
                builder.append(
                        findStatus(squareViews, column, row)
                                .map(this::toString)
                                .orElse(' ')
                );
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
