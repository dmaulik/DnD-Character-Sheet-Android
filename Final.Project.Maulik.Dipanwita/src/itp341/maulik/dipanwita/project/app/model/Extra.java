package itp341.maulik.dipanwita.project.app.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Extra implements Serializable
{
	private static final String JSON_NAME = "name";
	private static final String JSON_TYPE = "type";
	private static final String JSON_DESCRIPTION = "description";
	
	public static final String NUMBER = "number";
	public static final String STRING = "string";
	
	private String name;
	private String type;
	private String description;
	
	public Extra() {}
	
	public Extra(String name, String type, String description)
	{
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
	public Extra(JSONObject json) throws JSONException
	{
		this.name = json.getString(JSON_NAME);
		this.type = json.getString(JSON_TYPE);
		this.description = json.getString(JSON_DESCRIPTION);
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_NAME, this.name);
		json.put(JSON_TYPE, this.type);
		json.put(JSON_DESCRIPTION, this.description);
		
		return json;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
}