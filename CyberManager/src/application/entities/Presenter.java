package application.entities;

import java.math.BigDecimal;

public abstract class Presenter {

	protected static final String initialCounter = "00 : 00 : 00";

	/*
	 * Permet de formatter la valeur initiale d'un compteur sous la forme "XX : XX : XX"
	 */
	public static String formatCounter(Counter counter){
		return String.format("%02d", counter.getHours()) + " : " + String.format("%02d", counter.getMinutes())
		+ " : " + String.format("%02d", counter.getSeconds());
	}

	/*
	 * Permet de lire le nombre de postes à partir d'une chaine de carateres
	 */
	public static int readNbrPostes(String s){
		String[] array;
		array = s.split(":");
		int nbrPostes = Integer.parseInt(array[1]);
		return nbrPostes;
	}

	/*
	 * Permet de lire le prix tarif à partir d'une chaine de carateres
	 */
	public static double readPrixTarif(String s){
		String[] array;
		array = s.split(":");
		double prixTarif = Double.parseDouble(array[1]);
		return prixTarif;
	}

}
/*
 * Offre une methode statique qui permet d'arrondir une valeur decimale à un nombre de chiffre apres la virgule exacte
 * en fournissant comme parametres la valeur à arrondir et le nombre de chiffre apres la virgule
 */
class DecimalUtils {

    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
