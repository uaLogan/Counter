package ca.ualberta.Logan.counter;

import java.util.ArrayList;
import java.util.Arrays;

//the Counter class stores a list of counts/clicks (class Entry)
//also stores a name that is used by the user to organize counters
//and an id that is used internally to organize counters

//the Board class stores a list of all counters (class Counter)
//provides a method for generating new unique counter ID's
public class Board
{
	protected int total;
	protected ArrayList<Counter> counters;

	public Board(int total, ArrayList<Counter> counters) {
		super();
		this.total = total;
		this.counters = counters;
	}
	
	//returns a new counter ID that is unique to all ID's used by the stored counters
	protected int newUniqueId()
	{
		ArrayList<Integer> usedId = new ArrayList<Integer>();
		
		//gets ArrayList of all ID's
		for(Counter c: counters)
		{
			usedId.add(c.getId());
		}
		
		//gets array of all ID's
		Integer arr[] = usedId.toArray(new Integer[0]);
		
		//find the lowest unused ID >= 0
		
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
