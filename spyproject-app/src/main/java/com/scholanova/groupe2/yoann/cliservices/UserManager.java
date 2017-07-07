package com.scholanova.groupe2.yoann.cliservices;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.scholanova.groupe2.yoann.files.HiddenFile;
import com.scholanova.groupe2.yoann.files.InputFileAnalyser;
import com.scholanova.groupe2.yoann.files.NewFileGenerator;
import com.scholanova.groupe2.yoann.files.bmpfiles.BmpFile;

public class UserManager {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private boolean validBmpFile;
	private boolean validFileToHide;
	private boolean possibleProcess;
	private Scanner sc;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {

		new UserManager();

	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public UserManager() {

		this.validBmpFile = false;
		this.validFileToHide = false;
		this.possibleProcess = false;
		run();

	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public boolean isValidBmpFile() {
		return validBmpFile;
	}

	public boolean isValidFileToHide() {
		return validFileToHide;
	}

	public boolean isPossibleProcess() {
		return possibleProcess;
	}

	public Scanner getSc() {
		return sc;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setValidBmpFile(boolean validBmpFile) {
		this.validBmpFile = validBmpFile;
	}

	public void setValidFileToHide(boolean validFileToHide) {
		this.validFileToHide = validFileToHide;
	}

	public void setPossibleProcess(boolean possibleProcess) {
		this.possibleProcess = possibleProcess;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public void run() {

		InputFileAnalyser ifc = new InputFileAnalyser();
		int option;
		BmpFile bmpFile;
		NewFileGenerator nfg;
		HiddenFile fileToHide;

		do {

			System.out.println("Please enter the number corresponding to the option wanted : ");
			System.out.println("1 : Recover a hidden file");
			System.out.println("2 : Hide a file in a BmpFile");
			System.out.println("3 : Close the program");

			sc = new Scanner(System.in);
			this.validBmpFile = false;
			this.validFileToHide = false;
			this.possibleProcess = false;

			try {

				option = sc.nextInt();

				System.out.println("You chose option : " + option);
				possibleProcess = false;

				switch (option) {
				case 1:

					bmpFile = scanBmpFile();

					nfg = new NewFileGenerator(bmpFile);
					nfg.generateNewHiddenFile();

					break;

				case 2:

					do {

						bmpFile = scanBmpFile();

						fileToHide = scanFileToHide();

						ifc.setInputBmpFile(bmpFile);
						ifc.setHiddenFile(fileToHide);

						if (ifc.canHideInFile()) {

							possibleProcess = true;

							nfg = new NewFileGenerator(bmpFile, fileToHide);
							nfg.generateNewBmpFile();

						}
						
						this.validBmpFile = false;
						this.validFileToHide = false;

					} while (!possibleProcess);
					break;

				case 3:
					System.out.println("See you soon!");
					System.exit(0);
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer number");
			}
		} while (true);
	}

	public BmpFile scanBmpFile() {

		BmpFile bmpFile = null;

		while (!validBmpFile) {

			System.out.println("Please enter the path of the bmpfile (For example : \"C:\\file.bmp\")");
			String dirpath = scanFilePath();
			bmpFile = new BmpFile(dirpath);
			bmpFile.recoverFileExtension();

			if (bmpFile.tryIfExists()) {
				if (bmpFile.getFileExtension().equals("bmp")) {
					validBmpFile = true;
				} else {
					System.out.println("This is not a .bmp File");
				}
			} else {
				System.out.println("Please try again");
			}

			if (validBmpFile) {
				bmpFile.recoverFileSize();
			}
		}

		return bmpFile;
	}

	public HiddenFile scanFileToHide() {

		HiddenFile fileToHide = null;

		while (!validFileToHide) {

			System.out.println("Please enter the path of the file to hide (For example : \"C:\\file.txt\")");
			String dirpath = scanFilePath();
			fileToHide = new HiddenFile(dirpath);
			validFileToHide = fileToHide.tryIfExists();
			if (validFileToHide) {
				fileToHide.recoverFileExtension();
				fileToHide.recoverFileSize();
			}

		}

		return fileToHide;

	}

	public String scanFilePath() {

		sc = new Scanner(System.in);

		String filePath = sc.nextLine();
		System.out.println("The file path is : " + filePath);

		return filePath;

	}
}
