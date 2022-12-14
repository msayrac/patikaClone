package com.patikadev.view;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.model.Course;
import com.patikadev.model.Operator;
import com.patikadev.model.Patika;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane pnl_patika_list;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JPanel pnl_user_form;
    private JPanel pnl_user_top;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private final Operator operator;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object [] row_course_list;


    public OperatorGUI(Operator operator){
        this.operator = operator;
        add(wrapper);
        setSize(1000,500);

        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Ho�geldin : " + operator.getName());

        //  UserList
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column ==0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID","Ad Soyad","Kullan�c� Ad�","�ifre","�yelik Tipi"};

        mdl_user_list.setColumnIdentifiers(col_user_list);

        row_user_list = new Object[col_user_list.length];

        loadUserModel();



        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e-> {

            try{
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_user_id.setText(select_user_id);
            }catch (Exception exception) {
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                if (User.update(user_id,user_name,user_uname,user_pass,user_type)) {
                    Helper.showMessage("done");

                }
                loadUserModel();

            }
        });

        // ### UserList


        // PatikaList
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("G�ncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {

            if(Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());

                if(Patika.delete(select_id)){
                    Helper.showMessage("done");
                    loadPatikaModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Ad�"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });

        // ### PatikaList


        // Course List

        mdl_course_list = new DefaultTableModel();
        Object [] col_courseList = {"ID","Ders Ad�","Programlama Dili","Patika","E�itmen"};

        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];

        loadCourseList();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);





        // ###  Course List












        btn_user_add.addActionListener(e-> {

            if(Helper.isFieldEmpty(fld_user_name)|| Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)){

                Helper.showMessage("fill");
            } else{
                String name = fld_user_name.getText();
                String uname = fld_user_name.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();

                if(User.add(name,uname,pass,type)){
                    Helper.showMessage("done");
                    loadUserModel();

                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }
            }

        });

        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_user_id)){
                    Helper.showMessage("fill");
                } else{
                    if(Helper.confirm("sure")){
                        int user_id = Integer.parseInt(fld_user_id.getText());

                        if(User.delete(user_id)){
                            Helper.showMessage("done");
                            loadUserModel();
                        }else{
                            Helper.showMessage("error");
                        }
                    }
                    }
            }
        });


        btn_user_sh.addActionListener(e -> {

            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_user_type.getSelectedItem().toString();

            String query = User.searchQuery(name,uname,type);
            loadUserModel(User.searchUserList(query));
        });
        btn_logout.addActionListener(e -> {
            dispose();

        });



        btn_patika_add.addActionListener(e -> {

            if(Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            } else{
                if(Patika.add(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    fld_patika_name.setText(null);
                }else{
                    Helper.showMessage("error");

                }
            }

        });
    }

    private void loadCourseList() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        int i =0;
        for(Course obj: Course.getList()){
            i=0;
            row_course_list[i++] =obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list );
        }


    }

    private void loadPatikaModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(Patika obj : Patika.getList()){
            i=0;

            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }

    }

    public void loadUserModel(){

        DefaultTableModel clearModel = (DefaultTableModel)tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(User obj : User.getList()){

            i =0;
            row_user_list[i++]= obj.getId();
            row_user_list[i++]= obj.getName();
            row_user_list[i++]= obj.getUname();
            row_user_list[i++]= obj.getPass();
            row_user_list[i++]= obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list){

        DefaultTableModel clearModel = (DefaultTableModel)tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(User obj : list){

            i =0;
            row_user_list[i++]= obj.getId();
            row_user_list[i++]= obj.getName();
            row_user_list[i++]= obj.getUname();
            row_user_list[i++]= obj.getPass();
            row_user_list[i++]= obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }




    public static void main(String[] args) {

        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Mustafa �etinda�");
        op.setPass("1234");
        op.setUname("mustafa");
        op.setType("operator");


        OperatorGUI opGUI = new OperatorGUI(op);


    }


}
