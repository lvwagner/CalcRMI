/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcrmi;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author wagner
 */
public class ValidaCampos {

    public static boolean textFieldNotEmpty(TextField tf) {
        boolean r = false;
        if (tf.getText() != null && !tf.getText().isEmpty()) {
            r = true;
        }
        return r;
    }

    public static boolean textFieldNotEmpty(TextField tf, Label lbl, String mensagem) {
        boolean r = true;
        String msg = null;
        if (!textFieldNotEmpty(tf)) {
            r = false;
            msg = mensagem;
        }
        lbl.setText(msg);
        return r;
    }
}
