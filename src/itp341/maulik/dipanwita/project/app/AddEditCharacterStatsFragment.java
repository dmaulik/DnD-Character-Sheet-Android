package itp341.maulik.dipanwita.project.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import itp341.maulik.dipanwita.project.app.model.Character;
import itp341.maulik.dipanwita.project.app.model.CharacterDataStore;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class AddEditCharacterStatsFragment extends Fragment {

	// TODO: load character if needed

	public static final String TAG = AddEditCharacterStatsFragment.class
			.getSimpleName();

	public static Character character;

	private static final String NAME = "name";
	private static final String AGE = "age";
	private static final String SPECIES = "species";
	private static final String CHARCLASS = "charclass";
	private static final String LEVEL = " level";
	private static final String CURHP = "curhp";
	private static final String MAXHP = "maxhp";
	private static final String CUREXP = "curexp";
	private static final String NEXTLVLEXP = "nextlvlexp";
	private static final String INIT = "init";
	private static final String SAC = "ac";
	private static final String SSTR = "str";
	private static final String SWILL = "will";
	private static final String SDEX = "dex";
	private static final String SCON = "con";
	private static final String SINT = "int";
	private static final String SCHR = "chr";
	private static final String SPEED = "speed";
	private static final String ALIGN = "align";
	private static final String SBAB = "bab";
	private static final String SCMB = "cmb";
	private static final String SCMD = "cmd";
	private static final String SWIS = "wis";
	private static final String SFORT = "fort";
	private static final String SREF = "ref";
	private static final String PIC = "pic";

	EditText name;
	EditText age;
	EditText species;
	EditText charClass;
	EditText level;
	EditText curHP;
	EditText maxHP;
	EditText curExp;
	EditText nextLvlExp;
	EditText init;
	EditText AC;
	EditText STR;
	EditText WILL;
	EditText DEX;
	EditText CON;
	EditText INT;
	EditText CHR;
	EditText speed;
	EditText align;
	EditText BAB;
	EditText CMB;
	EditText CMD;
	EditText WIS;
	EditText FORT;
	EditText REF;

	ImageView pic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		// check if we are coming back from an orientation change otherwise get
		// the character from the intent
		if (savedInstanceState == null && character == null) {
			character = AddEditCharacterActivity.character;
		} else if (savedInstanceState != null) {
			character = new Character(savedInstanceState.getString(PIC),
					savedInstanceState.getString(NAME),
					savedInstanceState.getInt(AGE),
					savedInstanceState.getString(SPECIES),
					savedInstanceState.getString(CHARCLASS),
					savedInstanceState.getInt(LEVEL),
					savedInstanceState.getInt(CURHP),
					savedInstanceState.getInt(MAXHP),
					savedInstanceState.getInt(CUREXP),
					savedInstanceState.getInt(NEXTLVLEXP),
					savedInstanceState.getInt(INIT),
					savedInstanceState.getInt(SAC),
					savedInstanceState.getInt(SSTR),
					savedInstanceState.getInt(SCON),
					savedInstanceState.getInt(SDEX),
					savedInstanceState.getInt(SWIS),
					savedInstanceState.getInt(SINT),
					savedInstanceState.getInt(SCHR),
					savedInstanceState.getInt(SFORT),
					savedInstanceState.getInt(SREF),
					savedInstanceState.getInt(SWILL),
					savedInstanceState.getInt(SPEED),
					savedInstanceState.getString(ALIGN),
					savedInstanceState.getInt(SBAB),
					savedInstanceState.getInt(SCMD),
					savedInstanceState.getInt(SCMB), null, null, null, null,
					null, null, null, null);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.d(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.fragment_add_edit_stats,
				container, false);

		pic = (ImageView) view.findViewById(R.id.imageCharacter);
		name = (EditText) view.findViewById(R.id.editCharName);
		age = (EditText) view.findViewById(R.id.editAge);
		species = (EditText) view.findViewById(R.id.editSpecies);
		charClass = (EditText) view.findViewById(R.id.editClass);
		level = (EditText) view.findViewById(R.id.editLevel);
		curHP = (EditText) view.findViewById(R.id.editCurHP);
		maxHP = (EditText) view.findViewById(R.id.editMaxHP);
		curExp = (EditText) view.findViewById(R.id.editCurExp);
		nextLvlExp = (EditText) view.findViewById(R.id.editNxtLvlExp);
		init = (EditText) view.findViewById(R.id.editInit);
		AC = (EditText) view.findViewById(R.id.editAC);
		STR = (EditText) view.findViewById(R.id.editSTR);
		WILL = (EditText) view.findViewById(R.id.editWILL);
		DEX = (EditText) view.findViewById(R.id.editDEX);
		CON = (EditText) view.findViewById(R.id.editCON);
		INT = (EditText) view.findViewById(R.id.editINT);
		CHR = (EditText) view.findViewById(R.id.editCHR);
		speed = (EditText) view.findViewById(R.id.editSpeed);
		align = (EditText) view.findViewById(R.id.editAlign);
		BAB = (EditText) view.findViewById(R.id.editBAB);
		CMB = (EditText) view.findViewById(R.id.editCMB);
		CMD = (EditText) view.findViewById(R.id.editCMD);
		WIS = (EditText) view.findViewById(R.id.editWIS);
		FORT = (EditText) view.findViewById(R.id.editFORT);
		REF = (EditText) view.findViewById(R.id.editREF);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");

		super.onActivityCreated(savedInstanceState);

		// make sure this survives orientation changes
		setRetainInstance(true);

		// load the character
		if (character != null)
			loadData();

		// the picture is clickable so the user can change it
		pic.setClickable(true);
		pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				getActivity().startActivityForResult(i, 100);
			}

		});

	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	// load data from the intent
	private void loadData() {

		Log.d(TAG, "loadData");

		if (character.getPic() != null) {
			Log.d(TAG, character.getPic());
			if (!character.getPic().equals(Character.NO_PICTURE)) {
				Uri uri = Uri.parse(character.getPic());
				Bitmap bitmap;
				try {
					bitmap = BitmapFactory.decodeStream(getActivity()
							.getContentResolver().openInputStream(uri));
					pic.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		name.setText(character.getName());
		age.setText(String.valueOf(character.getAge()));
		species.setText(character.getSpecies());
		charClass.setText(character.getCharClass());
		level.setText(String.valueOf(character.getLevel()));
		curHP.setText(String.valueOf(character.getCurHP()));
		maxHP.setText(String.valueOf(character.getMaxHP()));
		curExp.setText(String.valueOf(character.getCurExp()));
		nextLvlExp.setText(String.valueOf(character.getNxtLevelExp()));
		init.setText(String.valueOf(character.getInit()));
		AC.setText(String.valueOf(character.getAC()));
		STR.setText(String.valueOf(character.getSTR()));
		WILL.setText(String.valueOf(character.getWILL()));
		DEX.setText(String.valueOf(character.getDEX()));
		CON.setText(String.valueOf(character.getCON()));
		INT.setText(String.valueOf(character.getINT()));
		CHR.setText(String.valueOf(character.getCHR()));
		speed.setText(String.valueOf(character.getSpeed()));
		align.setText(character.getAlign());
		BAB.setText(String.valueOf(character.getBAB()));
		CMB.setText(String.valueOf(character.getCMB()));
		CMD.setText(String.valueOf(character.getCMD()));
		WIS.setText(String.valueOf(character.getWIS()));
		FORT.setText(String.valueOf(character.getFORT()));
		REF.setText(String.valueOf(character.getREF()));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstance");

		super.onSaveInstanceState(outState);

		outState.putString(NAME, name.getText().toString());
		if (!age.getText().toString().isEmpty())
			outState.putInt(AGE, Integer.parseInt(age.getText().toString()));
		outState.putString(SPECIES, species.getText().toString());
		outState.putString(CHARCLASS, charClass.getText().toString());
		if (!level.getText().toString().isEmpty())
			outState.putInt(LEVEL, Integer.parseInt(level.getText().toString()));
		if (!curHP.getText().toString().isEmpty())
			outState.putInt(CURHP, Integer.parseInt(curHP.getText().toString()));
		if (!maxHP.getText().toString().isEmpty())
			outState.putInt(MAXHP, Integer.parseInt(maxHP.getText().toString()));
		if (!curExp.getText().toString().isEmpty())
			outState.putInt(CUREXP,
					Integer.parseInt(curExp.getText().toString()));
		if (!nextLvlExp.getText().toString().isEmpty())
			outState.putInt(NEXTLVLEXP,
					Integer.parseInt(nextLvlExp.getText().toString()));
		if (!init.getText().toString().isEmpty())
			outState.putInt(INIT, Integer.parseInt(init.getText().toString()));
		if (!AC.getText().toString().isEmpty())
			outState.putInt(SAC, Integer.parseInt(AC.getText().toString()));
		if (!STR.getText().toString().isEmpty())
			outState.putInt(SSTR, Integer.parseInt(STR.getText().toString()));
		if (!WILL.getText().toString().isEmpty())
			outState.putInt(SWILL, Integer.parseInt(WILL.getText().toString()));
		if (!DEX.getText().toString().isEmpty())
			outState.putInt(SDEX, Integer.parseInt(DEX.getText().toString()));
		if (!CON.getText().toString().isEmpty())
			outState.putInt(SCON, Integer.parseInt(CON.getText().toString()));
		if (!INT.getText().toString().isEmpty())
			outState.putInt(SINT, Integer.parseInt(INT.getText().toString()));
		if (!CHR.getText().toString().isEmpty())
			outState.putInt(SCHR, Integer.parseInt(CHR.getText().toString()));
		if (!speed.getText().toString().isEmpty())
			outState.putInt(SPEED, Integer.parseInt(speed.getText().toString()));
		outState.putString(ALIGN, align.getText().toString());
		if (!CMD.getText().toString().isEmpty())
			outState.putInt(SCMD, Integer.parseInt(CMD.getText().toString()));
		if (!BAB.getText().toString().isEmpty())
			outState.putInt(SBAB, Integer.parseInt(BAB.getText().toString()));
		if (!CMB.getText().toString().isEmpty())
			outState.putInt(SCMB, Integer.parseInt(CMB.getText().toString()));
		if (!WIS.getText().toString().isEmpty())
			outState.putInt(SWIS, Integer.parseInt(WIS.getText().toString()));
		if (!FORT.getText().toString().isEmpty())
			outState.putInt(SFORT, Integer.parseInt(FORT.getText().toString()));
		if (!REF.getText().toString().isEmpty())
			outState.putInt(SREF, Integer.parseInt(REF.getText().toString()));

		if (pic.getTag() != null) {
			if (pic.getTag().toString().equals(Character.NO_PICTURE))
				outState.putString(PIC, Character.NO_PICTURE);
			else
				outState.putString(PIC, pic.getTag().toString());
		}

	}
}