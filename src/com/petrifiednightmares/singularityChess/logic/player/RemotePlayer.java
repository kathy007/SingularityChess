package com.petrifiednightmares.singularityChess.logic.player;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;

/**
 * This is the player class that is for online games with remote player. The
 * scores are tracked for this mode.
 * 
 * @author formatjam
 * 
 */
public class RemotePlayer extends Player
{

	public RemotePlayer(boolean isWhite, String name, GameDrawingPanel gdp, Game g, GameUI gui)
	{
		super(isWhite, name, gdp, g, gui);
	}

	public void winGame()
	{
		_gdp.showFinishPrompt("You win!", "Congratulations, you win!");
	}

	public void loseGame()
	{
		// because machine player doesn't show anything, both will need to be
		// shown
		_gdp.showFinishPrompt("You lose!", "Unfortunately, you lost.");
	}

	public void tieGame()
	{
		_gdp.showFinishPrompt("Stalemate!", "This is stale meat. Should have put it in the fridge.");
	}

	/**
	 * this will trigger the Internet communication
	 */
	public void doTurn()
	{

	}

	@Override
	public void promote(AbstractPiece p)
	{
		// TODO Auto-generated method stub

	}

}
