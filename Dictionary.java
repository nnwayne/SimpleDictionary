/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Dictionary {

	private static ArrayList<String> word = new ArrayList<String>(10);
	private static ArrayList<String> explain = new ArrayList<String>(10);
	private static File file;
	private static PrintWriter storeWord;
	private static String fileName;

	public Dictionary(String dicName) {
		fileName = dicName;
		file = new File(fileName);
	}

	public void readDic() {
		Scanner keyboard = null;

		//if the file doesn't exist, creat a new file
		if (!file.exists()) {
			try {
				System.out.println("Can not find the given dictionary.");
				System.out.println("Try to create a new file.");
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Fail to create a new file.");
				System.exit(0);
			}
		}
		try {
			keyboard = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Fail to read the file.");
			System.exit(0);
		}

		word.clear();
		explain.clear();
		//read words with their meanings from the document
		while (keyboard.hasNextLine()) {
			String input = keyboard.nextLine();
			String[] dicContent = input.split(":");
			if (dicContent[0] != null) {
				String splitWord = dicContent[0];
				word.add(splitWord);
			}
			if (dicContent[1] != null) {
				String splitExp = dicContent[1];
				explain.add(splitExp);
			}
		}
		keyboard.close();

	}

	public void storeToFile() {
		//clear the file and write all the words and their meanings
		//to the file
		file.delete();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			storeWord = new PrintWriter(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Fail to write the file.");
			System.exit(0);
		}

		if (word.size() > 1) {
			for (int i = 0; i < word.size() - 1; i++) {
				storeWord.printf("%s:%s\n", word.get(i), explain.get(i));
			}
		}
		if (word.size() > 0) {
			storeWord.printf("%s:%s", word.get(word.size() - 1), explain.get(word.size() - 1));
		}
		storeWord.close();
	}

	public String search(String input) {
		try {
			for (int i = 0; i < word.size(); i++) {
				if (word.get(i).equals(input)) {
					String returnEx = explain.get(i);
					return returnEx;
				}
			}
			return "Search error:This word is not found in dictionary."; // word not found
		} catch (Exception e) {
			return "Sorry, an error occurs, please try again.";
		}
	}

	public String delete(String input) {
		try {
			for (int i = 0; i < word.size(); i++) {
				if (word.get(i).equals(input)) {
					word.remove(i);
					explain.remove(i);
					return "Delete successful!";
				}
			}
			return "Delete error:This word is not found in dictionary.";
		} catch (Exception e) {
			return "Sorry, an error occurs, please try again.";
		}
	}

	public String add(String input) {
		try {
			String[] splitWord = new String[2];
			splitWord = input.split(":");

			char[] checkword = input.toCharArray();
			if (checkword[0] == ':') {
				return "Please input a word.";
			}
			if (splitWord.length == 1) {
				return "Please give the explanation.";
			}
			String ifExist = search(splitWord[0]);
			if (ifExist.equals("Search error:This word is not found in dictionary.")) {
				word.add(splitWord[0]);
				explain.add(splitWord[1]);
				return "Add successful!";
			} else {
				return "This word is already exist in dictionary.";
			}
		} catch (Exception e) {
			return "Sorry, an error occurs, please check the format and try again.";
		}
	}

}
