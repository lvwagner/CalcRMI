/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wagner.calculos;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author wagner
 */
public interface Calculos extends Remote {

    public double soma(double v1, double v2) throws RemoteException;

    public boolean eDivisivel(double v1, double v2) throws RemoteException;
}
