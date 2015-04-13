package itp341.maulik.dipanwita.project.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartScreenActivity extends Activity {

	private static final String TAG = StartScreenActivity.class.getSimpleName();

	TextView textCharacters;
	TextView textExtras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_start_screen);

		textCharacters = (TextView) findViewById(R.id.textStartChar);
		textCharacters.setClickable(true);
		textCharacters.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.putExtra(MainActivity.CHAR, true);
				startActivity(i);
			}

		});
		
		textExtras = (TextView) findViewById(R.id.textStartExtras);
		textExtras.setClickable(true);
		textExtras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.putExtra(MainActivity.EXTRA, true);
				startActivity(i);
			}

		});
	}
}