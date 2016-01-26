package hw9;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser.MalformedDataException;
import hw7.EdgeCost;
import hw8.BuildingInfo;
import hw8.CampusPaths;
import hw8.Point;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class handle the GUI functions. It includes loading the image, detecting users' action, and
 * presenting the results.
 *
 */
@SuppressWarnings("serial")
public class HandleImage extends JFrame implements ActionListener {
	
    String startingImage = "src/hw8/data/" + "campus_map.jpg";
    BufferedImage biWorking; // These hold arrays of pixels.
    Graphics gWorking; 
    int w; // width of the current image.
    int h; // height of the current image.
    int newWidth = 1167;
    int newHeight = 800;
    String startName, destName;
    boolean pathsFound = false;
    boolean startBuildingSelected = false;
    boolean endBuildingSelected = false;
    double xStartBuilding, yStartBuilding, xEndBuilding, yEndBuilding;
	
    JPanel viewPanel; // Where the image will be painted.
    JMenuBar menuBar;
    JMenu pathFindingMenu, infoMenu;
    JMenuItem startBuildingItem, endBuildingItem, clearPathItem;
    JMenuItem infoItem, aboutItem;
 
    Graph<Point, EdgeCost> graph;
    HashMap<BuildingInfo, Point> mapBuildingsToPoints;
    HashMap<String, String> mapShortToLongName;
    Vertex<Point, EdgeCost> vStart, vEnd;
    LinkedList<Edge<Point, EdgeCost>> paths = new LinkedList<>();
    
    /**
     * The constructor to create a instance of HandleImage. It initializes the frame of the GUI.
     * @modifies this
     * @effects Initialize the frame.
     */
	public HandleImage() {
		graph = new Graph<>();
		mapBuildingsToPoints = new HashMap<>();
		mapShortToLongName = new HashMap<>();
		try {
			CampusPaths.buildGraph("campus_buildings.dat", "campus_paths.dat", graph, mapBuildingsToPoints, mapShortToLongName);
		}catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
	
		
    	setTitle("Finding shortest path in the UW"); 
        addWindowListener(new WindowAdapter() { // Handle any window close-box clicks.
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        viewPanel = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g) { 
        		super.paintComponent(g);
        		g.drawImage(biWorking.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        		g.setColor(Color.red);
        		// Draw a circle to mark the selected buildings
        		if (startBuildingSelected) {
        			g.drawOval((int)(xStartBuilding/w*newWidth)-20, (int)(yStartBuilding/h*newHeight)-20, 40, 40);
        		}
        		if (endBuildingSelected) {
        			g.drawOval((int)(xEndBuilding/w*newWidth)-20, (int)(yEndBuilding/h*newHeight)-20, 40, 40);
        		}
        		// Draw the path
        		if (pathsFound == true) {
        			Graphics2D g2 = (Graphics2D) g; 
        			for (int i = 0; i < paths.size(); i++){
        				double xEnd = paths.get(i).getEndVertex().getInfo().getX();
        				double yEnd = paths.get(i).getEndVertex().getInfo().getY();
        				double xStart = paths.get(i).getStartVertex().getInfo().getX();
        				double yStart = paths.get(i).getStartVertex().getInfo().getY();
        				g2.setColor(Color.red);
        				g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        				Shape sp = new Line2D.Double((xStart/w*newWidth), (yStart/h*newHeight), 
        						(xEnd/w*newWidth), (yEnd/h*newHeight));
        				g2.draw(sp);
        			}
        			clearPathItem.setEnabled(true);
        		}
        		
        	}
        };
 
        
        add("Center", viewPanel); // Put it smack dab in the middle of the JFrame.

        // Create standard menu bar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        pathFindingMenu = new JMenu("Shortest path");
        infoMenu = new JMenu("Information");
        menuBar.add(pathFindingMenu);
        menuBar.add(infoMenu);
        
        // Create the Shortest path menu items.
        startBuildingItem = new JMenuItem("Starting building");
        startBuildingItem.addActionListener(this);
        pathFindingMenu.add(startBuildingItem);
        endBuildingItem = new JMenuItem("Destination building");
        endBuildingItem.addActionListener(this);
        pathFindingMenu.add(endBuildingItem);
        clearPathItem = new JMenuItem("Reset");
        clearPathItem.addActionListener(this);
        pathFindingMenu.add(clearPathItem);
        clearPathItem.setEnabled(false);
        
        // Create the Info menu items.
        infoItem = new JMenuItem("Name of buildings");
        infoItem.addActionListener(this);
        infoMenu.add(infoItem);
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        infoMenu.add(aboutItem);
        
        loadImage(startingImage); // Read in the starting image.
        setVisible(true); // Display it.
    }
    
    
	/**
	 * Load the file and print the image
	 * @param filename the name of the file
	 */
	public void loadImage(String filename) {
    	
        try {
            biWorking = ImageIO.read(new File(filename));
            w = biWorking.getWidth();
            h = biWorking.getHeight();
            viewPanel.setSize(newWidth, newHeight);
            gWorking = biWorking.getGraphics();
            gWorking.drawImage(biWorking, 0, 0, null);
            pack(); 
            repaint();
        } catch (IOException e) {
            System.out.println("Image could not be read: "+filename);
            System.exit(1);
        }

    }

	/**
	 * Handle the clicks under the Shortest path menu.
	 * @param mi the JMenuItem instance
	 */
	public void handlePathFindMenu(JMenuItem mi){
	       final JFrame parent = new JFrame();
	       // If startBuildingItem is selected
	       if (mi == startBuildingItem) {
	    	   startName = JOptionPane.showInputDialog(parent,
	                        "Please enter the short name of the starting building", "CSE");
	    	   String longNameStart = null;
	    	   if (mapShortToLongName.containsKey(startName)) {
	    		   longNameStart = mapShortToLongName.get(startName);
	    		   BuildingInfo bInfoStart = new BuildingInfo(startName, longNameStart);
	    		   Point pStart = mapBuildingsToPoints.get(bInfoStart);
	    		   vStart = graph.getVertex(pStart);
	    		   xStartBuilding = vStart.getInfo().getX();
	    		   yStartBuilding = vStart.getInfo().getY();
	    		   startBuildingItem.setEnabled(false);
	    		   startBuildingSelected = true;
	    	   } 
	       }
	       // If endBuildingItem is selected
	       if (mi == endBuildingItem) {
	    	   destName = JOptionPane.showInputDialog(parent,
                   "Please enter the short name of the destination", "PAB");
	    	   String longNameEnd = null;
	    	   if (mapShortToLongName.containsKey(destName)) {
	    		   longNameEnd = mapShortToLongName.get(destName);
	    		   BuildingInfo bInfoEnd = new BuildingInfo(destName, longNameEnd);
	    		   Point pEnd = mapBuildingsToPoints.get(bInfoEnd);
	    		   vEnd = graph.getVertex(pEnd);
	    		   xEndBuilding = vEnd.getInfo().getX();
	    		   yEndBuilding = vEnd.getInfo().getY();
	    		   endBuildingItem.setEnabled(false);
	    		   endBuildingSelected = true;
	    	   }
	       }
	    // If reset item is selected
	       if (mi == clearPathItem) {
  				paths.clear();
  				pathsFound = false;
  				startBuildingSelected = false;
  				endBuildingSelected = false;
  				startBuildingItem.setEnabled(true);
  				endBuildingItem.setEnabled(true);
  				repaint();        			
  				clearPathItem.setEnabled(false);
	       }
	       // If both buildings are selected, calculate the path
	       if (!startBuildingItem.isEnabled() && !endBuildingItem.isEnabled()) {	
   				paths.clear();
   				pathsFound = false;
   				paths = CampusPaths.shortestPathDijkstra(graph, vStart, vEnd);
   				pathsFound = true;
   				repaint();
	       }
	       
	       
	}
	
	/**
	 * Handle the clicks under the Info menu.
	 * @param mi the JMenuItem instance
	 */
	public void handleInfoMenu(JMenuItem mi){
		if (mi == infoItem) {
			JTextArea textArea = new JTextArea("Buildings:"
					+"\nBAG: Bagley Hall (East Entrance)"
					+"\nBAG (NE): Bagley Hall (Northeast Entrance)"
					+"\nBGR: By George"
					+"\nCHL: Chemistry Library (West Entrance)"
					+"\nCHL (NE): Chemistry Library (Northeast Entrance)"
					+"\nCHL (SE): Chemistry Library (Southeast Entrance)"
					+"\nCMU: Communications Building"
					+"\nCSE: Paul G. Allen Center for Computer Science & Engineering"
					+"\nDEN: Denny Hall"
					+"\nEEB: Electrical Engineering Building (North Entrance)"
					+"\nEEB (S): Electrical Engineering Building (South Entrance)"
					+"\nFSH: Fishery Sciences Building"
					+"\nGWN: Gowen Hall"
					+"\nHUB: Student Union Building (Main Entrance)"
					+"\nHUB (Food, S): Student Union Building (South Food Entrance)"
					+"\nHUB (Food, W): Student Union Building (West Food Entrance)"
					+"\nIMA: Intramural Activities Building"
					+"\nKNE: Kane Hall (North Entrance)"
					+"\nKNE (E): Kane Hall (East Entrance)"
					+"\nKNE (S): Kane Hall (South Entrance)"
					+"\nKNE (SE): Kane Hall (Southeast Entrance)"
					+"\nKNE (SW): Kane Hall (Southwest Entrance)"
					+"\nLOW: Loew Hall"
					+"\nMCC: McCarty Hall (Main Entrance)"
					+"\nMCC (S): McCarty Hall (South Entrance)"
					+"\nMCM: McMahon Hall (Northwest Entrance)"
					+"\nMCM (SW): McMahon Hall (Southwest Entrance)"
					+"\nMGH: Mary Gates Hall (North Entrance)"
					+"\nMGH (E): Mary Gates Hall (East Entrance)"
					+"\nMGH (S): Mary Gates Hall (South Entrance)"
					+"\nMGH (SW): Mary Gates Hall (Southwest Entrance)"
					+"\nMLR: Miller Hall"
					+"\nMNY: Meany Hall (Northeast Entrance)"
					+"\nMNY (NW): Meany Hall (Northwest Entrance)"
					+"\nMOR: Moore Hall"
					+"\nMUS: Music Building (Northwest Entrance)"
					+"\nMUS (E): Music Building (East Entrance)"
					+"\nMUS (S): Music Building (South Entrance)"
					+"\nMUS (SW): Music Building (Southwest Entrance)"
					+"\nOUG: Odegaard Undergraduate Library"
					+"\nPAA: Physics/Astronomy Building A"
					+"\nPAB: Physics/Astronomy Building"
					+"\nPAR: Parrington Hall"
					+"\nRAI: Raitt Hall (West Entrance)"
					+"\nRAI (E): Raitt Hall (East Entrance)"
					+"\nROB: Roberts Hall"
					+"\nSAV: Savery Hall"
					+"\nSUZ: Suzzallo Library"
					+"\nT65: Thai 65"
					+"\nUBS: University Bookstore"
					+"\nUBS (Secret): University Bookstore (Secret Entrance)");
			JScrollPane scrollPane = new JScrollPane(textArea);  
			textArea.setLineWrap(true);  
			textArea.setWrapStyleWord(true); 
			scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
			JOptionPane.showMessageDialog(null, scrollPane, "Name of buildings",  
			                                       JOptionPane.PLAIN_MESSAGE);
			
		}
		
		if (mi == aboutItem) {
			JOptionPane.showMessageDialog(null, "About:" 
					+ "\nPlease enter the short name of the starting building and ending building by"
					+ "\nclicking the 'Shortest path' menu. "
					+ "\nThe path will not be shown until both starting building and ending building"
					+ "\nhave been entered.");
		}
		
	}
	

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(newWidth, newHeight+65); // Leave some extra height for the menu bar.
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource(); 
        if (obj instanceof JMenuItem) { 
            JMenuItem mi = (JMenuItem)obj; 
            JPopupMenu pum = (JPopupMenu)mi.getParent(); // Get the object it's a child of.
            JMenu m = (JMenu) pum.getInvoker(); // Get the menu from that (popup menu) object.

            if (m==pathFindingMenu)	{ handlePathFindMenu(mi); return; }  // Handle the item depending on what menu it's from.
            if (m==infoMenu)	{ handleInfoMenu(mi); return; }

        } else {
            System.out.println("Unhandled ActionEvent: "+e.getActionCommand());
        }
		
	}
	
}


