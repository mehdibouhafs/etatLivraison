<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.munisys.sap.dao;

/**
 *
 * @author abassou
 */
public class Encryption {
    public static String encrypt(String str) 
	{
	int keyLength=13;	
            String encrypted = "";
		for(int i = 0; i < str.length(); i++) 
		{
			int c = str.charAt(i);
			if (Character.isUpperCase(c)) 
			{
                            c = c + (keyLength % 26);
                            if (c > 'Z')
                            c = c - 26;
			} 
			else if (Character.isLowerCase(c)) 
			{
				c = c + (keyLength % 26);
				if (c > 'z')
				c = c - 26;
			}
			encrypted += (char) c;
		}
	return encrypted;
	}

    public static String decrypt(String str) 
	{
            int keyLength=13;
		
            String decrypted = "";
		for(int i = 0; i < str.length(); i++) 
		{
			int c = str.charAt(i);
			if (Character.isUpperCase(c)) 
			{
				c = c - (keyLength % 26);
				if (c < 'A')
				c = c + 26;
			} 
			else if (Character.isLowerCase(c)) 
			{
				c = c - (keyLength % 26);
				if (c < 'a')
				c = c + 26;
			}
			decrypted += (char) c;
		}
	return decrypted;
	}
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.munisys.sap.dao;

/**
 *
 * @author abassou
 */
public class Encryption {
    public static String encrypt(String str) 
	{
	int keyLength=13;	
            String encrypted = "";
		for(int i = 0; i < str.length(); i++) 
		{
			int c = str.charAt(i);
			if (Character.isUpperCase(c)) 
			{
                            c = c + (keyLength % 26);
                            if (c > 'Z')
                            c = c - 26;
			} 
			else if (Character.isLowerCase(c)) 
			{
				c = c + (keyLength % 26);
				if (c > 'z')
				c = c - 26;
			}
			encrypted += (char) c;
		}
	return encrypted;
	}

    public static String decrypt(String str) 
	{
            int keyLength=13;
		
            String decrypted = "";
		for(int i = 0; i < str.length(); i++) 
		{
			int c = str.charAt(i);
			if (Character.isUpperCase(c)) 
			{
				c = c - (keyLength % 26);
				if (c < 'A')
				c = c + 26;
			} 
			else if (Character.isLowerCase(c)) 
			{
				c = c - (keyLength % 26);
				if (c < 'a')
				c = c + 26;
			}
			decrypted += (char) c;
		}
	return decrypted;
	}
}
>>>>>>> munisysRepo/main
