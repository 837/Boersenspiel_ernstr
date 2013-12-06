package ch.kantibaden.projektunterricht;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class EingabeVC {
    
	   // Model
	   private DataBean dataBean;
	    
	   // View
	   private EingabeView view;
	    
	    
	   public EingabeVC(DataBean dataBean) {
	      this.dataBean = dataBean;
	      this.view = new EingabeView();
	       
	      // Eventhandler registrieren
	      view.getAddBtn().setOnAction(new addBtnEventHandler());   
	      view.getOkBtn().setOnAction(new OkBtnEventHandler());   
	   }
	    
	   public void show(){
	      view.show(dataBean.getPrimaryStage());
	   }
	    
	   //+++++++++++++++++++++++++++++++++++++++++++++
	   // Events
	   //+++++++++++++++++++++++++++++++++++++++++++++
	 
	    
	   class OkBtnEventHandler implements EventHandler<actionevent>{
	 
	      @Override
	      public void handle(ActionEvent e) {   
	      // zur naechsten Seiten springen! 
	          AusgabeVC ausgabeVC = new AusgabeVC(dataBean);
	          ausgabeVC.show();   
	      }
	       
	   }
	    
	   class addBtnEventHandler implements EventHandler<actionevent>{
	 
	      @Override
	      public void handle(ActionEvent e) {   
	         // Meldung reseten
	         view.getMeldungT().setText("");
	          
	         // Daten aus den Textfeldern holen
	         String vname = view.getVornameTF().getText();
	          String nname = view.getNachnameTF().getText();
	           
	         //Textfelder zuruecksetzen
	         view.getNachnameTF().setText("");
	         view.getVornameTF().setText("");
	          
	         // Daten testen
	         if(vname.isEmpty()){
	            view.getMeldungT().setText("Der Vorname fehlt!");
	            return;
	         }
	         if(nname.isEmpty()){
	            view.getMeldungT().setText("Der Nachname fehlt!");
	            return;
	         }
	          
	         // Daten hinzufuegen 
	         String erg = null;
	         erg = dataBean.getNamePwMap().put(nname, vname);
	          
	         // Meldung ausgeben
	         if(erg == null){
	            view.getMeldungT().setText("Leser hinzugefügt");
	         }else{
	            view.getMeldungT().setText("Leser bereits vorhanden");
	         }
	      }
	       
	   }
	 
	}