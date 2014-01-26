package ca.ualberta.Logan.counter;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ListView;

public class Board
{
	protected int total;
	protected ArrayList<Counter> counters;

	public Board(int total, ArrayList<Counter> counters) {
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
	
	public ArrayList<Counter> getCounters()
	{
		return counters;
	}

	public void setCounters(ArrayList<Counter> counters)
	{
		this.counters = counters;
	}
}
