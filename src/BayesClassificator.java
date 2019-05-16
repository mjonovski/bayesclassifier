
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BayesClassificator {

	static ArrayList<Double> Uspeh = new ArrayList<>();
	static ArrayList<Double> Sredno = new ArrayList<>();
	static ArrayList<Double> Prosek = new ArrayList<>();
	static ArrayList<Double> Predmet = new ArrayList<>();

	static ArrayList<Double> meanUspeh = new ArrayList<>();
	static ArrayList<Double> meanProsek = new ArrayList<>();
	static ArrayList<Double> varianceUspeh = new ArrayList<>();
	static ArrayList<Double> varianceProsek = new ArrayList<>();

	static ArrayList<Double> gimnazisko = new ArrayList<>();
	static ArrayList<Double> strucno = new ArrayList<>();

	static ArrayList<Double> nula = new ArrayList<>();
	static ArrayList<Double> od_eden_do_dva = new ArrayList<>();
	static ArrayList<Double> od_tri_do_pet = new ArrayList<>();
	static ArrayList<Double> poveke_od_pet = new ArrayList<>();

	// funkcija za sredna vrednost ili prosek
	public static double mean(List<Double> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		return sum / list.size();
	}

	// funkcija za standardna devijacija
	public static double variance(List<Double> array) {
		double sum = 0;
		double mean = mean(array);
		for (int i = 0; i < array.size(); i++) {
			sum += (array.get(i) - mean) * (array.get(i) - mean);
		}
		return sum / array.size();
	}

	// funkcija za normalna raspredelba
	public static double normal(double input, double mean, double variance) {
		double expo = (-((input - mean) * (input - mean))) / (2 * variance);
		return (1 / (Math.sqrt(2 * 3.14 * variance))) * Math.pow(Math.E, expo);
	}

	/*
	 * funkcija koja shto gi chita vleznite podatoci, so toa shto vrednostite za
	 * diplomiral i vid na sredno obrazovanie se dadeni kako vrednosti 0,1,2 za
	 * diplomiral (na vreme, so zadocnuvanje, ne) i 0,1 za vid na obazovanie (0
	 * gimnazisko,1 struchno)
	 */
	public static void readFile() throws NumberFormatException, IOException {
		File file = new File("C:/Users/marspotting/Documents/machine learning/data.txt");

		FileReader f = new FileReader(file);
		BufferedReader bf = new BufferedReader(f);
		String string;

		while ((string = bf.readLine()) != null) {

			String[] parts = string.split("\\s+");
			Uspeh.add(Double.parseDouble(parts[1]));
			Sredno.add(Double.parseDouble(parts[2]));
			Prosek.add(Double.parseDouble(parts[3]));
			Predmet.add(Double.parseDouble(parts[4]));

		}
	}

	public static void meansAndVariances() {

		double uspeh_mean_na_vreme = mean(Uspeh.subList(0, 5));
		meanUspeh.add(uspeh_mean_na_vreme);

		double uspeh_mean_so_zadocnuvanje = mean(Uspeh.subList(5, 10));
		meanUspeh.add(uspeh_mean_so_zadocnuvanje);

		double uspeh_mean_ne_diplomiral = mean(Uspeh.subList(10, 15));
		meanUspeh.add(uspeh_mean_ne_diplomiral);

		double prosek_mean_na_vreme = mean(Prosek.subList(0, 5));
		meanProsek.add(prosek_mean_na_vreme);

		double prosek_mean_so_zadocnuvanje = mean(Prosek.subList(5, 10));
		meanProsek.add(prosek_mean_so_zadocnuvanje);

		double prosek_mean_ne_diplomiral = mean(Prosek.subList(10, 15));
		meanProsek.add(prosek_mean_ne_diplomiral);

		double uspeh_variance_na_vreme = variance(Uspeh.subList(0, 5));
		varianceUspeh.add(uspeh_variance_na_vreme);

		double uspeh_variance_so_zadocnuvanje = variance(Uspeh.subList(5, 10));
		varianceUspeh.add(uspeh_variance_so_zadocnuvanje);

		double uspeh_variance_ne_diplomiral = variance(Uspeh.subList(10, 15));
		varianceUspeh.add(uspeh_variance_ne_diplomiral);

		double prosek_variance_na_vreme = variance(Prosek.subList(0, 5));
		varianceProsek.add(prosek_variance_na_vreme);

		double prosek_variance_so_zadocnuvanje = variance(Prosek.subList(5, 10));
		varianceProsek.add(prosek_variance_so_zadocnuvanje);

		double prosek_variance_ne_diplomiral = variance(Prosek.subList(10, 15));
		varianceProsek.add(prosek_variance_ne_diplomiral);

		ArrayList<Double> na_vreme = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			na_vreme.add(Sredno.get(i));
		}

		ArrayList<Double> so_zadocnuvanje = new ArrayList<>();

		for (int i = 5; i < 10; i++) {
			so_zadocnuvanje.add(Sredno.get(i));
		}

		ArrayList<Double> ne_diplomiral = new ArrayList<>();

		for (int i = 10; i < 15; i++) {
			ne_diplomiral.add(Sredno.get(i));
		}

		// dve ArrayList-i za presmetuvanje na verojatnosta za gimnazisko i
		// strucno obrazovanie za site tri klasi
		// na index 0 vo dvete ArrayList-i se verojatnostite za da diplomira na
		// vreme, na index 1 - so zadocnuvanje i na index 2 - ne diplomiral

		double gim = 0;
		double str = 0;
		for (int i = 0; i < na_vreme.size(); i++) {
			if (na_vreme.get(i) == 0) {
				gim++;
			} else {
				str++;
			}
		}

		gimnazisko.add(gim / na_vreme.size());
		strucno.add((str / na_vreme.size()));

		gim = 0;
		str = 0;
		for (int i = 0; i < so_zadocnuvanje.size(); i++) {
			if (so_zadocnuvanje.get(i) == 0) {
				gim++;
			} else {
				str++;
			}
		}
		gimnazisko.add((gim / so_zadocnuvanje.size()));
		strucno.add((str / so_zadocnuvanje.size()));

		gim = 0;
		str = 0;
		for (int i = 0; i < ne_diplomiral.size(); i++) {
			if (ne_diplomiral.get(i) == 0) {
				gim++;
			} else {
				str++;
			}
		}
		gimnazisko.add((gim / ne_diplomiral.size()));
		strucno.add((str / ne_diplomiral.size()));

		// broenje kolku nepolozeni predmeti ima vo sekoja od trite klasi
		for (int i = 0; i < 3; i++) {
			int j = i * 5;
			int k = j + 5;
			double nul = 0;
			double eden_do_dva = 0;
			double tri_do_pet = 0;
			double pet_plus = 0;
			for (j = j; j < k; j++) {
				if (Predmet.get(j) == 0)
					nul++;
				else if (Predmet.get(j) == 1)
					eden_do_dva++;
				else if (Predmet.get(j) == 2)
					tri_do_pet++;
				else if (Predmet.get(j) == 3)
					pet_plus++;

			}
			// Laplasovo izramnuvanje i smestuvanje na vrednostite vo 4
			// Arraylist-i vo zavisnost od brojot na nepolozeni predmeti od prva
			// godina
			nula.add((nul + 0.25) / (5 + 1));
			od_eden_do_dva.add((eden_do_dva + 0.25) / (5 + 1));
			od_tri_do_pet.add((tri_do_pet + 0.25) / (5 + 1));
			poveke_od_pet.add((pet_plus + 0.25) / (5 + 1));
		}

	}

	public static void generateAnswer(double uspeh, int sredno, double prosek, int predmeti) {

		// pochetni verojatnosti
		double probUspehNaVreme = normal(uspeh, meanUspeh.get(0), varianceUspeh.get(0));
		double probUspehSoZadocnuvanje = normal(uspeh, meanUspeh.get(1), varianceUspeh.get(1));
		double probUspehNeDiplomiral = normal(uspeh, meanUspeh.get(2), varianceUspeh.get(2));

		// presmetuvanje verojatnost za prosek za site tri klasi so normalna
		// raspredelba
		double probProsekNaVreme = normal(prosek, meanProsek.get(0), varianceProsek.get(0));
		double probProsekSoZadocnuvanje = normal(prosek, meanProsek.get(1), varianceProsek.get(1));
		double probProsekNeDiplomiral = normal(prosek, meanProsek.get(2), varianceProsek.get(2));

		double koefSredno = 0;
		double koefPredmeti = 0;

		// presmetka za verojatnosta da diplomira na vreme pri dadenite vlezni
		// podatoci
		// se zemaat vrednostite od nultite indexi za sredno obrazovanie i za
		// nepolozeni predmeti
		if (sredno == 0)
			koefSredno = gimnazisko.get(0);
		else
			koefSredno = strucno.get(0);

		if (predmeti == 0)
			koefPredmeti = nula.get(0);
		else if (predmeti == 1)
			koefPredmeti = od_eden_do_dva.get(0);
		else if (predmeti == 2)
			koefPredmeti = od_tri_do_pet.get(0);
		else if (predmeti == 3)
			koefPredmeti = poveke_od_pet.get(0);
		double naVreme = 0.33 * probUspehNaVreme * koefSredno * koefPredmeti * probProsekNaVreme;

		// presmetka za verojatnosta da diplomira so zadocnuvanje pri dadenite
		// vlezni podatoci
		// se zemaat vrednostite od prvite indexi za sredno obrazovanie i za
		// nepolozeni predmeti
		if (sredno == 0)
			koefSredno = gimnazisko.get(1);
		else
			koefSredno = strucno.get(1);
		if (predmeti == 0)
			koefPredmeti = nula.get(1);
		else if (predmeti == 1)
			koefPredmeti = od_eden_do_dva.get(1);
		else if (predmeti == 2)
			koefPredmeti = od_tri_do_pet.get(1);
		else if (predmeti == 3)
			koefPredmeti = poveke_od_pet.get(1);
		double soZadocnuvanje = 0.33 * probUspehSoZadocnuvanje * koefSredno * koefPredmeti * probProsekSoZadocnuvanje;

		// presmetka za verojatnosta da ne diplomira pri dadenite vlezni
		// podatoci
		// se zemaat vrednostite od vtorite indexi za sredno obrazovanie i za
		// nepolozeni predmeti
		if (sredno == 0)
			koefSredno = gimnazisko.get(2);
		else
			koefSredno = strucno.get(2);
		if (predmeti == 0)
			koefPredmeti = nula.get(2);
		else if (predmeti == 1)
			koefPredmeti = od_eden_do_dva.get(2);
		else if (predmeti == 2)
			koefPredmeti = od_tri_do_pet.get(2);
		else if (predmeti == 3)
			koefPredmeti = poveke_od_pet.get(2);
		double neDiplomiral = 0.33 * probUspehNeDiplomiral * koefSredno * koefPredmeti * probProsekNeDiplomiral;

		// presmetuvanje na evidence so sobiranje na verojatnostite za trite
		// klasi
		double evidence = naVreme + soZadocnuvanje + neDiplomiral;

		double pNaVreme = naVreme / evidence;
		double pSoZadocnuvanje = soZadocnuvanje / evidence;
		double pNeDiplomiral = neDiplomiral / evidence;

		// odbiranje na najgolemata verojatnost
		if (pNaVreme > pSoZadocnuvanje) {
			if (pNaVreme > pNeDiplomiral)
				System.out.println("Diplomiral na vreme.");
			else
				System.out.println("Ne diplomiral");
		} else {
			if (pSoZadocnuvanje > pNeDiplomiral)
				System.out.println("Diplomiral so zadocnuvanje.");
			else
				System.out.println("Ne diplomiral.");
		}
	}

	public static void main(String[] args) throws IOException {

		readFile();

		meansAndVariances();

		// Citanje na vleznite podatoci
		// se vnesuva uspehot,vid na sredno obrazovnie, prosekot i brojot na
		// predmeti
		// za sredno gimnazisko se vnesuva 0, a za strucno se vnesuva 1
		// za nula nepolozeni predmeti se vnesuva 0, od eden do dva - 1, od tri
		// do pet - 2, poveke od pet- 3
		Scanner s = new Scanner(System.in);
		double uspeh = s.nextDouble();
		int sredno = s.nextInt();
		double prosek = s.nextDouble();
		int predmeti = s.nextInt();

		generateAnswer(uspeh, sredno, prosek, predmeti);

	}
}
