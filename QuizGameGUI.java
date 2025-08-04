import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QuizGameGUI extends JFrame implements ActionListener {
    String[] questions = {
        "What is the size of int in Java?",
        "Which keyword is used to inherit a class in Java?",
        "Which method is the entry point of Java programs?",
        "Which package contains Scanner class?",
        "Which operator is used for comparison?"
    };

    String[][] options = {
        {"4 bytes", "2 bytes", "8 bytes", "Depends on system"},
        {"this", "super", "extends", "implements"},
        {"main()", "start()", "run()", "init()"},
        {"java.io", "java.lang", "java.util", "java.net"},
        {"=", "==", "!", "++"}
    };

    char[] answers = {'A', 'C', 'A', 'C', 'B'};

    int index = 0;
    int score = 0;
    boolean answered = false;

    JLabel questionLabel;
    JRadioButton[] radioButtons = new JRadioButton[4];
    ButtonGroup bg;
    JButton nextButton;
    JLabel feedbackLabel;

    Color bgColor = new Color(240, 248, 255); // Light Blue
    Color correctColor = new Color(144, 238, 144); // Light Green
    Color wrongColor = new Color(255, 182, 193); // Light Pink

    public QuizGameGUI() {
        setTitle("Java Quiz Game");
        setSize(650, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgColor);

        questionLabel = new JLabel("Question", JLabel.CENTER);
        questionLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
        optionsPanel.setBackground(bgColor);
        bg = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton();
            radioButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
            radioButtons[i].setBackground(bgColor);
            bg.add(radioButtons[i]);
            optionsPanel.add(radioButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        feedbackLabel = new JLabel(" ", JLabel.CENTER);
        feedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        feedbackLabel.setForeground(Color.DARK_GRAY);
        add(feedbackLabel, BorderLayout.EAST);

        nextButton = new JButton("Submit Answer");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(135, 206, 250)); // Light sky blue
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        loadQuestion(index);
        setVisible(true);
    }

    void loadQuestion(int qIndex) {
        questionLabel.setText("Q" + (qIndex + 1) + ": " + questions[qIndex]);
        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText((char)('A' + i) + ". " + options[qIndex][i]);
            radioButtons[i].setEnabled(true);
            radioButtons[i].setBackground(bgColor);
        }
        bg.clearSelection();
        feedbackLabel.setText(" ");
        nextButton.setText("Submit Answer");
        answered = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (!answered) {
            char selected = ' ';
            int selectedIndex = -1;

            for (int i = 0; i < 4; i++) {
                if (radioButtons[i].isSelected()) {
                    selected = (char) ('A' + i);
                    selectedIndex = i;
                    break;
                }
            }

            if (selected == ' ') {
                JOptionPane.showMessageDialog(this, "Please select an option!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            answered = true;

            if (selected == answers[index]) {
                score += 5;
                radioButtons[selectedIndex].setBackground(correctColor);
                feedbackLabel.setText("✅ Correct Answer!");
            } else {
                radioButtons[selectedIndex].setBackground(wrongColor);
                int correctIndex = answers[index] - 'A';
                radioButtons[correctIndex].setBackground(correctColor);
                feedbackLabel.setText("❌ Wrong! Correct: " + answers[index]);
            }

            for (JRadioButton rb : radioButtons) {
                rb.setEnabled(false);
            }

            if (index == questions.length - 1) {
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next Question");
            }

        } else {
            index++;
            if (index < questions.length) {
                loadQuestion(index);
            } else {
                showResult();
            }
        }
    }

    void showResult() {
        JOptionPane.showMessageDialog(this,
                "Quiz Completed!\nYour Score: " + score + " out of " + (questions.length * 5),
                "Result",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGameGUI());
    }
}
