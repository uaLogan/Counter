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
	
	protected void UpdateListView()
	{
		adapter = new ArrayAdapter<String>(this,R.layout.stats_list, statStrings);
		statsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
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
