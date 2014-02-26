package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import core.LevenSt;
import core.MetaphoneAlg;
import core.SoundexAlg;

@BenchmarkOptions(callgc = false, benchmarkRounds = 200, warmupRounds = 3)
public class TestConversion {
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
     
    List<String> liste = new ArrayList<String>();
    List<String> toCompare = new ArrayList<String>();
    int i = 4;
    int test = 0;

    @Before
    public void onBefore() {
//	System.out.println("----- Start Test -----");
	readFile(true);
	readFile(false);
    }

    @After
    public void onAfter() {
	liste.clear();
	toCompare.clear();
//	System.out.println("----- Finish Test -----\n");
    }

    public void test() {
	SoundexAlg alg = new SoundexAlg();
	System.out.println("Unterschied Soundex = "
		+ alg.compareStrings(liste.get(i), toCompare.get(i)));

	LevenSt alg2 = new LevenSt();
	System.out.println("Unterschied Levenshtein = "
		+ alg2.getLevenSt(liste.get(i), toCompare.get(i)));

	MetaphoneAlg alg3 = new MetaphoneAlg();
	System.out.println("Unterschied Metaphone = "
		+ alg3.getDiffrence(liste.get(i), toCompare.get(i)));
    }

    @Test
    public void Metaphone() {
	int amount1 = 0;
	MetaphoneAlg alg = new MetaphoneAlg();
	for (String str : liste) {
	    for (String str2 : str.split(" ")) {
		alg.getStringRepresantation(str2);
		amount1++;
	    }
	}
	int amount2 = 0;
	MetaphoneAlg alg2 = new MetaphoneAlg();
	for (String str : toCompare) {
	    for (String str2 : str.split(" ")) {
		alg2.getStringRepresantation(str2);
		amount2++;
	    }
	}
//	System.out.println("Anzahl 1 = " + amount1 + " Anzahl 2 = " + amount2);
//	System.out.println("Unterschied = "
//		+ alg.calculateDiffrence(alg2.getMap()));
    }

    @Test
    public void Soundex() {
	int amount1 = 0;
	SoundexAlg alg = new SoundexAlg();
	for (String str : liste) {
	    for (String str2 : str.split(" ")) {
		alg.getStringRepresantation(str2);
		amount1++;
	    }
	}
	int amount2 = 0;
	SoundexAlg alg2 = new SoundexAlg();
	for (String str : toCompare) {
	    for (String str2 : str.split(" ")) {
		alg2.getStringRepresantation(str2);
		amount2++;
	    }
	}

//	System.out.println("Anzahl 1 = " + amount1 + " Anzahl 2 = " + amount2);
//	System.out.println("Unterschied = "
//		+ alg.calculateDiffrence(alg2.getMap()));
    }

    @Test
    public void Leven() {
	int amount1 = 0;
	LevenSt alg = new LevenSt();
	for (String str : liste) {
	    for (String str2 : str.split(" ")) {
		alg.addString(str2);
		amount1++;
	    }
	}
	int amount2 = 0;
	LevenSt alg2 = new LevenSt();
	for (String str : toCompare) {
	    for (String str2 : str.split(" ")) {
		alg2.addString(str2);
		amount2++;
	    }
	}
//	System.out.println("Anzahl 1 = " + amount1 + " Anzahl 2 = " + amount2);
//	System.out.println("Unterschied = "
//		+ alg.calculateDiffrence(alg2.getList()));
    }

    private void readFile(boolean original) {
	String file;
	if (original) {
	    file = "test.txt";
	} else {
	    file = "test2.txt";
	}
	BufferedReader br = null;
	try {
	    br = new BufferedReader(new FileReader(new File(file)));
	    String line = null;
	    while ((line = br.readLine()) != null) {
		if (line.contains("ä")) {
		    line = line.replaceAll("ä", "ae");
		}
		if (line.contains("ü")) {
		    line = line.replaceAll("ü", "ue");
		}
		if (line.contains("ö")) {
		    line = line.replaceAll("ö", "oe");
		}
		if (line.contains("ß")) {
		    line = line.replaceAll("ß", "ss");
		}

		if (original) {
		    liste.add(line);
		} else {
		    toCompare.add(line);
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
}
