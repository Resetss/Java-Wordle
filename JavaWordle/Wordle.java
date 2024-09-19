package JavaWordle;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Wordle extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private String wordToGuess;
    private int lengthOfWord; 
    private int numberOfAttempts; 
    
    private int currentCol = 0; 
    private int currentRow = 0; 
    
    private WordleGridBox[][] wordleGrid;
    
    public Wordle(int lengthOfWord, int numberOfAttempts) {
        this.lengthOfWord = lengthOfWord; 
        this.numberOfAttempts = numberOfAttempts; 
        wordToGuess = WordManager.getRandomWord(lengthOfWord);
        
        buildGameUI();
        addEventListeners(); 
    }
    
    public void buildGameUI() {
        setTitle("Java Wordle");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);
        
        setLayout(new GridBagLayout());
        
        wordleGrid = new WordleGridBox[numberOfAttempts][lengthOfWord];
        for (int row = 0; row < numberOfAttempts; row++) {
            for (int col = 0; col < lengthOfWord; col++) {
                wordleGrid[row][col] = new WordleGridBox();
                add(wordleGrid[row][col], wordleGrid[row][col].getConstraints(row, col));
            }
        }
    }
    
    private void addEventListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyInput(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    handleBackspace();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSubmit();
                }
            }
        });
    }
    
    private void handleKeyInput(char keyChar) {
        if (currentCol < lengthOfWord && Character.isLetter(keyChar)) {
            wordleGrid[currentRow][currentCol].setCharacter(String.valueOf(keyChar));
            currentCol++;
        }
    }

    private void handleBackspace() {
        if (currentCol > 0) {
            currentCol--;
            wordleGrid[currentRow][currentCol].setCharacter("");
        }
    }

    private void handleSubmit() {
        if (currentCol == lengthOfWord) {
            StringBuilder userGuess = new StringBuilder();
            for (int i = 0; i < lengthOfWord; i++) {
                userGuess.append(wordleGrid[currentRow][i].getCharacter());
            }

            String guess = userGuess.toString();
            if (WordManager.isValidWord(guess)) {
                boolean isCorrectGuess = checkGuess(guess);

                if (isCorrectGuess) {
                    showGameOverBox(true);
                    return;
                }

                currentRow++;
                currentCol = 0;

                if (currentRow == numberOfAttempts) {
                	showGameOverBox(false);
                }
            } else {
                showInvalidWord();
            }
        }
    }
    
    private boolean checkGuess(String guess) {
        boolean[] letterUsed = new boolean[lengthOfWord];

        boolean allCorrect = true;

        for (int i = 0; i < lengthOfWord; i++) {
            if (guess.charAt(i) == wordToGuess.charAt(i)) {
                wordleGrid[currentRow][i].setToCorrectPositionColor();
                letterUsed[i] = true;
            } else {
                wordleGrid[currentRow][i].setToWrongLetterColor();;
                allCorrect = false;
            }
        }

        for (int i = 0; i < lengthOfWord; i++) {

            for (int j = 0; j < lengthOfWord; j++) {
                if (guess.charAt(i) == wordToGuess.charAt(j) && !letterUsed[j]) {
                	wordleGrid[currentRow][i].setToCorrectLetterColor(); 
                    letterUsed[j] = true;
                    allCorrect = false;
                    break;
                }
            }
        }

        return allCorrect;
    }

    private void showGameOverBox(boolean hasWon) {
        SwingUtilities.invokeLater(() -> {
            GameOverBox gameOverDialog = new GameOverBox(this, wordToGuess, hasWon, this::resetGame, this::quitGame);
            gameOverDialog.setVisible(true);
        });
    }
       
   private void showInvalidWord() {
       for (int i = 0; i < lengthOfWord; i++) {
           wordleGrid[currentRow][i].setToInvalidLetterColor();;
       }
       
       new Thread(() -> {
           try {
               Thread.sleep(500); 
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           SwingUtilities.invokeLater(() -> {
               for (int i = 0; i < lengthOfWord; i++) {
                   wordleGrid[currentRow][i].resetColorOnly();
               }
           });
       }).start(); 
   }
    
   private void resetGame() {
       currentRow = 0;
       currentCol = 0;

       wordToGuess = WordManager.getRandomWord(lengthOfWord);
       
       for (int row = 0; row < numberOfAttempts; row++) {
           for (int col = 0; col < lengthOfWord; col++) {
               wordleGrid[row][col].reset();
           }
       }
   }
   
   private void quitGame() {
       System.exit(0);
   }
}