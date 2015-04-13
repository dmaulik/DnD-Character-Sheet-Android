package itp341.maulik.dipanwita.project.app.model;

import itp341.maulik.dipanwita.project.app.R;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CharacterAdapter extends ArrayAdapter<Character> {

	private final Context context;
	private final ArrayList<Character> charactersArrayList;

	public CharacterAdapter(Context context, ArrayList<Character> characters) {
		super(context, R.layout.character_row, characters);
		
		this.context = context;
		if (characters == null)
			this.charactersArrayList = new ArrayList<Character>();
		else
			this.charactersArrayList = characters;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.character_row, parent, false);

		TextView nameView = (TextView) rowView
				.findViewById(R.id.textCharacterRowName);
		TextView infoView = (TextView) rowView
				.findViewById(R.id.textCharacterRowInfo);

		nameView.setText(charactersArrayList.get(position).getName());
		infoView.setText(charactersArrayList.get(position).getSpecies() + " | "
				+ charactersArrayList.get(position).getCharClass() + " | "
				+ charactersArrayList.get(position).getLevel());

		return rowView;
	}
}