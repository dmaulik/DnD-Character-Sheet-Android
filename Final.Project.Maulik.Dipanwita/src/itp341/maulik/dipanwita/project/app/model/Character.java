package itp341.maulik.dipanwita.project.app.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Character implements Serializable {

	public static final String NO_PICTURE = "no picture";
	private static final String JSON_PIC = "pic";
	private static final String JSON_NAME = "name";
	private static final String JSON_AGE = "age";
	private static final String JSON_SPECIES = "species";
	private static final String JSON_CLASS = "class";
	private static final String JSON_LVL = "lvl";
	private static final String JSON_CURHP = "curhp";
	private static final String JSON_MAXHP = "maxhp";
	private static final String JSON_CUREXP = "curexp";
	private static final String JSON_NXTLVLEXP = "nxtlvlexp";
	private static final String JSON_INIT = "init";
	private static final String JSON_AC = "ac";
	private static final String JSON_STR = "str";
	private static final String JSON_CON = "con";
	private static final String JSON_DEX = "dex";
	private static final String JSON_WIS = "wis";
	private static final String JSON_INT = "int";
	private static final String JSON_CHR = "chr";
	private static final String JSON_FORT = "fort";
	private static final String JSON_REF = "ref";
	private static final String JSON_WILL = "will";
	private static final String JSON_SPEED = "speed";
	private static final String JSON_ALIGN = "align";
	private static final String JSON_BAB = "bab";
	private static final String JSON_CMD = "cmd";
	private static final String JSON_CMB = "cmb";
	private static final String JSON_FEATS = "feats";
	private static final String JSON_SKILLS = "skills";
	private static final String JSON_LANGUAGES = "languages";
	private static final String JSON_MAGIC = "magic";
	private static final String JSON_ARMOR = "armor";
	private static final String JSON_WEAPONS = "weapons";
	private static final String JSON_MISC = "misc";
	private static final String JSON_EXTRAS = "extras";

	private String pic;
	private String name;
	private int age;
	private String species;
	private String charClass;
	private int level;

	private int curHP;
	private int maxHP;
	private int curExp;
	private int nxtLevelExp;
	private int init;
	private int AC;
	private int STR;
	private int CON;
	private int DEX;
	private int WIS;
	private int INT;
	private int CHR;
	private int FORT;
	private int REF;
	private int WILL;
	private int speed;
	private String align;
	private int BAB;
	private int CMD;
	private int CMB;

	private ArrayList<String> feats;
	private ArrayList<String> skills;
	private ArrayList<String> languages;
	private ArrayList<String> weapons;
	private ArrayList<String> armors;
	private ArrayList<String> magic;
	private ArrayList<String> misc;

	private ArrayList<String> extras;

	public Character() {
	}

	public Character(String pic, String name, int age, String species,
			String charClass, int level, int curHP, int maxHP, int curExp,
			int nxtLevelExp, int init, int AC, int STR, int CON, int DEX,
			int WIS, int INT, int CHR, int FORT, int REF, int WILL, int speed,
			String align, int BAB, int CMD, int CMB, ArrayList<String> feats,
			ArrayList<String> skills, ArrayList<String> languages,
			ArrayList<String> weapons, ArrayList<String> armors,
			ArrayList<String> magic, ArrayList<String> misc,
			ArrayList<String> extras) {
		this.pic = pic;
		this.name = name;
		this.age = age;
		this.species = species;
		this.charClass = charClass;
		this.level = level;
		this.curHP = curHP;
		this.maxHP = maxHP;
		this.curExp = curExp;
		this.nxtLevelExp = nxtLevelExp;
		this.init = init;
		this.AC = AC;
		this.STR = STR;
		this.CON = CON;
		this.DEX = DEX;
		this.WIS = WIS;
		this.INT = INT;
		this.CHR = CHR;
		this.FORT = FORT;
		this.REF = REF;
		this.WILL = WILL;
		this.speed = speed;
		this.align = align;
		this.BAB = BAB;
		this.CMD = CMD;
		this.CMB = CMB;

		this.feats = feats;
		this.skills = skills;
		this.languages = languages;
		this.weapons = weapons;
		this.armors = armors;
		this.magic = magic;
		this.misc = misc;

		this.extras = extras;
	}

	public Character(JSONObject json) throws JSONException {
		this.pic = json.getString(JSON_PIC);
		this.name = json.getString(JSON_NAME);
		this.age = json.getInt(JSON_AGE);
		this.species = json.getString(JSON_SPECIES);
		this.charClass = json.getString(JSON_CLASS);
		this.level = json.getInt(JSON_LVL);
		this.curHP = json.getInt(JSON_CURHP);
		this.maxHP = json.getInt(JSON_MAXHP);
		this.curExp = json.getInt(JSON_CUREXP);
		this.nxtLevelExp = json.getInt(JSON_NXTLVLEXP);
		this.init = json.getInt(JSON_INIT);
		this.AC = json.getInt(JSON_AC);
		this.STR = json.getInt(JSON_STR);
		this.CON = json.getInt(JSON_CON);
		this.DEX = json.getInt(JSON_DEX);
		this.WIS = json.getInt(JSON_WIS);
		this.INT = json.getInt(JSON_INT);
		this.CHR = json.getInt(JSON_CHR);
		this.FORT = json.getInt(JSON_FORT);
		this.REF = json.getInt(JSON_REF);
		this.WILL = json.getInt(JSON_WILL);
		this.speed = json.getInt(JSON_SPEED);
		this.align = json.getString(JSON_ALIGN);
		this.BAB = json.getInt(JSON_BAB);
		this.CMD = json.getInt(JSON_CMD);
		this.CMB = json.getInt(JSON_CMB);

		boolean hasFeats = true;
		boolean hasSkills = true;
		boolean hasMagic = true;
		boolean hasLanguages = true;
		boolean hasArmor = true;
		boolean hasWeapons = true;
		boolean hasMisc = true;
		boolean hasExtras = true;

		//instantiate all the arrays
		feats = new ArrayList<String>();
		skills = new ArrayList<String>();
		magic = new ArrayList<String>();
		languages = new ArrayList<String>();
		armors = new ArrayList<String>();
		weapons = new ArrayList<String>();
		misc = new ArrayList<String>();
		extras = new ArrayList<String>();
		
		JSONArray f = null;
		try {
			f = json.getJSONArray(JSON_FEATS);
		} catch (JSONException j) {
			hasFeats = false;
		}

		if (hasFeats) {
			for (int i = 0; i < f.length(); i++) {
				feats.add(f.get(i).toString());
			}
		}

		JSONArray s = null;

		try {
			s = json.getJSONArray(JSON_SKILLS);
		} catch (JSONException j) {
			hasSkills = false;
		}
		if (hasSkills) {
			for (int i = 0; i < s.length(); i++) {
				skills.add(s.get(i).toString());
			}
		}

		JSONArray l = null;
		try {
			l = json.getJSONArray(JSON_LANGUAGES);
		} catch (JSONException j) {
			hasLanguages = false;
		}
		if (hasLanguages) {
			for (int i = 0; i < l.length(); i++) {
				languages.add(l.get(i).toString());
			}
		}

		JSONArray w = null;
		try {
			w = json.getJSONArray(JSON_WEAPONS);
		} catch (JSONException j) {
			hasWeapons = false;
		}
		if (hasWeapons) {
			for (int i = 0; i < w.length(); i++) {
				weapons.add(w.get(i).toString());
			}
		}

		JSONArray a = null;
		try {
			a = json.getJSONArray(JSON_ARMOR);
		} catch (JSONException j) {
			hasArmor = false;
		}
		if (hasArmor) {
			for (int i = 0; i < a.length(); i++) {
				armors.add(a.get(i).toString());
			}
		}

		JSONArray ma = null;
		try {
			ma = json.getJSONArray(JSON_MAGIC);
		} catch (JSONException j) {
			hasMagic = false;
		}
		if (hasMagic) {
			for (int i = 0; i < ma.length(); i++) {
				magic.add(ma.get(i).toString());
			}
		}

		JSONArray m = null;
		try {
			m = json.getJSONArray(JSON_MISC);
		} catch (JSONException j) {
			hasMisc = false;
		}

		if (hasMisc) {
			for (int i = 0; i < m.length(); i++) {
				misc.add(m.get(i).toString());
			}
		}

		JSONArray se = null;
		try {
			se = json.getJSONArray(JSON_EXTRAS);
		} catch (JSONException j) {
			hasExtras = false;
		}
		if (hasExtras) {
			for (int i = 0; i < se.length(); i++) {
				extras.add(se.get(i).toString());
			}
		}
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();

		if(this.pic != null)
			json.put(JSON_PIC, this.pic);
		else
			json.put(JSON_PIC, NO_PICTURE);
		json.put(JSON_NAME, this.name);
		json.put(JSON_AGE, this.age);
		json.put(JSON_SPECIES, this.species);
		json.put(JSON_CLASS, this.charClass);
		json.put(JSON_LVL, this.level);
		json.put(JSON_CURHP, this.curHP);
		json.put(JSON_MAXHP, this.maxHP);
		json.put(JSON_CUREXP, this.curExp);
		json.put(JSON_NXTLVLEXP, this.nxtLevelExp);
		json.put(JSON_AC, this.AC);
		json.put(JSON_INIT, this.init);
		json.put(JSON_STR, this.STR);
		json.put(JSON_CON, this.CON);
		json.put(JSON_DEX, this.DEX);
		json.put(JSON_WIS, this.WIS);
		json.put(JSON_INT, this.INT);
		json.put(JSON_CHR, this.CHR);
		json.put(JSON_FORT, this.FORT);
		json.put(JSON_REF, this.REF);
		json.put(JSON_WILL, this.WILL);
		json.put(JSON_SPEED, this.speed);
		json.put(JSON_ALIGN, this.align);
		json.put(JSON_BAB, this.BAB);
		json.put(JSON_CMB, this.CMB);
		json.put(JSON_CMD, this.CMD);

		// instantiate these arrays since they might be null if we never added any of these features
		if(this.feats == null)
			this.feats = new ArrayList<String>();
		if(this.skills == null)
			this.skills = new ArrayList<String>();
		if(this.magic == null)
			this.magic = new ArrayList<String>();
		if(this.armors == null)
			this.armors = new ArrayList<String>();
		if(this.languages == null)
			this.languages = new ArrayList<String>();
		if(this.weapons == null)
			this.weapons = new ArrayList<String>();
		if(this.misc == null)
			this.misc = new ArrayList<String>();
		if(this.extras == null)
			this.extras = new ArrayList<String>();
		json.put(JSON_FEATS, new JSONArray(this.feats));
		json.put(JSON_SKILLS, new JSONArray(this.skills));
		json.put(JSON_MAGIC, new JSONArray(this.magic));
		json.put(JSON_LANGUAGES, new JSONArray(this.languages));
		json.put(JSON_ARMOR, new JSONArray(this.armors));
		json.put(JSON_WEAPONS, new JSONArray(this.weapons));
		json.put(JSON_MISC, new JSONArray(this.misc));
		json.put(JSON_EXTRAS, new JSONArray(this.extras));

		return json;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSpecies() {
		return this.species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getCharClass() {
		return this.charClass;
	}

	public void setCharClass(String charClass) {
		this.charClass = charClass;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurHP() {
		return this.curHP;
	}

	public void setCurHP(int curHP) {
		this.curHP = curHP;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getCurExp() {
		return this.curExp;
	}

	public void setCurExp(int curExp) {
		this.curExp = curExp;

	}

	public int getNxtLevelExp() {
		return this.nxtLevelExp;
	}

	public void setNxtLevelExp(int nxtLevelExp) {
		this.nxtLevelExp = nxtLevelExp;
	}

	public int getSTR() {
		return this.STR;
	}

	public void setSTR(int STR) {
		this.STR = STR;
	}

	public int getCON() {
		return this.CON;
	}

	public void setCON(int CON) {
		this.CON = CON;
	}

	public int getDEX() {
		return this.DEX;
	}

	public void setDEX(int DEX) {
		this.DEX = DEX;
	}

	public int getWIS() {
		return this.WIS;
	}

	public void setWIS(int WIS) {
		this.WIS = WIS;
	}

	public int getINT() {
		return this.INT;
	}

	public void setINT(int INT) {
		this.INT = INT;
	}

	public int getCHR() {
		return this.CHR;
	}

	public void setCHR(int CHR) {
		this.CHR = CHR;
	}

	public int getInit() {
		return this.init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getAC() {
		return this.AC;
	}

	public void setAC(int AC) {
		this.AC = AC;
	}

	public int getWILL() {
		return this.WILL;
	}

	public void setWILL(int WILL) {
		this.WILL = WILL;
	}

	public int getFORT() {
		return this.FORT;
	}

	public void setFORT(int FORT) {
		this.FORT = FORT;
	}

	public int getREF() {
		return this.REF;
	}

	public void setREF(int REF) {
		this.REF = REF;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getAlign() {
		return this.align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public int getBAB() {
		return this.BAB;
	}

	public void setBAB(int BAB) {
		this.BAB = BAB;
	}

	public int getCMD() {
		return this.CMD;
	}

	public void setCMD(int CMD) {
		this.CMD = CMD;
	}

	public int getCMB() {
		return this.CMB;
	}

	public void setCMB(int CMB) {
		this.CMB = CMB;
	}

	public ArrayList<String> getFeats() {
		return this.feats;
	}

	public void setFeats(ArrayList<String> feats) {
		this.feats = feats;
	}

	public ArrayList<String> getSkills() {
		return this.skills;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}

	public ArrayList<String> getLanguages() {
		return this.languages;
	}

	public void setLanguages(ArrayList<String> languages) {
		this.languages = languages;
	}

	public ArrayList<String> getMagic() {
		return this.magic;
	}

	public void setMagic(ArrayList<String> magic) {
		this.magic = magic;
	}

	public ArrayList<String> getArmors() {
		return this.armors;
	}

	public void setArmors(ArrayList<String> armors) {
		this.armors = armors;
	}

	public ArrayList<String> getWeapons() {
		return this.weapons;
	}

	public void setWeapons(ArrayList<String> weapons) {
		this.weapons = weapons;
	}

	public ArrayList<String> getMisc() {
		return this.misc;
	}

	public void setMisc(ArrayList<String> misc) {
		this.misc = misc;
	}

	public ArrayList<String> getExtras() {
		return this.extras;
	}

	public void setExtras(ArrayList<String> extras) {
		this.extras = extras;
	}
}