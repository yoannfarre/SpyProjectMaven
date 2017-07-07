package com.scholanova.groupe2.yoann.files.bmpfiles;

import java.nio.ByteBuffer;

public abstract class AbstractBmpFilePart {
	
	/********************************
	 ********** Attributes **********
	 ********************************/

	protected ByteBuffer byteBuffer;
	
	/********************************
	 *********** Test Main **********
	 ********************************/





	/********************************
	 *********** Builders ***********
	 ********************************/
	
	public AbstractBmpFilePart(ByteBuffer byteBuffer_) {

		byteBuffer = byteBuffer_;
	}

	public AbstractBmpFilePart(){
		byteBuffer = null;
	}
	
	/********************************
	 *********** Getters ************
	 ********************************/
	
	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}



	/********************************
	 *********** Setters ************
	 ********************************/
	
	public void setByteBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}
	

	/********************************
	 *********** Methods ************
	 ********************************/






	
}
