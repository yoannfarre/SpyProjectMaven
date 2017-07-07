package com.scholanova.groupe2.yoann.files;

public class HiddenFileMetaData {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private int size;
	private String extension;
	private String steg;

	/********************************
	 *********** Getters ************
	 ********************************/

	public int getSize() {
		return size;
	}

	public String getExtension() {
		return extension;
	}

	public String getSteg() {
		return steg;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setSize(int size) {
		this.size = size;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setSteg(String steg) {
		this.steg = steg;
	}

}
