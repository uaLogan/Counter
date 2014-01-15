package ca.ualberta.Logan.counter;

import java.util.ArrayList;


public class CounterModel
{
	private String name;
	private long count;
	private ArrayList<EntryModel> entries;

	public CounterModel(String name)
	{
		super();
		this.name = name;
		this.count = 0;
		this.entries = new ArrayList<EntryModel>();
	}

	public CounterModel(String name, Integer count, ArrayList<EntryModel> entries)
	{
		super();
		this.name = name;
		this.count = count;
		this.entries = entries;
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

	
	public ArrayList<EntryModel> getEntries()
	{
		return entries;
	}

	
	public void setEntries(ArrayList<EntryModel> entries)
	{
		this.entries = entries;
	}
	

}
