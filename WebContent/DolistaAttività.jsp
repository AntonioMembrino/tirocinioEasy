  
 <%@page import="bean.Registro"%>
  
 
 <%@page import="tirocinio.registro.gestore.RegistroDao"%>
  
  <jsp:useBean id="listaA" 
    class="tirocinio.registro.gestore.ListaAttivitą"
     scope="request"/>
             
 <jsp:useBean id="user" 
 class="bean.User"
  scope="session"/>

<jsp:useBean id="tutorAz" 
class="bean.TutorAz" 
scope="session"/>

 <jsp:useBean id="attivitą" 
class="bean.Attivitą" 
scope="session"/>

 <jsp:useBean id="registr" 
class="bean.Registro" 
scope="session"/>

<% 

String mail= request.getParameter("mail");
RegistroDao reg= new RegistroDao();

attivitą.setRegistroID(reg.selectId(mail));

reg.fillListaattivitą(listaA, mail);
 
%>
 <jsp:forward page="listaAttivitView.jsp"></jsp:forward>