package itp341.maulik.dipanwita.project.app.model;

import itp341.maulik.dipanwita.project.app.R;

import java.util.ArrayList; 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class ExtraAdapter extends ArrayAdapter<Extra> {
 
        private final Context context;
        private final ArrayList<Extra> extrasArrayList;
 
        public ExtraAdapter(Context context, ArrayList<Extra> extras) {
 
            super(context, R.layout.extra_row, extras);
 
            this.context = context;
            
            if(extras == null)
            	this.extrasArrayList = new ArrayList<Extra>();
            else
            	this.extrasArrayList = extras;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            View rowView = inflater.inflate(R.layout.extra_row, parent, false);
 
            TextView extraView = (TextView) rowView.findViewById(R.id.textExtraRowName);
            TextView typeView = (TextView) rowView.findViewById(R.id.textExtraRowType);
            TextView descView = (TextView) rowView.findViewById(R.id.textExtraRowDescription);
 
            extraView.setText(extrasArrayList.get(position).getName());
            typeView.setText(extrasArrayList.get(position).getType());
            descView.setText(extrasArrayList.get(position).getDescription());
            
            return rowView;
        }
}