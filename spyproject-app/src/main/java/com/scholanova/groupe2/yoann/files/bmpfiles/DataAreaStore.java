package com.scholanova.groupe2.yoann.files.bmpfiles;

import java.nio.ByteBuffer;

public class DataAreaStore {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private ByteBuffer dataInBytes;

	/********************************
	 *********** Getters ************
	 ********************************/

	public ByteBuffer getFileInBytes() {
		return dataInBytes;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setDataInBytes(ByteBuffer dataInBytes) {
		this.dataInBytes = dataInBytes;
	}

}
