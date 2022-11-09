package by.it_academy.seabattle.exercise;

class AddPlayerToQueueUseCaseTest extends SeaBattleTest {

//    @Test
//    void givenPlayerIsInQueue_whenAdd_thenPlayerIsInQueueThrown() {
//        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
//        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
//        useCase.add(firstPlayerId);
//
//        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(PlayerIsInQueue.class);
//    }
//
//    @Test
//    void givenGameStartedWithPlayer_whenRegister_thenGameWithPlayerExistsThrown() {
//        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
//        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
//        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
//        useCase.add(firstPlayerId);
//        useCase.add(secondPlayerId);
//
//        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(GameWithPlayerExists.class);
//    }
//
//    @Test
//    void givenGameWasOver_whenRegister_thenPlayerCanRegisterAgain() {
//        startBattle();
//        destroyAllEnemyShips();
//        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
//
//        assertThatCode(() -> useCase.add(firstPlayerId)).doesNotThrowAnyException();
//    }
//
//    @Test
//    void givenNonexistentPlayerId_whenRegister_thenPlayerWasNotFoundThrown() {
//        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
//
//        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(PlayerWasNotFound.class);
//    }
}