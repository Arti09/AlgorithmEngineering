package core;

import org.apache.commons.lang3.StringUtils;

public class LevenSt {
	public int getLevenSt(String s1, String s2) {
		return (StringUtils.getLevenshteinDistance(s1, s2));
	}
}
