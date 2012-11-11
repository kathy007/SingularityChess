package com.petrifiednightmares.singularityChess.pieces;

import java.util.HashSet;
import java.util.Set;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;

public class King extends AbstractPiece
{
	public King(Game game, Square location, boolean isWhite)
	{
		super(game, location, isWhite, isWhite?"♔":"♚");
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		moves.addAll(game.getBoard().getSideMovements(this, true));
		moves.addAll(game.getBoard().getCornerMovements(this, true));

		//TODO remove any moves that put itself under check.
		return moves;
	}

	@Override
	public boolean isCapturable()
	{
		return false;
	}

	
	public static AbstractPiece[] makeKings(Game game, boolean isWhite)
	{
		AbstractPiece[] kings = new AbstractPiece[2];

		int rank = isWhite ? 1 : Board.boardRanks['e'-'a'];
		
		Square location1 =  game.getBoard().getSquares().get("e" + rank);
		King r1 = new King(game,location1,isWhite);
		kings[0]=r1;
		location1.addPiece(r1);
		
		return kings;
	}
	
	public String toString()
	{
		return "King";
	}
}
