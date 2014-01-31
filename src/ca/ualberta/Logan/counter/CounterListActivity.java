package ca.ualberta.Logan.counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

//initial activity
//shows the user a list of counters in a ListView sorted by count
//implements button for adding new counters
//saves data after every operation that changes the list of counters
//loads data in onResume()
//saves from and loads to a BoardView object
//the ListView is controlled by a CounterAdapter in the BoardView object
public class CounterListActivity extends BaseActivity
{
	private BoardView board;
	private Gson gson;
	private ListView countersList;
	
	//sets up listener for the Add New button
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
			//add a new counter
			@Override
			public void onClick(View v)
			{
				board.addCounter();
				saveBoard(board);
			}
		});
		
		countersList.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		
		recoverData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}
	
	//loads the Board object from file using gson
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
		
		return b;
	}
	
	//saves the Board object to file using gson
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
	
	//no parameter to allow other classes (CounterAdapter specifically) to save
	public void saveBoard()
	{
		saveBoard(board);
	}
	
	//load the Board object and refresh the ListView
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
