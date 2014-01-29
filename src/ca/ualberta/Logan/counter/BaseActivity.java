package ca.ualberta.Logan.counter;

import com.google.gson.Gson;

import android.app.Activity;

abstract public class BaseActivity extends Activity
{
	protected static final String FILENAME = "counterBoard.sav";
	protected Gson gson;
	
	@Override
	protected void onResume()
	{
		super.onResume();
		recoverData();
	}
	
	abstract protected void recoverData();
}
