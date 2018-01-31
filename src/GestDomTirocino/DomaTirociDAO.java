
package GestDomTirocino;

import Db.Connector;
import bean.DomandaTirocinio;
import bean.ListDomandeTiro;
import gestRegTir.createRegistroDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
 


 
/**
 * Classe DomaTirociDAO, Si interfaccia col database per gestire le domande di tirocinio.
 */
public class DomaTirociDAO {

  String ip = "localhost";
  String port = "3306";
  String db = "tirocinioeasy";
  String username = "root";
  String password = "root";

  /**
   * Costruttore nullo.
   */
  public DomaTirociDAO() {
  }

  /**
   * Cerca e restituisce l'ultimo id delle domande di tirocinio.
   * @return ord 
   * @throws SQLException Gestisce errori nelle interrogazioni al database.
   */
  public int getMaxOrd() throws SQLException {
    Connection conn = Connector.getConnection();
    Statement st = conn.createStatement();

    String sql = "SELECT Id_Documento FROM domanda_di_tirocinio"
        + " WHERE Id_Documento=( SELECT max(Id_Documento) FROM domanda_di_tirocinio )";

    ResultSet res = st.executeQuery(sql);
    int ord;
    if (res.next()) {
      ord = res.getInt(1);
      if (res.wasNull()) {    // in case of empty table
        ord = -1;
        // no elements => return (-1), so that first element will be #0
      }
    } else {
      ord = -1;
    }

    st.close();
    conn.close();

    return ord;
  }
     
  /**
   * Memorizza una domanda di tirocinio nel database.
   * @param Data Domanda di tirocinio sottomessa.
   * @return true
   * @throws SQLException Gestisce errori nelle interrogazioni al database.
   */
  public boolean compilaDoma(DomandaTirocinio Data) throws SQLException {
    boolean flag = false;
    java.util.Date Dat  = new java.util.Date();
    new java.sql.Date(Dat.getTime());
    SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
    String da = dt1.format(Dat);

    Connection connection = null;
    PreparedStatement st = null;
    
    String sql2 = " INSERT INTO domanda_di_tirocinio  (Id_Documento, Data, "
        + "Firma_tutor_universitario, Firma_tutor_aziendale, Firma_dirigente_az, "
        + "Firma_dir_dip, Tutor_UniversitarioEmail, TirocinanteEmail,"
        + " dir_dipartimentoEmail, Tutor_Aziendale_Email) "
        + "VALUES (?,?,?,?,?,?,?,?,?,?) ";
    connection = Connector.getConnection();
    st = connection.prepareStatement(sql2);

    int c = getMaxOrd();
    c++;
    System.out.println(c);
    st.setInt(1, c);
    // st.setString(2,Data.getAzienda() );
    st.setString(2,da);
    st.setInt(3, 0);
    st.setInt(4, 0);
    st.setInt(5, 0);
    st.setInt(6, 0);
    st.setString(7, Data.getTutoUnirEmanil());
    st.setString(8, Data.getTirocinanteEmail());
    st.setString(9, "Fulgenzio@unisa.it");
    st.setString(10,Data.getTutoAzrEmanil());

    st.executeUpdate();
    System.out.println(Data.getTutoUnirEmanil());
    return true;
  }
  
  /**
   * Seleziona, dal database, la lista delle domande inerenti un determinato tutor aziendale.
   * @param listaDomande Arraylist di domande di tirocinio che verranno prelevate dal database.
   * @param taz Indirizzo email del tutor aziendale.
   * @throws SQLException Gestisce errori nelle interrogazioni al database.
   * @throws ClassNotFoundException  Gestisce errori nel caricamento delle classi.
   */
  public boolean fillDomadeTiroTA_DB(ListDomandeTiro listaDomande, String taz) 
      throws SQLException, ClassNotFoundException {
    Connection newConnection = null;
    // PreparedStatement preparedStatement = null;

    String nome = taz;
    String sql = "select * from domanda_di_tirocinio where Tutor_Aziendale_Email= '"
        + nome + "' and Firma_Tutor_Aziendale='0'";
    String TutorUniversitarioEmail;
    String TirocinanteEmail;
    String TutorAziendaleEmail;
    int id;
    int FirmatutorUniversitario;
    int firma_tutor_aziendale;
    int firma_dir_az;
    int firma_dir_dip;
    Date data;
      
    //STEP 2: Register JDBC driver
    newConnection = Connector.getConnection();
    try {
      java.sql.Statement st  = newConnection.createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) {
        //Retrieve by column name
        id  = rs.getInt("Id_Documento");
        data = rs.getDate("Data");
        FirmatutorUniversitario = rs.getInt("Firma_tutor_universitario");
        firma_tutor_aziendale = rs.getInt("Firma_tutor_aziendale");
        firma_dir_az = rs.getInt("Firma_dirigente_az");
        firma_dir_dip = rs.getInt("Firma_dir_dip");
        TutorUniversitarioEmail = rs.getString("Tutor_UniversitarioEmail");
        TirocinanteEmail = rs.getString("TirocinanteEmail");
        TutorAziendaleEmail = rs.getString("Tutor_Aziendale_Email");
         
        DomandaTirocinio doma = new DomandaTirocinio(id, FirmatutorUniversitario, 
            firma_tutor_aziendale, firma_dir_az, firma_dir_dip, TutorUniversitarioEmail, 
            TirocinanteEmail, TutorAziendaleEmail);
        listaDomande.aggiungi(doma);
      }

      st.close();
      newConnection.close();

    } catch (SQLException  e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Seleziona, dal database, la lista delle domande inerenti un determinato dirigente aziendale.
   * @param listaDomande Arraylist di domande di tirocinio che verranno prelevate dal database.
   * @return true
   * @param daz Indirizzo email del dirigente aziendale.
   * @throws SQLException Gestisce errori nelle interrogazioni al database.
   * @throws ClassNotFoundException Gestisce errori nel caricamento delle classi.
   */
  public boolean fillDomadeTiroDAz_DB(ListDomandeTiro listaDomande, String daz) 
      throws SQLException, ClassNotFoundException {
    Connection newConnection = null;
    // PreparedStatement preparedStatement = null;
    String nome = daz;
    String sql = "SELECT domanda_di_tirocinio.* FROM domanda_di_tirocinio, Azienda "
        + "where domanda_di_tirocinio.Tutor_Aziendale_Email=azienda.Tutor_AziendaleEmail "
        + "AND azienda.Dir_AziendaEmail='" + nome + "' "
        + "AND domanda_di_tirocinio.Firma_dirigente_az=" + 0 + " ";
    String dirAzEm;
    String TutorUniversitarioEmail;
    String TirocinanteEmail;
    String TutorAziendaleEmail;
    int id;
    int FirmatutorUniversitario;
    int firma_tutor_aziendale;
    int firma_dir_az;
    int firma_dir_dip;
    Date data;
      
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");
   
    newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":"
        + port + "/" + db, username, password);
    try {
      java.sql.Statement st  = newConnection.createStatement();
      ResultSet rs = st.executeQuery(sql);
     
      while (rs.next()) {
        //Retrieve by column name
        id  = rs.getInt("Id_Documento");
        data = rs.getDate("Data");
        FirmatutorUniversitario = rs.getInt("Firma_tutor_universitario");
        firma_tutor_aziendale = rs.getInt("Firma_tutor_aziendale");
        firma_dir_az = rs.getInt("Firma_dirigente_az");
        firma_dir_dip = rs.getInt("Firma_dir_dip");
        TutorUniversitarioEmail = rs.getString("Tutor_UniversitarioEmail");
        TirocinanteEmail = rs.getString("TirocinanteEmail");
        TutorAziendaleEmail = rs.getString("Tutor_Aziendale_Email");
       
        DomandaTirocinio doma = new DomandaTirocinio(id, FirmatutorUniversitario, 
            firma_tutor_aziendale, firma_dir_az, firma_dir_dip, TutorUniversitarioEmail, 
            TirocinanteEmail, TutorAziendaleEmail);
        listaDomande.aggiungi(doma);
      }

      st.close();
      newConnection.close();
    } catch (SQLException  e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
  * Richiama il metodo fillDomadeTiroTA_DB.
  * @param domande Domande di tirocinio inerenti il tutor aziendale.
  * @param taz Indirizzo email del tutor aziendale.
  */
  public void fillListaDomandeTAZ (ListDomandeTiro domande,  String taz) {
    try {
      fillDomadeTiroTA_DB(domande, taz);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Seleziona, dal database, la lista delle domande inerenti un determinato tutor universitario.
  * @param listaDomande (domande di tirocinio) oggetto della classe ListDomandetiro, 
  *     l'arraylist viene riempito da una query al db.
  * @param TUNI Indirizzo email del tutor aziendale.
  * @return true
  * @throws SQLException Gestisce errori nelle interrogazioni al database.
  * @throws ClassNotFoundException Gestisce errori nel caricamento delle classi.
  */
  public boolean fillDomadeTiroTu_DB(ListDomandeTiro listaDomande, String TUNI) 
      throws SQLException, ClassNotFoundException {
    Connection newConnection = null;
    //PreparedStatement preparedStatement = null;
    String nome = TUNI;
    String sql = "select * from domanda_di_tirocinio where Tutor_UniversitarioEmail='"
        + nome + "' and Firma_tutor_Universitario='0'";
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":"
        + port + "/" + db, username, password);
    try {
      java.sql.Statement st  = newConnection.createStatement();
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {

        //Retrieve by column name
        int id  = rs.getInt("Id_Documento");
        Date data = rs.getDate("Data");
        int FirmatutorUniversitario = rs.getInt("Firma_tutor_universitario");
        int firma_tutor_aziendale = rs.getInt("Firma_tutor_aziendale");
        int firma_dir_az = rs.getInt("Firma_dirigente_az"); 
        int firma_dir_dip = rs.getInt("Firma_dir_dip");
        String TutorUniversitarioEmail = rs.getString("Tutor_UniversitarioEmail");
        String TirocinanteEmail = rs.getString("TirocinanteEmail");
        String TutorAziendaleEmail = rs.getString("Tutor_Aziendale_Email");

        DomandaTirocinio doma = new DomandaTirocinio(id,FirmatutorUniversitario, 
            firma_tutor_aziendale, firma_dir_az, firma_dir_dip, TutorUniversitarioEmail, 
            TirocinanteEmail, TutorAziendaleEmail);
        listaDomande.aggiungi(doma);
      }
      st.close();
      newConnection.close();
    } catch (SQLException  e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
  * Richiama il metodo fillDomadeTiroTu_DB.
  * @param domande Domande di tirocinio inerenti il tutor universitario.
  * @param TUNI Indirizzo email del tutor universitario.
  */
  public void fillListaDomandeTUNI(ListDomandeTiro domande,  String TUNI) {
    try {
      fillDomadeTiroTu_DB(domande, TUNI);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
  * Riempie la lista delle domande di tirocinio inerenti il direttore di dipartimento.
  * @param listaDomande Domande di tirocinio inerenti il diretto di dipartimento.
  * @param dirdip Indirizzo email del direttore di dipartimento.
  * @return true
  */
  public boolean  fillListaDomandeDirDip(ListDomandeTiro listaDomande, String dirdip) {
    String mail = dirdip;
    Connection newConnection = Connector.getConnection();
    try {
      java.sql.Statement st  = newConnection.createStatement();
      String sql = "select * from domanda_di_tirocinio where dir_dipartimentoEmail= '" + mail + "' "
          + "and Firma_tutor_universitario=" + 1 + " "
          + "and Firma_tutor_aziendale= " + 1 + " "
          + "and Firma_dirigente_az= " + 1 + " "
          + "and Firma_dir_dip=" + 0 + " ";
      ResultSet rs = st.executeQuery(sql);

      while (rs.next()) {

        //Retrieve by column name
        int id  = rs.getInt("Id_Documento");
        Date data = rs.getDate("Data");
        int FirmatutorUniversitario = rs.getInt("Firma_tutor_universitario");
        int firma_tutor_aziendale = rs.getInt("Firma_tutor_aziendale");
        int firma_dir_az = rs.getInt("Firma_dirigente_az");
        int firma_dir_dip = rs.getInt("Firma_dir_dip");
        String TutorUniversitarioEmail = rs.getString("Tutor_UniversitarioEmail");
        String TirocinanteEmail = rs.getString("TirocinanteEmail");
        String TutorAziendaleEmail = rs.getString("Tutor_Aziendale_Email");
        
        DomandaTirocinio doma = new DomandaTirocinio(id, FirmatutorUniversitario, 
            firma_tutor_aziendale, firma_dir_az, firma_dir_dip, TutorUniversitarioEmail, 
            TirocinanteEmail, TutorAziendaleEmail);
        listaDomande.aggiungi(doma);
      }

    } catch (SQLException  e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
  * Metodo per la firma delle domande di tirocinio da parte del tutor aziendale.
  * @param id Identificativo della domanda di tirocinio.
  * @return true
  */
  public boolean firmaTAz(int id) {
    Connection conn = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":"
          + port + "/" + db, username, password);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  
    try {
      int firma = 1;
      java.sql.Statement  stmt = conn.createStatement();
      String sql = "UPDATE domanda_di_tirocinio  SET Firma_Tutor_Aziendale = "
          + firma + " WHERE Id_Documento= '" + id + "' ";
      System.out.println(sql);
      stmt.executeUpdate(sql);
       
      stmt.close();
      conn.close();

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
 
    return true;
  }

  /**
   * Metodo per la firma delle domande di tirocinio da parte del tutor universitario.
   * @param id Identificativo della domanda di tirocinio.
   * @return true
   */
  public boolean firmaTuni(int id) {
    Connection conn = null;

   ((Connector) conn).getConnection();

    try {
      int firma = 1;
      java.sql.Statement stmt = conn.createStatement();
      String sql = "UPDATE domanda_di_tirocinio  SET Firma_tutor_Universitario= "
          + firma + " WHERE Id_Documento= '" + id + "' ";
      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      conn.close();

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return true;
  }

  
  /**
   * Metodo per la firma delle domande di tirocinio da parte del dirigente aziendale.
   * @param id Identificativo della domanda di tirocinio.
   * @return true
   */
  public boolean firmaDirAz(int id) {
    Connection conn = Connector.getConnection();
     
    try {
      int firma = 1;
      java.sql.Statement stmt = conn.createStatement();
      String sql = "UPDATE domanda_di_tirocinio  SET Firma_dirigente_az= "
          + firma + " WHERE Id_Documento= '" + id + "' ";
      System.out.println(sql);
      stmt.executeUpdate(sql);

      stmt.close();
      conn.close();

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return true;
  }

  /**
   * Metodo per la firma delle domande di tirocinio da parte del direttore di dipartimento.
   * @param id Identificativo della domanda di tirocinio.
   * @return true
   */
  public boolean firmaDirDip(int id) {
	  Connection conn = Connector.getConnection();

    try {
      int firma = 1;
      java.sql.Statement stmt = conn.createStatement();
      String sql = "UPDATE domanda_di_tirocinio  SET Firma_dir_dip= "
          + firma + " WHERE Id_Documento= '" + id + "' ";
      System.out.println(sql);

      /** Creazione registro in seguito all'accettazione della domanda.*/
      createRegistroDAO cr = new createRegistroDAO();
      /**vengono prelevati i dati del nuovo registro direttamente dalla domanda.*/
      cr.getDatiDomanda(id);
      /**viene creato il registro nel db*/
      cr.CreaRegistro(id, 0);

      stmt.executeUpdate(sql);

      stmt.close();
      conn.close();

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return true;
  }
}