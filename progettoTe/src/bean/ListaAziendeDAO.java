package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaAziendeDAO {
	
	public void fillListaAziende(ListaAziende Azienda) throws SQLException, ClassNotFoundException{
		Connection newConnection = null;
		
		String sql="select * from azienda";
		
		 Class.forName("com.mysql.jdbc.Driver");

			String ip = "localhost";
			String port = "3306";
			String db = "tirocinioeasy";
			String username = "root";
			String password = "root";
	        
			newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db, username, password);
			
			 	try{
			 		 java.sql.Statement st  = newConnection.createStatement();
			 		  ResultSet rs = st.executeQuery(sql);
			 		  
			 		 while(rs.next()){
 
			 		    int Id_Azienda = rs.getInt("Id_Azienda");
			 		    String Nome = rs.getString("Nome");
			 		    String Sede_Operativa = rs.getString("Sede_Operativa");
			 		    String Amministrativa  = rs.getString("Sede_Amministrativa");
			 		    Boolean Convenzionata = rs.getBoolean("Convenzionata");
			 		   	String Dir_AziendaEmail = rs.getString("Dir_AziendaEmail");
			 		  	String Tutor_AziendaEmail =rs.getString("Tutor_AziendaleEmail");
			 		  	Azienda a= new Azienda( Nome, Sede_Operativa, Amministrativa, Dir_AziendaEmail, Tutor_AziendaEmail, Id_Azienda, Convenzionata);
			 		  	Azienda.aggiungi(a);
	}	
			 		rs.close();
			 		newConnection.close();
			 		
			 		 
			 	  }catch (SQLException  e) {
			 			e.printStackTrace();
			 		 }
	}
			 	
			 	public void fillListaAziende1 (ListaAziende Azienda) {
				      
					  try {
						  fillListaAziende(Azienda);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
}
	
