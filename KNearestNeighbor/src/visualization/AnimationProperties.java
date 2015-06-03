package visualization;

import java.util.HashMap;

public class AnimationProperties {
	private HashMap<String, Float> floatMap = new HashMap<String, Float>();
	private HashMap<String, Integer> integerMap = new HashMap<String, Integer>();
	private HashMap<String, Boolean> booleanMap = new HashMap<String, Boolean>();

	public AnimationProperties(String[] floatKeys, float[] floatValues, String[] intKeys, int[] intValues, String[] booleanKeys, boolean[] booleanValues) {
		if(floatKeys != null && floatValues != null) {
			set(floatKeys, floatValues);
		}

		if(intKeys != null && intValues != null) {
			set(intKeys, intValues);
		}

		if(booleanKeys != null && booleanValues != null) {
			set(booleanKeys, booleanValues);
		}
	}

	public void set(String key, float value) {
		floatMap.put(key, new Float(value));
	}

	public void set(String key, int value) {
		integerMap.put(key, new Integer(value));
	}

	public void set(String key, boolean value) {
		booleanMap.put(key, new Boolean(value));
	}

	public void set(String[] keys, float[] values) {
		for(int i = 0; i < keys.length; i++) {
			floatMap.put(keys[i], new Float(values[i]));
		}
	}

	public void set(String[] keys, int[] values) {
		for(int i = 0; i < keys.length; i++) {
			integerMap.put(keys[i], new Integer(values[i]));
		}
	}

	public void set(String[] keys, boolean[] values) {
		for(int i = 0; i < keys.length; i++) {
			booleanMap.put(keys[i], new Boolean(values[i]));
		}
	}

	public float getFloat(String key) {
		return floatMap.get(key).floatValue();
	}

	public int getInt(String key) {
			return integerMap.get(key).intValue();
	}

	public boolean getBoolean(String key) {
		return booleanMap.get(key).booleanValue();
	}
}