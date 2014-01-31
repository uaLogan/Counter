package ca.ualberta.Logan.counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//sub activity that is launched when the "Stats" button on a counter is pressed
//implements four buttons for hourly, daily, weekly, and monthly statistics
//displays counts that occurred in each hour/day/week/month in a ListView
//loads data in onResume()
//loads to a Storage object
public class StatsActivity extends BaseActivity
{
	private Storage storage;
	
	private Button hourButton;
	private Button dayButton;
	private Button weekButton;
	private Button monthButton;
	private ListView statsList;
	
	ArrayList<String> statStrings = new ArrayList<String>();
	ArrayAdapter<String> adapter = null;
	CounterStats stats = null;

	int id = 0;
	
	//fetches counter ID as intent from CounterListActivity
	//sets up listeners for the four buttons
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats_activity);
		
		gson = new GsonBuilder().create();
		
		hourButton = (Button) findViewById(R.id.hourButton);
		dayButton = (Button) findViewById(R.id.dayButton);
		weekButton = (Button) findViewById(R.id.weekButton);
		monthButton = (Button) findViewById(R.id.monthButton);
		
		statsList = (ListView) findViewById(R.id.statsListView);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("COUNTER_ID", 0);
		
		UpdateListView();
		recoverData();
		
		stats = new CounterStats(storage.getCounter(id));
		
		hourButton.setOnClickListener(new View.OnClickListener()
		{
			//each listener simply gets a new list of statistic strings
			//and then refreshes the adapter using UpdateListView()
			@Override
			public void onClick(View v)
			{
				statStrings = stats.HourStats();
				UpdateListView();
			}
		});
		
		dayButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				statStrings = stats.DayStats();
				UpdateListView();
			}
		});
		
		weekButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				statStrings = stats.WeekStats();
				UpdateListView();
			}
		});
		
		monthButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				statStrings = stats.MonthStats();
				UpdateListView();
			}
		});
		
	}
	
	//refreshes the adapter using the current statStrings ArrayList
	protected void UpdateListView()
	{
		adapter = new ArrayAdapter<String>(this,R.layout.stats_list, statStrings);
		statsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	//loads the Storage object from file using gson
	protected Storage loadStorage()
	{
		Storage storage = null;
		
		try
		{ 
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);
			storage = gson.fromJson(in, Storage.class);
		}
		catch(Exception e)
		{
			storage = new Storage();
		}
		
		if(storage == null)
			storage = new Storage();
		
		return storage;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}
	
	//simply loads the Storage object
	@Override
	protected void recoverData()
	{
		storage = loadStorage();
		if(storage == null)
		{
			storage = new Storage();
		}
		else
		{
			stats = new CounterStats(storage.getCounter(id));
		}
	}

}
