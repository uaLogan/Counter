package ca.ualberta.Logan.counter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

//Mostly adapted from http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
//Retrieved January 21, 2014

public class CounterAdapter extends ArrayAdapter<Counter>
{
	private Context context;

    public CounterAdapter(Context context, int textViewResourceId, ArrayList<Counter> items)
    {
        super(context, textViewResourceId, items);
        this.context = context;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.counters_list, null);
        }

        Counter item = getItem(position);
        
        if (item!= null)
        {
            //only one TextView
            TextView itemView = (TextView) view.findViewById(R.id.countersListText);
            if (itemView != null)
            {
                //format view
                itemView.setText(String.format("%s: %s", item.getName(), item.getCount()));
            }
            
            //Count! button
            Button goButton = (Button) view.findViewById(R.id.countersGo);
            if (goButton != null)
            {
            	//setup listener
            	goButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                    	((Activity)context).setResult(Activity.RESULT_OK);
                    	((Activity)context).finish();
                    }
                });
            }
            
            //Delete button
            Button deleteButton = (Button) view.findViewById(R.id.countersDelete);
            deleteButton.setTag(Integer.valueOf(position));
            if (deleteButton != null)
            {
            	//setup listener
            	deleteButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                    	Object tag = v.getTag();
                    	Integer buttonPos = (Integer)v.getTag();
                    	if(buttonPos == 2)
                    	{
                    	((Activity)context).setResult(Activity.RESULT_OK);
                    	((Activity)context).finish();
                    	}
                    }
                });
            }
         }
        
        view.setTag(Integer.valueOf(position));

        return view;
    }
}