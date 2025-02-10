package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("select id_employe , nomEmploye , passwd from employe where id_ligue is null");
			ResultSet resultat = instruction.executeQuery();
			
			
			
			if(resultat != null && resultat.next()) {
			gestionPersonnel.addRoot(gestionPersonnel, resultat.getString(2), resultat.getString(3), resultat.getInt(1));
			}

			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	public void update(Ligue ligue) throws SauvegardeImpossible {
	    try {
	        PreparedStatement instruction;
	        instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE id = ?");
	        instruction.setString(1, ligue.getNom());
	        instruction.setInt(2, ligue.getId()); 
	        instruction.executeUpdate();
	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}


	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (NOM_EMP, PRENOM_EMP, MDP_EMP, DATE_ARRIVE, DATE_DEPART, ADMIN, MAIL_EMP, ID_EMP) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());	
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getPass());
			instruction.setString(4, employe.getDateArrivee().toString());
			instruction.setString(5, employe.getDateDepart().toString());
			instruction.setBoolean(6, employe.getAdmin());
			instruction.setString(7, employe.getMail());
			instruction.setInt(8, employe.getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public void update(Employe employe) throws SauvegardeImpossible {
		// TODO Stub de la méthode généré automatiquement
		
	}

	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible {
		// TODO Stub de la méthode généré automatiquement
		
	}

	@Override
	public void delete(Employe employe) throws SauvegardeImpossible {
		// TODO Stub de la méthode généré automatiquement
		
	}
}
