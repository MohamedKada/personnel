package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible d'ajouter cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					// Saisie en chaîne - demande toutes les informations d'abord
					String nom = getString("nom : ");
					String prenom = getString("prenom : ");
					String mail = getString("mail : ");
					String password = getString("password : ");
					String dateArrStr = getString("date arrivée (AAAA-MM-JJ) : ");
					String dateDepStr = getString("date départ (AAAA-MM-JJ) : ");
					
					try {
						// Exception Format - vérifie le format des dates
						LocalDate dateArrivee = LocalDate.parse(dateArrStr);
						LocalDate dateDepart = LocalDate.parse(dateDepStr);
						
						// Exception ordre - vérifie l'ordre chronologique
						if (dateDepart.isBefore(dateArrivee)) {
							throw new Erreurdate();
						}
						
						// Si tout est valide, crée l'employé
						ligue.addEmploye(nom, prenom, mail, password, dateArrivee, dateDepart);
						System.out.println("Employé ajouté avec succès");
						
					} catch (DateTimeParseException e) {
						// Gestion de l'exception de format
						System.out.println("Erreur : Format de date invalide. Utilisez le format AAAA-MM-JJ");
					} catch (Erreurdate e) {
						// Gestion de l'exception d'ordre chronologique
						System.out.println(e.getMessage());
					}
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(GererEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	private List<Employe> GererEmploye(final Ligue ligue)
	{
		return new List<Employe>("Gérer un employé", "x", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> gerer7employe(element)
				);
	}
	
	private Menu gerer7employe(final Employe employe)
	{
		Menu menu = new Menu("Gérer un employé " + employe.getNom(), "m");
		menu.add(modifierEmploye(employe));
		menu.add(supprimerEmploye( employe));
		menu.add(changerAdministrateur(employe));
		menu.addBack("q");
		return menu;
	}
	
	private Option changerAdministrateur( final Employe employe)
	{
		return new Option("Changer admin" , "d" , () -> adm(employe));
	}
	
	private void adm(Employe employe) {
		employe.getLigue().setAdministrateur(employe);
		System.out.println(employe.getNom() + " " + employe.getPrenom() + " " + "est maintenant administrateur");
	}

	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
	private Option supprimerEmploye(final Employe employe)
	{
	    return new Option("Supprimer " + employe.getNom() + " " + employe.getPrenom(), "s", 
	            () -> { 
	                employe.getLigue().getEmployes().remove(employe);  
	            });
	}

	
	private Option modifierEmploye( final Employe employe)
	{
	    return employeConsole.editerEmploye(employe);  
	            
	}

	
	/*private List<Employe> supprimerEmploye(final Ligue ligue)       ANCIEN CODE
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}*/
	
	
	/*private List<Employe> modifierEmploye(final Ligue ligue)             ANCIEN CODE
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}*/
	
	
}
