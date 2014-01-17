package ca.ualberta.Logan.counter;

import java.util.Date;


public class Entry
{
private Date timestamp;

	public Entry(Date timestamp)
	{
		super();
		this.timestamp = timestamp;
	}
	
	public Entry()
	{
		super();
		this.timestamp = new Date();
	}
		
	public Date getTimestamp()
	{
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}

}
