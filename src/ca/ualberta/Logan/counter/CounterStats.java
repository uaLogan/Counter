package ca.ualberta.Logan.counter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
		
		//fair to assume that entries will already be sorted
		return stats.getListing();
	}
	
	public ArrayList<String> WeekStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		WeekStats stats = new WeekStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		//fair to assume that entries will already be sorted
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
	
	private class WeekStat extends BaseStat
	{
		private int weekof;
		
		public WeekStat(int weekof, int month, int year, int count)
		{
			super(month, year, count);
			this.weekof = weekof;
		}
		
		public int getWeekof()
		{
			return weekof;
		}

		public boolean isStat(int week, int month, int year)
		{
			if(month == this.month && year == this.year)
			{
				//is day in week?
				if(week == this.weekof)
					return true;
			}
			
			return false;
		}
	}

	interface BaseStats
	{
		public void addEntry(Entry entry);
		public ArrayList<String> getListing();
	}
	
	protected class MonthStats implements BaseStats
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
	
	protected class WeekStats implements BaseStats
	{
		private ArrayList<WeekStat> statsList;

		public WeekStats()
		{
			super();
			statsList = new ArrayList<WeekStat>();
		}
		
		public void addEntry(Entry entry)
		{
			Date date = entry.getTimestamp();
			
			int month = date.getMonth();
			int year = date.getYear();
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(entry.getTimestamp());
			int week = c.get(Calendar.WEEK_OF_YEAR);
			
			boolean found = false;
			
			for(WeekStat stat: statsList)
			{
				if(stat.isStat(week, month, year))
				{
					//inc
					stat.incStat();
					found = true;
					break;
				}
			}
			
			if(found == false)
			{
				//add new
				WeekStat newStat = new WeekStat(week, month, year, 1);
				statsList.add(newStat);
			}
		}
		
		public ArrayList<String> getListing()
		{
			String str = "";
			ArrayList<String> strings = new ArrayList<String>();
			
			//strings.add(Integer.toString(statsList.size()));
			
			for(WeekStat stat: statsList)
			{
				//determine first day of week
				Calendar c = Calendar.getInstance();
				c.clear();
				c.set(Calendar.WEEK_OF_YEAR, stat.getWeekof());
				c.set(Calendar.YEAR, stat.getYear());
				Date d = c.getTime();
				
				str = String.format("Week of %s %d %d: %d", formatMonth(stat.getMonth()), d.getDate(), stat.getYear() + 1900, stat.getCount());
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
