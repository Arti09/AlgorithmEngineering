package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;

public class SoundexAlg {
	Soundex algorithm;
	Map<String, Integer> map = new HashMap<String, Integer>();

	public SoundexAlg() {
		algorithm = new Soundex();
	}

	public void getStringRepresantation(String toConvert) {
		String str = algorithm.encode(toConvert);
		if (map.containsKey(str)) {
			int temp = map.get(str);
			map.put(str, temp + 1);
		} else {
			map.put(str, 1);
		}
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public int calculateDiffrence(Map<String, Integer> mapOther) {
		Set<String> otherKeys = mapOther.keySet();
		List<String> temp = new ArrayList<String>();
		Set<String> keys = map.keySet();
		for (String key : otherKeys) {
			if (!keys.contains(key)) {
				temp.add(key);
			}
		}

		int difference = 0;
		for (String str : map.keySet()) {
			if (mapOther.containsKey(str)) {
				difference += Math.abs(map.get(str) - mapOther.get(str));
			} else {
				difference += map.get(str);
			}
		}

		for (String str : temp) {
			difference += mapOther.get(str);
		}

		return difference;
	}

	public int compareStrings(String s1, String s2) {
		try {
			return algorithm.difference(s1, s2);
		} catch (EncoderException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
