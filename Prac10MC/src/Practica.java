import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

/* TopLevelDemo.java requires no other files. */
public class Practica {
    
    static String textfile = "";
    static String inputs = "";

    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Interprete de URM");
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ////////////////////////*PANEL*\\\\\\\\\\\\\\\\\\\\\\\\
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JTextArea input = new JTextArea();
        input.setFont(new Font("Serif", Font.PLAIN, 32));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        input.setBorder(BorderFactory.createCompoundBorder(border, 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        input.setLineWrap(true);
        input.setColumns(20);
        input.setRows(10);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        panel.add(input, c);
        JScrollPane sp1 = new JScrollPane(input, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(sp1,c);
        
        JTextArea output = new JTextArea();
        output.setFont(new Font("Serif", Font.PLAIN, 32));
        output.setColumns(20);
        output.setRows(2);
        output.setBorder(BorderFactory.createCompoundBorder(border, 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        output.setLineWrap(true);
        output.setEditable(false);

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        panel.add(output, c);
        JScrollPane sp = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(sp,c);
 
        
        JButton bConvert = new JButton("Ejecutar");
        bConvert.setPreferredSize(new Dimension(115, 65));
        bConvert.setFont(new Font("Monospaced", Font.PLAIN, 16));
        Font font = bConvert.getFont();  
        bConvert.setFont(font.deriveFont(Font.BOLD));
        bConvert.addActionListener((ActionEvent ex) -> {
            String s, resu = "";
            int[] regs = new int[100];
            s = input.getText();
            String[] instr = s.split("\n");
            String[] numericos = inputs.split(",");
            try{
                for(int i = 0; i < numericos.length; i++)
                {
                    regs[i] = Integer.parseInt(numericos[i]);
                }
            }catch(Exception e){}
            URMinter urm = new URMinter(instr, regs);
            
            int res = urm.interpreta();
            resu = String.valueOf(res);
            
            output.setText(resu);
        });
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(bConvert, c);
        
        JLabel flecha = new JLabel("→");
        flecha.setFont(new Font("Serif", Font.PLAIN, 40));
        c.gridx = 2;
        c.gridy =2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 0, 60, 0);
        panel.add(flecha, c);

        //Set the menu bar and add the label to the content pane.
        frame.getContentPane().add(panel);
 
        ////////////////////////*MENU*\\\\\\\\\\\\\\\\\\\\\\\\

        JMenuBar blackmenu = new JMenuBar();
	blackmenu.setOpaque(true);
	blackmenu.setBackground(new Color(255, 255, 255));
        blackmenu.setPreferredSize(new Dimension(200, 20));
        
        frame.setJMenuBar(blackmenu);

	JMenu menu1 = new JMenu("Abrir");
	menu1.setOpaque(true);
	menu1.setBackground(new Color(255, 255, 255));
        menu1.setPreferredSize(new Dimension(50, 20));
        blackmenu.add(menu1);
        
        JMenuItem item1 = new JMenuItem("fichero de texto", KeyEvent.VK_F1);
        item1.addActionListener((ActionEvent e) -> {
            textfile = "";
            JFileChooser fc = new JFileChooser("C:\\Users\\Luis\\Documents\\NetBeansProjects\\Prac10MC");
            int returnVal = fc.showOpenDialog(null);
            fc.setAcceptAllFileFilterUsed(false);
            File file = fc.getSelectedFile();
            
            Scanner s;
            try {
                s = new Scanner(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
                s = null;
            }
            
            while(s.hasNextLine())
            {
                textfile = textfile + s.nextLine();
                if(s.hasNextLine())
                    textfile = textfile + "\n";
            }
                
            
            input.setText(textfile);
            
        });
        menu1.add(item1);
        
        JMenu menu2 = new JMenu("Input");
        menu2.setOpaque(true);
	menu2.setBackground(new Color(255, 255, 255));
        menu2.setPreferredSize(new Dimension(80, 20));
        blackmenu.add(menu2);
        
        JMenuItem item2_1 = new JMenuItem("Añadir input", KeyEvent.VK_1);
        menu2.add(item2_1);
        item2_1.addActionListener((ActionEvent e) -> {
            
            JFrame d = new JFrame("input");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Introduzca los inputs (separados por comas): ");
            JTextField j = new JTextField(11);
            JButton b = new JButton("Añadir");
            b.addActionListener((ActionEvent ev) -> {
                inputs = j.getText();
                d.dispose();
            });
            
            p.add(l);
            p.add(j);
            p.add(b);
            d.add(p);
            d.setSize(350,100);
            d.setLocationRelativeTo(frame);
            d.setVisible(true);
        });
        

 
        
 
        //Display the window.
        frame.setVisible(true);
    }
    
    
    
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

