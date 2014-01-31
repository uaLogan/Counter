package ca.ualberta.Logan.counter;

import java.util.ArrayList;

//the Storage class stores a list of counters (class Counter)
//it is only for loading from and storing to file
//Storage objects are serialized to file using gson in order to save counter data
//  and to pass counter data between the different activities in the app
public class Storage
{
	private int total;
	private ArrayList<Counter> counters;
	
	//create a Storage from an existing set of counters
	public Storage(int total, ArrayList<Counter> counters)
	{
		super();
		this.total = total;
		this.counters = counters;
	}
	
	//initialize a Storage from a Board object
	//copies the Board for serialization
	public Storage(Board board)
	{
		super();
		this.total = board.getTotal();
		this.counters = board.getCounters();
	}
	
	//create a new, empty Storage
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
	
	//get one counter with a specific ID
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
