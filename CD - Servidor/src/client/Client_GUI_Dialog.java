package src.client;

import src.AES;
import src.server.RemoteInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client_GUI_Dialog extends JDialog {
    private JPanel contentPane;
    private JButton conectarButton;
    private JRadioButton rad_a;
    private JRadioButton rad_b;
    private JComboBox cmb_oper;
    private JTextField txt_val;
    private JButton btn_exe;
    private JTextArea txta_console;
    private JTextField txt_IP;
    private JTextField txt_Port;
    private JTextPane pnl_console;

    private int variable = 0;
    private int operacion = 0;
    private int value = 0;
    private String var_letra = "";
    private RemoteInterface service;

    private AES aes;

    public Client_GUI_Dialog() {
        setContentPane(contentPane);
        setModal(true);



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        conectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConnect();
            }
        });
        rad_a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRad_A();
            }
        });

        rad_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRad_B();
            }
        });

        cmb_oper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOper();
            }
        });

        btn_exe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExec();
            }
        });
    }

    private void onOper() {
        int index = cmb_oper.getSelectedIndex();
        if (index > 1) {
            operacion = index - 2;
            txt_val.setEnabled(true);
            btn_exe.setEnabled(true);
        } else if (index == 1) {
            operacion = 3;
            btn_exe.setEnabled(true);
        } else {
            btn_exe.setEnabled(false);
        }
    }

    private void onExec() {
        try {

            JSONObject json = new JSONObject();
            JSONParser parser = new JSONParser();
            JSONObject answer_json;
            String answer;
            String request;


            json.put("operation", operacion + "");
            json.put("variable", variable + "");





            if (operacion == 3) {

                //Se completa el json
                json.put("value", "0");

                //Se encripta el mensaje
                request = aes.encrypt(json.toString());

                //Se hace la solicitud al servidor
                answer = aes.decrypt(service.operation(request));

                //Se transforma el mensaje recibido a un json
                answer_json = (JSONObject) parser.parse(answer);

                txta_console.append("Variable " + var_letra + "= " + answer_json.get("result") + "\n");
            } else if (operacion > 3) {
                txta_console.append("Operación no válida.\n");
            } else {
                if (variable == 0 || variable == 1) {

                    value = Integer.parseInt(txt_val.getText());

                    //Se completa el json
                    json.put("value", value + "");

                    //Se encripta el mensaje
                    request = aes.encrypt(json.toString());

                    //Se hace la solicitud al servidor
                    answer = aes.decrypt(service.operation(request));

                    //Se transforma el mensaje recibido a un json
                    answer_json = (JSONObject) parser.parse(answer);


                    txta_console.append("Respuesta del servidor: " + answer_json.get("result") + "\n");

                    json.clear();

                    json.put("operation", "3");
                    json.put("variable", variable + "");
                    json.put("value", "0");

                    request = aes.encrypt(json.toString());

                    answer = aes.decrypt(service.operation(request));

                    answer_json.clear();
                    answer_json = (JSONObject) parser.parse(answer);

                    txta_console.append("Valor actual de la variable  " + var_letra + ": " + answer_json.get("result") + "\n");
                    cmb_oper.setSelectedIndex(0);
                    txt_val.setText("");
                    txt_val.setEnabled(false);
                    btn_exe.setEnabled(false);
                } else {
                    txta_console.append("Variable no válida.\n");
                }
            }
        } catch (RemoteException e) {
            txta_console.append("Error: " + e.toString() + "\n");
        } catch (NumberFormatException e) {
            txta_console.append("Error: Entrada no válida. Escribir un número entero.\n");
        } catch (ParseException e) {
            txta_console.append("Error: No se puede parsear el Json recibido.\n");
            throw new RuntimeException(e);
        } catch (Exception e) {
            txta_console.append("Error: No se puede encriptar/desencriptar.\n");
            throw new RuntimeException(e);
        }
    }

    private void onConnect() {
        try {
            String lookup_Name = "rmi://" + txt_IP.getText() + ":" + txt_Port.getText() + "/Compute";
            System.out.println(lookup_Name);
            service = (RemoteInterface) Naming.lookup(lookup_Name);
            txta_console.append("Status: Se estableció la conexión al servidor.\n");
            aes = new AES();
            aes.init();
            rad_a.setEnabled(true);
            rad_b.setEnabled(true);
            conectarButton.setEnabled(false);
        } catch (Exception e) {
            txta_console.append("Error: No se puede establecer la conexión al servidor.\n");
        }

    }

    private void onRad_A() {
        variable = 0;
        var_letra = "a";
        cmb_oper.setEnabled(true);
    }

    private void onRad_B() {
        variable = 1;
        var_letra = "b";
        cmb_oper.setEnabled(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Client_GUI_Dialog dialog = new Client_GUI_Dialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(10, 10, 10, 10), -1, -1));
        conectarButton = new JButton();
        conectarButton.setText("Conectar");
        contentPane.add(conectarButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Variables");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rad_a = new JRadioButton();
        rad_a.setEnabled(false);
        rad_a.setText("a");
        panel1.add(rad_a, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rad_b = new JRadioButton();
        rad_b.setEnabled(false);
        rad_b.setText("b");
        panel1.add(rad_b, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Operación");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cmb_oper = new JComboBox();
        cmb_oper.setEnabled(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("Leer");
        defaultComboBoxModel1.addElement("Asignar");
        defaultComboBoxModel1.addElement("Sumar");
        defaultComboBoxModel1.addElement("Multiplicar");
        cmb_oper.setModel(defaultComboBoxModel1);
        panel3.add(cmb_oper, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Valor");
        panel4.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_val = new JTextField();
        txt_val.setEnabled(false);
        txt_val.setText("");
        panel4.add(txt_val, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btn_exe = new JButton();
        btn_exe.setEnabled(false);
        btn_exe.setText("Ejecutar");
        contentPane.add(btn_exe, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txta_console = new JTextArea();
        txta_console.setEditable(false);
        scrollPane1.setViewportView(txta_console);
        final JLabel label4 = new JLabel();
        label4.setText("IP");
        contentPane.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Puerto");
        contentPane.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_Port = new JTextField();
        txt_Port.setText("5099");
        contentPane.add(txt_Port, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txt_IP = new JTextField();
        txt_IP.setText("localhost");
        contentPane.add(txt_IP, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rad_b);
        buttonGroup.add(rad_a);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
