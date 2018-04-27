import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HangMan implements KeyListener {
	JFrame frame;
	JLabel label;
	JLabel label2;
	JPanel panel;
	String string = "";
	String st = "";
	Stack<String> s = new Stack();
	int lives = 9;
	int numOfWordsSolved = 0;
	int numOfWords = 0;
	ArrayList<String> list = new ArrayList<>();

	public static void main(String[] args) {
		HangMan h = new HangMan();
		h.start();
	}

	void start() {
		String num = JOptionPane.showInputDialog("Choose a number between 1 and 2999");
		numOfWords = Integer.parseInt(num);
		frame = new JFrame();
		frame.addKeyListener(this);
		label = new JLabel();
		label2 = new JLabel();
		label2.setText("LIVES LEFT: " + lives);
		panel = new JPanel();
		int r = new Random().nextInt(numOfWords - 1);

		try {
			BufferedReader br = new BufferedReader(new FileReader("src/dictionary.txt"));
			for (int i = 0; i < numOfWords; i++) {
				String line = br.readLine();
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j < numOfWords; j++) {
			s.push(list.get(r));
			list.remove(r);
			if (list.size() > 1) {
				r = new Random().nextInt(list.size() - 1);
			} else
				break;
		}
		string = s.pop();
		for (int k = 0; k < string.length(); k++) {
			st += "_";
		}
		label.setText(st);
		panel.add(label);
		panel.add(label2);
		frame.add(panel);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		boolean b = false;
		for (int k = 0; k < string.length(); k++) {
			char c = st.charAt(k);
			if (e.getKeyChar() == string.charAt(k)) {
				c = string.charAt(k);
				st.replace(st.charAt(k), c);
				b = true;
				
			}
		}
		label.setText(st);
		frame.repaint();
		if (!b) {
			lives -= 1;

			label2.setText("LIVES LEFT: " + lives);
			frame.repaint();
			if (lives == 0) {
				System.out.println("GAME OVER");
				System.exit(0);
			}
		}
		if (st == string) {
			numOfWordsSolved += 1;
			JOptionPane.showMessageDialog(null, "You solved this word! Onto the next one!");
			st = s.pop();
		}
		if (numOfWordsSolved == numOfWords) {
			System.out.println("GG! You win!");
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
