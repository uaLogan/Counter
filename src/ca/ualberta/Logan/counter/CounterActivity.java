package ca.ualberta.Logan.counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CounterActivity extends Activity
{
	private static final String FILENAME = "counterBoard.sav";
	private ListView countersList;
	private Board board;
	private Gson gson;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		gson = new GsonBuilder().create();
		
		countersList = (ListView) findViewById(R.id.listView1);
		Button addButton = (Button) findViewById(R.id.add_id);
		
		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				board.addCounter();
				saveBoard(board);
			}
		});
		
		recoverBoard();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		recoverBoard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}
	
	private Board loadBoard()
	{
		Board b = null;
		
		try
		{ 
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);
			Storage storage = gson.fromJson(in, Storage.class);
			b = new Board(storage, this, countersList);
		}
		catch(Exception e)
		{
			b = new Board(this, countersList);
		}
		
		if(b == null)
			b = new Board(this, countersList);
		
		return b;
	}
	
	private void saveBoard(Board board)
	{
		FileOutputStream fos;
		
		Storage storage = new Storage(board);

		try
		{
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(storage, osw);
			osw.close();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void recoverBoard()
	{
		board = loadBoard();
		if(board == null)
			board = new Board(this, countersList);
		else
			board.refreshAdapter();
	}
}
