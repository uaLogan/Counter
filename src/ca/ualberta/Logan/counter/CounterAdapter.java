package ca.ualberta.Logan.counter;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;

//Mostly adapted from http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
//Retrieved January 21, 2014

public class CounterAdapter extends ArrayAdapter<Counter> implements OnClickListener
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
            	
            	//no id
                itemView.setText(String.format("%s: %s", item.getName(), item.getCount()));
                
                //show id
            	//itemView.setText(String.format("%s | %s: %s", Integer.toString(item.getId()), item.getName(), item.getCount()));
            }
            
            //Count! button
            Button goButton = (Button) view.findViewById(R.id.countersGo);
            goButton.setTag(Integer.valueOf(position));
            if (goButton != null)
            {
            	//setup listener
            	goButton.setOnClickListener(this);
            }
            
            //Stats button
            Button statsButton = (Button) view.findViewById(R.id.countersStats);
            statsButton.setTag(Integer.valueOf(position));
            if (statsButton != null)
            {
            	//setup listener
            	statsButton.setOnClickListener(this);
            }
            
            //Delete button
            Button deleteButton = (Button) view.findViewById(R.id.countersDelete);
            deleteButton.setTag(Integer.valueOf(position));
            if (deleteButton != null)
            {
            	//setup listener
            	deleteButton.setOnClickListener(this);
            }
            
            //Rename button
            Button renameButton = (Button) view.findViewById(R.id.countersRename);
            renameButton.setTag(Integer.valueOf(position));
            if (renameButton != null)
            {
            	//setup listener
            	renameButton.setOnClickListener(this);
            }
         }
        
        view.setTag(Integer.valueOf(position));

        return view;
    }
    
	@Override
	public void onClick(View v)
	{
		final Integer buttonPos;
		Counter item = null;
		Intent intent = null;
		
		switch(v.getId())
		{
			case R.id.countersGo:
            	buttonPos = (Integer)v.getTag();
            	item = getItem(buttonPos);
            	intent = new Intent(context, CounterActivity.class);
            	intent.putExtra("COUNTER_ID", Integer.valueOf(item.getId()));
            	context.startActivity(intent);
				break;
			case R.id.countersStats:
            	buttonPos = (Integer)v.getTag();
            	item = getItem(buttonPos);
            	intent = new Intent(context, StatsActivity.class);
            	intent.putExtra("COUNTER_ID", Integer.valueOf(item.getId()));
            	context.startActivity(intent);
				break;
			case R.id.countersRename:
				buttonPos = (Integer)v.getTag();
				
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Rename");
				alert.setMessage("Enter new name:");
				
				final EditText input = new EditText(context);
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				InputFilter filters[] = new InputFilter[1];
				filters[0] = new InputFilter.LengthFilter(15);
				input.setFilters(filters);
				alert.setView(input);
				
				alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						  String value = input.getText().toString();
						  Counter item = getItem(buttonPos);
						  item.setName(value);
						  notifyDataSetChanged();
						  ((CounterListActivity)context).saveBoard();
					}
				});
				
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					  public void onClick(DialogInterface dialog, int whichButton)
					  {
					  }
				});
				
				alert.show();
				
				break;
			case R.id.countersDelete:
            	buttonPos = (Integer)v.getTag();
            	this.remove(this.getItem(buttonPos));
            	this.notifyDataSetChanged();
            	((CounterListActivity)context).saveBoard();
				break;
		}
	}
}