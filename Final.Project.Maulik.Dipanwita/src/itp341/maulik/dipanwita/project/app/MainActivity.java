package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.Character;
import itp341.maulik.dipanwita.project.app.model.Extra;
import itp341.maulik.dipanwita.project.app.model.ExtraDataStore;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	CharacterFragment charFrag;
	ExtraFragment extraFrag;
	FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.main_activity);

		fragmentManager = getFragmentManager();
		
		if (null == savedInstanceState) 
		{
			charFrag = new CharacterFragment();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container, charFrag,
					CharacterFragment.TAG);
			fragmentTransaction.commit();
			getActionBar().setTitle(R.string.title_fragment_character);
		}
		else if (savedInstanceState != null)
		{
			charFrag = (CharacterFragment) fragmentManager.findFragmentByTag(CharacterFragment.TAG);
			extraFrag = (ExtraFragment) fragmentManager.findFragmentByTag(ExtraFragment.TAG);
		}
		
		if(charFrag == null) 
			charFrag = new CharacterFragment();
		if(extraFrag == null)
			extraFrag = new ExtraFragment();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		CharacterFragment cf = (CharacterFragment) getFragmentManager()
				.findFragmentByTag(CharacterFragment.TAG);
		ExtraFragment ef = (ExtraFragment) getFragmentManager()
				.findFragmentByTag(ExtraFragment.TAG);

		switch (item.getItemId()) {
		case R.id.menu_add: {
			if (cf != null && cf.isVisible()) {
				Intent i = new Intent(getApplicationContext(),
						AddEditCharacterActivity.class);
				startActivityForResult(i, 1);
			} else if (ef != null && ef.isVisible()) {
				Intent i = new Intent(getApplicationContext(),
						AddEditExtraActivity.class);
				startActivityForResult(i, 2);
			}
			return true;
		}
		case R.id.menu_switch: {
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			if (cf != null && cf.isVisible()) {
				fragmentTransaction.replace(R.id.fragment_container, extraFrag,
						ExtraFragment.TAG);
				fragmentTransaction.commit();
				getActionBar().setTitle(R.string.title_fragment_extras);
			} else if (ef != null && ef.isVisible()) {
				fragmentTransaction.replace(R.id.fragment_container, charFrag,
						CharacterFragment.TAG);
				fragmentTransaction.commit();
				getActionBar().setTitle(R.string.title_fragment_character);
			}
			return true;
		}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult");

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			Character character = (Character) data
					.getSerializableExtra(AddEditCharacterActivity.EXTRA_CHARACTER_OBJECT);
			if (character != null) {
				Log.d(TAG, charFrag.toString());
				charFrag.getCharacters().add(character);
				charFrag.getCharAdapter().notifyDataSetChanged();
			}
		} else if (requestCode == 2) {
			Extra extra = (Extra) data
					.getSerializableExtra(AddEditExtraActivity.EXTRA_EXTRA_OBJECT);
			if (extra != null) {
				extraFrag.getExtras().add(extra);
				extraFrag.getExtraAdapter().notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}
}
