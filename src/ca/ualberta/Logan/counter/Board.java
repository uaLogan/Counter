package ca.ualberta.Logan.counter;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	protected int newUniqueId()
	{
		ArrayList<Integer> usedId = new ArrayList<Integer>();
		
		for(Counter c: counters)
		{
			usedId.add(c.getId());
		}
		
		Integer arr[] = usedId.toArray(new Integer[0]);
		
		//GOAL: find lowest non-negative id that is not present in arr
		//ALGO: sort arr, then iterate it until (curr > prev + 1)
		
		if(arr.length == 0)
			return 0;
		
		Arrays.sort(arr);
		
		if(arr[0] != 0)
			return 0;
		
		//arr is an array of ascending integers, arr[0] = 0
		int prev = 0;
		for(int i: arr)
		{
			if(i > (prev + 1))
				return prev + 1;
			
			prev = i;
		}
		
		//if reached here, array is a perfect sequence 0, 1, 2, ...
		//prev is highest item
		
		return prev + 1;
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
