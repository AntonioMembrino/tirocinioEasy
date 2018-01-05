/**
 * 
 * @author antonio
 * 
 */
package GestDomTirocino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.mysql.jdbc.Statement;

import bean.DomandaTirocinio;
import bean.ListDomandeTiro;
import bean.tutorAz;
 
import GestDomTirocino.DriverManagerConnectionPool;
 

public class DoDomaTirociDAO {

	public DoDomaTirociDAO()   {
	 }
	
	public void compilaDoma(DomandaTirocinio Data) throws SQLException    {
		 System.out.println("tutor aziendaleemail  "+ Data.getTutoAzrEmanil().toString()+" email tirocinante   "+ Data.getTirocinanteEmail().toString()+" email tutr uni   "+Data.getTutoUnirEmanil());
		 
		Random ran=new Random();
	int x=	ran.nextInt(200);
		
		  java.util.Date Dat  = new java.util.Date();
		new java.sql.Date(Dat.getTime());
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
		   String da=dt1.format(Dat );
		
		  
         
		  int flse=0;
		 
		  Connection connection = null;
			PreparedStatement st = null;
     
		
       String sql2=" INSERT INTO  domandaTirocinio (IdDoccumento, Azienda, data,"
    		+ " FirmatutorUniversitario, FirmaTutorAziendale, TutorUniversitarioEmail,"
    		+ " TirocinanteEmail, TutorAziendaleEmail, attivato) "
    		+ "VALUES (?,?,?,?,?,?,?,?,?) ";
   	try { 
       connection = DriverManagerConnectionPool.getConnection();
		st = connection.prepareStatement(sql2);
		
	st.setInt(1, x );
	st.setString(2,Data.getAzienda());
	st.setString(3,da );
	st.setInt(4, 0);
	st.setInt(5, 0);	
	st.setString(6, Data.getTutoUnirEmanil());
	st.setString(7, Data.getTirocinanteEmail() );
	st.setString(8, Data.getTutoAzrEmanil() );
    st.setInt(9, 0);	
   
    st.executeUpdate();

	connection.commit();
} finally {
	try {
		if (st != null)
			st.close();
	} finally {
		DriverManagerConnectionPool.releaseConnection(connection);
	}
   }
  }
	
	
	
	public void fillDomadeTiroTA(ListDomandeTiro lista, String taz) throws SQLException{
		Connection con = DriverManagerConnectionPool.getConnection(); 
		  
	 
		
		Statement st= (Statement) con.createStatement();
		String sql="select * from domandatirocinio where TutorAziendaleEmail="+taz+" ";
		
		try {
			ResultSet res=st.executeQuery(sql);
			while (res.next()) {
				  lista.aggiungi(res.getInt("IdDoccumento"),res.getString("azienda"),res.getDate("data"),res.getBoolean("FirmatutorUniversitario"),res.getBoolean("FirmaTutorAziendale") ,res.getString("TutorUniversitarioEmail"), res.getString("TirocinanteEmail") , res.getString("TutorAziendaleEmail"));
				
			}
			
			 st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}