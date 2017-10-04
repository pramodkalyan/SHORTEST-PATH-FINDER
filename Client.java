package Client;

//import java.awt.Graphics;
import java.awt.*;
import java.awt.event.MouseEvent;
//import javax.swing.JFrame;
import javax.swing.*;
//import java.awt.Color;
//import java.awt.Font;
import java.lang.Math;
import java.util.*;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;


public  class Client extends JFrame implements ActionListener, MouseListener 
{
    String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    int count=0;
    int [][]adj;//=new int[1000][1000];
    //Scanner in = new Scanner(System.in);
    int [][]d;//=new int[1000][2];
    //mult-array of edges : vertex,vertex,weight
    int [][]e;
    int [][]tempe=new int[200][3];
    int coff=26;
    int loff=18;
    int [][]a;
    //screen dimensions
    int swidth=1300;
    int sheight=700;
    
    //keep count of mouse clicks
    int mousecount=0;
    //current selected nodes
    int s1,s2;
    
    // int[] shortestPaths;
    List <Integer>[] shortestPathCl;
    
    //dummy object
    //Client clientDumm;
    //int nn = in.nextInt();
    
    int N;
    
    //constructor
    //sets the frame
    public Client(int n)
    {
        getContentPane().setBackground(Color.black);
        setTitle("Shortest Path Generator");
        setSize(swidth,sheight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        N = n;//number of nodes. input from user
        adj = new int[N][N];
        d = new int[N][2];
        shortestPathCl = (List<Integer>[]) new List[N];
        for (int i = 0; i < N; i++)
            shortestPathCl[i] = new ArrayList<Integer>();        
    } //end of Client() constructor1
    
    //constructor2
   /* public Client(Client clientDumm)
    {
        this.clientDumm=clientDumm;   
    } */
    
    public void createE()
    {
        int tc=0;
        for(int i=0;i<N;i++)
        {
            int v1=i+1;
            for(int j=0;j<2;j++)
            {
                int v2=(int)Math.ceil(Math.random()*N);
                while(v2==v1) //remove self loops
                {
                    v2=(int)Math.ceil(Math.random()*N);
                }
                int w=(int)Math.ceil(Math.random()*9);
                Boolean rep=false;
                for(int a=0;a<tc;a++)
                {
                    if(tempe[a][0]==v2&&tempe[a][1]==v1)
                        rep=true;
                    if(j==1&&tempe[tc-1][1]==v2)
                        rep=true;
                }
                if(!rep)
                { 
                    tempe[tc][0]=v1;
                    tempe[tc][1]=v2;
                    tempe[tc][2]=w;
                    tc++;
                }
            }
        }
        e=new int[tc][3];
        for(int i=0;i<tc;i++)
        {
            for(int j=0;j<3;j++)
                e[i][j]=tempe[i][j];
            
        }
       // System.out.println(e.length);
        
    } //end of createE()
    
    //called automatically
    //draws the graph.
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        //draw vertices
        
        g.setFont(new Font(null,Font.BOLD,20));
        count=0; // track number of nodes
        for(int i=0;i<N;i++)
        { 
            Boolean rep=false; //check for repetition
            for(int j=0;j<1;j++)
            {
                //System.out.println("#1:count="+count);
                if(count==0)
                { 
                    d[i][0]=(int)Math.floor(25+Math.random()*1225); //random x coord
                    d[i][1]=(int)Math.floor(50+Math.random()*615); //random y coord
                    //System.out.println("2 center: "+(d[i][0]+coff/2)+" "+(d[i][1]+coff/2));
                } 
                else
                { 
                    New:while(!rep) //label added here
                    { 
                        d[i][0]=(int)Math.floor(25+Math.random()*1225); //random x coord
                        d[i][1]=(int)Math.floor(50+Math.random()*615);//random y coord
                        //System.out.println("#2 center: "+(d[i][0]+coff/2)+" "+(d[i][1]+coff/2));
                        for(int a=0;a<i;a++)
                        {
                            double dist=Math.sqrt(Math.pow((d[i][0]+coff/2)-(d[a][0]+coff/2),2)+Math.pow((d[i][1]+coff/2)-(d[a][1]+coff/2),2));
                            //System.out.println("#3 dist: "+dist);
                            if(dist<80)
                                continue New; //labelled continue statement
                        }
                        rep=true;
                    }  
                } 
                g.setColor(Color.RED);
                g.fillOval(d[i][0], d[i][1], coff, coff);
                g.setColor(Color.yellow);
                int x=d[i][0]-loff;
                int y=d[i][1]+loff;
                //System.out.println("count - " + count);
                g.drawString(letters[count],x,y);
                //System.out.println((d[i][0]+coff/2)+" "+(d[i][1]+coff/2));
                count++;
            }
        } 
        
        //draw edges
        g.setColor(Color.white);
        //System.out.println(e.length);
        for(int i=0;i<e.length;i++)
        {
            int v1=e[i][0];
            int v2=e[i][1];
            int x1=d[v1-1][0];
            int y1=d[v1-1][1];
            int x2=d[v2-1][0];
            int y2=d[v2-1][1];
            int x=((x1+coff/2)+(x2+coff/2))/2;
            int y=((y1+coff/2)+(y2+coff/2))/2;
            g.drawLine(x1+coff/2, y1+coff/2, x2+coff/2, y2+coff/2);
            g.drawString(""+e[i][2],x,y); 
            //System.out.println(i);
        } 
    } //end of paint method
    
    public void createAdjMatrix()
    {
        for(int i=0;i<e.length;i++)
        {
            int v1=e[i][0]-1;
            int v2=e[i][1]-1;
            adj[v1][v2]=e[i][2];
            adj[v2][v1]=e[i][2];
        }
    }  
    public void drawcircle(int x,int y,int vnum)
    {
        Graphics g = this.getGraphics();
        //System.out.println(x+" "+y);
        if(mousecount==3)
        {
            setPaintMode(g);
            g.setColor(Color.RED);
            g.fillOval(d[s1-1][0], d[s1-1][1], coff,coff);
            g.fillOval(d[s2-1][0], d[s2-1][1], coff,coff);
            g.setColor(Color.WHITE);
            for(int i=s1-1,j=0;j<N;j++)
            {
                if(adj[i][j]!=0)
                {
                    //g.setColor(Color.WHITE);
                    g.drawLine(d[s1-1][0]+coff/2,d[s1-1][1]+coff/2,d[j][0]+coff/2,d[j][1]+coff/2);
                }
            }
            for(int i=s2-1,j=0;j<N;j++)
            {
                if(adj[i][j]!=0)
                {
                    //g.setColor(Color.WHITE);
                    g.drawLine(d[s2-1][0]+coff/2,d[s2-1][1]+coff/2,d[j][0]+coff/2,d[j][1]+coff/2);
                }
            }
            
            mousecount=1;
        }
        
        if(mousecount==1) //first node selection
        { 
            g.setColor(Color.BLUE);
            g.fillOval(x, y, coff,coff);
            s1=vnum;
            
            //path[0]=s1;
        }
        
        if(mousecount==2) //second node selection
        { 
            s2=vnum;
            //path[1]=s2;
            g.setColor(Color.BLUE);
            g.fillOval(x, y, coff,coff);
            //int N = letters.length;
            
            
            HeapPathFinder p = new HeapPathFinder(N);//PathFinder p = new PathFinder(N);
            p.readMatrix(adj); //client.e is the edge matrix with weights
            
            //p.read(args[0]);
            //p.printLists();
            
        
            int source = s1;
            source = source-1;
            //System.out.println("Source="+source);
            
            
            p.findPaths(source);
        
            //p.printScores();
        
            //p.setPath(clientDumm);`
            // System.out.println("SHORTEST "+s2+" SIZE in setPath= " + p.shortestPath[s2].size());~testing
            shortestPathCl = p.shortestPath;   
            // System.out.println("***"+p.shortestPath.length);
            //client.drawPath();
            // System.out.println("\n\n\n\n\n");~testing
            // p.printScores();
            
            setPaintMode(g);
        }
        
    }
    
    //draws the path
    public void setPaintMode(Graphics g) 
    {
        //Thread.sleep(3000); //gives a delay
        /*
         setState ( Frame.ICONIFIED );
         Thread.sleep(5000);
         setState ( Frame.NORMAL );*/
        
        if(mousecount==2) 
        {
            g.setColor(Color.GREEN);
            
        }    
        if(mousecount==3)
        { 
            g.setColor(Color.WHITE);
        }
        
        
        List <Integer> path = shortestPathCl[s2-1];///
        
       // for (int i = 0; i < path.size(); i++)
        {
  //          System.out.println("<" + path.get(i) + ">"); 
        }
        
  //      System.out.println("curr, next ");
        for(int i=0;i < path.size()-1;i++)
        {
            int curr=path.get(i);//-1;
            int next=path.get(i+1);//-1;
  //          System.out.println("curr="+curr);
  //          System.out.println("next="+next);   
        }
        
        for (int i = 0; i < path.size() -1; i++)
            //for (int i = 0; i < path2.length - 1; i++)
        {
            int curr = path.get(i);
            int next = path.get(i+1);
            //Graphics2D g2 = (Graphics2D) g;
            int x1=d[curr][0]+coff/2;
            // System.out.println("x1="+x1);
            int y1=d[curr][1]+coff/2;
            
            int x2=d[next][0]+coff/2;
            int y2= d[next][1]+coff/2;
            //draws a thicker line
            //g2.setStroke(new BasicStroke(6)); //thickness
            //g2.setStroke(new BasicStroke(1));
            g.drawLine(x1, y1, x2, y2);                     
            //g.drawLine(x1, y1, x2,y2);  
        }
        
        
    }
    
    // */
    @Override
    public void actionPerformed(ActionEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int xc = e.getX() ;
        int yc = e.getY() ;
        Graphics g = this.getGraphics();
        for(int i=0;i<N;i++)
        {
            // for(int j=0;j<2;j++)
            
            // if(d[i][0]==xc && d[i][1]==yc)
            
            double dist=Math.sqrt(Math.pow((d[i][0]+coff/2)-(xc),2)+Math.pow((d[i][1]+coff/2)-(yc),2));
            //System.out.println(dist);
            if(dist<(coff/2))
            {
                //System.out.println(dist); 
                if((mousecount==1&&(i+1)!=s1)||mousecount!=1)
                { 
                    mousecount++; // counts the number of nodes selected
                    
                    //System.out.println(mousecount);
                    drawcircle(d[i][0],d[i][1],i+1);
                }
            }
        } 
        
        
        
        
        //drawcircle(e.getX(), e.getY());
        //System.out.println("hello");
        // repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


