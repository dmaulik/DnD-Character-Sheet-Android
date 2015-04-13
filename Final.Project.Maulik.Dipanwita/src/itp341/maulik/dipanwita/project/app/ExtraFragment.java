package itp341.maulik.dipanwita.project.app;

import itp341.maulik.dipanwita.project.app.model.Extra;
import itp341.maulik.dipanwita.project.app.model.ExtraAdapter;
import itp341.maulik.dipanwita.project.app.model.ExtraDataStore;

import java.text.ParseException;
import java.util.ArrayList;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExtraFragment extends ListFragment {

	//TODO: have a few default extras
	
	public static final String TAG = ExtraFragment.class.getSimpleName();
	
	private ArrayList<Extra> extras;
	private ExtraAdapter extraAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreateView");
		
		try {
			extras = ExtraDataStore.loadExtras(getActivity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		extraAdapter = new ExtraAdapter(inflater.getContext(), extras);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		setListAdapter(extraAdapter);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d(TAG, "onListItemClick");

		Intent i = new Intent(getActivity(), AddEditExtraActivity.class);
		i.putExtra(AddEditExtraActivity.EXTRA_EXTRA_OBJECT, extras.get(position));
		startActivityForResult(i, position);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult");
		
		super.onActivityResult(requestCode, resultCode, data);
		
		Extra extra = (Extra) data.getSerializableExtra(AddEditExtraActivity.EXTRA_EXTRA_OBJECT);
		if(extra.getName() != null)
		{
			extras.set(requestCode, extra);	
			extraAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		
		super.onPause();
		ExtraDataStore.saveExtras(extras, getActivity());
	}
	
	public ArrayList<Extra> getExtras()
	{
		return this.extras;
	}
	
	public ExtraAdapter getExtraAdapter()
	{
		return this.extraAdapter;
	}
}
