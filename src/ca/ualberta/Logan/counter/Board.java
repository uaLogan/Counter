package ca.ualberta.Logan.counter;

import java.util.ArrayList;


public class Board
{
	private int total;
	private ArrayList<Counter> counters;
	
	public Board()
	{
		super();
		total = 0;
		counters = new ArrayList<Counter>();
	}
	
	public Board(int total, ArrayList<Counter> counters)
	{
		super();
		this.total = total;
		this.counters = counters;
	}
	
	public void addCounter()
	{
		Counter counter = new Counter();
		ArrayList<Counter> list = this.getCounters();
		list.add(counter);
	}

	public void addCounter(String name)
	{
		Counter counter = new Counter(name);
		ArrayList<Counter> list = this.getCounters();
		list.add(counter);
	}
	
	public void addCounter(String name, long count, long id, ArrayList<Entry> entries)
	{
		Counter counter = new Counter(name, count, id, entries);
		ArrayList<Counter> list = this.getCounters();
		list.add(counter);
	}
	
	public void deleteCounter(long id)
	{
		ArrayList<Counter> list = this.getCounters();
		for(Counter c : list)
			if(c.getId() == id)
				list.remove(c);
	}
	
	public void renameCounter(String name, long id)
	{
		ArrayList<Counter> list = this.getCounters();
		for(Counter c : list)
			if(c.getId() == id)
				c.setName(name);
	}
	
	public void saveAll()
	{
		
	}
	
	public void loadAll()
	{
		
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
	
}
