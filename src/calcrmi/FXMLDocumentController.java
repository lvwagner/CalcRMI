/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcrmi;

import com.wagner.calculos.Calculos;
import com.wagner.calculos.servidor.ServidorCalc;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JOptionPane;

/**
 *
 * @author wagner
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField tfValor1;
    @FXML
    private Button btn_resultado;
    @FXML
    private Button btnSair;
    @FXML
    private Label lblDivisivel;
    @FXML
    private TextField tfValor2;
    @FXML
    private Label lblResultado;
    @FXML
    private Label lblValor1;
    @FXML
    private Label lblValor2;
    @FXML
    private Label lbTitulo;
    
    Calculos calc;
    
    @FXML
    void Calcular(ActionEvent event) throws RemoteException {
        
        boolean campo1 = ValidaCampos.textFieldNotEmpty(tfValor1);
        boolean campo2 = ValidaCampos.textFieldNotEmpty(tfValor2);
        
        if (!campo1 || !campo2) {
            tfValor1.setPromptText("*obrigatório");
            tfValor1.getStyleClass().add("promptTf");
            tfValor2.setPromptText("*obrigatório");
            tfValor2.getStyleClass().add("promptTf");
            lblResultado.setVisible(false);
            lblDivisivel.setVisible(false);
        } else {
            lblResultado.setVisible(true);
            lblDivisivel.setVisible(true);
            try {
                Double v1 = Double.parseDouble(tfValor1.getText());
                Double v2 = Double.parseDouble(tfValor2.getText());
                double soma = calc.soma(v1, v2);
                boolean eDiv = calc.eDivisivel(v1, v2);
                String divResult = String.valueOf(eDiv);
                lblResultado.setText("Soma: " + formataDouble(soma));
                lblResultado.setTextFill(Color.GREEN);
                
                if (eDiv) {
                    lblDivisivel.setText(v1 + " é divisível por " + v2);
                    lblDivisivel.setTextFill(Color.BLUE);
                } else if (!eDiv) {
                    lblDivisivel.setText(formataDouble(v1) + " não é divisível por " + formataDouble(v2));
                    lblDivisivel.setTextFill(Color.RED);
                }
                
            } catch (RemoteException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void iniciaLayout() {
        btn_resultado.getStyleClass().add("btEnviar");
        btn_resultado.setEffect(new Reflection());
        
        btnSair.setText("Sair");
        btnSair.setPadding(new Insets(10, 10, 10, 10));
        btnSair.setEffect(new Reflection());
        btnSair.getStyleClass().add("btSair");
        
        lbTitulo.setText("Calculadora Usando RMI".toUpperCase());
        lbTitulo.setFont(Font.font("Source Code Pro", FontWeight.BLACK, 18.5));
        lbTitulo.setTextFill(Color.DARKCYAN);
        lbTitulo.setEffect(new Lighting());
        
        lblResultado.getStyleClass().add("lblResultado");
        lblDivisivel.getStyleClass().add("lblDivisivel");
        lblResultado.setVisible(false);
        lblDivisivel.setVisible(false);
    }
    
    @FXML
    public static String formataDouble(double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            return String.format("%s", d);
        }
    }
    
    @FXML
    void sairDoSistema(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaLayout();
        try {
            ServidorCalc ServC = new ServidorCalc(1099);
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("ServidorCalc", ServC);
            JOptionPane.showMessageDialog(null, "Servidor Online!");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Erro ao carregar o servidor" + e);
        }
//        Calendar calendario = Calendar.getInstance();
//        int hora = calendario.get(Calendar.HOUR_OF_DAY);
//        if (hora < 12) {
//            JOptionPane.showMessageDialog(null, "Bom Dia!");
//        } else if (hora > 12 && hora < 18) {
//            JOptionPane.showMessageDialog(null, "Boa Tarde!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Boa Noite!");
//        }
        String remoto = ("rmi://localhost/ServidorCalc");
        try {
            calc = (Calculos) Naming.lookup(remoto);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erro no cliente" + e);
        }
    }
}
