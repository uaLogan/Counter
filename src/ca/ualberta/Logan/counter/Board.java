package ca.ualberta.Logan.counter;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ListView;


public class Board
{
	private int total;
	private ArrayList<Counter> counters;
	private CounterAdapter adapter;
	private Context context;
	private ListView countersList;

	public Board(Context context, ListView countersList)
	{
		this(0, new ArrayList<Counter>(), context, countersList);
	}
	
	public Board(int total, ArrayList<Counter> counters, Context context, ListView countersList)
	{
		super();
		this.total = total;
		this.counters = counters;
		this.context = context;
		this.countersList = countersList;
		
		Counter c = new Counter("aaaa");
		this.counters.add(c);
		
		this.adapter = new CounterAdapter(this.context,R.layout.counters_list, this.counters);
		this.countersList.setAdapter(this.adapter);
	}
	
	public void addCounter(String name, long count, long id, ArrayList<Entry> entries)
	{
		Counter counter = new Counter(name, count, id, entries);
		adapter.add(counter);
		adapter.notifyDataSetChanged();
		total++;
	}
	
	public void addCounter()
	{
		this.addCounter("Untitled");
	}

	public void addCounter(String name)
	{
		long newId = 0; //TODO: get lowest unused ID
		ArrayList<Entry> list = new ArrayList<Entry>();
		this.addCounter(name, 0, newId, list);
	}
	
	public void refreshAdapter()
	{
		this.adapter = new CounterAdapter(this.context,R.layout.counters_list, this.counters);
		this.countersList.setAdapter(this.adapter);
	}
	
	public void deleteCounter(long id)
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
	
	public void renameCounter(String name, long id)
	{
		ArrayList<Counter> list = this.getCounters();
		for(Counter c : list)
			if(c.getId() == id)
				c.setName(name);
		
		adapter.notifyDataSetChanged();
		//TODO: this won't actually do anything
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public void setTotal(int total)
	{
		this.total = total;
	}
	
	public ArrayList<Counter> getCounters()
	{
		return counters;
	}

	public void setCounters(ArrayList<Counter> counters)
	{
		this.counters = counters;
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
}
