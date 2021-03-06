package com.petrifiednightmares.singularityChess.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.petrifiednightmares.singularityChess.logging.MoveLogger;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.logic.player.Player;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;
import com.petrifiednightmares.singularityChess.ui.GameUI;

public class GameSaveable implements Saveable
{
	int					gameType;
	boolean				isWhiteTurn;
	boolean				isControllingWhite;
	AbstractPiece[]		whitePieces;
	AbstractPiece[]		blackPieces;

	PieceSaveable[]		whitePieceSaveables;
	PieceSaveable[]		blackPieceSaveables;

	PlayerSaveable		whitePlayerSaveable;
	PlayerSaveable		blackPlayerSaveable;

	Player				whitePlayer;
	Player				blackPlayer;

	MoveLoggerSaveable	mls;

	// For resuming
	private Game		_game;
	MoveLogger			_ml;

	public int getGameType()
	{
		return gameType;
	}

	public boolean isWhiteTurn()
	{
		return isWhiteTurn;
	}

	public AbstractPiece[] getWhitePieces()
	{
		return whitePieces;
	}

	public AbstractPiece[] getBlackPieces()
	{
		return blackPieces;
	}

	public MoveLogger getMoveLogger()
	{
		return _ml;
	}

	public Player getWhitePlayer()
	{
		return whitePlayer;
	}

	public Player getBlackPlayer()
	{
		return blackPlayer;
	}

	public boolean getIsControllingWhite()
	{
		return isControllingWhite;
	}

	// Empty constructor for reading
	public GameSaveable(Game game, GameUI gui)
	{
		this._game = game;
		mls = new MoveLoggerSaveable();

		whitePieces = new AbstractPiece[16];
		blackPieces = new AbstractPiece[16];

		this.whitePlayerSaveable = new PlayerSaveable(game, game.getDrawingPanel(), gui);
		this.blackPlayerSaveable = new PlayerSaveable(game, game.getDrawingPanel(), gui);
	}

	// full constructor for writing
	public GameSaveable(int gameType, boolean isWhiteTurn, AbstractPiece[] whitePieces,
			AbstractPiece[] blackPieces, MoveLogger ml, Player whitePlayer, Player blackPlayer,
			boolean isControllingWhite)
	{
		this.gameType = gameType;
		this.isWhiteTurn = isWhiteTurn;
		this.isControllingWhite = isControllingWhite;

		whitePieceSaveables = new PieceSaveable[16];
		blackPieceSaveables = new PieceSaveable[16];

		for (int i = 0; i < 16; i++)
		{
			whitePieceSaveables[i] = new PieceSaveable(whitePieces[i]);
			blackPieceSaveables[i] = new PieceSaveable(blackPieces[i]);
		}

		mls = new MoveLoggerSaveable(ml);

		this.whitePlayerSaveable = new PlayerSaveable(whitePlayer);
		this.blackPlayerSaveable = new PlayerSaveable(blackPlayer);
	}

	// ****************************Saveable ****************************/

	// deserialize by calling getPiece() to build AbstractPiece[]
	public void deserialize(InputStream in) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(in);

		gameType = dataIn.readInt();

		isWhiteTurn = dataIn.readBoolean();

		isControllingWhite = dataIn.readBoolean();

		PieceSaveable ps;
		for (int i = 0; i < 16; i++)
		{
			ps = new PieceSaveable(_game);
			ps.deserialize(in);
			whitePieces[i] = ps.getPiece();
		}

		for (int i = 0; i < 16; i++)
		{
			ps = new PieceSaveable(_game);
			ps.deserialize(in);
			blackPieces[i] = ps.getPiece();
		}
		mls.deserialize(dataIn);
		_ml = mls.getMoveLogger();

		whitePlayerSaveable.deserialize(in);
		blackPlayerSaveable.deserialize(in);

		whitePlayer = whitePlayerSaveable.getPlayer();
		blackPlayer = blackPlayerSaveable.getPlayer();

	}

	public void serialize(OutputStream out) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(out);

		dataOut.writeInt(gameType);

		dataOut.writeBoolean(isWhiteTurn);

		dataOut.writeBoolean(isControllingWhite);

		for (int i = 0; i < 16; i++)
		{
			whitePieceSaveables[i].serialize(dataOut);
		}

		for (int i = 0; i < 16; i++)
		{
			blackPieceSaveables[i].serialize(dataOut);
		}

		mls.serialize(out);

		whitePlayerSaveable.serialize(out);
		blackPlayerSaveable.serialize(out);

		out.flush();
	}

}
