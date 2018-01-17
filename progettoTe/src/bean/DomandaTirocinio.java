package bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DomandaTirocinio {
  public DomandaTirocinio(){
	  
  }
	
	private	int Id_Documento  ;
	/** Aggiunte stringhe dirigente e direttore*/
	private String Azienda , autore , TirocinanteEmail, tutoAzrEmanil, tutoUnirEmanil;
 
	private int Firma_tutor_universitario,  Firma_tutor_aziendale , firma_dir_az, firma_dir_dip;

	/** Modifica del costruttore. Angrisani ha aggiunto la firma del dirigente e del direttore di dipartimento con relativi set e get */
	public DomandaTirocinio(int id_Documento, String azienda,Date data, int firma_tutor_universitario,  
			int firma_tutor_aziendale, int firma_dir_az, int firma_dir_dip, String tutoUnirEmanil ,  String tirocinanteEmail, String tutoAzrEmanil ) {
		 
		Id_Documento = id_Documento;
		Azienda = azienda;
		TirocinanteEmail = tirocinanteEmail;
		this.tutoAzrEmanil = tutoAzrEmanil;
		this.tutoUnirEmanil = tutoUnirEmanil;
		this.firma_dir_az=firma_dir_az;
		this.firma_dir_dip=firma_dir_dip;
		Firma_tutor_universitario = firma_tutor_universitario;
		Firma_tutor_aziendale = firma_tutor_aziendale;
		Data = data;
	}
	

	private java.util.Date Data = new java.util.Date();
	java.sql.Date oggi = new java.sql.Date(Data.getTime());
	SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
	
	private String da=dt1.format(Data);
	

	public int getId_Documento() {
		return Id_Documento;
	}
	public void setId_Documento(int id_Documento) {
		this.Id_Documento = id_Documento;
	}
	public String getAzienda() {
		return Azienda;
	}
	public void setAzienda(String azienda) {
		this.Azienda = azienda;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getTirocinanteEmail() {
		return TirocinanteEmail;
	}
	public void setTirocinanteEmail(String tirocinanteEmail) {
		this.TirocinanteEmail = tirocinanteEmail;
	}
	public String getTutoAzrEmanil() {
		return tutoAzrEmanil;
	}
	public void setTutoAzrEmanil(String tutoAzrEmanil) {
		this.tutoAzrEmanil = tutoAzrEmanil;
	}
	public String getTutoUnirEmanil() {
		return tutoUnirEmanil;
	}
	
	public int getFirma_dir_az() {
		return firma_dir_az;
	}
	public void setFirma_dir_az(int firma_dir_az) {
		this.firma_dir_az = firma_dir_az;
	}
	public int getFirma_dir_dip() {
		return firma_dir_dip;
	}
	public void setFirma_dir_dip(int firma_dir_dip) {
		this.firma_dir_dip = firma_dir_dip;
	}
	public void setTutoUnirEmanil(String tutoUnirEmanil) {
		this.tutoUnirEmanil = tutoUnirEmanil;
	}
	public String getData() {
		return  da;
	}
	public void setData(Date data) {
		 this.Data=data;
	}
	public int getFirma_tutor_universitario() {
		return Firma_tutor_universitario;
	}
	public void setFirma_tutor_universitario(int firma_tutor_universitario) {
		this.Firma_tutor_universitario = firma_tutor_universitario;
	}
	/** set e get aggiunti*/
	public int getFirma_tutor_aziendale() {
		return Firma_tutor_aziendale;
	}
	public void setFirma_tutor_aziendale(int firma_tutor_aziendale) {
		this.Firma_tutor_aziendale = firma_tutor_aziendale;
	}

	
/**cancellato attivato perch� non serve*/
		
	
    


}
