package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.CharacterAdapter;
import itp341.maulik.dipanwita.project.app.model.Character;
import itp341.maulik.dipanwita.project.app.model.CharacterDataStore;
import itp341.maulik.dipanwita.project.app.model.Extra;
import itp341.maulik.dipanwita.project.app.model.ExtraDataStore;

import java.text.ParseException;
import java.util.ArrayList;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CharacterFragment extends ListFragment {

	public static final String TAG = CharacterFragment.class.getSimpleName();

	private ArrayList<Character> characters;
	private CharacterAdapter charAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");

		try {
			characters = CharacterDataStore.loadCharacters(getActivity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (characters == null)
			characters = new ArrayList<Character>();

		charAdapter = new CharacterAdapter(inflater.getContext(), characters);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "activityCreated");

		super.onActivityCreated(savedInstanceState);
		// make sure this survives orientation changes
		setRetainInstance(true);

		// if we're coming back from an orientation change we don't need to
		// reintialize data
		if (savedInstanceState == null) {

			setListAdapter(charAdapter);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d(TAG, "onListItemClick");

		Intent i = new Intent(getActivity(), AddEditCharacterActivity.class);
		i.putExtra(AddEditCharacterActivity.EXTRA_CHARACTER_OBJECT,
				characters.get(position));
		startActivityForResult(i, position);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult");

		super.onActivityResult(requestCode, resultCode, data);

		Character character = (Character) data
				.getSerializableExtra(AddEditCharacterActivity.EXTRA_CHARACTER_OBJECT);

		if (character != null) {
			characters.set(requestCode, character);
			charAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();

		CharacterDataStore.saveCharacters(characters, getActivity());
	}

	public ArrayList<Character> getCharacters() {
		return this.characters;
	}

	public CharacterAdapter getCharAdapter() {
		return this.charAdapter;
	}
}
