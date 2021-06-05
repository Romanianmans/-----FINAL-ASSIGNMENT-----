import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

    int count = 0;
    JRadioButton malebutton;
    JRadioButton femalebutton;
    JLabel label;
    JFrame mainframe;  
    JFrame name;
    JPanel panel;

    public static void main (String [] args) {
        new GUI();
    }

    public GUI() {

        mainframe = new JFrame ();   // create JFrame object
        name = new JFrame();
        String n = JOptionPane.showInputDialog(name, "Enter Name");

        SpinnerModel value =  new SpinnerNumberModel(5, 5, 95, 1);  
        JSpinner age = new JSpinner(value);   
        age.setBounds(75, 200, 100, 30);    
        mainframe.add(age);
        // YOU WERE WORKING HERE ON THE SPINNER VALUE REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

        JButton button = new JButton ("Click me");
        button.addActionListener(this); // goes to THIS class, as in the one that it is currently in

        JLabel nameLabel = new JLabel ("Welcome, " + n);
        mainframe.add(nameLabel);


        label = new JLabel ("Number of clicks: 0"); // preset clicks to 0
        panel = new JPanel ();   // create JPanel object and set it up:
        panel.add(button);
        panel.add(label);
        button.setBounds(75, 100, 100, 30);
    

        mainframe.add(panel, BorderLayout.SOUTH);                  // centers the panel
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // action when gui is closed is to exit
        mainframe.setTitle("Daily Fitness Tracker");
        mainframe.pack();   
        mainframe.setVisible(true);                         
        
        malebutton = new JRadioButton("Male");
        femalebutton = new JRadioButton("Female");    
        malebutton.setBounds(75, 50, 100, 30);    
        femalebutton.setBounds(75, 75, 100, 30);    
        ButtonGroup bg = new ButtonGroup();    
        bg.add(malebutton);
        bg.add(femalebutton);   
        mainframe.add(malebutton); 
        mainframe.add(femalebutton);      
        mainframe.setSize(1000, 750);    
        mainframe.setLayout(null);    
        mainframe.setVisible(true); 
        

        /*  JBUTTON
        JFrame f = new JFrame ("Button Example");
        final JTextField tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);
        JButton b = new JButton ("Click Here");
        b.setBounds(50, 100, 95, 30);
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                tf.setText("welcome to Javapoint.");
            }
        });
        f.add(b);
        f.add(tf);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        */
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);   
        if (malebutton.isSelected()) {
            JOptionPane.showMessageDialog (this, "you are Male.");
        }
        if (femalebutton.isSelected()){    
            JOptionPane.showMessageDialog(this,"You are Female.");    
        }    
    }
}       