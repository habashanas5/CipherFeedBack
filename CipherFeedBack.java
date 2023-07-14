package testing;

import java.util.Scanner;

public class Testing {

    public static String encrypt(String key, String IV, String pt) {
        String cipherText = "";
        for (int i = 0; i < pt.length() / 16; i++) {
            String p = pt.substring(i * 16, (i + 1) * 16); // to take the correct 16 bits of the String
            String o = XOR(IV, key); // IV xor key
            String s = o.substring(0, 16); // Suppose IV (after XORed with some key)
            String ct = XOR(s, p); // Suppose IV (after XORed with some key) XOR with some bits of plain text
            // shift and add the CT to the new IV
            IV = IV.substring(16);
            IV = IV + ct;
            cipherText += ct;
           // System.out.println(pt.length() + "   i=" + i);
        }
        System.out.println("CipherText in binary:   " + cipherText);
        return cipherText;
    }

    public static String decrypt(String key, String IV, String ct) {
        String plainText = "";
        for (int i = 0; i < ct.length() / 16; i++) {
            String c = ct.substring(i * 16, (i + 1) * 16);
            String o = XOR(IV, key);
            String s = o.substring(0, 16);
            String pt = XOR(s, c);
            IV = IV.substring(16);
            IV = IV + c;
            plainText += pt;
        }
        System.out.println("PlainText in binary:   " + plainText);
        return plainText;
    }

    public static String stringToBinary(String str) {
        String binaryStr = "";
        for (int i = 0; i < str.length(); i++) {
            int ascii = (int) str.charAt(i);  // get ASCII value of character
            String binary = Integer.toBinaryString(ascii);  // convert ASCII to binary string
            binary = String.format("%8s", binary).replace(' ', '0');  // ensure 8 bits in binary representation
            binaryStr += binary;  // append binary string to result
        }
        return binaryStr;
    }

    public static String XOR(String s1, String s2) {
        int length = Math.max(s1.length(), s2.length());
        s1 = String.format("%" + length + "s", s1).replace(' ', '0');
        s2 = String.format("%" + length + "s", s2).replace(' ', '0');
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            result.append(c1 == c2 ? '0' : '1');
        }
        return result.toString();
    }

    public static String binaryToString(String binaryCipher) {
        StringBuilder stringCipher = new StringBuilder();
        for (int i = 0; i < binaryCipher.length(); i += 8) {
            String binary = binaryCipher.substring(i, i + 8);
            int value = Integer.parseInt(binary, 2);
            char character = (char) value;
            stringCipher.append(character);
        }
        return stringCipher.toString();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        String data = "";
        System.out.print("Enter a string: ");
        data = in.nextLine();
        String dataBinary = stringToBinary(data);
        if (dataBinary.length() < 16) {
            int x = 16 - dataBinary.length();
            String b = "";
            for (int i = 0; i < x; i++) {
                b += "0";
            }
            b += dataBinary;
            dataBinary = b;
        }
        if (dataBinary.length() % 16 != 0) {
            int x = dataBinary.length() / 16;
            x++;
            x *= 16;
            x -= dataBinary.length();
            String b = "";
            for (int i = 0; i < x; i++) {
                b += "0";
            }
            b += dataBinary;
            dataBinary = b;
        }
        System.out.println("Data: " + dataBinary);
        String key = "";
        for (int i = 0; i < 64; i++) {
            key += "1";
        }
        String IV = "";
        for (int i = 0; i < 64; i++) {
            IV += "0";
        }
        String ct = encrypt(key, IV, dataBinary);
        System.out.println("CipherText is: " + binaryToString(ct));
        String pt = decrypt(key, IV, ct);
        System.out.println("PlainText is: " + binaryToString(pt));
    }

}
