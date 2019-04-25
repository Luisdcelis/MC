import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


class Lienzo extends Canvas
{
    randomGenerator r = new randomGenerator(new BigInteger("23"));
    int nRand;
    String generador;
    
    public void dibujar(int nRand, String generador)
    {
        this.nRand = nRand;
        this.generador = generador;
        repaint();
    }
    
    public void paint(Graphics g)
    {
//        int x, y;
        for(int i = 0; i < nRand; i++)
        {
            int x = (int) Math.round(r.nextRandomGen(generador)*700);
            int y = (int) Math.round(r.nextRandomGen(generador)*700); 
            //System.out.println("x:" + x + " y:" + y);
            g.fillOval(x, y, 1, 1);
        }
        System.out.println("ENDED");
    }
    
    
}
public class Practica{
    
    static Lienzo l = new Lienzo();
    
    private static void mostrar(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame ventana = new JFrame("Simulaciones");
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDesktopPane dp = new JDesktopPane();
        ventana.setContentPane(dp);
        ventana.setSize(1207, 752);
        ventana.setLocationRelativeTo(null);
        
        
        //////*Internal frame 1: Graficos*\\\\\\

        
        JInternalFrame intF = new JInternalFrame("Graficos");
        intF.setSize(700, 700);
        intF.setResizable(true);
        intF.setClosable(true);
        
        dp.add(intF);
        
        
        //////*Internal frame 2: Parametros*\\\\\\
        
        JInternalFrame intF2 = new JInternalFrame("Parametros");

        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JButton acerca = new JButton("Acerca de");
        acerca.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Acerca de...");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el boton de \"Acerca de\"");
            
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(intF2);
            d.setVisible(true);
        });
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.insets = new Insets(0, 0, 20, 20);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panel1.add(acerca, c);
        
        JButton ayuda = new JButton("Ayuda");
        ayuda.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Ayuda");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Esta es una ventana de ayuda");
            JButton b = new JButton("OK");
            b.addActionListener((ActionEvent ev) -> {
                d.dispose();
            });
            
            p.add(l);
            p.add(b);
            d.add(p);
            d.setSize(200,100);
            d.setLocationRelativeTo(intF2);
            d.setVisible(true);
        });
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        //c.insets = new Insets(0, 20, 20, 0);

        panel1.add(ayuda, c);
        JLabel etiq = new JLabel("Generador a usar: ");
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        String[] gener = {"1) 26 1a", "2) 26 1b", "3) 26 2", "4) 26 3",
                          "5) Generador combinado 26_42",
                          "6)  Generador Fishman and Moore1",
                          "7)  Generador Fishman and Moore2",
                          "8)  Generador Randu"};
        
        JComboBox<String> ListaGen = new JComboBox<String>(gener);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(ListaGen, c);
        
        etiq = new JLabel("Cantidad de puntos");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel1.add(etiq, c);
        
        JTextField parametros = new JTextField(20);
        JButton para = new JButton("Dibujar");
        para.addActionListener((ActionEvent e) -> {
            int nRand;
            try{
                nRand = Integer.parseInt(parametros.getText());
            }catch(Exception exc){
                JFrame d = new JFrame("ERROR!");
                JPanel p = new JPanel(true);
                JLabel l = new JLabel("Debes introducir un numero");
                JButton b = new JButton("OK");
                b.addActionListener((ActionEvent ev) -> {
                    d.dispose();
                });
            
                p.add(l);
                p.add(b);
                d.add(p);
                d.setSize(200,100);
                d.setLocationRelativeTo(intF2);
                d.setVisible(true);
                nRand = Integer.parseInt(parametros.getText());
            }
            String generador = (String) ListaGen.getSelectedItem();
            l.dibujar(nRand, generador);
            intF.add(l);
            try {
                intF.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.insets = new Insets(20, 0, 0, 0);
        panel1.add(parametros, c);
        
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        //c.anchor = GridBagConstraints.PAGE_END;
        panel1.add(para, c);

        intF2.add(panel1);
        intF2.setSize(500, 350);
        intF2.setLocation(701, 0);
        intF2.setResizable(true);
        intF2.setClosable(true);
        intF2.setVisible(true);
        
        dp.add(intF2);
        
        //////*Internal frame 3: Estadisticas*\\\\\\
        
        JInternalFrame intF3 = new JInternalFrame("Estadisticas");
        intF3.setSize(500, 350);
        intF3.setLocation(701, 351);
        intF3.setResizable(true);
        intF3.setClosable(true);
        intF3.setVisible(true);
        
        Canvas can2 = new Canvas();
        can2.setBackground(Color.gray);
        can2.setVisible(true);
        can2.setFocusable(false);
        intF3.add(can2);
        
        dp.add(intF3);
        
        
        //////*Menu*\\\\\\

	JMenuBar blackmenu = new JMenuBar();
	blackmenu.setOpaque(true);
	blackmenu.setBackground(new Color(255, 255, 255));
        blackmenu.setPreferredSize(new Dimension(200, 20));
        
        ventana.setJMenuBar(blackmenu);

	JMenu menu1 = new JMenu("Menu 1");
	menu1.setOpaque(true);
	menu1.setBackground(new Color(255, 255, 255));
        menu1.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu1);

        JMenu menu2 = new JMenu("Menu 2");
	menu2.setOpaque(true);
	menu2.setBackground(new Color(255, 255, 255));
        menu2.setPreferredSize(new Dimension(150, 20));
        blackmenu.add(menu2);
        
        JMenuItem item1 = new JMenuItem("Primer Item del menu", KeyEvent.VK_F1);
        item1.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item1");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el primer item del menu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu1.add(item1);
        
        JMenuItem item2 = new JMenuItem("Primer item del menu2", KeyEvent.VK_3);
        item2.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el primer item del menu2");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu2.add(item2);
        
        JMenuItem item3 = new JMenuItem("Segundo Item del menu1", KeyEvent.VK_A);
        item3.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item2");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el segundo item del menu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        menu1.add(item3);
        
        JMenu submenu1 = new JMenu("Submenu");
        menu1.add(submenu1);
        
        JMenuItem subItem1 = new JMenuItem("item del submenu", KeyEvent.VK_4);
        subItem1.addActionListener((ActionEvent e) -> {
            JFrame d = new JFrame("Item del submenu");
            JPanel p = new JPanel(true);
            JLabel l = new JLabel("Has pulsado el item del submenu");
            
            l.setHorizontalAlignment(JLabel.CENTER);
    	    l.setVerticalAlignment(JLabel.CENTER);
            
            d.add(p);
            d.add(l);
            d.setSize(250,100);
            d.setLocationRelativeTo(ventana);
            d.setVisible(true);
        });
        submenu1.add(subItem1);
        
	ventana.setVisible(true);
        intF.setVisible(true);
	}

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mostrar();
            }
        });
    }
}
