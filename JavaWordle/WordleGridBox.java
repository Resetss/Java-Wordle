package JavaWordle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class WordleGridBox extends JTextField {

    private static final long serialVersionUID = 1L;
	
	private GridBagConstraints constraints; 
	
	public WordleGridBox() {
		setHorizontalAlignment(JTextField.CENTER);
		setFont(new Font("Arial", Font.BOLD, 24));
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, false));
		setEditable(false);
		setFocusable(false);
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
	}
	
	public GridBagConstraints getConstraints(int row, int col) {
		constraints.gridx = col;
		constraints.gridy = row;
		
		return constraints; 
	}
	
	public void setCharacter(String character) {
		setText(character.toUpperCase());
	}
	
	public String getCharacter() {
		return getText().toLowerCase(); 
	}
	
	public void setToCorrectPositionColor() {
		setBackground(Color.GREEN);
	}
	
	public void setToCorrectLetterColor() {
		setBackground(Color.YELLOW);
	}
	
	public void setToWrongLetterColor() {
		setBackground(Color.LIGHT_GRAY);
	}
	
	public void setToInvalidLetterColor() {
		setBackground(Color.RED);
	}
	
	public void reset() {
		setText(""); 
		setBackground(Color.DARK_GRAY);
	}
	
}