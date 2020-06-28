package application.entities;

/*
 * Offre les fonctionnalités qui permettent de controller le compteur
 */
public interface ICounterOp {

	/*
	 * Permet de mettre en etat de pause le compteur
	 */
	public void pauseCounter();

	/*
	 * Permet de mettre en etat de marche le compteur
	 */
	public void playCounter();

	/*
	 * Permet de mettre en etat initiale le compteur
	 */
	public void setCounterToZero();

}
