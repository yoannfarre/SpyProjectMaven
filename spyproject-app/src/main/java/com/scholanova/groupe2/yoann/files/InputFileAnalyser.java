package com.scholanova.groupe2.yoann.files;

import java.io.File;

import com.scholanova.groupe2.yoann.files.bmpfiles.BmpFile;

public class InputFileAnalyser {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private HiddenFile hiddenFile;
	private BmpFile inputBmpFile;

	/********************************
	 *********** Builders ***********
	 ********************************/

	public InputFileAnalyser() {
		this.hiddenFile = null;
		this.inputBmpFile = null;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public boolean canHideInFile() {

		boolean result = false;
		
		if (hiddenFile.getFileSize()>(inputBmpFile.getFileSize()-(54+48)/4)){
			result = false;
			System.out.println("The file to hide is too big to be hidden in the input file.");
		} else {
			result = true;
			System.out.println("We can do it! =)");
		}

		return result;
	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public HiddenFile getHiddenFile() {
		return hiddenFile;
	}

	public File getInputBmpFile() {
		return inputBmpFile;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setHiddenFile(HiddenFile hiddenFile) {
		this.hiddenFile = hiddenFile;
	}

	public void setInputBmpFile(BmpFile inputBmpFile) {
		this.inputBmpFile = inputBmpFile;
	}

}
