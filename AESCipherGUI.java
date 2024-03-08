import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AESCipherGUI extends JFrame {
    private JTextField inputText;
    private JTextArea outputText;
    private SecretKeySpec secretKey;
    private Cipher cipher;

    public AESCipherGUI() {
        setTitle("AES Encryption/Decryption");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Replace "MySecretKey12345" with your secret key (must be 16/24/32 bytes long)
        String secret = "MySecretKey12345";
        secretKey = new SecretKeySpec(secret.getBytes(), "AES");

        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel inputLabel = new JLabel("Enter text:");
        inputLabel.setBounds(20, 20, 80, 25);
        panel.add(inputLabel);

        inputText = new JTextField(20);
        inputText.setBounds(110, 20, 250, 25);
        panel.add(inputText);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(20, 50, 150, 25);
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performEncryption();
            }
        });
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(200, 50, 150, 25);
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performDecryption();
            }
        });
        panel.add(decryptButton);

        JLabel outputLabel = new JLabel("Result:");
        outputLabel.setBounds(20, 80, 60, 25);
        panel.add(outputLabel);

        outputText = new JTextArea(5, 20);
        outputText.setBounds(20, 110, 340, 120);
        outputText.setEditable(false);
        panel.add(outputText);         
    }

    private void performEncryption() {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(inputText.getText().getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            outputText.setText("Encrypted Result: " + encryptedText);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    private void performDecryption() {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(inputText.getText()));
            String decryptedText = new String(decryptedBytes);
            outputText.setText("Decrypted Result: " + decryptedText);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AESCipherGUI();
            }
        });
    }
}