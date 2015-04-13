package itp341.maulik.dipanwita.project.app;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import itp341.maulik.dipanwita.project.app.model.Character;
import itp341.maulik.dipanwita.project.app.model.Extra;
import itp341.maulik.dipanwita.project.app.model.ExtraDataStore;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditCharacterItemsFragment extends Fragment implements
		AddDialogFragment.AddDialogListener {

	public static final String TAG = AddEditCharacterItemsFragment.class
			.getSimpleName();
	
	public static final String ARMOR = "armor";
	public static final String WEAPONS = "weapons";
	public static final String MISC = "misc";
	public static final String EXTRAS = "extras";
	private static final String WARN_EMPTY_NAME = "Name cannot be empty!";

	Character character;
	HashMap<TableRow, Integer> armors;
	HashMap<TableRow, Integer> weapons;
	HashMap<TableRow, Integer> misc;
	HashMap<TableRow, Integer> extras;

	TextView addArmor;
	TextView addWeapons;
	TextView addMisc;
	TextView addExtras;

	TableLayout armorTable;
	TableLayout weaponsTable;
	TableLayout miscTable;
	TableLayout extrasTable;

	static ArrayList<Extra> e;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		try {
			e = ExtraDataStore.loadExtras(getActivity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(savedInstanceState != null)
		{
			character = new Character();
			character.setArmors((ArrayList<String>)savedInstanceState.getSerializable(ARMOR));
			character.setWeapons((ArrayList<String>)savedInstanceState.getSerializable(WEAPONS));
			character.setMisc((ArrayList<String>)savedInstanceState.getSerializable(MISC));
			character.setExtras((ArrayList<String>)savedInstanceState.getSerializable(EXTRAS));
		} else {
			Intent i = getActivity().getIntent();
			character = (Character) i
					.getSerializableExtra(AddEditCharacterActivity.EXTRA_CHARACTER_OBJECT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_edit_items,
				container, false);

		armorTable = (TableLayout) view.findViewById(R.id.tableArmor);
		weaponsTable = (TableLayout) view.findViewById(R.id.tableWeapons);
		miscTable = (TableLayout) view.findViewById(R.id.tableMisc);
		extrasTable = (TableLayout) view.findViewById(R.id.tableExtras);

		addArmor = (TextView) view.findViewById(R.id.textAddArmor);
		addWeapons = (TextView) view.findViewById(R.id.textAddWeapons);
		addMisc = (TextView) view.findViewById(R.id.textAddMisc);
		addExtras = (TextView) view.findViewById(R.id.textAddExtras);

		// the hashmaps store the row number for each set of things in order to
		// implement clicking more easily
		if (armors == null)
			armors = new HashMap<TableRow, Integer>();
		if (weapons == null)
			weapons = new HashMap<TableRow, Integer>();
		if (misc == null)
			misc = new HashMap<TableRow, Integer>();
		if (extras == null)
			extras = new HashMap<TableRow, Integer>();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		 // load data from intent
		 if (character != null)
			 loadData();

		// these are the little plus signs at the end of first row; clicking
		// them lets you add a new thing
		addArmor.setClickable(true);
		addArmor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, ARMOR, -1);
			}

		});

		addWeapons.setClickable(true);
		addWeapons.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, WEAPONS, -1);
			}

		});

		addMisc.setClickable(true);
		addMisc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(false, null, null, MISC, -1);
			}

		});

		addExtras.setClickable(true);
		addExtras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAddDialog(true, null, null, EXTRAS, -1);
			}

		});

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	private void loadData() {

		// loads character data from the intent for when we are editing existing
		// character. we check if the arrays are null first since it's possible
		// there are no items in each list. we also set listeners for each row
		// so the user can click and update them
		if (character.getArmors() != null) {
			for (int i = 0; i < character.getArmors().size(); i++) {
				int j = i + 1;
				String str = character.getArmors().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);
				name.setClickable(true);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);
				desc.setClickable(true);

				TableRow armorRow = new TableRow(getActivity());
				armorRow.addView(name);
				armorRow.addView(desc);
				armorRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, ARMOR);
					}

				});

				armorTable.addView(armorRow, i + 1);
			}
		}

		if (character.getWeapons() != null) {
			for (int i = 0; i < character.getWeapons().size(); i++) {
				int j = i + 1;
				String str = character.getWeapons().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);
				name.setClickable(true);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);
				desc.setClickable(true);

				TableRow weaponsRow = new TableRow(getActivity());
				weaponsRow.addView(name);
				weaponsRow.addView(desc);
				weaponsRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, WEAPONS);
					}

				});

				weaponsTable.addView(weaponsRow, i + 1);
			}
		}

		if (character.getMisc() != null) {
			for (int i = 0; i < character.getMisc().size(); i++) {
				int j = i + 1;
				String str = character.getMisc().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);
				name.setClickable(true);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);
				desc.setClickable(true);

				TableRow miscRow = new TableRow(getActivity());
				miscRow.addView(name);
				miscRow.addView(desc);
				miscRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, MISC);
					}

				});

				miscTable.addView(miscRow, i + 1);
			}
		}

		if (character.getExtras() != null) {
			for (int i = 0; i < character.getExtras().size(); i++) {
				int j = i + 1;
				String str = character.getExtras().get(i);
				String[] parts = str.split(":");

				TextView name = new TextView(getActivity());
				name.setText(parts[0]);
				name.setClickable(true);

				TextView desc = new TextView(getActivity());
				if (parts.length > 1)
					desc.setText(parts[1]);
				else
					desc.setText("");
				desc.setGravity(Gravity.RIGHT);
				desc.setClickable(true);

				TableRow extraRow = new TableRow(getActivity());
				extraRow.addView(name);
				extraRow.addView(desc);
				extraRow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						click(arg0, EXTRAS);
					}

				});

				extrasTable.addView(extraRow, i + 1);
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstance");
		
		super.onSaveInstanceState(outState);

		ArrayList<String> armor = new ArrayList<String>();
		ArrayList<String> weapon = new ArrayList<String>();
		ArrayList<String> miscs = new ArrayList<String>();
		ArrayList<String> extra = new ArrayList<String>();
		
		for (HashMap.Entry<TableRow,Integer> entry : armors.entrySet()) {
		    TableRow key = entry.getKey();
		    String str = key.getChildAt(0) + ":" + key.getChildAt(1);
		    armor.add(str);
		}
		for (HashMap.Entry<TableRow,Integer> entry : weapons.entrySet()) {
		    TableRow key = entry.getKey();
		    String str = key.getChildAt(0) + ":" + key.getChildAt(1);
		    weapon.add(str);
		}
		for (HashMap.Entry<TableRow,Integer> entry : misc.entrySet()) {
		    TableRow key = entry.getKey();
		    String str = key.getChildAt(0) + ":" + key.getChildAt(1);
		    miscs.add(str);
		}
		for (HashMap.Entry<TableRow,Integer> entry : extras.entrySet()) {
		    TableRow key = entry.getKey();
		    String str = key.getChildAt(0) + ":" + key.getChildAt(1);
		    extra.add(str);
		}
		
		outState.putSerializable(ARMOR, armor);
		outState.putSerializable(WEAPONS, weapon);
		outState.putSerializable(MISC, miscs);
		outState.putSerializable(EXTRAS, extra);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String type,
			String name, String desc, int pos) {
		Log.d(TAG, "posClick");

		// if the user clicked ok, we either add an item to the appropriate list
		// or update the edited item. when adding we add a listener to the item
		// so the user can later edit it
		if (name != null && !isEmpty(name)) {
			if (type.equals(ARMOR)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					armorTable.addView(newRow);
					armors.put(newRow, armorTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, ARMOR);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) armorTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(WEAPONS)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					weaponsTable.addView(newRow);
					weapons.put(newRow, weaponsTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, WEAPONS);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) weaponsTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(MISC)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					miscTable.addView(newRow);
					misc.put(newRow, miscTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, MISC);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) miscTable.getChildAt(pos);
					((TextView) row.getChildAt(0)).setText(name);
					((TextView) row.getChildAt(1)).setText(desc);
				}
			} else if (type.equals(EXTRAS)) {
				// new item to add to list
				if (pos == -1) {
					TableRow newRow = new TableRow(getActivity());

					TextView n = new TextView(getActivity());
					n.setText(name);
					TextView d = new TextView(getActivity());
					d.setText(desc);

					newRow.addView(n);
					newRow.addView(d);

					extrasTable.addView(newRow);
					extras.put(newRow, extrasTable.getChildCount() - 1);
					newRow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							click(arg0, EXTRAS);
						}

					});
				} else // update existing row
				{
					TableRow row = (TableRow) extrasTable.getChildAt(pos);
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

	// method that starts the dialog for adding/editing
	private void showAddDialog(boolean isExtra, String name, String desc,
			String type, int pos) {
		DialogFragment dialog = newInstance(isExtra, name, desc, type, pos);
		dialog.setTargetFragment(this, 1);
		dialog.show(getFragmentManager(), "AddDialogFragment");
	}

	// used to pass arguments to the add/edit dialogue
	static AddDialogFragment newInstance(boolean isExtra, String name,
			String desc, String type, int pos) {
		AddDialogFragment f = new AddDialogFragment();

		Bundle args = new Bundle();
		args.putBoolean(AddDialogFragment.IS_EXTRA, isExtra);

		args.putString(AddDialogFragment.TYPE, type);

		if (isExtra)
			args.putSerializable(AddDialogFragment.EXTRAS, e);

		if (name != null)
			args.putString(AddDialogFragment.NAME, name);

		if (desc != null)
			args.putString(AddDialogFragment.DESC, desc);

		args.putInt(AddDialogFragment.POSITION, pos);

		f.setArguments(args);

		return f;
	}

	// method to check empty string
	private boolean isEmpty(String str) {
		return str.trim().length() == 0 || str == null;
	}

	// method that is called any time an item is pressed so we can edit it
	private void click(View v, String type) {
		Log.d(TAG, "click");
		TableRow tr = (TableRow) v;
		String name = ((TextView) tr.getChildAt(0)).getText().toString();
		String desc = ((TextView) tr.getChildAt(1)).getText().toString();

		if (type.equals(ARMOR)) {
			showAddDialog(false, name, desc, type, armors.get(tr));
		} else if (type.equals(WEAPONS)) {
			showAddDialog(false, name, desc, type, weapons.get(tr));
		} else if (type.equals(MISC)) {
			showAddDialog(false, name, desc, type, misc.get(tr));
		} else if (type.equals(EXTRAS)) {
			showAddDialog(true, name, desc, type, extras.get(tr));
		}
	}
}