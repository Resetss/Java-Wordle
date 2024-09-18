package JavaWordle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameOverBox extends JDialog {

    private static final long serialVersionUID = 1L;

    public GameOverBox(JFrame parentFrame, String wordToGuess, boolean hasWon, Runnable resetAction, Runnable quitAction) {
        super(parentFrame, "Game Over", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        panel.setBackground(Color.DARK_GRAY);

        panel.setPreferredSize(new Dimension(350, 350));
        panel.setMaximumSize(new Dimension(350, 350));

        String gameOverText = hasWon ? "Congratulations!" : "Game Over!";
        String wordText = hasWon ? "You guessed the word" : "The word was: " + wordToGuess;

        JLabel gameOverLabel = new JLabel(gameOverText, JLabel.CENTER);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel wordLabel = new JLabel(wordText, JLabel.CENTER);
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 18));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> {
            resetAction.run();
            dispose();
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener(e -> {
            quitAction.run();
        });

        panel.add(Box.createVerticalGlue());
        panel.add(gameOverLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(wordLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(playAgainButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());

        getContentPane().add(panel);

        pack();
        setLocationRelativeTo(parentFrame);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}