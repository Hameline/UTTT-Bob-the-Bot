package dk.easv.bll.bot;

import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;

public class BobtheBot implements IBot{

    final int moveTimeMs = 1000;
    private String BOT_NAME = "Bob the Bot";

    @Override
    public IMove doMove(IGameState state) {
        return null;
    }

    @Override
    public String getBotName() {
        return BOT_NAME;
    }
}
