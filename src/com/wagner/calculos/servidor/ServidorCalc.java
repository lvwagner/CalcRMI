/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wagner.calculos.servidor;

import com.wagner.calculos.Calculos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;

/**
 *
 * @author wagner
 */
public class ServidorCalc extends UnicastRemoteObject implements Calculos {

    public ServidorCalc(int port) throws RemoteException {
        super(port);
    }

    @Override
    public double soma(double v1, double v2) throws RemoteException {
//        
        return v1 + v2;
    }

    @Override
    public boolean eDivisivel(double v1, double v2) throws RemoteException {
        boolean divisivel;
        if (v1 % v2 == 0) {
            divisivel = true;
        } else {
            divisivel = false;
        }
        return divisivel;
    }

    public static void main(String[] args) {
        try {
            ServidorCalc ServC = new ServidorCalc(1099);
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("ServidorCalc", ServC);
            JOptionPane.showMessageDialog(null, "Servidor Online!");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Erro ao carregar o servidor" + e);
        }
    }

}
