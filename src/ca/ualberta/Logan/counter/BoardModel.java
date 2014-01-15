package ca.ualberta.Logan.counter;

import java.util.ArrayList;


public class BoardModel
{
	private int total;
	private ArrayList<CounterModel> counters;
	
	public BoardModel()
	{
		super();
		total = 0;
		counters = new ArrayList<CounterModel>();
	}
	
	public BoardModel(int total, ArrayList<CounterModel> counters)
	{
		super();
		this.total = total;
		this.counters = counters;
	}

	
	public int getTotal()
	{
		return total;
	}

	
	public void setTotal(int total)
	{
		this.total = total;
	}

	
	public ArrayList<CounterModel> getCounters()
	{
		return counters;
	}

	
	public void setCounters(ArrayList<CounterModel> counters)
	{
		this.counters = counters;
	}
	
}
