  
 <%@page import="bean.Registro"%>
  
 
 <%@page import="tirocinio.registro.gestore.RegistroDao"%>
  
  <jsp:useBean id="listaA" 
    class="tirocinio.registro.gestore.ListaAttivitą"
     scope="request"/>
             
 <jsp:useBean id="user" 
 class="bean.User"
  scope="session"/>

    <jsp:useBean id="tirocinan" class="bean.Tirocinante" scope="session"/>
    
 <jsp:useBean id="attivitą" 
class="bean.Attivitą" 
scope="session"/>

 <jsp:useBean id="registr" 
class="bean.Registro" 
scope="session"/>

<% 

String mail=tirocinan.getEmail();
RegistroDao reg= new RegistroDao();

attivitą.setRegistroID(reg.selectId(mail));

reg.fillListaattivitąStud(listaA, mail);
 
%>
 <jsp:forward page="ViewAttivitąStudente.jsp"></jsp:forward>