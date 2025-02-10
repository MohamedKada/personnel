package testsUnitaires;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;



class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
		assertEquals("BabyFoot", ligue.getNom());
	}
	
	void setLigue() throws SauvegardeImpossible {
		
		Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
		ligue.setNom("Test");
	}
	
	
	@Test 
	void Suppression() throws SauvegardeImpossible, Erreurdate {

		Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
		Employe employe;
		
			employe = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp",LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			Employe employe1 = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp",LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1)); 
			Employe employe2 = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp",LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1)); 
			
			employe.remove();
			assertFalse(ligue.getEmployes().contains(employe));
			
			ligue.remove();
			assertFalse(gestionPersonnel.getLigues().contains(ligue));
			
		
		
		
	}
	
	@Test
	void changementetSuppAdmin() throws SauvegardeImpossible, Erreurdate{
		Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
		
		
		Employe test;
			 
			test = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp",LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			
			
			//teste personnel 
			assertEquals(gestionPersonnel.getRoot() , ligue.getAdministrateur());
			
			
			//MODIF ADMIN
			ligue.setAdministrateur(test);
			assertEquals(test, ligue.getAdministrateur());
			
			
			//SUPP ADMIN 
			test.remove();
			assertFalse(ligue.getEmployes().contains(test));
		
			//VERIFIE QUE ROOT EST BIEN ADMIN 
			assertFalse(ligue.getEmployes().contains(test));
			assertEquals(gestionPersonnel.getRoot() , ligue.getAdministrateur());
			
		
		
		
		
	}
	
	@Test
	void Employe() throws SauvegardeImpossible, Erreurdate 
	{
		//GETTEUR
		
		Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
		Employe test = ligue.addEmploye("testnom2", "testprénom2", "testmail2@mail.com", "testmdp",LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1)); 
		assertEquals("testprénom2" , test.getPrenom());
		assertEquals("mail" , test.getMail());
		assertEquals(ligue,test.getLigue());
		
		// SETTEUR
		test.setMail("nouveaumail");
		assertEquals("nouveaumail" , test.getMail());
		
		test.setNom("nouveaunom");
		assertEquals("nouveaunom" , test.getNom());
		
		test.setPassword("nouveaumdp");
		assertTrue(test.checkPassword("nouveaumdp"));
		
		test.setPrenom("nouveauprenom");
		assertEquals("nouveauprenom" , test.getPrenom());
		
		}
		
		
	
	
	@Test
	void addEmploye() throws SauvegardeImpossible, Erreurdate
	{
		
		
			Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
			Employe employe;
			employe = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			assertEquals(employe, ligue.getEmployes().first());
	} 
		
	


    @Test
     void testValidDates() throws SauvegardeImpossible {
    	
    	Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
        assertDoesNotThrow(() -> ligue.addEmploye("testnom2", "testprénom2", "testmail2@testmail.com", "testmdp" ,LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1) ));
        
    }
    
    
    @Test
     void testInvalidDates() throws SauvegardeImpossible{
    	Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
        Exception exception = assertThrows(Erreurdate.class, () -> {
            ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , LocalDate.of(2024, 12, 1), LocalDate.of(2022, 3, 15));
        });
        assertEquals("La date de départ est antérieur à la date d'arrivée.", exception.getMessage());
        
        
          
    }
    
    @Test
    void testDateArriveNull() throws SauvegardeImpossible{
    	Ligue ligue = gestionPersonnel.addLigue("Basket");
    	
    
     Exception exception1 = assertThrows(Erreurdate.class, () -> {
            ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , null , LocalDate.of(2022, 3, 15));
        });
        assertEquals("La date de départ est antérieur à la date d'arrivée.", exception1.getMessage());
        
     }
    
    
    @Test
    void setDateArriveNull()throws SauvegardeImpossible, Erreurdate {
    	Ligue ligue = gestionPersonnel.addLigue("Basket");
    
    		Employe employe = ligue.addEmploye("a", "a", "a", "a", LocalDate.of(2024, 12, 1), LocalDate.of(2022, 3, 15));
    		Exception exception1 = assertThrows(Erreurdate.class, () -> {
                employe.setDateArrivee(null);
            });
    		
 
    }
    
    
    

    @Test
     void testSetDateDepartInvalid() throws SauvegardeImpossible , Erreurdate {
    	Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
        	
        		
			Employe employe = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			
			Exception exception = assertThrows(Erreurdate.class , () -> employe.setDateDepart(LocalDate.of(2022, 1, 1)));
	        assertEquals("La date de départ est antérieur à la date d'arrivée." , exception.getMessage());    
	        
			
			
        	
		}
    
    
    @Test
     void testSetDateArriveInvalid() throws SauvegardeImpossible , Erreurdate {
    	Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
        
        		
			Employe employe = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			Exception erreur = assertThrows(Erreurdate.class, () -> employe.setDateArrivee(LocalDate.of(2024, 12, 2)));
			assertEquals("La date de départ est antérieur à la date d'arrivée." , erreur.getMessage());
        	
        		
        	
		}
    
    
    @Test
    void testGetDate() throws SauvegardeImpossible ,  Erreurdate {
    	Ligue ligue = gestionPersonnel.addLigue("BabyFoot");
    
  
    		Employe employe = ligue.addEmploye("testnom", "testprénom", "testmail@mail.com", "testmdp"  , LocalDate.of(2022, 3, 15), LocalDate.of(2024, 12, 1));
			assertEquals( employe.getDateArrivee() , LocalDate.of(2022, 3, 15)); 
			assertEquals( employe.getDateDepart() , LocalDate.of(2024, 12, 1)); 
    		
    		
    	
    
        
    
}
    }


