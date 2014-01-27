package ca.ualberta.Logan.counter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;


public class CounterStats
{
	private Counter counter;

	public CounterStats(Counter counter)
	{
		super();
		this.counter = counter;
	}
	
	public Counter getCounter()
	{
		return counter;
	}

	public ArrayList<String> MonthStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		MonthStats stats = new MonthStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		return stats.getListing();
	}
	
	protected String formatMonth(int month)
	{
		return (new DateFormatSymbols().getMonths()[month]).substring(0, 3);
	}
	
	abstract class BaseStat
	{
		protected int month;
		protected int year;
		protected int count;
		
		public BaseStat(int month, int year, int count)
		{
			super();
			this.month = month;
			this.year = year;
			this.count = count;
		}
		
		public int getMonth()
		{
			return month;
		}
		
		public int getYear()
		{
			return year;
		}

		public int getCount()
		{
			return count;
		}

		public void incStat()
		{
			count++;
		}
	}

	private class MonthStat extends BaseStat
	{
		public MonthStat(int month, int year, int count)
		{
			super(month, year, count);
		}

		public boolean isStat(int month, int year)
		{
			if(month == this.month && year == this.year)
				return true;
			else
				return false;
		}
	}

	interface BaseStats
	{
		public void addEntry(Entry entry);
		public void sortAll();
		public ArrayList<String> getListing();
	}
	
	protected class MonthStats
	{
		private ArrayList<MonthStat> statsList;

		public MonthStats()
		{
			super();
			statsList = new ArrayList<MonthStat>();
		}
		
		public void addEntry(Entry entry)
		{
			int month = entry.getTimestamp().getMonth();
			int year = entry.getTimestamp().getYear();
			
			boolean found = false;
			
			for(MonthStat monthstat: statsList)
			{
				if(monthstat.isStat(month, year))
				{
					//inc
					monthstat.incStat();
					found = true;
					break;
				}
			}
			
			if(found == false)
			{
				//add new
				MonthStat newMonth = new MonthStat(month, year, 1);
				statsList.add(newMonth);
			}
		}
		
		public void sortAll()
		{
			
		}
		
		public ArrayList<String> getListing()
		{
			String str = "";
			ArrayList<String> strings = new ArrayList<String>();
			
			//strings.add(Integer.toString(statsList.size()));
			
			for(MonthStat stat: statsList)
			{
				str = String.format("Month of %s %d: %d", formatMonth(stat.getMonth()), stat.getYear() + 1900, stat.getCount());
				strings.add(str);
			}

			return strings;
		}
	}
}



/* struct{month, year, count} monthStats
 * vector<monthStats>
 * 
 * 
 * for each entry
 * is in list?
 * if yes:
 *   inc
 * if no:
 *   add new
 * 
 * then qsort
 * 
 * 
 * 
 * 
 * 
 * 
 */
