package ca.ualberta.Logan.counter;

import java.util.ArrayList;

//the Counter class stores a list of counts/clicks (class Entry)
//also stores a name that is used by the user to organize counters
//and an id that is used internally to organize counters
public class Counter
{
	private String name;
	private long count;
	private int id;
	private ArrayList<Entry> entries;

	public Counter(int id)
	{
		super();
		this.name = "New";
		this.count = 0;
		this.entries = new ArrayList<Entry>();
		this.id = id;
	}
	
	public Counter(String name, int id)
	{
		super();
		this.name = name;
		this.count = 0;
		this.entries = new ArrayList<Entry>();
		this.id = id;
	}

	public Counter(String name, long count, int id, ArrayList<Entry> entries)
	{
		super();
		this.name = name;
		this.count = count;
		this.id = id;
		this.entries = entries;
	}
	
	//add a new Entry object to the list
	protected void addEntry()
	{
		Entry entry = new Entry();
		ArrayList<Entry> list = this.getEntries();
		list.add(entry);
	}
	
	//new count/click
	public long increment()
	{
		this.addEntry();
		count++;
		return count;
	}
	
	//remove all Entry objects
	protected void clearEntries()
	{
		ArrayList<Entry> list = this.getEntries();
		list.clear();
	}

	//reset the Counter completely
	public void reset()
	{
		this.clearEntries();
		count = 0;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	
	public long getCount()
	{
		return count;
	}
	
	
	public void setCount(long count)
	{
		this.count = count;
	}

	public ArrayList<Entry> getEntries()
	{
		return entries;
	}

	public void setEntries(ArrayList<Entry> entries)
	{
		this.entries = entries;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
