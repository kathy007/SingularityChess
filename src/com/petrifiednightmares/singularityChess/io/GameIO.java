package com.petrifiednightmares.singularityChess.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class GameIO
{

	private static enum StorageOption
	{
		STDOUT, SQLITE, FILE, NETWORK
	};

	private static StorageOption _storageOption = StorageOption.FILE;

	// determine where the shiet is saved
	private static String _fileName = "game_state";

	private static Context _context;

	public static void setContext(Context context)
	{
		_context = context;
	}

	public static void intentionSaveGame()
	{
		_fileName = "game_state";
	}

	// for saving
	public static OutputStream getOutputStream() throws FileNotFoundException
	{
		switch (_storageOption)
		{
		case STDOUT:
			return new BufferedOutputStream(System.out);
		case FILE:
			FileOutputStream fos = new FileOutputStream(new File(
					_context.getExternalFilesDir(null), _fileName));
			// FileOutputStream fos = _context.openFileOutput(_fileName,
			// Context.MODE_PRIVATE);
			return new BufferedOutputStream(fos);
		case SQLITE:
			return null;
		default:
			return null;
		}
	}

	// for reading
	public static InputStream getInputStream() throws FileNotFoundException
	{
		switch (_storageOption)
		{
		case STDOUT:
			return null;
		case FILE:
			FileInputStream fos = new FileInputStream(new File(
					_context.getExternalFilesDir(null), _fileName));
			return new BufferedInputStream(fos);
		case SQLITE:
			return null;
		default:
			return null;
		}

	}
}