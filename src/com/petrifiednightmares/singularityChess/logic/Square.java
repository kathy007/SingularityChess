package com.petrifiednightmares.singularityChess.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.GameException;
import com.petrifiednightmares.singularityChess.geom.Circle;
import com.petrifiednightmares.singularityChess.geom.ComplexShape;
import com.petrifiednightmares.singularityChess.geom.Rectangle;
import com.petrifiednightmares.singularityChess.pieces.AbstractPiece;

public class Square
{
	private Square[] _corners;
	private Square[] _sides;
	private final char file; // rooks are on file a and h
	private final int rank; // white on 1 black on 8
	static Bitmap _squareBitMap;
	private static Canvas _squareCanvas;
	private boolean _isWhite;
	private Paint _paint;

	private AbstractPiece piece = null;
	private ComplexShape _shape;
	private boolean _highlighted;

	public Square(char file, int rank)
	{
		this.file = file;
		this.rank = rank;
	}

	public Square(Square[] corners, Square[] sides, char file, int rank)
	{
		this._corners = corners;
		this._sides = sides;
		this.file = file;
		this.rank = rank;
	}

	public void highlight()
	{
		_highlighted = true;
	}

	public void unhighlight()
	{
		_highlighted = false;
	}

	public void setUpBitMap()
	{

		// if parity is the same, it's white
		_isWhite = ((file - 'a') + 1) % 2 == rank % 2;
		_paint = GameDrawingPanel.darkPaint;
		if (_isWhite)
		{
			_paint = GameDrawingPanel.lightPaint;
		}
		_paint.setFlags(Paint.ANTI_ALIAS_FLAG);

		setupShape();

		// Create bitmaps if don't exist yet

		if (Square._squareBitMap == null)
			Square._squareBitMap = Bitmap.createBitmap(GameDrawingPanel.WIDTH,
					GameDrawingPanel.HEIGHT, Bitmap.Config.ARGB_8888);
		if (Square._squareCanvas == null)
			Square._squareCanvas = new Canvas(_squareBitMap);

		// draw onto bitmap
		Square._squareCanvas.save();

		_shape.clip(Square._squareCanvas);

		Square._squareCanvas.drawCircle(GameDrawingPanel.WIDTH / 2, GameDrawingPanel.HEIGHT / 2,
				(1 + fileOutwards() + rankOutwards()) * 12 * GameDrawingPanel.UNIT, _paint);

		Square._squareCanvas.restore();

	}

	private void setupShape()
	{
		_shape = new ComplexShape();

		Circle outterCircle = new Circle(GameDrawingPanel.WIDTH / 2, GameDrawingPanel.HEIGHT / 2,
				(1 + fileOutwards() + rankOutwards()) * 12 * GameDrawingPanel.UNIT);

		_shape.addInsideShape(outterCircle);

		Rectangle borderRect;

		if (file <= 'd')
		{
			borderRect = new Rectangle(GameDrawingPanel.WIDTH / 2 - ('d' - file + 1) * 12
					* GameDrawingPanel.UNIT, 0, GameDrawingPanel.WIDTH / 2 - ('d' - file) * 12
					* GameDrawingPanel.UNIT, GameDrawingPanel.HEIGHT);
		} else
		{
			borderRect = new Rectangle(GameDrawingPanel.WIDTH / 2 + (file - 'e') * 12
					* GameDrawingPanel.UNIT, 0, GameDrawingPanel.WIDTH / 2 + (file - 'e' + 1) * 12
					* GameDrawingPanel.UNIT, GameDrawingPanel.HEIGHT);
		}
		_shape.addInsideShape(borderRect);

		Circle innerCircle = new Circle(GameDrawingPanel.WIDTH / 2, GameDrawingPanel.HEIGHT / 2,
				(fileOutwards() + rankOutwards()) * 12 * GameDrawingPanel.UNIT);

		_shape.addOutsideShape(innerCircle);

		// Determines which side of the board the square is on
		Rectangle boardSideRect;
		if (rank > Board.boardRanks[file - 'a'] / 2 + 1)
		{
			boardSideRect = new Rectangle(0, 0, GameDrawingPanel.WIDTH, GameDrawingPanel.HEIGHT / 2);
			_shape.addInsideShape(boardSideRect);
		} else if (rank < Board.boardRanks[file - 'a'] / 2 + 1)
		{

			boardSideRect = new Rectangle(0, GameDrawingPanel.HEIGHT / 2, GameDrawingPanel.WIDTH,
					GameDrawingPanel.HEIGHT);
			_shape.addInsideShape(boardSideRect);
		}

	}

	private int fileOutwards()
	{
		if (file <= 'd')
			return 'd' - file;
		else
			return file - 'e';
	}

	private int rankOutwards()
	{

		if (rank >= Board.boardRanks[file - 'a'] / 2 + 1)
		{
			return rank - (Board.boardRanks[file - 'a'] / 2 + 1);
		} else
		{
			return Board.boardRanks[file - 'a'] / 2 + 1 - rank;
		}

	}

	private void drawPiece(Canvas c)
	{
		// if (piece != null)
		{
			// float textWidth =
			// GameDrawingPanel.piecePaint.measureText(piece.toString());
			// c.drawCircle(_shape.getX(), _shape.getY(),
			// 5,GameDrawingPanel.piecePaint);
			// c.drawText(piece.toString(), _shape.getX() - textWidth / 2,
			// _shape.getY(),
			// GameDrawingPanel.attackPaint);

			float textWidth = GameDrawingPanel.piecePaint.measureText(file + "" + rank);
			c.drawText(file + "" + rank, _shape.getX() - textWidth / 2, _shape.getY(),
					GameDrawingPanel.piecePaint);

			// if(file=='e' && rank ==4)
			// {
			// System.out.println("e4");
			// System.out.println(_shape.getX()+" "+_shape.getY());
			// }
		}

	}

	public void onDraw(Canvas c)
	{
		// TODO draw the pieces, if any
		drawPiece(c);

		if (_highlighted)
		{
			_paint = GameDrawingPanel.highlightPaint;
			if (piece != null)
			{
				_paint = GameDrawingPanel.attackPaint;
			}

			drawSquare(c);
		}
	}

	private void drawSquare(Canvas c)
	{
		c.save();

		_shape.clip(c);

		if (rank == Board.boardRanks[file - 'a'] / 2 + 1)
			c.drawCircle(GameDrawingPanel.WIDTH / 2, GameDrawingPanel.HEIGHT / 2,
					(1 + fileOutwards() + rankOutwards()) * 12 * GameDrawingPanel.UNIT, _paint);
		else if (rank > Board.boardRanks[file - 'a'] / 2 + 1)
			c.drawRect(0, 0, GameDrawingPanel.WIDTH, GameDrawingPanel.HEIGHT / 2, _paint);
		else
			c.drawRect(0, GameDrawingPanel.HEIGHT / 2, GameDrawingPanel.WIDTH,
					GameDrawingPanel.HEIGHT, _paint);

		c.restore();
	}

	public void removePiece()
	{
		this.piece = null;
	}

	public void addPiece(AbstractPiece piece)
	{
		this.piece = piece;
	}

	public AbstractPiece getPiece()
	{
		return this.piece;
	}

	public Square getNextSide(Square firstSide) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square s = _sides[i];
			if (s.equals(firstSide))
			{
				return _sides[(i + 2) % 4]; // might be null
			}
		}
		throw new GameException("given side square " + firstSide + " is not adjacent to this square" + this);
	}

	// This is for knights, it gets the elbow shaped ones.
	public Square[] getAdjacentSides(Square firstSide) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square s = _sides[i];
			if (s.equals(firstSide))
			{
				return new Square[] { _sides[(i + 1) % 4], _sides[(i - 1) % 4] };
			}
		}
		throw new GameException("given square side " + firstSide + " is not adjacent to this square " + this +".");
	}

	public Square getNextCorner(Square firstCorner) throws GameException
	{
		for (int i = 0; i < 4; i++)
		{
			Square c = _corners[i];
			if (c.equals(firstCorner))
			{
				return _corners[(i + 2) % 4]; // might be null
			}
		}
		throw new GameException("given corner " + firstCorner + " is not adjacent to this square" + this);
	}

	@Override
	public String toString()
	{
		return "Square: " + file + "" + rank;
	}

	public Square[] getCorners()
	{
		return _corners;
	}

	public Square[] getSides()
	{
		return _sides;
	}

	public char getFile()
	{
		return file;
	}

	public int getRank()
	{
		return rank;
	}

	public void setSides(Square[] sides)
	{
		this._sides = sides;
	}
	
	public void setCorners(Square[] corners)
	{
		this._corners = corners;
	}
}
