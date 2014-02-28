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
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

import core.LevenSt;
import core.MetaphoneAlg;
import core.SemiGlobalAlg;
import core.SoundexAlg;

@AxisRange(min = 0, max = 9)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkHistoryChart(labelWith = LabelType.RUN_ID, maxRuns = 20, filePrefix = "benchmark-chart2")
@BenchmarkOptions(callgc = true, benchmarkRounds = 1, warmupRounds = 0)
public class TestConversion {

	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	List<String> liste = new ArrayList<String>();
	int listeSize = 0;
	List<String> toCompare = new ArrayList<String>();
	int listeCompSize = 0;

	@Before
	public void onBefore() {
		readFile(true);
		readFile(false);
	}

	@After
	public void onAfter() {
		liste.clear();
		toCompare.clear();
	}

	@Test
	public void Metaphone() {
		MetaphoneAlg alg = new MetaphoneAlg();
		for (String str : liste) {
			for (String str2 : str.split(" ")) {
				alg.getStringRepresantation(str2);
			}
		}
		MetaphoneAlg alg2 = new MetaphoneAlg();
		for (String str : toCompare) {
			for (String str2 : str.split(" ")) {
				alg2.getStringRepresantation(str2);
			}
		}

		int unterschied = alg.calculateDiffrence(alg2.getMap());
		// System.out.println("\n Unterschied = " + unterschied);
		// System.out.format("Prozentual zu 1 = %f \n",
		// (100 * ((double) unterschied / liste.size())));
		// System.out.format("Prozentual zu 2 = %f \n",
		// (100 * ((double) unterschied / toCompare.size())));

	}

	@Test
	public void Soundex() {
		SoundexAlg alg = new SoundexAlg();
		for (String str : liste) {
			for (String str2 : str.split(" ")) {
				alg.getStringRepresantation(str2);
			}
		}
		SoundexAlg alg2 = new SoundexAlg();
		for (String str : toCompare) {
			for (String str2 : str.split(" ")) {
				alg2.getStringRepresantation(str2);
			}
		}

		int unterschied = alg.calculateDiffrence(alg2.getMap());
		// System.out.println("\n Unterschied = " + unterschied);
		// System.out.format("Prozentual zu 1 = %f \n",
		// (100 * ((double) unterschied / liste.size())));
		// System.out.format("Prozentual zu 2 = %f \n",
		// (100 * ((double) unterschied / toCompare.size())));
	}

	@Test
	public void Levensthein() {
		LevenSt alg = new LevenSt();
		for (String str : liste) {
			for (String str2 : str.split(" ")) {
				alg.addString(str2);
			}
		}
		LevenSt alg2 = new LevenSt();
		for (String str : toCompare) {
			for (String str2 : str.split(" ")) {
				alg2.addString(str2);
			}
		}

		int unterschied = alg.calculateDiffrence(alg2.getList());
		// System.out.println("\n Unterschied = " + unterschied);
		// System.out.format("Prozentual zu 1 = %f \n",
		// (100 * ((double) unterschied / listeSize)));
		// System.out.format("Prozentual zu 2 = %f \n",
		// (100 * ((double) unterschied / listeCompSize)));
	}

	@Test
	public void SemiGlobalAlignment() {
		SemiGlobalAlg alg = new SemiGlobalAlg();
		for (String str : liste) {
			for (String str2 : str.split(" ")) {
				alg.addString(str2);
			}
		}
		SemiGlobalAlg alg2 = new SemiGlobalAlg();
		for (String str : toCompare) {
			for (String str2 : str.split(" ")) {
				alg2.addString(str2);
			}
		}

		int unterschied = alg.calculateDiffrence(alg2.getList());
		// System.out.println("\n Unterschied = " + unterschied);
		// System.out.format("Prozentual zu 1 = %f\n",
		// (100 - (100 * ((double) unterschied / listeSize))));
		// System.out.format("Prozentual zu 2 = %f\n",
		// (100 - (100 * ((double) unterschied / listeCompSize))));
	}

	private void readFile(boolean original) {
		String file;
		if (original) {
			file = "data/10000a";
		} else {
			file = "data/10001a";
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
					listeSize += line.length();
				} else {
					toCompare.add(line);
					listeCompSize += line.length();
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
