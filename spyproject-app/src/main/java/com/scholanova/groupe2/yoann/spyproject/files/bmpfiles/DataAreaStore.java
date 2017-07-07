package com.scholanova.groupe2.yoann.spyproject.files.bmpfiles;

import java.nio.ByteBuffer;


public class DataAreaStore {
	
	private ByteBuffer dataInBytes;


	public ByteBuffer getFileInBytes() {
		return dataInBytes;
	}


	public void setDataInBytes(ByteBuffer dataInBytes) {
		this.dataInBytes = dataInBytes;
	}

}
