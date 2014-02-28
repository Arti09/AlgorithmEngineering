package core;

import java.util.ArrayList;
import java.util.List;

public class SemiGlobalAlg {

	List<String> liste = new ArrayList<String>();

	public SemiGlobalAlg() {
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
				difference += getDifference(liste.get(i), listeOther.get(i));
			} else {
				difference += getDifference(liste.get(i), "");
			}
		}

		if (listeOther.size() > liste.size()) {
			for (int i = liste.size(); i < listeOther.size(); i++) {
				difference += getDifference("", listeOther.get(i));
			}
		}

		return difference;
	}

	private int getDifference(String first, String second) {
		String shorter = "";
		String longer = "";
		if (first.length() < second.length()) {
			shorter = shorter.concat(first);
			longer = longer.concat(second);
		} else {
			shorter = shorter.concat(second);
			longer = longer.concat(first);
		}

		int shorterLng = shorter.length();
		int longerLng = longer.length();

		int[][] temp = new int[shorterLng + 1][longerLng + 1];
		for (int i = 0; i < longerLng + 1; i++) {
			temp[0][i] = 0;
		}
		for (int i = 1; i < shorterLng + 1; i++) {
			temp[i][0] = temp[i - 1][0] - 2;
		}
		for (int i = 1; i < shorterLng + 1; i++) {
			for (int j = 1; j < longerLng + 1; j++) {
				boolean same = (shorter.charAt(i - 1) == longer.charAt(j - 1));
				temp[i][j] = getMax(temp, i, j, same);
			}
		}

		return temp[shorter.length()][longer.length()];
	}

	private int getMax(int[][] temp, int i, int j, boolean same) {
		int leftUp = temp[i - 1][j - 1] + (same ? 1 : -1);
		int left = temp[i][j - 1] - 2;
		int up = temp[i - 1][j] - 2;

		return Math.max(Math.max(left, up), leftUp);
	}
}
