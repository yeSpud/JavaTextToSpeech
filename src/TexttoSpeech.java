import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TexttoSpeech {
	final static String INFO = "Text to Speech version 1.2";
	static String Recent1 = "I am";
	static String Recent2 = "your most";
	static String Recent3 = "recent bar!";

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame mainwindow = new JFrame(INFO);

		mainwindow.setLayout(new GridBagLayout());
		GridBagConstraints gbc0 = new GridBagConstraints();
		gbc0.fill = GridBagConstraints.HORIZONTAL;
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea input = new JTextArea("Enter something for me to say: ");
		gbc0.gridx = 0;
		gbc0.gridy = 0;
		gbc0.gridwidth = 3;
		mainwindow.add(input, gbc0);

		JButton Speak = new JButton("Speak!");
		Speak.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		gbc0.gridx = 0;
		gbc0.gridy = 1;
		gbc0.gridwidth = 1;
		mainwindow.add(Speak, gbc0);

		JButton Settings = new JButton("Settings");
		gbc0.gridx = 1;
		gbc0.gridy = 1;
		gbc0.gridwidth = 1;
		mainwindow.add(Settings, gbc0);

		JButton Quit = new JButton("Quit");
		gbc0.gridx = 2;
		gbc0.gridy = 1;
		gbc0.gridwidth = 1;
		mainwindow.add(Quit, gbc0);

		JButton MostRecent1 = new JButton(Recent1.toString());
		gbc0.gridx = 0;
		gbc0.gridy = 2;
		gbc0.gridwidth = 1;
		mainwindow.add(MostRecent1, gbc0);

		JButton MostRecent2 = new JButton(Recent2.toString());
		gbc0.gridx = 1;
		gbc0.gridy = 2;
		gbc0.gridwidth = 1;
		mainwindow.add(MostRecent2, gbc0);

		JButton MostRecent3 = new JButton(Recent3.toString());
		gbc0.gridx = 2;
		gbc0.gridy = 2;
		gbc0.gridwidth = 1;
		mainwindow.add(MostRecent3, gbc0);

		mainwindow.setSize(400, 120);
		mainwindow.setVisible(true);

		JRootPane rootPane = SwingUtilities.getRootPane(Speak);
		rootPane.setDefaultButton(Speak);

		Speak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String speak = input.getText();
				//speaklength = input.getS
				System.out.println(speak);
				if (speak == "\n ") {
					input.setText("I am too lazy to enter anything creative into a simple text to speech program. Please publicly shame me!");
				} else {
					mainwindow.setVisible(false);
					Recent3 = Recent2;
					Recent2 = Recent1;
					Recent1 = speak;
					MostRecent1.setText(Recent1);
					MostRecent2.setText(Recent2);
					MostRecent3.setText(Recent3);
					speak(speak);
					mainwindow.setVisible(true);
				}

			}
		});

		Settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] command = { "/bin/bash", "-c",
							"open \"x-apple.systempreferences:com.apple.preference.universalaccess?TextToSpeech\"" };
					Process p = Runtime.getRuntime().exec(command);
					p.waitFor();
					java.io.BufferedReader reader = new java.io.BufferedReader(
							new java.io.InputStreamReader(p.getInputStream()));
					String line = "";
					StringBuffer output = new StringBuffer();
					while ((line = reader.readLine()) != null) {
						output.append(line + "\n");
					}
				} catch (Exception e1) {
					String ErrorMessage = "\nAn error has occured\nPlease contact the developer of this program\nEmail: jeffrypig23@gmail.com";
					System.out.println(ErrorMessage);
					javax.swing.JOptionPane.showMessageDialog(null, ErrorMessage, "Error handler",
							javax.swing.JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});

		Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainwindow.setVisible(false);
				System.exit(0);
			}
		});
		MostRecent1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input.setText(Recent1);
				String speak = input.getText();
				Recent3 = Recent2;
				Recent2 = Recent1;
				Recent1 = speak;
				MostRecent1.setText(Recent1);
				MostRecent2.setText(Recent2);
				MostRecent3.setText(Recent3);
				speak(speak);
			}
		});
		MostRecent2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input.setText(Recent2);
				String speak = input.getText();
				Recent3 = Recent2;
				Recent2 = Recent1;
				Recent1 = speak;
				MostRecent1.setText(Recent1);
				MostRecent2.setText(Recent2);
				MostRecent3.setText(Recent3);
				speak(speak);
			}
		});
		MostRecent3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				input.setText(Recent3);
				String speak = input.getText();
				Recent3 = Recent2;
				Recent2 = Recent1;
				Recent1 = speak;
				MostRecent1.setText(Recent1);
				MostRecent2.setText(Recent2);
				MostRecent3.setText(Recent3);
				speak(speak);
			}
		});

	}

	public static void speak(String speech) {
		try {
			String[] command = { "/bin/bash", "-c", "say " + speech };
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			java.io.BufferedReader reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(p.getInputStream()));
			String line = "";
			StringBuffer output = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			String ErrorMessage = "\nAn error has occured\nPlease contact the developer of this program\nEmail: jeffrypig23@gmail.com";
			System.out.println(ErrorMessage);
			javax.swing.JOptionPane.showMessageDialog(null, ErrorMessage, "Error handler",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
