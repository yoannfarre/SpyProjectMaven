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
		BmpFile bmpfile;
		NewFileGenerator nfg;
		HiddenFile filetohide;

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

					bmpfile = scanBmpFile();

					nfg = new NewFileGenerator(bmpfile);
					nfg.generateNewHiddenFile();

					break;

				case 2:

					do {

						bmpfile = scanBmpFile();

						filetohide = scanFileToHide();

						ifc.setInputBmpFile(bmpfile);
						ifc.setHiddenFile(filetohide);

						if (ifc.canHideInFile()) {

							possibleProcess = true;

							nfg = new NewFileGenerator(bmpfile, filetohide);
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

		BmpFile bmpfile = null;

		while (!validBmpFile) {

			System.out.println("Please enter the path of the bmpfile (For example : \"C:\\file.bmp\")");
			String dirpath = scanFilePath();
			bmpfile = new BmpFile(dirpath);
			bmpfile.recoverFileExtension();

			if (bmpfile.tryIfExists()) {
				if (bmpfile.getFileExtension().equals("bmp")) {
					validBmpFile = true;
				} else {
					System.out.println("This is not a .bmp File");
				}
			} else {
				System.out.println("Please try again");
			}

			if (validBmpFile) {
				bmpfile.recoverFileSize();
			}
		}

		return bmpfile;
	}

	public HiddenFile scanFileToHide() {

		HiddenFile filetohide = null;

		while (!validFileToHide) {

			System.out.println("Please enter the path of the file to hide (For example : \"C:\\file.txt\")");
			String dirpath = scanFilePath();
			filetohide = new HiddenFile(dirpath);
			validFileToHide = filetohide.tryIfExists();
			if (validFileToHide) {
				filetohide.recoverFileExtension();
				filetohide.recoverFileSize();
			}

		}

		return filetohide;

	}

	public String scanFilePath() {

		sc = new Scanner(System.in);

		String filepath = sc.nextLine();
		System.out.println("The file path is : " + filepath);

		return filepath;

	}
}
