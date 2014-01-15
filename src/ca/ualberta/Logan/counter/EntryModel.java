package ca.ualberta.Logan.counter;

import java.util.Date;


public class EntryModel
{
private Date timestamp;

	public EntryModel(Date timestamp)
	{
		super();
		this.timestamp = timestamp;
	}
	
	public EntryModel()
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
