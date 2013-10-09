import java.util.Random;

/**
 * @author Eddie Bachle
 *
 */
public class cipher {

	public static String sourceCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String cypherCharacters;

	public static int shiftLength = 5;

	public static String stringToEncrypt = "Few black taxis drive up major roads on quiet hazy nights and I dont know what I will do about this crazy cypher";

	/**
	 * @param args
	 */
	public static void main(String [] args) 
	{
		// This method allows you to create a cipher'ed string with varying length shift.
		// We looked at how to do this when we talked on Friday.
		// This MUST run before you encrypt otherwise you've got nothing to find matching encrypted characters from.
		createCipherString(shiftLength);
		
		stringToEncrypt = pad(stringToEncrypt);

		System.out.println("String to encrypt: " + stringToEncrypt);
		// This is a bit of a new method not necessarily 
		String encryptedString = encrypt(stringToEncrypt);

		// Now we can print out the result of our encryption before the program ends.
		System.out.println("Encrypted string: ");
		printLines(encryptedString);
		
		String decryptedString = decrypt(encryptedString);
		
		System.out.println("Decrypted string: ");
		printLines(decryptedString);
	}

	/**
	 * @param toEncrypt
	 * @return
	 */
	public static String encrypt(String toEncrypt)
	{
		// We'll need to take it to upper case becasue our cipherString is only uppercase.
		toEncrypt = toEncrypt.toUpperCase();
		// This should start out blank so we can add to it and as we assemble more encrypted characters.
		// This needs to be declared outside of the for loop to ensure it is also outside when we're done.
		String encryptedString = "";

		// Worth noting that you had i<sourceCharacters.length (or alpha in your case)
		// That's not the length of the string we're looking at so it'll go over.
		for(int i=0; i<toEncrypt.length();i++)
		{
			// Here's where we find which letter (of the type char) we're looking at.
			char currentCharToEncrypt = toEncrypt.charAt(i);
			// Here's where we find out what is the location of that character in the source String.
			int sourceLocation = sourceCharacters.indexOf(currentCharToEncrypt);
			// That number then can be used to match it against a character at the same location in the cipher string.
			char encryptedChar = cypherCharacters.charAt(sourceLocation);

			// Then just add one by one the new char onto the end of the encryptedString (stared emtpy).
			encryptedString += encryptedChar;
		}

		// From there, it's just a matter of returning the encryptedString to the main method.
		return block(encryptedString);
	}
	
	/**
	 * @param toStrip
	 * @return
	 */
	public static String stripSpaces(String toStrip)
	{
		String spacesStrippedString = toStrip.replaceAll(" ", "");
		
		return spacesStrippedString;
	}
	
	/**
	 * @param toPad
	 * @return
	 */
	public static String pad(String toPad)
	{
		toPad = stripSpaces(toPad).toUpperCase();
		
		String paddedString;
		char randomPadChar;
		Random random = new Random();
		
		int toPadLength = toPad.length();
		
		// The second modular division is to deal with when toPadLength % 5 = 0
		int padLength = (5 - toPadLength % 5) % 5;
		
		paddedString = toPad;
		// Add two random characters available in the sourceCharacters string.
		for(int i = 0; i < padLength; i++)
		{
			randomPadChar = sourceCharacters.charAt(random.nextInt(sourceCharacters.length()));
			
			paddedString += randomPadChar;
		}
		
		return paddedString;
	}
	
	/**
	 * @param toBlock
	 * @return
	 */
	public static String block(String toBlock)
	{
		String blockedString = "";
		
		for(int i = 0; i < toBlock.length()/5; i++)
		{
			String block = toBlock.substring(i*5, (i+1)*5);
			blockedString += block + " ";
		}
		
		blockedString = blockedString.substring(0,blockedString.length() -1 );
		
		return blockedString;
		
		 
	}
	
	/**
	 * @param toDecrypt
	 * @return
	 */
	public static String decrypt(String toDecrypt)
	{
		toDecrypt = stripSpaces(toDecrypt);
		String decryptedString = "";
		
		toDecrypt = toDecrypt.toUpperCase();
		
		for(int i=0; i<toDecrypt.length(); i++)
		{
			char currentCharToDecrypt = toDecrypt.charAt(i);
			int cypherLocation = cypherCharacters.indexOf(currentCharToDecrypt);
			char decryptedChar = sourceCharacters.charAt(cypherLocation);
			
			decryptedString += decryptedChar;
		}
		
		return block(decryptedString);
	}

	/**
	 * @param shift
	 * @return
	 */
	public static String createCipherString(int shift)
	{
		if(shift < 0)
		{
			shift = 26 + shift;
		}
		
		// This is what we talked about Friday.  You've got something similar in your code.
		cypherCharacters = sourceCharacters.substring(shift, sourceCharacters.length());
		cypherCharacters += sourceCharacters.substring(0,shift);

		return cypherCharacters;
	}
	
	/**
	 * @param stringToPrint
	 */
	public static void printLines(String stringToPrint)
	{
		int printStringLength = stringToPrint.length();
		
		int i;
		
		for(i = 0; i < printStringLength / 60; i++)
		{
			System.out.println(stringToPrint.substring(i*60, (i+1)*60));
		}
		
		System.out.println(stringToPrint.substring(i*60, printStringLength));
	}
}
