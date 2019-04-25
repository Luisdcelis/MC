import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Practica_2{
    
    public static Dibujo2D life;
    public static Stats stats;
    public static ca2DSimulator game;
    public static ca2DSimulator gameSpeed;
    public static BufferedImage imagen2D;
    public static int[] vivas;
    

    public class Dibujo2D extends JPanel{
   
        @Override
        public void paintComponent(Graphics g) { 

            super.paintComponent(g);
            Graphics2D grafica = (Graphics2D)g;
            grafica.drawImage(imagen2D,0,0,null);
        }
        public void pintar()
        {

            for(int i = 0;i<game.m1.length;++i)
                for(int j = 0;j<game.m1.length;++j)
                    if(game.m1[i][j]==1)
                        imagen2D.setRGB(i,j,Color.BLACK.getRGB());
                    else
                        imagen2D.setRGB(i,j,Color.WHITE.getRGB());

        }
    }
    
    class Stats extends JPanel
    {
        int sizex = 700;
        int sizey = 234;
        int tamano;
        
        public Stats(int tamano)
        {
            this.tamano = tamano;
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            int ppg = sizex/vivas.length; //pixeles por generacion
            int cpp = (tamano*tamano)/sizey; //celulas por pixel
            int maxVivas = tamano*tamano;
            int x1, x2, y1, y2;
            for(int i = 0; i < vivas.length-1; i++)
            {
                x1 = i*ppg;
                x2 = (i+1)*ppg;
                y1 = 200 - vivas[i]*sizey/maxVivas;
                y2 = 200 - vivas[i+1]*sizey/maxVivas;
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
    
    
    
    public Practica_2()
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
        life = new Dibujo2D();

        
        
        //////*Internal frame 1: Graficos*\\\\\\

        
        JInternalFrame intF = new JInternalFrame("Graficos");
        intF.setSize(700, 700);
        intF.setResizable(true);
        intF.setClosable(false);
        intF.setVisible(true);
        
        dp.add(intF);
        
        //////*Internal frame 3: Estadisticas*\\\\\\
        
        JInternalFrame intF3 = new JInternalFrame("Células vivas");
        intF3.setSize(700, 234);
        intF3.setLocation(701, 468);
        intF3.setResizable(true);
        intF3.setClosable(false);
        intF3.setVisible(true);

        
        dp.add(intF3);
        
        
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
        
        JTextField dimensiones = new JTextField(10);
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
        
        JTextField numGen = new JTextField(10);
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
        
        
        JButton dibu = new JButton("Dibujar");
        dibu.addActionListener((ActionEvent e) -> {
            
            int tamano = Integer.parseInt(dimensiones.getText());
            int generations = Integer.parseInt(numGen.getText());
            imagen2D = new BufferedImage(tamano, tamano, 10);
            
            vivas = new int[generations];
            
            int nNuc = Runtime.getRuntime().availableProcessors();
            try {
                intF.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica_2.class.getName()).log(Level.SEVERE, null, ex);
            }
            intF.add(life);
            
            game = new ca2DSimulator(tamano, nNuc);
            
            for(int i = 0; i < generations; i++)
            {
                
                for(int j = 0; j < tamano; j++)
                {
                    for(int k = 0; k < tamano; k++)
                    {
                        if(game.m1[j][k] == 1)
                            vivas[i]++;
                    }
                }
                
                System.out.println("Generacion: " + i);
                System.out.println("Vivas: " + vivas[i]);
                System.out.println("Muertas: " + ((tamano*tamano)-vivas[i]));
                double porcentaje = (double)vivas[i]*100/(double)(tamano*tamano);
                System.out.printf("Porcentaje de vivas: %.2f", porcentaje);
                System.out.println("%\n");

                intF.paintComponents(intF.getGraphics());
                intF.update(intF.getGraphics());
                intF.moveToFront();
                life.pintar();
                game.caComputation();

            }
            stats = new Stats(tamano);
            intF3.add(stats);
            try {
                intF3.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Practica_2.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        });
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(dibu, c);
        
        JButton speed = new JButton("Calcular SpeedUp");
        speed.addActionListener((ActionEvent e) -> {
            
            int tamano = Integer.parseInt(dimensiones.getText());
            int generations = Integer.parseInt(numGen.getText());
            int nNuc = Runtime.getRuntime().availableProcessors();
            
            
            gameSpeed = new ca2DSimulator(tamano, nNuc);
            long ini = System.currentTimeMillis();
            for(int i = 0; i < generations; i++)
            {
                gameSpeed.caComputation();
            }
            long tn = System.currentTimeMillis() - ini;
            
            System.out.println("vamos bien");
            
            gameSpeed = new ca2DSimulator(tamano, 1);
            ini = System.currentTimeMillis();
            for(int i = 0; i < generations; i++)
            {
                gameSpeed.caComputation();
            }
            long t1 = System.currentTimeMillis() - ini;
            
            float speedup = (float)t1/(float)tn;
            JOptionPane.showMessageDialog(null, "El SpeedUp es: " + speedup);
            
        });
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel1.add(speed, c);
        
        
        
        intF2.add(panel1);
        intF2.setSize(700, 467);
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
            Practica_2 p2 = new Practica_2();
        }
        
        

	public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                CreateAndShow();
            });
        }
}
