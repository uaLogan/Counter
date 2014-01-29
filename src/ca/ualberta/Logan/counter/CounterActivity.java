package ca.ualberta.Logan.counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//sub activity that is launched when the "Count" button on a counter is clicked
//implements a button that increases count by 1, and a button which resets the count
//shows user the current count
//saves data after each increment/reset
//loads data in onResume()
//saves from and loads to a Storage object
public class CounterActivity extends BaseActivity
{
	private Storage storage;
	
	private Button countButton;
	private Button resetButton;
	private TextView titleText;
	//private TextView currText;
	
	int id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_counter);
		
		gson = new GsonBuilder().create();
		
		countButton = (Button) findViewById(R.id.countButton);
		resetButton = (Button) findViewById(R.id.resetButton);
		titleText = (TextView) findViewById(R.id.titleTextView);
		//currText = (TextView) findViewById(R.id.currTextView);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("COUNTER_ID", 0);
		
		countButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Counter item = storage.getCounter(id);
				long newCount = item.increment();
				saveStorage(storage);
				setCurrCount(newCount);
			}
		});
		
		resetButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Counter item = storage.getCounter(id);
				item.reset();
				saveStorage(storage);
				setCurrCount(0);
			}
		});
		
		recoverData();
	}
	
	protected void saveStorage(Storage storage)
	{
		FileOutputStream fos;

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
	
	protected void recoverData()
	{
		storage = loadStorage();
		if(storage == null)
			storage = new Storage();
		else
		{
			Counter item = storage.getCounter(id);
			titleText.setText(String.format("%s - Counter", item.getName()));
			setCurrCount(item.getCount());
		}
	}
	
	private void setCurrCount(long count)
	{
		//currText.setText(String.format("Count: %s", Long.toString(count)));
		countButton.setText(String.format("Count: %s", Long.toString(count)));
	}
}
