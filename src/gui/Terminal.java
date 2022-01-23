package gui;

import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Terminal extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panel;
    private JTextArea txtMessage;
    private JTextField input;
    private String msgs = "";
    private String command = "";

    private String host;
    private int port;
    public JButton button;

    public static void main(String[] arg) {
        new Terminal("test");
    }


    public Terminal(String name) {
        super(name);
        this.port = port;

        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panel);

        this.txtMessage = new JTextArea(15,50);
        txtMessage.setWrapStyleWord(true);
        this.txtMessage.setEditable(false);
        JScrollPane scroller = new JScrollPane(txtMessage);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel inputpanel = new JPanel();
        //inputpanel.setLayout(new FlowLayout());
        input = new JTextField(40);
        button = new JButton("Enter");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getAndClearInput();

            }});

        DefaultCaret caret = (DefaultCaret) txtMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(scroller);
        inputpanel.add(input);
        inputpanel.add(button);
        panel.add(inputpanel);
        //this.getContentPane().add(BorderLayout.CENTER, panel);
        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
        input.requestFocus();

    }


    public void getAndClearInput() {
        command = this.input.getText();
        input.setText("");
    }

    public Terminal(int port, String host) {
        this(" ");
        this.host = host;
    }


    public void append(String s) {
        this.msgs += s;
        this.msgs += "\n";
    }

    public JButton getButton() {
        return this.button;
    }
    public String getInput() {
        return input.getText();
    }

    public String getCommand()
    {
        return command;
    }
    public void clearCommand() {
        this.command = "";
    }

    public void setMsg(String s) {
        append(s);
        this.txtMessage.setText(this.msgs);
    }

    public void refresh() {
        txtMessage.update(txtMessage.getGraphics());
    }

}


