package itp341.maulik.dipanwita.project.app.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class CharacterDataStore {
	private static final String TAG = CharacterDataStore.class.getSimpleName();
	private static final String FILENAME = "characters.json";

	public static void saveCharacters(ArrayList<Character> characters,
			Context context) {
		JSONArray array = new JSONArray();
		for (Character n : characters)
			try {
				array.put(n.toJSON());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		Writer writer = null;
		OutputStream out;
		try {
			out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Character> loadCharacters(Context context)
			throws ParseException {
		Log.d(TAG, "loadcharacters");

		ArrayList<Character> characters = new ArrayList<Character>();
		BufferedReader reader = null;

		// get the characters stored in the local file directory
		try {
			InputStream in = context.openFileInput(FILENAME);
			if (in != null) {

				reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					jsonString.append(line);
				}
				JSONArray array = (JSONArray) new JSONTokener(
						jsonString.toString()).nextValue();
				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);

					Log.d(TAG, "creating character from json");
					Character n = new Character(json);
					Log.d(TAG, "adding character to array");
					characters.add(n);
				}
			}
			
			if (reader != null)
				reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return characters;
	}
}