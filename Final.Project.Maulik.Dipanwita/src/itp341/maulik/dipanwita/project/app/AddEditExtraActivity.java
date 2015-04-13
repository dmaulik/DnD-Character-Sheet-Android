package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.CharacterDataStore;
import itp341.maulik.dipanwita.project.app.model.Character;
import itp341.maulik.dipanwita.project.app.model.Extra;
import itp341.maulik.dipanwita.project.app.model.ExtraDataStore;

import java.text.ParseException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddEditExtraActivity extends Activity {

	private static final String TAG = AddEditExtraActivity.class
			.getSimpleName();
	public static final String EXTRA_EXTRA_OBJECT = "extra_extra_object";

	EditText editName;
	RadioGroup radioGroupType;
	EditText editDesc;

	Extra extra;

	ArrayList<Character> characters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_extra);

		editName = (EditText) findViewById(R.id.editExtraName);
		radioGroupType = (RadioGroup) findViewById(R.id.radiogroupExtraType);
		editDesc = (EditText) findViewById(R.id.editExtraDescription);

		Intent i = getIntent();
		extra = (Extra) i.getSerializableExtra(EXTRA_EXTRA_OBJECT);

		// only load data if we're editing and the screen hasn't been rotated to
		// trigger another onCreate()
		if (extra != null && savedInstanceState == null)
			loadData();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.menu_edit_extra, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save_extra: {
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

	private void saveAndClose() throws ParseException {
		Log.d(TAG, "onClick");

		try {
			characters = CharacterDataStore
					.loadCharacters(getApplicationContext());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String oldName = null;
		if (extra == null)
			extra = new Extra();
		else
			oldName = extra.getName();

		if (editName.getText() == null)
			extra.setName("");
		else
			extra.setName(editName.getText().toString());

		if (editDesc.getText() == null)
			extra.setDescription("");
		else
			extra.setDescription(editDesc.getText().toString());

		int checkedId = radioGroupType.getCheckedRadioButtonId();
		if (checkedId == R.id.radiobuttonExtraNumber)
			extra.setType(Extra.NUMBER);
		else
			extra.setType(Extra.STRING);

		if (oldName != null && characters != null) {
			for (int i = 0; i < characters.size(); i++) {
				ArrayList<String> e = characters.get(i).getExtras();
				if (e != null) {
					for (int j = 0; j < e.size(); j++) {
						String[] s = e.get(j).split(":");
						if (s[0].equals(oldName))
							e.set(j, extra.getName() + ":" + s[1]);
					}
				}
			}
		}

		CharacterDataStore.saveCharacters(characters, getApplicationContext());
		Intent i = new Intent();
		i.putExtra(EXTRA_EXTRA_OBJECT, extra);
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putExtra(EXTRA_EXTRA_OBJECT, new Extra());
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	private void loadData() {
		editName.setText(extra.getName());
		editDesc.setText(extra.getDescription());
		if (extra.getType().equals(Extra.NUMBER))
			radioGroupType.check(R.id.radiobuttonExtraNumber);
		else
			radioGroupType.check(R.id.radiobuttonExtraString);
	}
}
