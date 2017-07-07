package com.scholanova.groupe2.yoann.spyproject.files.bmpfiles;

import java.nio.ByteBuffer;

public class MetaDataAreaStore {
	
	private ByteBuffer steganographyInBytes;
	private ByteBuffer sizeInBytes;
	private ByteBuffer extensionInBytes;
	
	
	public ByteBuffer getSteganographyInBytes() {
		return steganographyInBytes;
	}
	public void setSteganographyInBytes(ByteBuffer steganographyInBytes) {
		this.steganographyInBytes = steganographyInBytes;
	}
	public ByteBuffer getSizeInBytes() {
		return sizeInBytes;
	}
	public void setSizeInBytes(ByteBuffer sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}
	public ByteBuffer getExtensionInBytes() {
		return extensionInBytes;
	}
	public void setExtensionInBytes(ByteBuffer extensionInBytes) {
		this.extensionInBytes = extensionInBytes;
	}
	
	

	
	

}
