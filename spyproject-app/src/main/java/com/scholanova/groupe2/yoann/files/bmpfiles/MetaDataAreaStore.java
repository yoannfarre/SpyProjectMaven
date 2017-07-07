package com.scholanova.groupe2.yoann.files.bmpfiles;

import java.nio.ByteBuffer;

public class MetaDataAreaStore {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private ByteBuffer steganographyInBytes;
	private ByteBuffer sizeInBytes;
	private ByteBuffer extensionInBytes;

	/********************************
	 *********** Getters ************
	 ********************************/

	public ByteBuffer getSteganographyInBytes() {
		return steganographyInBytes;
	}

	public ByteBuffer getSizeInBytes() {
		return sizeInBytes;
	}

	public ByteBuffer getExtensionInBytes() {
		return extensionInBytes;
	}

	/********************************
	 *********** Setters ************
	 ********************************/
	
	public void setSteganographyInBytes(ByteBuffer steganographyInBytes) {
		this.steganographyInBytes = steganographyInBytes;
	}

	public void setSizeInBytes(ByteBuffer sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	public void setExtensionInBytes(ByteBuffer extensionInBytes) {
		this.extensionInBytes = extensionInBytes;
	}

}
