package ca.ualberta.Logan.counter;

import java.util.ArrayList;

public class Storage
{
	private int total;
	private ArrayList<Counter> counters;
	
	public Storage(int total, ArrayList<Counter> counters)
	{
		super();
		this.total = total;
		this.counters = counters;
	}
	
	public Storage(Board board)
	{
		super();
		this.total = board.getTotal();
		this.counters = board.getCounters();
	}
	
	public Storage()
	{
		super();
		this.total = 0;
		this.counters = new ArrayList<Counter>();
	}
	
	public int getTotal()
	{
		return total;
	}

	public ArrayList<Counter> getCounters()
	{
		return counters;
	}
	
	public Counter getCounter(int id)
	{
		for(Counter c : counters)
		{
			if(c.getId() == id)
				return c;
		}
		
		return null;
	}
}
