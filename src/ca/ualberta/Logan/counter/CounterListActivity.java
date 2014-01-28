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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CounterListActivity extends BaseActivity
{
	private BoardView board;
	private Gson gson;
	private ListView countersList;
	
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
		
		countersList.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		
		countersList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
		    public void onItemClick(AdapterView parent, View v, int position, long id)
		    {    	
		    	setResult(RESULT_OK);
		        finish();
		    }
		});
		
		recoverData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}
	
	protected BoardView loadBoard()
	{
		BoardView b = null;
		
		try
		{ 
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);
			Storage storage = gson.fromJson(in, Storage.class);
			b = new BoardView(storage, this, countersList);
		}
		catch(Exception e)
		{
			b = new BoardView(this, countersList);
		}
		
		if(b == null)
			b = new BoardView(this, countersList);
		
		return b;
	}
	
	protected void saveBoard(BoardView board)
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
	
	public void saveBoard()
	{
		saveBoard(board);
	}
	
	protected void recoverData()
	{
		board = loadBoard();
		if(board == null)
			board = new BoardView(this, countersList);
		else
			board.refreshAdapter();
		
		board.sortCounters();
	}
}
