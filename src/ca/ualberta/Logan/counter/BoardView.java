package ca.ualberta.Logan.counter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import android.content.Context;
import android.widget.ListView;

//the BoardView class extends the Board with methods related to
//  displaying the list of counters in the CounterListActivity
//  responding to requests to add, delete, or rename counters
public class BoardView extends Board
{
	//inherits ArrayList<Counter> counters 
	private CounterAdapter adapter; //subclassed ArrayAdapter
	private Context context; //main activity's context
	private ListView countersList;
	
	//build BoardView from Storage object
	public BoardView(Storage storage, Context context, ListView countersList)
	{
		this(storage.getTotal(), storage.getCounters(), context, countersList);
	}

	//create new empty BoardView
	public BoardView(Context context, ListView countersList)
	{
		this(0, new ArrayList<Counter>(), context, countersList);
	}
	
	//build BoardView from existing list of counters
	public BoardView(int total, ArrayList<Counter> counters, Context context, ListView countersList)
	{
		super(total, counters);
		this.context = context;
		this.countersList = countersList;
		this.adapter = new CounterAdapter(this.context,R.layout.counters_list, this.counters);
		this.countersList.setAdapter(this.adapter);
	}
	
	//adds a new counter and updates the ListView
	public void addCounter(String name, long count, int id, ArrayList<Entry> entries)
	{
		Counter counter = new Counter(name, count, id, entries);
		adapter.add(counter);
		adapter.notifyDataSetChanged();
		total++;
		
		this.sortCounters();
	}
	
	//adds new counter without a name provided
	public void addCounter()
	{
		//default name is "New"
		this.addCounter("New");
	}

	//adds an empty counter with a specified name
	public void addCounter(String name)
	{
		int newId = newUniqueId();
		ArrayList<Entry> list = new ArrayList<Entry>();
		this.addCounter(name, 0, newId, list);
	}
	
	//refresh the ListView's adapter on the counter list by creating a new adapter
	//not extremely efficient but there isn't really a better way to do this
	public void refreshAdapter()
	{
		this.adapter = new CounterAdapter(this.context,R.layout.counters_list, this.counters);
		this.countersList.setAdapter(this.adapter);
	}
	
	//deletes a counter with the specified ID and updates the ListView
	public void deleteCounter(int id)
	{
		ArrayList<Counter> list = this.getCounters();
		for(Counter c : list)
			if(c.getId() == id){
				list.remove(c);
				total--;
			}
		
		adapter.notifyDataSetChanged();
		//TODO: this won't actually do anything
	}
	
	//renames a counter with the specified ID and updates the ListView
	public void renameCounter(String name, int id)
	{
		ArrayList<Counter> list = this.getCounters();
		for(Counter c : list)
			if(c.getId() == id)
				c.setName(name);
		
		adapter.notifyDataSetChanged();
		//TODO: this won't actually do anything
	}

	public CounterAdapter getAdapter()
	{
		return adapter;
	}

	public void setAdapter(CounterAdapter adapter)
	{
		this.adapter = adapter;
	}
	
	public Context getContext()
	{
		return context;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	//sorts counters in ascending order and updates the ListView
	public void sortCounters()
	{
		ArrayList<Counter> arraylist = this.getCounters();
		Counter list[] = arraylist.toArray(new Counter[0]);
		
		Arrays.sort(list, new CounterComparator());
		
		arraylist = new ArrayList<Counter>(Arrays.asList(list));
		this.setCounters(arraylist);
		
		this.refreshAdapter();
	}
	
	//comparator used for counter sorting, simply compares the total count
	class CounterComparator implements Comparator<Counter>
	{
		@Override
		public int compare(Counter a, Counter b)
		{
			return (int)(a.getCount()) - (int)(b.getCount());
		}
	}
}
