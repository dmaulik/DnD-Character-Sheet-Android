package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.Extra;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddDialogFragment extends DialogFragment {

	private static final String TAG = AddDialogFragment.class.getSimpleName();

	public static final String IS_EXTRA = "is_extra";
	public static final String EXTRAS = "extras";
	public static final String NAME = "name";
	public static final String DESC = "desc";
	public static final String TYPE = "type";
	public static final String POSITION = "position";

	View view;

	EditText addName;
	EditText addDesc;
	TextView extraDesc;
	Spinner chooseExtra;
	boolean isExtra;

	String type;
	int position;

	public interface AddDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String type,
				String name, String desc, int position);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	private AddDialogListener mListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		// get the type of attribute we're editing, its position in its table,
		// and whether or not its an extra
		type = getArguments().getString(AddDialogFragment.TYPE);
		position = getArguments().getInt(AddDialogFragment.POSITION);
		isExtra = getArguments().getBoolean(IS_EXTRA);

		// attach a listener to the fragment
		try {
			mListener = (AddDialogListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Fragment must implement AddDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Log.d(TAG, "onCreateDialog");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// the layout of the dialog depends on whether or not it you are editing
		// an extra; extras have a spinner instead of an editext for editing
		// names
		if (!isExtra)
			view = inflater.inflate(R.layout.dialog_add, null);
		else
			view = inflater.inflate(R.layout.dialog_add_extra, null);
		builder.setView(view);

		// if the user clicks ok, update/add the attribute. if cancel, do
		// nothing
		builder.setPositiveButton(R.string.labelAdd,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						String name, desc;

						if (!isExtra)
							name = addName.getText().toString();
						else
							name = chooseExtra.getSelectedItem().toString();

						desc = addDesc.getText().toString();
						mListener.onDialogPositiveClick(AddDialogFragment.this,
								type, name, desc, position);
					}
				});

		builder.setNegativeButton(R.string.labelCancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						mListener.onDialogNegativeClick(AddDialogFragment.this);
					}
				});

		return builder.create();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");

		super.onActivityCreated(savedInstanceState);

		int pos = -1;

		// initialize either the spinner or the edittext depending on what we're
		// adding/changing
		if (!isExtra) {
			addName = (EditText) view.findViewById(R.id.editAddName);
			addDesc = (EditText) view.findViewById(R.id.editAddDesc);
			if (getArguments().getString(NAME) != null)
				addName.setText(getArguments().getString(NAME));
		} else {
			addDesc = (EditText) view.findViewById(R.id.editAddExtraVal);
			extraDesc = (TextView) view.findViewById(R.id.textAddExtraDesc);
			chooseExtra = (Spinner) view.findViewById(R.id.spinnerChooseExtra);
			ArrayList<Extra> e = (ArrayList<Extra>) getArguments()
					.getSerializable(EXTRAS);
			ArrayAdapter<String> sa = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_dropdown_item);
			for (int i = 0; i < e.size(); i++) {
				sa.add(e.get(i).getName());
				if (getArguments().getString(NAME) != null
						&& e.get(i).getName().equals(getArguments().getString(NAME)))
					extraDesc.setText(e.get(i).getDescription());
			}

			// if we're editing an extra we want to ensure users can't change
			// the name from this screen, only the description
			if (getArguments().getString(NAME) != null) {
				chooseExtra.setEnabled(false);
				chooseExtra.setClickable(false);
				pos = sa.getPosition(getArguments().getString(NAME));
				chooseExtra.setSelection(pos);
			} else 
			{
				chooseExtra.setOnItemSelectedListener(new OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String sel = arg0.getItemAtPosition(arg2).toString();
						ArrayList<Extra> e = (ArrayList<Extra>) getArguments().getSerializable(EXTRAS);
						for(int i = 0; i < e.size(); i++)
						{
							if(e.get(i).getName().equals(sel))
							{
								extraDesc.setText(e.get(i).getDescription());
								break;
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						extraDesc.setText("");
					}
					
				});
			}

			chooseExtra.setAdapter(sa);
		}

		// add the former description if we're editing
		if (getArguments().getString(DESC) != null)
			addDesc.setText(getArguments().getString(DESC));
	}
}