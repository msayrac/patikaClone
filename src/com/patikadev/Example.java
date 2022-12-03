package com.patikadev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;

    public Example(){

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
           }
        }


        add(wrapper);
        setSize(400,300);
        setTitle("Uygulama Adý");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/2;
        setLocation(x,y);
        setVisible(true);

        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(fld_username.getText().length() ==0 || fld_password.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"Tüm alanlarý doldurun!","Hata",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }


}
