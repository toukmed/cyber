package application.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {

	private int nbrPostes;
	private double prixTarif;

	public Config(int nbrPostes, double prixTarif) {
		super();
		this.nbrPostes = nbrPostes;
		this.prixTarif = prixTarif;
	}

	@Override
	public String toString() {
		return "Vous avez effectuer la configuration suivante \n"
				+ "Nombre de Postes : " + nbrPostes + ", prix Tarif : " + prixTarif;
	}

	/*
	 * Enregister la configuration effectuée par le client dans le fichier config.txt
	 * Le fichier de configuration contient le nombre de postes et le prix tarif
	 */
	public boolean enregistrerConfig(){
		File file = new File("config.txt");
		PrintWriter pw;
		FileWriter writer;
		try {
			pw = new PrintWriter(file);
			pw.print("");
		    writer = new FileWriter(file, true);
            writer.write("Nombre de postes:"+nbrPostes);
            writer.write("\r\n");   // write new line
            writer.write("Prix Tarif:"+prixTarif);
            writer.close();
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}




}
