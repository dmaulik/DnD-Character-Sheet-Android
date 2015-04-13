package itp341.maulik.dipanwita.project.app;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import itp341.maulik.dipanwita.project.app.model.Character;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditCharacterActivity extends Activity implements
		AddDialogFragment.AddDialogListener {

	private static final String TAG = AddEditCharacterActivity.class
			.getSimpleName();
	public static final String EXTRA_CHARACTER_OBJECT = "character_character_object";
	private static final String WARN_EMPTY_NAME = "Name cannot be empty!";

	AddEditCharacterStatsFragment aecStats;
	AddEditCharacterItemsFragment aecItems;
	AddEditCharacterPowersFragment aecPowers;

	FragmentManager fragmentManager;

	boolean inStats;
	boolean inItems;

	public static Character character;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		setContentView(R.layout.activity_add_edit_character);

		fragmentManager = getFragmentManager();

		inStats = true;
		inItems = false;

		Intent i = getIntent();
		character = (Character) i
				.getSerializableExtra(AddEditCharacterActivity.EXTRA_CHARACTER_OBJECT);

		if (character == null) {
			character = new Character();
			character.setPic(Character.NO_PICTURE);
		}

		// if we're returning from an orientation change, we want to get the
		// already existing fragment(s)
		if (savedInstanceState != null) {
			aecStats = (AddEditCharacterStatsFragment) fragmentManager
					.findFragmentByTag(AddEditCharacterStatsFragment.TAG);
			aecItems = (AddEditCharacterItemsFragment) fragmentManager
					.findFragmentByTag(AddEditCharacterItemsFragment.TAG);
			aecPowers = (AddEditCharacterPowersFragment) fragmentManager
					.findFragmentByTag(AddEditCharacterPowersFragment.TAG);
		} else if (null == savedInstanceState) {
			aecStats = new AddEditCharacterStatsFragment();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.addedit_fragment_container,
					aecStats, AddEditCharacterStatsFragment.TAG);
			fragmentTransaction.commit();
		}

		// final check since its possible one or more of these were never
		// instantiated since if 4we never switched to them
		if (aecStats == null)
			aecStats = new AddEditCharacterStatsFragment();
		if (aecItems == null)
			aecItems = new AddEditCharacterItemsFragment();
		if (aecPowers == null)
			aecPowers = new AddEditCharacterPowersFragment();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.menu_edit_character, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem back = menu.findItem(R.id.menu_back);
		MenuItem next = menu.findItem(R.id.menu_next);

		// the back button is not visible on the first screen; the forward
		// button is not visible on the last
		if (inStats)
			back.setVisible(false);
		if (inItems)
			next.setVisible(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AddEditCharacterStatsFragment aecs = (AddEditCharacterStatsFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterStatsFragment.TAG);
		AddEditCharacterItemsFragment aeci = (AddEditCharacterItemsFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterItemsFragment.TAG);
		AddEditCharacterPowersFragment aecp = (AddEditCharacterPowersFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterPowersFragment.TAG);

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// pressing forward/back does different things depending on which
		// fragment we are currently on.
		switch (item.getItemId()) {
		case R.id.menu_next: {
			if (aecs != null && aecs.isVisible()) {
				// save what we have in current fragment before switching to new
				// one
				saveStats();
				saveItems();

				fragmentTransaction.replace(R.id.addedit_fragment_container,
						aecPowers, AddEditCharacterPowersFragment.TAG);
				fragmentTransaction.commit();
				inStats = false;

			} else if (aecp != null && aecp.isVisible()) {
				// first save whatever is in current fragment, then go to new
				// fragment
				savePowers();
				saveStats();

				fragmentTransaction.replace(R.id.addedit_fragment_container,
						aecItems, AddEditCharacterItemsFragment.TAG);
				fragmentTransaction.commit();
				inItems = true;
			}
			invalidateOptionsMenu();
			return true;
		}
		case R.id.menu_back: {
			if (aeci != null && aeci.isVisible()) {
				saveItems();
				saveStats();

				fragmentTransaction.replace(R.id.addedit_fragment_container,
						aecPowers, AddEditCharacterPowersFragment.TAG);
				fragmentTransaction.commit();
				inItems = false;
			} else if (aecp != null && aecp.isVisible()) {
				// first save whatever is in the fragment so we can come back to
				// it
				savePowers();
				saveItems();

				fragmentTransaction.replace(R.id.addedit_fragment_container,
						aecStats, AddEditCharacterStatsFragment.TAG);
				fragmentTransaction.commit();
				inStats = true;
			}
			invalidateOptionsMenu();
			return true;
		}
		case R.id.menu_save_char: {
			try {
				saveAndClose();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// if we press back, then no data should be passed back as we did not
		// save the character
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}

	private void saveAndClose() throws ParseException {
		Log.d(TAG, "onClickSave");

		// from each fragment save each of the different attributes onto the
		// character and then pass it back to the call activity
		if (aecStats.name.getText() != null
				&& !isEmpty(aecStats.name.getText().toString())) {

			character.setName(aecStats.name.getText().toString());

			if (aecStats.align.getText() == null)
				character.setAlign("");
			else
				character.setAlign(aecStats.align.getText().toString());

			if (isEmpty(aecStats.age.getText().toString()))
				character.setAge(0);
			else
				character.setAge(Integer.parseInt(aecStats.age.getText()
						.toString()));

			if (aecStats.species.getText() == null)
				character.setSpecies("");
			else
				character.setSpecies(aecStats.species.getText().toString());

			if (aecStats.charClass.getText() == null)
				character.setCharClass("");
			else
				character.setCharClass(aecStats.charClass.getText().toString());

			if (isEmpty(aecStats.level.getText().toString()))
				character.setLevel(0);
			else
				character.setLevel(Integer.parseInt(aecStats.level.getText()
						.toString()));

			if (isEmpty(aecStats.curHP.getText().toString()))
				character.setCurHP(0);
			else
				character.setCurHP(Integer.parseInt(aecStats.curHP.getText()
						.toString()));

			if (isEmpty(aecStats.maxHP.getText().toString()))
				character.setMaxHP(0);
			else
				character.setMaxHP(Integer.parseInt(aecStats.maxHP.getText()
						.toString()));

			if (isEmpty(aecStats.curExp.getText().toString()))
				character.setCurExp(0);
			else
				character.setCurExp(Integer.parseInt(aecStats.curExp.getText()
						.toString()));

			if (isEmpty(aecStats.nextLvlExp.getText().toString()))
				character.setNxtLevelExp(0);
			else
				character.setNxtLevelExp(Integer.parseInt(aecStats.nextLvlExp
						.getText().toString()));

			if (isEmpty(aecStats.AC.getText().toString()))
				character.setAC(0);
			else
				character.setAC(Integer.parseInt(aecStats.AC.getText()
						.toString()));

			if (isEmpty(aecStats.init.getText().toString()))
				character.setInit(0);
			else
				character.setInit(Integer.parseInt(aecStats.init.getText()
						.toString()));

			if (isEmpty(aecStats.BAB.getText().toString()))
				character.setBAB(0);
			else
				character.setBAB(Integer.parseInt(aecStats.BAB.getText()
						.toString()));

			if (isEmpty(aecStats.CMB.getText().toString()))
				character.setCMB(0);
			else
				character.setCMB(Integer.parseInt(aecStats.CMB.getText()
						.toString()));

			if (isEmpty(aecStats.CMD.getText().toString()))
				character.setCMD(0);
			else
				character.setCMD(Integer.parseInt(aecStats.CMD.getText()
						.toString()));

			if (isEmpty(aecStats.STR.getText().toString()))
				character.setSTR(0);
			else
				character.setSTR(Integer.parseInt(aecStats.STR.getText()
						.toString()));

			if (isEmpty(aecStats.CON.getText().toString()))
				character.setCON(0);
			else
				character.setCON(Integer.parseInt(aecStats.CON.getText()
						.toString()));

			if (isEmpty(aecStats.DEX.getText().toString()))
				character.setDEX(0);
			else
				character.setDEX(Integer.parseInt(aecStats.DEX.getText()
						.toString()));

			if (isEmpty(aecStats.INT.getText().toString()))
				character.setINT(0);
			else
				character.setINT(Integer.parseInt(aecStats.INT.getText()
						.toString()));

			if (isEmpty(aecStats.CHR.getText().toString()))
				character.setCHR(0);
			else
				character.setCHR(Integer.parseInt(aecStats.CHR.getText()
						.toString()));

			if (isEmpty(aecStats.WIS.getText().toString()))
				character.setWIS(0);
			else
				character.setWIS(Integer.parseInt(aecStats.WIS.getText()
						.toString()));

			if (isEmpty(aecStats.FORT.getText().toString()))
				character.setFORT(0);
			else
				character.setFORT(Integer.parseInt(aecStats.FORT.getText()
						.toString()));

			if (isEmpty(aecStats.REF.getText().toString()))
				character.setREF(0);
			else
				character.setREF(Integer.parseInt(aecStats.REF.getText()
						.toString()));

			if (isEmpty(aecStats.WILL.getText().toString()))
				character.setWILL(0);
			else
				character.setWILL(Integer.parseInt(aecStats.WILL.getText()
						.toString()));

			// the following are stored in the format of name:description for
			// easy
			// parsing later on

			/** Powers **/
			// instantiate a new array each time since we're going to be
			// re-adding everything anyway
			character.setFeats(new ArrayList<String>());
			character.setSkills(new ArrayList<String>());
			character.setMagic(new ArrayList<String>());
			character.setLanguages(new ArrayList<String>());

			// feats
			// check to make sure the fragment was instantiated at least once,
			// otherwise these will all be null
			if (aecPowers.featsTable != null && aecPowers.skillsTable != null
					&& aecPowers.magicTable != null
					&& aecPowers.languagesTable != null) {
				Log.d(TAG, String.valueOf(aecPowers.featsTable.getChildCount()));
				for (int i = 1; i < aecPowers.featsTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecPowers.featsTable.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getFeats().add(str);
				}

				// skills
				for (int i = 1; i < aecPowers.skillsTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecPowers.skillsTable
							.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getSkills().add(str);
				}

				// languages
				for (int i = 1; i < aecPowers.languagesTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecPowers.languagesTable
							.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getLanguages().add(str);
				}

				// magic
				for (int i = 1; i < aecPowers.magicTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecPowers.magicTable.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getMagic().add(str);
				}
			}

			/** Items **/
			// instantiate a new array every time since we're going to be
			// re-adding everything anyway
			character.setArmors(new ArrayList<String>());
			character.setWeapons(new ArrayList<String>());
			character.setMisc(new ArrayList<String>());
			character.setExtras(new ArrayList<String>());

			// armor
			// check to make sure the items fragment was instantiated at least
			// once
			// or these will all be null
			if (aecItems.armorTable != null && aecItems.weaponsTable != null
					&& aecItems.miscTable != null
					&& aecItems.extrasTable != null)

			{
				for (int i = 1; i < aecItems.armorTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecItems.armorTable.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getArmors().add(str);
				}

				// weapons
				for (int i = 1; i < aecItems.weaponsTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecItems.weaponsTable
							.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getWeapons().add(str);
				}

				// misc
				for (int i = 1; i < aecItems.miscTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecItems.miscTable.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getMisc().add(str);
				}

				for (int i = 1; i < aecItems.extrasTable.getChildCount(); i++) {
					TableRow tr = (TableRow) aecItems.extrasTable.getChildAt(i);
					TextView n = (TextView) tr.getChildAt(0);
					TextView d = (TextView) tr.getChildAt(1);

					String str = "";
					if (n.getText() == null)
						break;
					else
						str += n.getText().toString();

					str += ":";

					if (d.getText() == null)
						str += "";
					else
						str += d.getText().toString();

					character.getExtras().add(str);
				}
			}

			Intent i = new Intent();
			i.putExtra(EXTRA_CHARACTER_OBJECT, character);
			setResult(RESULT_OK, i);
			finish();
		} else {
			// if the user entered a blank for the character name we can't save,
			// so we let the user know to enter a name
			Toast.makeText(getApplicationContext(), WARN_EMPTY_NAME,
					Toast.LENGTH_SHORT).show();
		}
	}

	// check if string is empty
	private boolean isEmpty(String str) {
		return str.trim().length() == 0 || str == null;
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String type,
			String name, String desc, int position) {
		Log.d(TAG, "postClick");
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	// update the character image after user chooses something from the picture
	// library
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "activityResult");

		if (resultCode == RESULT_OK && requestCode == 100) {
			Uri targetUri = data.getData();
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(targetUri));
				((ImageView) findViewById(R.id.imageCharacter))
						.setImageBitmap(bitmap);
				((ImageView) findViewById(R.id.imageCharacter))
						.setTag(targetUri.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			character.setPic(targetUri.toString());
		}
	}

	private void saveStats() {
		AddEditCharacterStatsFragment aecs = (AddEditCharacterStatsFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterStatsFragment.TAG);

		if (aecs != null) {
			character.setName(aecs.name.getText().toString());
			if (!aecs.age.getText().toString().isEmpty())
				character.setAge(Integer
						.parseInt(aecs.age.getText().toString()));
			character.setSpecies(aecs.species.getText().toString());
			character.setCharClass(aecs.charClass.getText().toString());
			if (!aecs.level.getText().toString().isEmpty())
				character.setLevel(Integer.parseInt(aecs.level.getText()
						.toString()));
			if (!aecs.curHP.getText().toString().isEmpty())
				character.setCurHP(Integer.parseInt(aecs.curHP.getText()
						.toString()));
			if (!aecs.maxHP.getText().toString().isEmpty())
				character.setMaxHP(Integer.parseInt(aecs.maxHP.getText()
						.toString()));
			if (!aecs.curExp.getText().toString().isEmpty())
				character.setCurExp(Integer.parseInt(aecs.curExp.getText()
						.toString()));
			if (!aecs.nextLvlExp.getText().toString().isEmpty())
				character.setNxtLevelExp(Integer.parseInt(aecs.nextLvlExp
						.getText().toString()));
			if (!aecs.init.getText().toString().isEmpty())
				character.setInit(Integer.parseInt(aecs.init.getText()
						.toString()));
			if (!aecs.AC.getText().toString().isEmpty())
				character.setAC(Integer.parseInt(aecs.AC.getText().toString()));
			if (!aecs.STR.getText().toString().isEmpty())
				character.setSTR(Integer
						.parseInt(aecs.STR.getText().toString()));
			if (!aecs.WILL.getText().toString().isEmpty())
				character.setWILL(Integer.parseInt(aecs.WILL.getText()
						.toString()));
			if (!aecs.DEX.getText().toString().isEmpty())
				character.setDEX(Integer
						.parseInt(aecs.DEX.getText().toString()));
			if (!aecs.CON.getText().toString().isEmpty())
				character.setCON(Integer
						.parseInt(aecs.CON.getText().toString()));
			if (!aecs.INT.getText().toString().isEmpty())
				character.setINT(Integer
						.parseInt(aecs.INT.getText().toString()));
			if (!aecs.CHR.getText().toString().isEmpty())
				character.setCHR(Integer
						.parseInt(aecs.CHR.getText().toString()));
			if (!aecs.speed.getText().toString().isEmpty())
				character.setSpeed(Integer.parseInt(aecs.speed.getText()
						.toString()));
			character.setAlign(aecs.align.getText().toString());
			if (!aecs.CMD.getText().toString().isEmpty())
				character.setCMD(Integer
						.parseInt(aecs.CMD.getText().toString()));
			if (!aecs.BAB.getText().toString().isEmpty())
				character.setBAB(Integer
						.parseInt(aecs.BAB.getText().toString()));
			if (!aecs.CMB.getText().toString().isEmpty())
				character.setCMB(Integer
						.parseInt(aecs.CMB.getText().toString()));
			if (!aecs.WIS.getText().toString().isEmpty())
				character.setWIS(Integer
						.parseInt(aecs.WIS.getText().toString()));
			if (!aecs.FORT.getText().toString().isEmpty())
				character.setFORT(Integer.parseInt(aecs.FORT.getText()
						.toString()));
			if (!aecs.REF.getText().toString().isEmpty())
				character.setREF(Integer
						.parseInt(aecs.REF.getText().toString()));

			if (aecs.pic.getTag() != null) {
				if (aecs.pic.getTag().toString().equals(Character.NO_PICTURE))
					character.setPic(Character.NO_PICTURE);
				else
					character.setPic(aecs.pic.getTag().toString());
			}
		}

	}

	private void savePowers() {
		AddEditCharacterPowersFragment aecp = (AddEditCharacterPowersFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterPowersFragment.TAG);

		if (aecp != null) {
			ArrayList<String> feat = new ArrayList<String>();
			ArrayList<String> skill = new ArrayList<String>();
			ArrayList<String> language = new ArrayList<String>();
			ArrayList<String> magics = new ArrayList<String>();
			
			for (HashMap.Entry<TableRow, Integer> entry : aecp.feats.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				feat.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aecp.skills.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				skill.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aecp.languages.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				language.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aecp.magic.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				magics.add(str);
			}
			
			character.setFeats(feat);
			character.setSkills(skill);
			character.setLanguages(language);
			character.setMagic(magics);
		}
	}

	private void saveItems() {
		AddEditCharacterItemsFragment aeci = (AddEditCharacterItemsFragment) getFragmentManager()
				.findFragmentByTag(AddEditCharacterItemsFragment.TAG);

		if (aeci != null) {
			ArrayList<String> armor = new ArrayList<String>();
			ArrayList<String> weapon = new ArrayList<String>();
			ArrayList<String> miscs = new ArrayList<String>();
			ArrayList<String> extra = new ArrayList<String>();
			for (HashMap.Entry<TableRow, Integer> entry : aeci.armors.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				armor.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aeci.weapons.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				weapon.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aeci.misc.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				miscs.add(str);
			}
			for (HashMap.Entry<TableRow, Integer> entry : aeci.extras.entrySet()) {
				TableRow key = entry.getKey();
				String str = ((TextView) key.getChildAt(0)).getText().toString()
						+ ":" + ((TextView) key.getChildAt(1)).getText().toString();
				extra.add(str);
			}
			character.setArmors(armor);
			character.setWeapons(weapon);
			character.setMisc(miscs);
			character.setExtras(extra);
		}

	}
}
