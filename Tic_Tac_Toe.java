// @author: Gurpreet Singh

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Tic_Tac_Toe
{
	private char [][] brd = new char [3][3];
	private startScr start; private gameScr board; 
	private modeScr mode; private int type; String user1,user2;

	private boolean put(int x,int y,char val)
	{
		if( x<0 || x>=3 || y<0 || y>=3 || brd[x][y]!='-' ) return false;
		board.setGrid(x+y*3,val);
		brd[x][y]=val; return true;
	}

	public Tic_Tac_Toe()
	{
		for(int i=0;i<3;i++) for(int j=0;j<3;j++) brd[i][j]='-';
		start = new startScr();	
	}
	
	public int checkState()
	{
		int i,j;
		
		for(i=0;i<3;i++) if(brd[i][0]!='-' && brd[i][0]==brd[i][1] && brd[i][0]==brd[i][2])
		{	
			board.setColor(i,3+i,6+i);
			if(brd[i][0]=='X') return 1;
			else return 2;
		}

		for(i=0;i<3;i++)	if(brd[0][i]!='-' && brd[0][i]==brd[1][i] && brd[0][i]==brd[2][i])
		{	
			board.setColor(i*3,i*3+1,i*3+2);
			if(brd[0][i]=='X') return 1;
			else return 2;
		}

		if(brd[0][0]!='-' && brd[0][0]==brd[1][1] && brd[0][0]==brd[2][2])
		{
			board.setColor(0,4,8);
			if(brd[1][1]=='X') return 1;
			return 2;
		}

		if(brd[0][2]!='-' && brd[0][2]==brd[1][1] && brd[0][2]==brd[2][0])
		{
			board.setColor(6,4,2);
			if(brd[1][1]=='X') return 1;
			return 2;
		}

		boolean flag=true;
		for(i=0;i<3;i++) for(j=0;j<3;j++)
		if(brd[i][j]=='-')
		{
			flag=false;
			break;
		}

		if(flag) return 3;
		return 0;
	}

	private void CPU_move(char c)
	{
		int i,j,k=0,l=0,arri[]=new int[9],arrj[]=new int[9];

		for(i=0;i<3;i++)
			for(j=0;j<3;j++)

				if(brd[i][j]=='-')
				{
					arri[k++] = i;
					arrj[l++] = j;
				}

		int x1=new Random().nextInt(k);
		put(arri[x1],arrj[x1],c);
	}

	private void AI_move(char c)
	{
		int i;
		
		//horizontal
		for(i=0;i<3;i++)
		{
			if(brd[i][0]!='-' && brd[i][0]==brd[i][1] && put(i,2,c)) return;
			if(brd[i][2]!='-' && brd[i][2]==brd[i][1] && put(i,0,c)) return;
			if(brd[i][0]!='-' && brd[i][0]==brd[i][2] && put(i,1,c)) return;
		}
		
		// vertical
		for(i=0;i<3;i++)
		{
			if(brd[0][i]!='-' && brd[0][i]==brd[1][i] && put(2,i,c)) return;
			if(brd[2][i]!='-' && brd[2][i]==brd[1][i] && put(0,i,c)) return;
			if(brd[0][i]!='-' && brd[0][i]==brd[2][i] && put(1,i,c)) return;
		}

		// daignol1
		if(brd[0][0]!='-' && brd[0][0]==brd[1][1] && put(2,2,c)) return;
		if(brd[1][1]!='-' && brd[2][2]==brd[1][1] && put(0,0,c)) return;
		if(brd[0][0]!='-' && brd[0][0]==brd[2][2] && put(1,1,c)) return;

		// daignol2
		if(brd[0][2]!='-' && brd[0][2]==brd[1][1] && put(2,0,c)) return;
		if(brd[2][0]!='-' && brd[2][0]==brd[1][1] && put(0,2,c)) return;
		if(brd[2][0]!='-' && brd[0][2]==brd[2][0] && put(1,1,c)) return;

		if(brd[1][1]=='-'){ put(1,1,c); return; }
		if(brd[0][0]=='-'){ put(0,0,c); return; }
		if(brd[0][2]=='-'){ put(0,2,c); return; }
		if(brd[2][0]=='-'){ put(2,0,c); return; }
		if(brd[2][2]=='-'){ put(2,2,c); return; }

		CPU_move(c);
	}

	private void setMode(int i)
	{
		//1 u vs u
		//2 u vs cpu
		//3 cpu vs ai
		//4 u vs ai
		
		mode.dispose();
		type=i;
		
		if(type==1) new namesScr(2);
		else if (type==2) { new namesScr(1); user2="CPU(O)"; }
		else if (type==4) { new namesScr(1); user2="AI(O)"; }
		else
		{
			board = new gameScr();
			user1 = "CPU(X)"; user2 = "AI(O)";

			Timer move = new Timer(1000,null);

			move.addActionListener(
			new ActionListener() 
			{
			    public void actionPerformed(ActionEvent e) 
			    {
		    		if(checkState()==1)
		    		{ 
		    			move.stop(); 
		    			new Message(user1+" WINS!!",true); 
		    		}
					
					else if(checkState()==2)
					{ 
						move.stop(); 
						new Message(user2+" WINS!!",true); 
					}
					
					else if(checkState()==3)
					{ 
						move.stop(); 
						new Message("That's a tie!!",true); 
					}

					else if(board.getText().equals(user1+"'s turn"))
					{
						CPU_move('X'); 
						board.setOutput(user2+"'s turn");
					}

					else
					{
						AI_move('O'); 
						board.setOutput(user1+"'s turn");
					}
				}
			});

			move.start();
		}
	}

	private class startScr extends JFrame
	{
		private JLabel lbl; private JButton b1,b2;

		public startScr()
		{
			lbl = new JLabel("Zero Katta",JLabel.CENTER);
			b1 = new JButton(new ImageIcon("play.png"));
			b2 = new JButton(new ImageIcon("exit.png"));

			setTitle("Tic-Tac-Toe"); setSize(300,250);
			getContentPane().setBackground(new Color(128, 212, 255));

			lbl.setMaximumSize(new Dimension(300,50));
			lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			lbl.setFont(new Font("Sans",Font.BOLD,30));
			
			b1.setMaximumSize(new Dimension(100,35));
			b1.setAlignmentX(Component.CENTER_ALIGNMENT);
			b1.setBackground(new Color(0, 170, 255));
			b1.setText("Play");
			b1.setActionCommand("play");
			b1.addActionListener(new ButtonClick());
			
			b2.setMaximumSize(new Dimension(100,35));
			b2.setAlignmentX(Component.CENTER_ALIGNMENT);
			b2.setText("Exit");
			b2.setBackground(new Color(0, 170, 255));
			b2.setActionCommand("exit");
			b2.addActionListener(new ButtonClick());

			add(Box.createRigidArea(new Dimension(0,20))); add(lbl); 
			add(Box.createRigidArea(new Dimension(0,25))); add(b1); 
			add(Box.createRigidArea(new Dimension(0,25))); add(b2);

			int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
			setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
			setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
			setVisible(true); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		private class ButtonClick implements ActionListener // handles all input
		{
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();

				if(cmd.equals("play"))
				{ 
					dispose();
					mode = new modeScr();
				}

				else if(cmd.equals("exit")) System.exit(0);
			}
		}
	}

	private class modeScr extends JFrame
	{
		private JLabel lbl; private JButton b1,b2,b3,b4,b5;

		public modeScr()
		{
			lbl = new JLabel("Select Game Mode",JLabel.CENTER);
			b1 = new JButton(); b2 = new JButton();
			b3 = new JButton(); b4 = new JButton();
			b5 = new JButton();

			setTitle("Tic-Tac-Toe"); setSize(300,420);
			getContentPane().setBackground(new Color(128, 212, 255));

			lbl.setMaximumSize(new Dimension(300,50));
			lbl.setFont(new Font("Sans",Font.BOLD,25));
			lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			b1.setMaximumSize(new Dimension(200,35));
			b1.setAlignmentX(Component.CENTER_ALIGNMENT);
			b1.setBackground(new Color(0, 170, 255));
			b1.setText("User Vs User");
			b1.setActionCommand("1");
			b1.addActionListener(new ButtonClick());

			b2.setMaximumSize(new Dimension(200,35));
			b2.setAlignmentX(Component.CENTER_ALIGNMENT);
			b2.setBackground(new Color(0, 170, 255));
			b2.setText("User Vs CPU");
			b2.setActionCommand("2");
			b2.addActionListener(new ButtonClick());

			b3.setMaximumSize(new Dimension(200,35));
			b3.setAlignmentX(Component.CENTER_ALIGNMENT);
			b3.setBackground(new Color(0, 170, 255));
			b3.setText("CPU Vs AI");
			b3.setActionCommand("3");
			b3.addActionListener(new ButtonClick());

			b4.setMaximumSize(new Dimension(200,35));
			b4.setAlignmentX(Component.CENTER_ALIGNMENT);
			b4.setBackground(new Color(0, 170, 255));
			b4.setText("User Vs AI");
			b4.setActionCommand("4");
			b4.addActionListener(new ButtonClick());
			
			b5.setText("Exit");
			b5.setBackground(new Color(0, 170, 255));
			b5.setAlignmentX(Component.CENTER_ALIGNMENT);
			b5.setMaximumSize(new Dimension(200,35));
			b5.setActionCommand("exit");
			b5.addActionListener(new ButtonClick());

			add(Box.createRigidArea(new Dimension(0,20))); add(lbl); 
			add(Box.createRigidArea(new Dimension(0,20))); add(b1); 
			add(Box.createRigidArea(new Dimension(0,20))); add(b2); 
			add(Box.createRigidArea(new Dimension(0,20))); add(b3); 
			add(Box.createRigidArea(new Dimension(0,20))); add(b4); 
			add(Box.createRigidArea(new Dimension(0,20))); add(b5);

			int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
			setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
			setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
			setVisible(true); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		private class ButtonClick implements ActionListener // handles all input
		{
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();

				if(cmd.equals("exit")) System.exit(0);
				else{ setMode(Integer.parseInt(cmd)); }
			}
		}
	}

	private class gameScr extends JFrame
	{
		private JLabel lbl = new JLabel("",JLabel.CENTER);
		private JButton exit = new JButton(new ImageIcon("exit.png"));
		JButton btns[] = new JButton[9]; JFrame grid2,grid;

		public gameScr()
		{
			setTitle("Tic-Tac-Toe"); setSize(360,400);
			getContentPane().setBackground(new Color(128, 212, 255));

			int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
			setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
			setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			grid = new JFrame();
			for(int i=0; i<9; i++)
			{
				btns[i] = new JButton();	
				btns[i].setBackground(new Color(255, 255, 255));
				btns[i].setMaximumSize(new Dimension(90,90));
				btns[i].setActionCommand(String.valueOf(i)+" btns");
				btns[i].addActionListener(new ButtonClick());
				grid.add(btns[i]);
			}

			grid.setLayout(new GridLayout(3,3));
			grid.getContentPane().setMaximumSize(new Dimension(270,270));

			grid2 = new JFrame();
			grid2.getContentPane().setBackground(new Color(128, 212, 255));
			grid2.setLayout(new BoxLayout(grid2.getContentPane(),BoxLayout.X_AXIS));
			grid2.add(Box.createRigidArea(new Dimension(45,0)));
			grid2.add(grid.getContentPane());
			grid2.add(Box.createRigidArea(new Dimension(45,0)));
			grid2.getContentPane().setMaximumSize(new Dimension(360,270));
			
			lbl.setMaximumSize(new Dimension(240,20));
			lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			lbl.setFont(new Font("Sans",Font.BOLD,15));
			lbl.setText("  ");

			exit.setText("Exit");
			exit.setBackground(new Color(0, 170, 255));
			exit.setMaximumSize(new Dimension(100,35));
			exit.setAlignmentX(Component.CENTER_ALIGNMENT);
			exit.setActionCommand("exit");
			exit.addActionListener(new ButtonClick());

			add(Box.createRigidArea(new Dimension(0,20))); add(lbl); 
			add(Box.createRigidArea(new Dimension(0,20))); add(grid2.getContentPane()); 
			add(Box.createRigidArea(new Dimension(0,20))); add(exit);
			add(Box.createRigidArea(new Dimension(0,20)));

			setVisible(true);
		}

		public void setOutput(String s) { lbl.setText(s); }
		public void setGrid(int index,char value)
		{ 
			if( value == 'X' ) btns[index].setIcon(new ImageIcon("katta.png"));
			else btns[index].setIcon(new ImageIcon("zero.png")); 
		}
		
		public void setColor(int index1,int index2,int index3)
		{
			btns[index1].setBackground(new Color(204,204,0));
			btns[index2].setBackground(new Color(204,204,0));
			btns[index3].setBackground(new Color(204,204,0));
		}

		public String getText() { return lbl.getText(); }

		private class ButtonClick implements ActionListener // handles all input
		{
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();
				
				if(cmd.equals("exit")) 
					System.exit(0);
				
				else if(cmd.endsWith("btns"))
				{
					
					if(type==1) // u vs u
					{
						if(lbl.getText().equals(user1+"'s turn"))
						{
							put(Integer.parseInt(cmd.split(" ")[0])%3,Integer.parseInt(cmd.split(" ")[0])/3,'X');
							
							if(checkState()==1) new Message(user1+" WINS!!",true);
							else if(checkState()==3) new Message("That's a tie!!",true);
							else lbl.setText(user2+"'s turn");
						}
						else
						{
							put(Integer.parseInt(cmd.split(" ")[0])%3,Integer.parseInt(cmd.split(" ")[0])/3,'O');
							
							if(checkState()==2) new Message(user2+" WINS!!",true);
							else if(checkState()==3) new Message("That's a tie!!",true);	
							else lbl.setText(user1+"'s turn");
						}
					}
					
					else if(type==2) // u vs cpu
					{
						if(lbl.getText().equals(user1+"'s turn"))
						{
							put(Integer.parseInt(cmd.split(" ")[0])%3,Integer.parseInt(cmd.split(" ")[0])/3,'X');
							
							if(checkState()==1) new Message(user1+" WINS!!",true);
							else if(checkState()==3) new Message("That's a tie!!",true);
							else
							{
								lbl.setText(user2+"'s turn");
								CPU_move('O');

								if(checkState()==2) new Message(user2+" WINS!!",true);
								else if(checkState()==3) new Message("That's a tie!!",true);
								else lbl.setText(user1+"'s turn");
							}

						}
					}

					else if(type==4) // u vs ai
					{
						if(lbl.getText().equals(user1+"'s turn"))
						{
							put(Integer.parseInt(cmd.split(" ")[0])%3,Integer.parseInt(cmd.split(" ")[0])/3,'X');
							
							if(checkState()==1) new Message(user1+" WINS!!",true);
							else if(checkState()==3) new Message("That's a tie!!",true);
							else
							{
								lbl.setText(user2+"'s turn");
								AI_move('O');

								if(checkState()==2) new Message(user2+" WINS!!",true);
								else if(checkState()==3) new Message("That's a tie!!",true);
								else lbl.setText(user1+"'s turn");
							}
						}
					}
				}
			}
		}
	}

	private class Message extends JFrame
	{
		JLabel msg; JButton ok; boolean isSpecial;

		public Message(String show,boolean isSpecial)
		{
			msg = new JLabel(show,JLabel.CENTER); ok = new JButton();
			this.isSpecial = isSpecial;

			setTitle("Tic-Tac-Toe"); setSize(220,120);
			getContentPane().setBackground(new Color(128, 212, 255));

			msg.setMaximumSize(new Dimension(200,20));
			msg.setAlignmentX(Component.CENTER_ALIGNMENT);
			msg.setFont(new Font("SansSerif",Font.BOLD+Font.ITALIC,15));

			ok.setText("OK");
			ok.setAlignmentX(Component.CENTER_ALIGNMENT);
			ok.setBackground(new Color(0, 170, 255));
			ok.setMaximumSize(new Dimension(60,20));
			ok.setActionCommand("ok");
			ok.addActionListener(new ButtonClick());

			add(Box.createRigidArea(new Dimension(0,20))); add(msg); add(Box.createRigidArea(new Dimension(0,20))); 
			add(ok); add(Box.createRigidArea(new Dimension(0,20)));
			
			int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
			setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
			setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS)); setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		private class ButtonClick implements ActionListener // handles all input
		{
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();
				if(cmd.equals("ok"))
				{ 
					dispose();
					if(isSpecial)
					{
						board.dispose();
						new Tic_Tac_Toe();
					}
				}
			}
		}
	}

	private class namesScr extends JFrame
	{
		JTextField user1_in,user2_in;
		JLabel usr1,usr2; JButton ok;
		int noOfUsers;

		public namesScr(int noOfUsers)
		{
			this.noOfUsers = noOfUsers;
			usr1 = new JLabel(); usr2 = new JLabel();
			user1_in = new JTextField(30); user2_in = new JTextField(30);
			ok = new JButton();

			setTitle("Tic-Tac-Toe"); setSize(200,180);
			getContentPane().setBackground(new Color(128, 212, 255));

			usr1.setText("User1: ");
			usr1.setBounds(10,20,40,20);
			usr1.setFont(new Font("Sans",Font.BOLD,10));

			usr2.setText("User2: ");
			usr2.setBounds(10,60,40,20);
			usr2.setFont(new Font("Sans",Font.BOLD,10));

			user1_in.setBounds(60,20,120,20);
			user2_in.setBounds(60,60,120,20);
			
			ok.setText("OK");
			ok.setBackground(new Color(0, 170, 255));
			ok.setBounds((getWidth()-80)/2,100,80,30);
			ok.setActionCommand("ok");
			ok.addActionListener(new ButtonClick());

			add(usr1); add(user1_in); add(ok);
			if(noOfUsers == 2) { add(usr2); add(user2_in); }
			
			int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
			setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
		   	setLayout(null); setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		private class ButtonClick implements ActionListener // handles all input
		{
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();
				if(cmd.equals("ok"))
				{
					if(user1_in.getText().isEmpty())
						new Message("Enter all values",false);

					else if(noOfUsers==2 && user2_in.getText().isEmpty())
						new Message("Enter all values",false);

					else
					{
						user1 = user1_in.getText()+"(X)";
						if(noOfUsers==2) user2 = user2_in.getText()+"(O)";
						dispose(); board = new gameScr();
						board.setOutput(user1+"'s turn");
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new Tic_Tac_Toe();
	}
}