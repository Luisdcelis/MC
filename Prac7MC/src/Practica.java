import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Practica{
    
    public static parallelBelZab belzab;
    public static BufferedImage imagen2D;
    static boolean para = false;
    static int i_act;
    static int generations_act;

    

    public class Dibujo2D extends JPanel{
   
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) { 

            super.paintComponent(g);
            Graphics2D grafica = (Graphics2D)g;
            grafica.drawImage(imagen2D,0,0,null);
        }
        public void pintar()
        {
            for(int i = 0;i<parallelBelZab.m_color.length;++i)
                for(int j = 0;j<parallelBelZab.m_color.length;++j)
                {
                    imagen2D.setRGB(i, j, parallelBelZab.m_color[i][j].getRGB());
                }

        }
    }
    
    
    
    
    public Practica()
    {
        mostrar();
    }
    
    
    private void mostrar(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame ventana = new JFrame("Game of Life");
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDesktopPane dp = new JDesktopPane();
        ventana.setContentPane(dp);
        ventana.setSize(1407, 752);
        ventana.setLocationRelativeTo(null);
        
        Dibujo2D dibujito = new Dibujo2D();

        
        
        //////*Internal frame 1: Graficos*\\\\\\

        
        JInternalFrame intF = new JInternalFrame("Graficos");
        intF.setSize(700, 700);
        intF.setResizable(true);
        intF.setClosable(false);
        intF.setVisible(true);
        
        dp.add(intF);
        
        //////*Internal frame 3: Estadisticas*\\\\\\
        
//        JInternalFrame intF3 = new JInternalFrame("Células vivas");
//        intF3.setSize(700, 234);
//        intF3.setLocation(701, 468);
//        intF3.setResizable(true);
//        intF3.setClosable(false);
//        intF3.setVisible(true);
//
//        
//        dp.add(intF3);
        
        
        //////*Internal frame 2: Parametros*\\\\\\
        
        JInternalFrame intF2 = new JInternalFrame("Parámetros");

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

        panel1.add(ayuda, c);
        
        c.anchor = GridBagConstraints.CENTER;
        
        JTextField dimensiones = new JTextField("700", 10);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(dimensiones, c);
        
        JLabel label = new JLabel("Dimensiones: ");
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(label, c);
        
        JTextField numGen = new JTextField("500", 10);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 50, 0, 0);
        panel1.add(numGen, c);
        
        JLabel label2 = new JLabel("Numero de generaciones: ");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(label2, c);
        
        
        JTextField TFalpha = new JTextField("1.2", 10);
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 50, 0, 0);
        panel1.add(TFalpha, c);
        
        JLabel Jalpha = new JLabel("Alpha: ");
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(Jalpha, c);
        
        
        JTextField TFbeta = new JTextField("1.0", 10);
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 50, 0, 0);
        panel1.add(TFbeta, c);
        
        JLabel Jbeta = new JLabel("Beta: ");
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(Jbeta, c);
        
        
        JTextField TFgamma = new JTextField("1.0", 10);
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 50, 0, 0);
        panel1.add(TFgamma, c);
        
        JLabel Lgamma = new JLabel("Gamma: ");
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0, 0, 0);
        panel1.add(Lgamma, c);
        
        
        JButton dibu = new JButton("Dibujar");
        dibu.addActionListener((ActionEvent e) -> {
            
            i_act = 0;
            para = false;
            int tamano = Integer.parseInt(dimensiones.getText());
            int generations = Integer.parseInt(numGen.getText());
            generations_act = generations;
            imagen2D = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_RGB);
            double alpha = Double.parseDouble(TFalpha.getText());
            double beta = Double.parseDouble(TFbeta.getText());
            double gamma = Double.parseDouble(TFgamma.getText());
            belzab = new parallelBelZab(tamano, alpha, beta, gamma);
            
            try {
                intF.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            intF.add(dibujito);
            
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run()
                {
                    for(int i=0; i<generations && para == false; ++i)
                    {
                        i_act = i;
                        intF.paintComponents(intF.getGraphics());
                        dibujito.pintar();
                        belzab.caComputation();
                    }
                }
            });
            
            
        });
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(dibu, c);
        
        
        JButton parar = new JButton("PARAR");
        parar.setForeground(Color.red);
        parar.setContentAreaFilled(false);
        parar.addActionListener((ActionEvent e) -> {
            
            para = true;
        });
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(parar, c);

        
        intF2.add(panel1);
        intF2.setSize(700, 700);
        intF2.setLocation(701, 0);
        intF2.setResizable(true);
        intF2.setClosable(false);
        intF2.setVisible(true);
        
        
        JButton continuar = new JButton("CONTINUAR");
        continuar.setForeground(Color.green);
        continuar.setContentAreaFilled(false);
        continuar.addActionListener((ActionEvent e) -> {
            
            para = false;
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run()
                {
                    for(int i=i_act; i<generations_act && para == false; ++i)
                    {
                        i_act = i;
                        intF.paintComponents(intF.getGraphics());
                        dibujito.pintar();
                        belzab.caComputation();
                    }
                }
            });
        });
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(continuar, c);

        
        intF2.add(panel1);
        intF2.setSize(700, 700);
        intF2.setLocation(701, 0);
        intF2.setResizable(true);
        intF2.setClosable(false);
        intF2.setVisible(true);
        
        
        
        dp.add(intF2);
        

        
        
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

	}
    
        public static void CreateAndShow()
        {
            Practica p2 = new Practica();
        }
        
        

	public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                CreateAndShow();
            });
        }
}
