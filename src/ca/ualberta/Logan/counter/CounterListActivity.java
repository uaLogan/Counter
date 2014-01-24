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
	//inherited
	//private ListView countersList;
	//private Board board;
	//private Gson gson;
	
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
}
