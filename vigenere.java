
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class vigenere {

    static char[] charArray = new char[512];
    static String keyInput = "";

    //reading the file
    private static void readParseFile(String[] args) {
        String line;
        File fileKey = new File(args[0]);
        File filePlainTxt = new File(args[1]);

        try {
            Scanner scanner = new Scanner(new FileReader(fileKey));
            scanner.useDelimiter("\r\n");
            while (scanner.hasNext()) {
                line = scanner.next();
                String lettersOnly = line.replaceAll("[^a-zA-Z]+","").toLowerCase();
                keyInput += lettersOnly;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Scanner scanner = new Scanner(new FileReader(filePlainTxt));
            scanner.useDelimiter("\r\n");
            while (scanner.hasNext()) {
                line = scanner.next();
                String lettersOnly = line.replaceAll("[^a-zA-Z]+","").toLowerCase();
                for(int i = 0; i < 512; i++){
                    if(i < lettersOnly.length()){
                        charArray[i] = lettersOnly.charAt(i);
                    }else{
                        charArray[i] = 'x';
                    }
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

    }

    //generates the key to the length of the input 
    static String generateKey(String originalStr, String keyInput) 
    { 
        for (int i = 0; keyInput.length() < originalStr.length() ; i++) 
        { 
            keyInput+= keyInput.charAt(i); 
        } 
        return keyInput; 
    } 

    //now cipher text
    static String cipherText(String plaintext, String key) 
    { 
        String cipher_text=""; 
        for (int i = 0, j = 0; i < plaintext.length(); i++) {
                char c = plaintext.charAt(i);
                cipher_text += (char)((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
                j = (j+1) % key.length();
        }

        return cipher_text; 
    } 

    public static void main(String[] args) {
        String key = "";
        String cipherText = "";
        String originalTxt;

        if(args.length != 2){
            System.err.println("Please have 2 arguments.");
            System.exit(-1);
        }
        readParseFile(args);
    
        originalTxt = String.valueOf(charArray);
        key = generateKey(originalTxt, keyInput).toLowerCase();
        cipherText = cipherText(originalTxt, key).toLowerCase();

        keyInput = keyInput.replaceAll(".{80}", "$0\n");
        System.out.println("\n\nVigenere Key:\n\n" + keyInput);
        originalTxt = originalTxt.replaceAll(".{80}", "$0\n");
        System.out.println("\n\nPlaintext:\n\n" + originalTxt);
        cipherText = cipherText.replaceAll(".{80}", "$0\n");
        System.out.println("\n\nCiphertext:\n\n" + cipherText);
    }
}