package tirocinio.registro.gestore;

import java.util.ArrayList;
import java.util.List;
 
 
import bean.Attivitą;
 
 

public class ListaAttivitą {

	private List<Attivitą> attivity;
	
	
	public ListaAttivitą() {
		this.attivity = new ArrayList<Attivitą>();
	}
	
	 public List<Attivitą> getAttivitą() {
		    return attivity;
		  }
	
	 public void aggiungi(Attivitą reg) {
		 attivity.add(reg);
		  }
}
