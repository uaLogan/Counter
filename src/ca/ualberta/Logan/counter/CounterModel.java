package ca.ualberta.Logan.counter;

import java.util.ArrayList;


public class CounterModel
{
	private String name;
	private long count;
	private long id;
	private ArrayList<EntryModel> entries;

	public CounterModel(String name)
	{
		super();
		this.name = name;
		this.count = 0;
		this.entries = new ArrayList<EntryModel>();
		// TODO: get new unused ID
	}

	public CounterModel(String name, long count, long id, ArrayList<EntryModel> entries)
	{
		super();
		this.name = name;
		this.count = count;
		this.id = id;
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
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	

}
