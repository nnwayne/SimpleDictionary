/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package client;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class GUI {

	private JFrame frame;
	private JTextField wordColum;
	private static JTextField textExplain;
	private static client c;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//check if there are two parameters
					if (args.length == 2) {
						//check if the second parameter is an integer
						try {
							Integer.parseInt(args[1]);
						}catch(Exception e) {
							System.out.println("Please input the correct port number");
							System.exit(0);
						}
						
						c = new client(args[0], Integer.parseInt(args[1]));
						GUI window = new GUI();
						window.frame.setVisible(true);
					}
					else {
						System.out.println("Please input the correct format");
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	public static void display(String msg) { 
		textExplain.setText(msg);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		wordColum = new JTextField();
		wordColum.setColumns(10);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String delWord = wordColum.getText();
				if(checkBlank(delWord) == 0) {
					display("Please input a word.");
				}else {
					c.delete(delWord);
				}
			}
		});

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchWord = wordColum.getText();
				if(checkBlank(searchWord) == 0) {
					display("Please input a word.");
				}else {
					c.search(searchWord);
				}
			}
		});

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addWord = wordColum.getText();
				if(checkBlank(addWord) == 0) {
					display("Please input a word.");
				}else {
					c.add(addWord);
				}
			}
		});
		
		frame.addWindowListener(new WindowAdapter()
        {
			//If user close the GUI, the system close the socket
			//and exist
            @Override
            public void windowClosing ( WindowEvent e )
            {
            	c.closeSocket();
                System.exit (0);
            }
        });

		textExplain = new JTextField();
		textExplain.setColumns(10);

		JLabel lblWord = new JLabel("word:");

		JLabel lblExplain = new JLabel("explain:");

		JLabel lblNotice = new JLabel("Notice: Dictionary add format- word:explain");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addComponent(textExplain, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(55)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblWord)
								.addGap(11)
								.addComponent(wordColum, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
							.addComponent(lblNotice)
							.addComponent(lblExplain)))
					.addGap(28))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWord)
						.addComponent(wordColum, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNotice)
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete)
						.addComponent(btnSearch)
						.addComponent(btnAdd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblExplain)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textExplain, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public int checkBlank(String Input) {
		if(Input.equals("")) {
			return 0;
		}
		else return 1;
	}

}
