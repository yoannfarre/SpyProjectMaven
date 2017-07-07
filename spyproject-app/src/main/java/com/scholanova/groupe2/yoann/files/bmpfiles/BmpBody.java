package com.scholanova.groupe2.yoann.files.bmpfiles;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class BmpBody extends AbstractBmpFilePart {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private DataAreaStore dataAreaStore;
	private MetaDataAreaStore metaDataAreaStore;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {

		byte[] b = new byte[4];
		System.out.println(b.length);
	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public BmpBody(ByteBuffer byteBuffer) {

		super(byteBuffer);

		metaDataAreaStore = new MetaDataAreaStore();
		dataAreaStore = new DataAreaStore();

		ByteBuffer steganographyInBytes = ByteBuffer.wrap(Arrays.copyOfRange(byteBuffer.array(), 0, 16));
		metaDataAreaStore.setSteganographyInBytes(steganographyInBytes);

		ByteBuffer sizeInBytes = ByteBuffer.wrap(Arrays.copyOfRange(byteBuffer.array(), 16, 32));
		metaDataAreaStore.setSizeInBytes(sizeInBytes);

		ByteBuffer extensionInBytes = ByteBuffer.wrap(Arrays.copyOfRange(byteBuffer.array(), 32, 48));
		metaDataAreaStore.setExtensionInBytes(extensionInBytes);

		ByteBuffer dataInBytes = ByteBuffer.wrap(Arrays.copyOfRange(byteBuffer.array(), 48, byteBuffer.array().length));
		dataAreaStore.setDataInBytes(dataInBytes);
	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public DataAreaStore getDataAreaStore() {
		return dataAreaStore;
	}

	public MetaDataAreaStore getMetaDataAreaStore() {
		return metaDataAreaStore;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setDataAreaStore(DataAreaStore dataAreaStore) {
		this.dataAreaStore = dataAreaStore;
	}

	public void setMetaDataAreaStore(MetaDataAreaStore metaDataAreaStore) {
		this.metaDataAreaStore = metaDataAreaStore;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

}
