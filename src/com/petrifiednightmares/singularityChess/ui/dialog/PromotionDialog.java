package com.petrifiednightmares.singularityChess.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.petrifiednightmares.singularityChess.GameDrawingPanel;
import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.logic.Game;
import com.petrifiednightmares.singularityChess.ui.GameUI;
import com.petrifiednightmares.singularityChess.ui.SUI;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class PromotionDialog extends HoverDialog
{
	Game _game;
	PromotionTile queenTile, knightTile;

	public PromotionDialog(GameDrawingPanel gdp, Game game, GameUI gui)
	{
		super(gdp,gui, "Promotion", (SUI.HEIGHT / 100) * 30, (SUI.WIDTH / 100) * 10, SUI.WIDTH - 2
				* ((SUI.WIDTH / 100) * 10), SUI.HEIGHT - 2 * (SUI.HEIGHT / 100) * 30);

		this._game = game;

		int tileWidth = (int) (_width * 0.35);
		int tileTopMargin = (_height - tileWidth) / 2;
		int tileMargin = (_width - tileWidth * 2) / 6;
		int tileLeftMargin = tileMargin * 2;
		int _tileTop = _top + tileTopMargin;

		Bitmap queenBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.queen : R.drawable.black_queen,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		Bitmap knightBitmap = SingularBitmapFactory.buildScaledBitmap(gdp.getResources(),
				game.isWhiteTurn() ? R.drawable.knight : R.drawable.black_knight,
				(int) (tileWidth * 0.7), (int) (tileWidth * 0.64));

		queenTile = new PromotionTile(gdp, "Queen", queenBitmap, _tileTop, _left + tileLeftMargin,
				tileWidth, tileWidth);
		knightTile = new PromotionTile(gdp, "Knight", knightBitmap, _tileTop, _left + _width / 2
				+ tileMargin, tileWidth, tileWidth);
	}

	public synchronized void display()
	{
		super.display();
		queenTile.show();
		queenTile.redraw();

		knightTile.show();
		knightTile.redraw();
		redraw();
	}

	public synchronized void hide()
	{
		super.hide();
		queenTile.hide();
		queenTile.redraw();

		knightTile.hide();
		knightTile.redraw();
	}

	public void onDraw(Canvas c)
	{
		super.onDraw(c);
		queenTile.onDraw(c);
		knightTile.onDraw(c);
	}

	public boolean onClick(int x, int y)
	{
		if (super.onClick(x, y))
		{
			_gui.closePrompt();
			return true;
		}
		return false;
	}
}