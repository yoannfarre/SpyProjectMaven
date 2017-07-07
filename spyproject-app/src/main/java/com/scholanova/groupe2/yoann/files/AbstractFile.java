package com.scholanova.groupe2.yoann.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class AbstractFile extends File {

	/********************************
	 ********** Attributes **********
	 ********************************/

	protected String fileExtension;
	protected long fileSize;
	private static final long serialVersionUID = -7871602384568352238L;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {
		new HiddenFile("test.txt");

	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public AbstractFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public long getFileSize() {
		return fileSize;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/********************************
	 *********** Methods ************
	 ********************************/
	

	public boolean tryIfExists() {

		boolean result;

		if (this.exists()) {
			System.out.println("This file exists");
			result = true;
		} else {
			System.out.println("This file doesn't exist");
			result = false;
		}
		return result;
	}

	public void recoverFileExtension() {

		String fileExtension = "";
		int i = this.getName().lastIndexOf('.');
		if (i > 0) {
			fileExtension = this.getName().substring(i + 1);
		}
		setFileExtension(fileExtension);
	}

	public void recoverFileSize() {

		long fileSize = 0;

		try {
			fileSize = Files.size(this.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		setFileSize(fileSize);
	}

}
