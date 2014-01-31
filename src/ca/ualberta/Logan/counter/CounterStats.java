package ca.ualberta.Logan.counter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//a class that provides statistical data for a single Counter object which it aggregates
//returns hourly/daily/weekly/monthly stats as an Arraylist of strings
@SuppressWarnings("deprecation")
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

	//get monthly stats
	public ArrayList<String> MonthStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		MonthStats stats = new MonthStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		//fair to assume that entries will already be sorted
		return stats.getListing();
	}
	
	//get weekly stats
	public ArrayList<String> WeekStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		WeekStats stats = new WeekStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		//fair to assume that entries will already be sorted
		return stats.getListing();
	}
	
	//get daily stats
	public ArrayList<String> DayStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		DayStats stats = new DayStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		//fair to assume that entries will already be sorted
		return stats.getListing();
	}
	
	//get hourly stats
	public ArrayList<String> HourStats()
	{
		ArrayList<Entry> entries = counter.getEntries();
		HourStats stats = new HourStats();
		
		for(Entry entry : entries)
			stats.addEntry(entry);
		
		//fair to assume that entries will already be sorted
		return stats.getListing();
	}
	
	//convert a month integer to the first three letters of the month string
	protected String formatMonth(int month)
	{
		return (new DateFormatSymbols().getMonths()[month]).substring(0, 3);
	}
	
	//base class for a single statistic
	//a statistic represents the number of counts for single period of time
	abstract class BaseStat
	{
		//all statistics objects will require a month and year
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

	//monthly statistic class
	private class MonthStat extends BaseStat
	{
		public MonthStat(int month, int year, int count)
		{
			super(month, year, count);
		}

		//compare this month to the argument month
		public boolean isStat(int month, int year)
		{
			if(month == this.month && year == this.year)
				return true;
			else
				return false;
		}
	}
	
	//weekly statistic class
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

		//compare this week to the argument week
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
	
	//daily statistic class
	private class DayStat extends BaseStat
	{
		private int day;
		
		public DayStat(int day, int month, int year, int count)
		{
			super(month, year, count);
			this.day = day;
		}
		
		public int getDay()
		{
			return day;
		}

		//compare this day to the argument day
		public boolean isStat(int day, int month, int year)
		{
			if(month == this.month && year == this.year)
			{
				if(day == this.day)
					return true;
			}
			
			return false;
		}
	}
	
	//hourly stat class
	private class HourStat extends BaseStat
	{
		private int day;
		private int hour;
		
		public HourStat(int hour, int day, int month, int year, int count)
		{
			super(month, year, count);
			this.day = day;
			this.hour = hour;
		}
		
		public int getDay()
		{
			return day;
		}
		
		public int getHour()
		{
			return hour;
		}

		//compare this hour to the argument hour
		public boolean isStat(int hour, int day, int month, int year)
		{
			if(month == this.month && year == this.year)
			{
				if(day == this.day && hour == this.hour)
					return true;
			}
			
			return false;
		}
	}

	//base class for a list of statistics (class BaseStat)
	//should allow for Entry objects to be processed
	//should be able to return statistics info as ArrayList of strings
	interface BaseStats
	{
		abstract public void addEntry(Entry entry);
		abstract public ArrayList<String> getListing();
	}
	
	//monthly stats list class
	protected class MonthStats implements BaseStats
	{
		private ArrayList<MonthStat> statsList;

		public MonthStats()
		{
			super();
			statsList = new ArrayList<MonthStat>();
		}
		
		//add a new entry
		//if it already exists in list, then increment that statistic
		//otherwise add a new statistic to the list
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
		
		//get the actual statistics info as an ArrayList of String
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
	
	//weekly stats list class
	protected class WeekStats implements BaseStats
	{
		private ArrayList<WeekStat> statsList;

		public WeekStats()
		{
			super();
			statsList = new ArrayList<WeekStat>();
		}
		
		//add a new entry
		//if it already exists in list, then increment that statistic
		//otherwise add a new statistic to the list
		public void addEntry(Entry entry)
		{
			Date date = entry.getTimestamp();
			
			int month = date.getMonth();
			int year = date.getYear();
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(entry.getTimestamp());
			c.setFirstDayOfWeek(Calendar.MONDAY);
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
		
		//get the actual statistics info as an ArrayList of String
		public ArrayList<String> getListing()
		{
			String str = "";
			ArrayList<String> strings = new ArrayList<String>();
			
			//strings.add(Integer.toString(statsList.size()));
			
			for(WeekStat stat: statsList)
			{
				//determine first day of week
				//Calendar c = Calendar.getInstance();
				//c.clear();
				//c.set(Calendar.YEAR, stat.getYear());
				//c.set(Calendar.WEEK_OF_YEAR, stat.getWeekof());
				//Date d = c.getTime();
				
				Date d = CounterStats.GetFirstDateOfWeek(stat.getWeekof(), stat.getYear());
				
				str = String.format("Week of %s %d %d: %d", formatMonth(d.getMonth()), d.getDate(), stat.getYear() + 1900, stat.getCount());
				strings.add(str);
			}

			return strings;
		}
	}
	
	//converts a WEEK_OF_YEAR attribute to the actual month and date
	static Date GetFirstDateOfWeek(int weekOfYear, int year)
	{
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year+1900);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		
		while(c.get(Calendar.DAY_OF_WEEK) != c.getFirstDayOfWeek())
		{
			c.add(Calendar.DATE, -1);
		}
		
		return c.getTime();
	}
	
	//daily stats list class
	protected class DayStats implements BaseStats
	{
		private ArrayList<DayStat> statsList;

		public DayStats()
		{
			super();
			statsList = new ArrayList<DayStat>();
		}
		
		//add a new entry
		//if it already exists in list, then increment that statistic
		//otherwise add a new statistic to the list
		public void addEntry(Entry entry)
		{
			Date date = entry.getTimestamp();
			
			int month = date.getMonth();
			int year = date.getYear();
			int day = date.getDate();
			
			boolean found = false;
			
			for(DayStat stat: statsList)
			{
				if(stat.isStat(day, month, year))
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
				DayStat newStat = new DayStat(day, month, year, 1);
				statsList.add(newStat);
			}
		}
		
		//get the actual statistics info as an ArrayList of String
		public ArrayList<String> getListing()
		{
			String str = "";
			ArrayList<String> strings = new ArrayList<String>();
			
			//strings.add(Integer.toString(statsList.size()));
			
			for(DayStat stat: statsList)
			{
				str = String.format("Day of %s %d %d: %d", formatMonth(stat.getMonth()), stat.getDay(), stat.getYear() + 1900, stat.getCount());
				strings.add(str);
			}

			return strings;
		}
	}
	
	//hourly stats list class
	protected class HourStats implements BaseStats
	{
		private ArrayList<HourStat> statsList;

		public HourStats()
		{
			super();
			statsList = new ArrayList<HourStat>();
		}
		
		//add a new entry
		//if it already exists in list, then increment that statistic
		//otherwise add a new statistic to the list
		public void addEntry(Entry entry)
		{
			Date date = entry.getTimestamp();
			
			int month = date.getMonth();
			int year = date.getYear();
			int day = date.getDate();
			int hour = date.getHours();
			
			boolean found = false;
			
			for(HourStat stat: statsList)
			{
				if(stat.isStat(hour, day, month, year))
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
				HourStat newStat = new HourStat(hour, day, month, year, 1);
				statsList.add(newStat);
			}
		}
		
		//get the actual statistics info as an ArrayList of String
		public ArrayList<String> getListing()
		{
			String str = "";
			ArrayList<String> strings = new ArrayList<String>();
			
			//strings.add(Integer.toString(statsList.size()));
			
			for(HourStat stat: statsList)
			{
				str = String.format("Hour %d of %s %d %d: %d", stat.getHour(), formatMonth(stat.getMonth()), stat.getDay(), stat.getYear() + 1900, stat.getCount());
				strings.add(str);
			}

			return strings;
		}
	}
}
