package application.fxml;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.config.Config;
import application.entities.Counter;
import application.entities.Presenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class MainController implements Initializable{

	/*
	 * rootPane de Main.fxml
	 */
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane myPane;

    @FXML
    private Button addCounterBtn;

    @FXML
    private Button changeConfig;

    /*
	 * rootPane de Config.fxml
	 */
    @FXML
    private AnchorPane rootPane1;

    @FXML
    private Spinner<Integer> postesSpinner = new Spinner<>();

    @FXML
    private Spinner<Double> prixSpinner = new Spinner<>();

    @FXML
    private Button valider;

    @FXML
    private Button annuler;

    /*
     * Les SpinnerValueFactory permettent d'assurer une navigation entre les differentes valeurs fournies pour chacun
     * On definit le type de valeur prise par chaque factory
     */
    private SpinnerValueFactory<Double> prixFactory;
    private SpinnerValueFactory<Integer> posteFactory;


	private double layoutX = 380;
    private double layoutY = 4;
    private int nbrCounters = 0;
    private  int nbrPostes;
    private double prixTarif;
    private Config config;

    /*
     * Static block
     * Permet d'initialiser le nombre de postes qui seront creer lors de l'execution de l'application
     */
    {
    	Path file;
		try {
			file = Paths.get("config.txt");
			List<String> list = Files.readAllLines(file, Charset.defaultCharset() );
			nbrPostes = Presenter.readNbrPostes(list.get(0));
			System.out.println(nbrPostes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /*
     * Permet d'ajouter les compteur d'une maniere automatique dependant du nombre de compteur souhaité
     * suivant le parametre nbrCounters a condition qu'il aura un maximum de 12 compteur (11 - 0 + 1)
     */
    @FXML
    void onAddCounter(ActionEvent event) throws IOException {
    	if(nbrCounters <= 11){
    		addCounter();
    	}

    }

    /*
     * En annulant la configuration, la fenetre se ferme aussi
     */
    @FXML
    void onAnnuler(ActionEvent event) {
    	rootPane1.setVisible(false);
    }

    /*
     * En cliquant sur le boutton changer config la fenetre de configuration s'affiche pour effectuer
     * les changements souhaités
     */
    @FXML
    void onChangeConfig(ActionEvent event) {
		rootPane1.setVisible(true);
    }

    @FXML
    void onValider(ActionEvent event) {
    	validerAlert();
    }

    /*
     * Permet de declancher une alerte assurant la validation du changement de configuration
     * fait par le client
     * Si le boutton OK est cliqué, la derniere configuration sera remplacée par une nouvelle config recement
     * etablie par le client et la fenetre d'alerte se ferme
     * Si Cancel est cliqué le fenetre d'alerte se ferme et la configuration ne change pas
     */
    private void validerAlert(){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Changer la configuration");
    	alert.setHeaderText("Changement de votre configuration!");
    	alert.setContentText("Vous êtes sûr ?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		AnchorPane pane;
        	try {
        		Counter.setIdCounter(0);
        		int nbrPostes = posteFactory.getValue();
        		double prixTarif = prixFactory.getValue();

        		config = new Config(nbrPostes, prixTarif);

        		config.enregistrerConfig();
        		rootPane.getChildren().clear();
        		pane = FXMLLoader.load(getClass().getResource("/application/fxml/Main.fxml"));
        		rootPane.getChildren().add(pane);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    }

    /*
     * Ajouter un compteur à la fenetre principale (Main.fxml) ce qui permet d'incrementer le nombre de compteurs par un
     */
    private void addCounter() throws IOException{

    	AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/fxml/Counter.fxml"));
    	layoutY += 22;
    	pane.setLayoutX(layoutX);
    	pane.setLayoutY(layoutY);
    	rootPane.getChildren().add(pane);
    	layoutY += 30;
    	nbrCounters++;
    }

    /*
     * Mettre les parametres de l'application dans leurs etat predefinie dans le fichier de configuration
     * en recuperant le nombre de poste et le prix tarif deja configurés
     */
    private void setParameters() {
    	Path file;
		try {
			file = Paths.get("config.txt");
			List<String> list = Files.readAllLines(file);
			for (String string : list) {
				System.out.println(string);
			}
			nbrPostes = Presenter.readNbrPostes(list.get(0));
			prixTarif = Presenter.readPrixTarif(list.get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     * L'initialisation se fait automatiquement la premiere apres le bloc static
     * On definie la visibilité de la fenetre de configuration a false
     * On met à jour les parametres qui concenent le nombre de postes et le tarif à partir
     * du fichier de configuration en utilisant setParameters();
     * On initialise les FactorySpinners avec les valeurs (Min, Max, Default, Step)
     * On cree les compteurs à partir du nombre de postes mentionné dans le fichier de configuration
     *
     */
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	rootPane1.setVisible(false);
    	setParameters();
		posteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, nbrPostes);
		postesSpinner.setValueFactory(posteFactory);

		prixFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 15.0, prixTarif, 0.5);
		prixSpinner.setValueFactory(prixFactory);

		try {
			for(int i = 0; i < nbrPostes; i++){
				addCounter();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}



