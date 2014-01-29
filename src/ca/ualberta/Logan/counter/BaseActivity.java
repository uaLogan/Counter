package ca.ualberta.Logan.counter;

import com.google.gson.Gson;

import android.app.Activity;

//base class for all Activity classes used in this app
//factors out a few things
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
