package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.Character;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditCharacterPowersFragment extends Fragment implements
		AddDialogFragment.AddDialogListener {

	public static final String TAG = AddEditCharacterPowersFragment.class
			.getSimpleName();
	public static final String FEATS = "feats";
	public static final String SKILLS = "skills";
	public static final String LANGUAGES = "languages";
	public static final String MAGIC = "magic";
	private static final String WARN_EMPTY_NAME = "Name cannot be empty!";

	Character character;
	HashMap<TableRow, Integer> feats;
	HashMap<TableRow, Integer> skills;
	HashMap<TableRow, Integer> languages;
	HashMap<TableRow, Integer> magic;

	TextView addFeat;
	TextView addSkill;
	TextView addLanguage;
	TextView addMagic;

	TableLayout featsTable;
	TableLayout skillsTable;
	TableLayout languagesTable;
	TableLayout magicTable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		if (savedInstanceState != null) {
			character = new Character();
			character.setFeats((ArrayList<String>) savedInstanceState
					.getSerializable(FEATS));
			character.setSkills((ArrayList<String>) savedInstanceState
					.getSerializable(SKILLS));
			character.setLanguages((ArrayList<String>) savedInstanceState
					.getSerializable(LANGUAGES));
			character.setMagic((ArrayList<String>) savedInstanceState
					.getSerializable(MAGIC));
		} else if (character == null) {
			character = AddEditCharacterActivity.character;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_edit_powers,
				container, false);

		featsTable = (TableLayout) view.findViewById(R.id.tableFeats);
		skillsTable = (TableLayout) view.findViewById(R.id.tableSkills);
		languagesTable = (TableLayout) view.findViewById(R.id.tableLanguages);
		magicTable = (TableLayout) view.findViewById(R.id.tableMagic);

		addFeat = (TextView) view.findViewById(R.id.textAddFeats);
		addSkill = (TextView) view.findViewById(R.id.textAddSkills);
		addLanguage = (TextView) view.findViewById(R.id.textAddLanguages);
		addMagic = (TextView) view.findViewById(R.id.textAddMagic);

		// hashmaps to help find correct tablerow when doing the onclick
		if (feats == null)
			feats = new HashMap<TableRow, Integer>();
		if (skills == null)
			skills = new HashMap<TableRow, Integer>();
		if (languages == null)
			languages = new HashMap<TableRow, Integer>();
		if (magic == null)
			magic = new HashMap<TableRow, Integer>();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "activityCreated");

		super.onActivityCreated(savedInstanceState);

		// make sure this survives orientation changes
		setRetainInstance(true);

		// load from intent
		if (character != null)
			loadData();

		// add onclick listeners to each of the plus signs for each table
		addFeat.setClickable(true);
		addFeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, FEATS, -1);
			}

		});

		addSkill.setClickable(true);
		addSkill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, SKILLS, -1);
			}

		});

		addLanguage.setClickable(true);
		addLanguage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, LANGUAGES, -1);
			}

		});

		addMagic.setClickable(true);
		addMagic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, MAGIC, -1);
			}

		});
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstance");

		super.onSaveInstanceState(outState);

		ArrayList<String> feat = new ArrayList<String>();
		ArrayList<String> skill = new ArrayList<String>();
		ArrayList<String> language = new ArrayList<String>();
		ArrayList<String> magics = new ArrayList<String>();

		for (HashMap.Entry<TableRow, Integer> entry : feats.entrySet()) {
			TableRow key = entry.getKey();
			String str = ((TextView) key.getChildAt(0)).getText().toString()
					+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
			feat.add(str);
		}
		for (HashMap.Entry<TableRow, Integer> entry : skills.entrySet()) {
			TableRow key = entry.getKey();
			String str = ((TextView) key.getChildAt(0)).getText().toString()
					+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
			skill.add(str);
		}
		for (HashMap.Entry<TableRow, Integer> entry : languages.entrySet()) {
			TableRow key = entry.getKey();
			String str = ((TextView) key.getChildAt(0)).getText().toString()
					+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
			language.add(str);
		}
		for (HashMap.Entry<TableRow, Integer> entry : magic.entrySet()) {
			TableRow key = entry.getKey();
			String str = ((TextView) key.getChildAt(0)).getText().toString()
					+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
			magics.add(str);
		}

		outState.putSerializable(FEATS, feat);
		outState.putSerializable(SKILLS, skill);
		outState.putSerializable(LANGUAGES, language);
		outState.putSerializable(MAGIC, magics);
	}

	// loads data from intent if it exists
	private void loadData() {
		// loads character data from the intent for when we are editing existing
		// character. we check if the arrays are null first since it's possible
		// there are no items in each list. we also set listeners for each row
		// so the user can click and update them
		
		//clear the hashmaps since we are reloading them anyway to ensure no duplicates
		feats.clear();
		skills.clear();
		magic.clear();
		languages.clear();
		
		if (character.getFeats() != null) {
			for (int i = 0; i < character.getFeats().size(); i++) {
				String str = character.getFeats().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);

				TextView desc = new TextView(getActivity());

				// handle case where we have blank text in the description
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");

				desc.setGravity(Gravity.RIGHT);

				TableRow featRow = new TableRow(getActivity());
				featRow.addView(name);
				featRow.addView(desc);
				feats.put(featRow, i + 1);
				featRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, FEATS);
					}

				});
				featsTable.addView(featRow, i + 1);
			}
		}

		if (character.getSkills() != null) {
			for (int i = 0; i < character.getSkills().size(); i++) {
				int j = i + 1;
				String str = character.getSkills().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);

				TableRow skillRow = new TableRow(getActivity());
				skillRow.addView(name);
				skillRow.addView(desc);
				skills.put(skillRow, i + 1);
				skillRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, SKILLS);
					}

				});

				skillsTable.addView(skillRow, i + 1);
			}
		}

		if (character.getLanguages() != null) {
			for (int i = 0; i < character.getLanguages().size(); i++) {
				int j = i + 1;
				String str = character.getLanguages().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);

				TableRow languageRow = new TableRow(getActivity());
				languageRow.addView(name);
				languageRow.addView(desc);
				languages.put(languageRow, i + 1);
				languageRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, LANGUAGES);
					}

				});

				languagesTable.addView(languageRow, i + 1);
			}
		}

		if (character.getMagic() != null) {
			for (int i = 0; i < character.getMagic().size(); i++) {
				int j = i + 1;
				String str = character.getMagic().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);

				TableRow magicRow = new TableRow(getActivity());
				magicRow.addView(name);
				magicRow.addView(desc);
				magic.put(magicRow, i + 1);
				magicRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, MAGIC);
					}

				});

				magicTable.addView(magicRow, i + 1);
			}
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String type,
			String name, String desc, int pos) {
		Log.d(TAG, "posClick");

		// adds a new item/edits existing item, and for new items adds onclick
		// listeners for them
		if (name != null && !isEmpty(name)) {
			if (type.equals(FEATS)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					featsTable.addView(newRow);
					feats.put(newRow, featsTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, FEATS);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) featsTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(SKILLS)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					skillsTable.addView(newRow);
					skills.put(newRow, skillsTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, SKILLS);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) skillsTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(MAGIC)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					magicTable.addView(newRow);
					magic.put(newRow, magicTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, MAGIC);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) magicTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(LANGUAGES)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					languagesTable.addView(newRow);
					languages.put(newRow, languagesTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, LANGUAGES);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) languagesTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			}
		} else {
			Toast.makeText(getActivity(), WARN_EMPTY_NAME, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// do nothing
	}

	// method for showing add/edit dialog
	private void showAddDialog(boolean isExtra, String name, String desc,
			String type, int pos) {
		DialogFragment dialog = newInstance(isExtra, name, desc, type, pos);
		dialog.setTargetFragment(this, 1);
		dialog.show(getFragmentManager(), "AddDialogFragment");
	}

	// for passing arguments to dialog
	static AddDialogFragment newInstance(boolean isExtra, String name,
			String desc, String type, int pos) {
		AddDialogFragment f = new AddDialogFragment();

		Bundle args = new Bundle();
		args.putBoolean(AddDialogFragment.IS_EXTRA, isExtra);

		args.putString(AddDialogFragment.TYPE, type);

		if (name != null)
			args.putString(AddDialogFragment.NAME, name);

		if (desc != null)
			args.putString(AddDialogFragment.DESC, desc);

		args.putInt(AddDialogFragment.POSITION, pos);

		f.setArguments(args);

		return f;
	}

	// checks for empty string
	private boolean isEmpty(String str) {
		return str.trim().length() == 0 || str == null;
	}

	// called every time an existing item is clicked so the edit dialog can be
	// pulled up
	private void click(View v, String type) {
		TableRow tr = (TableRow) v;
		String name = ((TextView) tr.getChildAt(0)).getText().toString();
		String desc = ((TextView) tr.getChildAt(1)).getText().toString();

		if (type.equals(FEATS)) {
			showAddDialog(false, name, desc, type, feats.get(tr));
		} else if (type.equals(SKILLS)) {
			showAddDialog(false, name, desc, type, skills.get(tr));
		} else if (type.equals(MAGIC)) {
			showAddDialog(false, name, desc, type, magic.get(tr));
		} else if (type.equals(LANGUAGES)) {
			showAddDialog(false, name, desc, type, languages.get(tr));
		}
	}
}