package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int screenCenterPoint(String eksen, Dimension size){

        int point = 0;

        switch (eksen){

            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point = 0;
        }

        return point;

    }

    public static boolean isFieldEmpty(JTextField field){

        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str){
        optionPaneTR();
        String msg;
        String title;

        switch (str){
            case "fill":
                msg = "Lütfen tüm alanlarý doldurunuz!";
                title = "Hata!";
                break;
            case "done":
                msg = "Ýþlem baþarýlý";
                title= "Sonuç";
                break;
            case "error":
                msg= "Bir hata oluþtu!";
                title = "Sonuç ";
            default:
                msg = str;
                title="Hata";
                break;
        }

        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPaneTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }

}
