package ca.ualberta.Logan.counter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CounterActivity extends Activity
{
	private static final String FILENAME = "counterEntries.sav";
	private ListView countersList;
	private Board board;
	
	public ArrayAdapter<Counter> adapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		board = new Board();
		board.loadAll();
		
		countersList = (ListView) findViewById(R.id.listView1);
		
		Button addButton = (Button) findViewById(R.id.add_id);
		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				board.addCounter();
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		adapter = new ArrayAdapter<Counter>(this,R.layout.counters_list, board.getCounters());
		countersList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}

}
