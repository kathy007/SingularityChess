package com.petrifiednightmares.singularityChess.pieces;

import java.util.HashSet;
import java.util.Set;

import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Board;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.Square;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class Rook extends AbstractPiece
{
	public Rook(Game game, Square location, boolean isWhite)
	{
		super(game, location, isWhite, isWhite ? "\u2656" : "\u265C", SingularBitmapFactory
				.buildSingularScaledBitmap(game.getDrawingPanel().getResources(), isWhite ? R.drawable.rook
						: R.drawable.black_rook, SUI.PIECE_SIZE, SUI.PIECE_SIZE), PieceType.Rook);
	}

	public Set<Square> getMoves() throws GameException
	{
		Set<Square> moves = new HashSet<Square>();
		moves.addAll(game.getBoard().getSideMovements(this, false));
		return moves;
	}

	public static AbstractPiece[] makeRooks(Game game, boolean isWhite)
	{
		AbstractPiece[] rooks = new AbstractPiece[2];

		int rank = isWhite ? 1 : Board.boardRanks['a' - 'a'];

		Square location1 = game.getBoard().getSquares().get("a" + rank);
		Rook r1 = new Rook(game, location1, isWhite);
		rooks[0] = r1;
		location1.addPiece(r1);

		Square location2 = game.getBoard().getSquares().get("h" + rank);
		Rook r2 = new Rook(game, location2, isWhite);
		rooks[1] = r2;
		location2.addPiece(r2);

		return rooks;
	}

	public String toString()
	{
		return "Rook";
	}
}
