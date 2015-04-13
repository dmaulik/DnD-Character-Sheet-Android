package itp341.maulik.dipanwita.project.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartScreenActivity extends Activity {

	private static final String TAG = StartScreenActivity.class.getSimpleName();

	Button buttonGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_start_screen);

		buttonGo = (Button) findViewById(R.id.buttonStart);
		buttonGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
			}

		});
	}
}