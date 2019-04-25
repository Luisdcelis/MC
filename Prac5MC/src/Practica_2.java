import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
 
/* TopLevelDemo.java requires no other files. */
public class Practica_2 {
    
    static String textfile = "";
    static BigInteger clave = new BigInteger("0");

    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Cifrado");
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
        output.setRows(10);
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
 
        
        JButton bConvert = new JButton("Convert");
        bConvert.setPreferredSize(new Dimension(115, 65));
        bConvert.setFont(new Font("Monospaced", Font.PLAIN, 16));
        Font font = bConvert.getFont();  
        bConvert.setFont(font.deriveFont(Font.BOLD));
        bConvert.addActionListener((ActionEvent ex) -> {
            String s, resu;
            s = input.getText();
            resu = codificar(s);
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
        panel.add(flecha, c);

        //Set the menu bar and add the label to the content pane.
        frame.getContentPane().add(panel);
 
        ////////////////////////*MENU*\\\\\\\\\\\\\\\\\\\\\\\\

        JMenuBar blackmenu = new JMenuBar();
	blackmenu.setOpaque(true);
	blackmenu.setBackground(new Color(255, 255, 255));
        blackmenu.setPreferredSize(new Dimension(200, 20));
        
        frame.setJMenuBar(blackmenu);

	JMenu menu1 = new JMenu("Seleccionar");
	menu1.setOpaque(true);
	menu1.setBackground(new Color(255, 255, 255));
        menu1.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu1);
        
        JMenuItem item1 = new JMenuItem("fichero de texto", KeyEvent.VK_F1);
        item1.addActionListener((ActionEvent e) -> {
            textfile = "";
            JFileChooser fc = new JFileChooser("C:\\Users\\Luis\\Documents\\NetBeansProjects\\Prac5MC\\src");
            int returnVal = fc.showOpenDialog(null);
            fc.setAcceptAllFileFilterUsed(false);
            File file = fc.getSelectedFile();
            
            Scanner s;
            try {
                s = new Scanner(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Practica_2.class.getName()).log(Level.SEVERE, null, ex);
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
        
        JMenu menu2 = new JMenu("Opciones");
        menu2.setOpaque(true);
	menu2.setBackground(new Color(255, 255, 255));
        menu2.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu2);
        
        JMenuItem item2_1 = new JMenuItem("Asignar nueva clave", KeyEvent.VK_1);
        menu2.add(item2_1);
        item2_1.addActionListener((ActionEvent e) -> {
            
            JFrame d = new JFrame("Clave");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Introduzca la clave: ");
            JTextField j = new JTextField(11);
            JButton b = new JButton("Añadir");
            b.addActionListener((ActionEvent ev) -> {
                try{
                clave = new BigInteger(j.getText());
                }catch(Exception be){
                    JFrame de = new JFrame("ERROR!");
                    JPanel pe = new JPanel(true);
                    JLabel le = new JLabel("LA CLAVE DEBE SER UN NUMERO");
                    pe.add(le);
                    de.add(pe);
                    de.setSize(200,70);
                    de.setLocationRelativeTo(frame);
                    de.setVisible(true);
                }
                d.dispose();
            });
            
            p.add(l);
            p.add(j);
            p.add(b);
            d.add(p);
            d.setSize(200,120);
            d.setLocationRelativeTo(frame);
            d.setVisible(true);
        });
        
        JMenuItem item2_2 = new JMenuItem("Mostrar clave", KeyEvent.VK_1);
        menu2.add(item2_2);
        item2_2.addActionListener((ActionEvent e) -> {
            
            JFrame d = new JFrame("Clave");
            JPanel p = new JPanel(new GridBagLayout());
            JLabel l = new JLabel("La clave es: " + clave);
            JButton b = new JButton("OK");
            b.addActionListener((ActionEvent ev) -> {
                d.dispose();
            });
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            p.add(l, c);
            
            c.gridx = 0;
            c.gridy = 1; 

            p.add(b, c);
            d.add(p);
            d.setSize(200, 120);
            d.setLocationRelativeTo(frame);
            d.setVisible(true);
        });
 
        
 
        //Display the window.
        frame.setVisible(true);
    }
    
    static String str2bin(String s)
    {
        String binS = "";
        String c;
        for(int i = 0; i<s.length(); i++)
        {
            c = Integer.toBinaryString((int)s.charAt(i));
            while(c.length() < 8)
                c = "0" + c;
                
            binS += c;
        }
        
        return binS;
    }
    
    static String getCode(BigInteger clave, int sizey) //sizey es el length de la clave en binario
    {
        ca1DSimulator ca1d = new ca1DSimulator(2, 1, 90, 300, 1, "5) Generador combinado 26_42", clave);
        String code = "";
        for(int i = 0; i < sizey; i++)
        {
            code += ca1d.vec1[150];
            ca1d.caComputation(i);
        }
        
        return code;
    }
    
    static String codificate(String binS, String code)
    {
        String binCodi = "";
        String resu = "";
        String aux = "";
        Converter c = new Converter();
        
        for(int i = 0; i < binS.length(); i++)
        {
            if(binS.charAt(i) == code.charAt(i))
                binCodi += "0";
            else
                binCodi += "1";
        }
        
        for(int i  = 0; i < binCodi.length(); i+=8)
        {
            aux = "";
            for(int j = 0; j < 8; j++)
            {
                aux += Character.toString(binCodi.charAt(i+j));
            }
            resu += Character.toString((char) Integer.parseInt(aux, 2));
        }
        return resu;
    }
    
    static String codificar(String s)
    {
        String binS = str2bin(s);
        String code = getCode(clave,binS.length());
        String resu = codificate(binS, code);
        return resu;
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

