package core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class LevenSt {
    List<String> liste = new ArrayList<String>();

    public int getLevenSt(String s1, String s2) {
	return (StringUtils.getLevenshteinDistance(s1, s2));
    }

    public void addString(String toConvert) {
	liste.add(toConvert);
    }

    public List<String> getList() {
	return liste;
    }

    public int calculateDiffrence(List<String> listeOther) {
	int difference = 0;
	for (int i = 0; i < liste.size(); i++) {
	    if (listeOther.size() > i) {
		difference += StringUtils.getLevenshteinDistance(liste.get(i),
			listeOther.get(i));
	    } else {
		difference += StringUtils.getLevenshteinDistance(liste.get(i),
			"");
	    }
	}

	if (listeOther.size() > liste.size()) {
	    for (int i = liste.size(); i < listeOther.size(); i++) {
		difference += StringUtils.getLevenshteinDistance("",
			listeOther.get(i));
	    }
	}

	return difference;
    }

}
