package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;
import dk.easv.bll.move.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BobtheBot implements IBot{

    final int moveTimeMs = 1000;
    private String BOT_NAME = "Bob the Bot";

    @Override
    public IMove doMove(IGameState state) {
        List<IMove> winMoves = getWinningMoves(state);
        List<IMove> normalMoves = normalMove(state);

        if(!winMoves.isEmpty())
            return winMoves.get(0);
        if(winMoves.isEmpty()) {
            List<IMove> prefMove = new ArrayList<>();
            for (int[] move : prefferedMoves)
            {
                if(state.getField().getMacroboard()[move[0]][move[1]].equals(IField.AVAILABLE_FIELD))
                {
                    //find move to play
                    for (int[] selectedMove : prefferedMoves)
                    {
                        int x = move[0]*3 + selectedMove[0];
                        int y = move[1]*3 + selectedMove[1];
                        if(state.getField().getBoard()[x][y].equals(IField.EMPTY_FIELD))
                        {
                            return new Move(x,y);
                        }
                    }
                }
            }
        }

        return doMove(state);
    }

    @Override
    public String getBotName() {
        return BOT_NAME;
    }

    private List<IMove> normalMove(IGameState state) {
        List<IMove> normalMove = new ArrayList<>();
        return normalMove;
    }
    private int[][] prefferedMoves = {
                {0, 0}, {2, 2}, {0, 2}, {2, 0},  //Corners ordered across
                {0, 1}, {2, 1}, {1, 0}, {1, 2}, //Outer Middles ordered across
                {1, 1}
    };


    private boolean isYouLooseMate(IGameState state, IMove move, String enemyPlayer) {


        return false;
    }

    private boolean isWinningMove(IGameState state, IMove move, String player){
        // Clones the array and all values to a new array, so we don't mess with the game
        String[][] board = Arrays.stream(state.getField().getBoard()).map(String[]::clone).toArray(String[][]::new);

        //Places the player in the game. Sort of a simulation.
        board[move.getX()][move.getY()] = player;

        int startX = move.getX()-(move.getX()%3);
        if(board[startX][move.getY()]==player)
            if (board[startX][move.getY()] == board[startX+1][move.getY()] &&
                    board[startX+1][move.getY()] == board[startX+2][move.getY()])
                return true;

        int startY = move.getY()-(move.getY()%3);
        if(board[move.getX()][startY]==player)
            if (board[move.getX()][startY] == board[move.getX()][startY+1] &&
                    board[move.getX()][startY+1] == board[move.getX()][startY+2])
                return true;


        if(board[startX][startY]==player)
            if (board[startX][startY] == board[startX+1][startY+1] &&
                    board[startX+1][startY+1] == board[startX+2][startY+2])
                return true;

        if(board[startX][startY+2]==player)
            if (board[startX][startY+2] == board[startX+1][startY+1] &&
                    board[startX+1][startY+1] == board[startX+2][startY])
                return true;

        return false;
    }

    private List<IMove> getWinningMoves(IGameState state){
        String player = "1";
        if(state.getMoveNumber()%2==0)
            player="0";

        List<IMove> avail = state.getField().getAvailableMoves();

        List<IMove> winningMoves = new ArrayList<>();
        for (IMove move:avail) {
            if(isWinningMove(state,move,player))
                winningMoves.add(move);
        }
        return winningMoves;
    }
}
