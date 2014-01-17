package ca.ualberta.Logan.counter;

import java.util.ArrayList;


public class Counter
{
	private String name;
	private long count;
	private long id;
	private ArrayList<Entry> entries;

	public Counter()
	{
		super();
		this.name = "Untitled";
		this.count = 0;
		this.entries = new ArrayList<Entry>();
		// TODO: get new unused ID
	}
	
	public Counter(String name)
	{
		super();
		this.name = name;
		this.count = 0;
		this.entries = new ArrayList<Entry>();
		// TODO: get new unused ID
	}

	public Counter(String name, long count, long id, ArrayList<Entry> entries)
	{
		super();
		this.name = name;
		this.count = count;
		this.id = id;
		this.entries = entries;
	}
	
	public void addEntry()
	{
		Entry entry = new Entry();
		ArrayList<Entry> list = this.getEntries();
		list.add(entry);
	}
	
	public void increment()
	{
		this.addEntry();
	}
	
	public void clearEntries()
	{
		ArrayList<Entry> list = this.getEntries();
		list.clear();
	}

	public void reset()
	{
		this.clearEntries();
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
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	
	public String toString ()
	{
		String res = this.getName() + " - " + this.getCount();
		return res;
	}

}
