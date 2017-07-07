package com.scholanova.groupe2.yoann.tools;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.scholanova.groupe2.yoann.files.HiddenFile;
import com.scholanova.groupe2.yoann.files.HiddenFileMetaData;
import com.scholanova.groupe2.yoann.files.bmpfiles.AbstractBmpFilePart;
import com.scholanova.groupe2.yoann.files.bmpfiles.BmpBody;
import com.scholanova.groupe2.yoann.files.bmpfiles.BmpFile;

public class BmpPartConvertor {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private BmpFile bmpFile;
	private AbstractBmpFilePart bmpPart;
	private HiddenFile hiddenFile;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {

		byte[] b = { -23, -4, 8, 3 };
		ByteBuffer bb = ByteBuffer.wrap(b);

		int integer = bb.getInt();

		System.out.println(integer);

	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public BmpPartConvertor() {

		this.bmpPart = null;
		this.hiddenFile = null;

	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public BmpFile getBmpfile() {
		return bmpFile;
	}

	public HiddenFile getHiddenfile() {
		return hiddenFile;
	}

	public AbstractBmpFilePart getBmppart() {
		return bmpPart;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setBmpfile(BmpFile bmpFile) {
		this.bmpFile = bmpFile;
	}

	public void setBmppart(AbstractBmpFilePart bmpPart) {
		this.bmpPart = bmpPart;
	}

	public void setHiddenFile(HiddenFile hiddenFile) {
		this.hiddenFile = hiddenFile;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public void hideFileInBody() { // TODO à séparer en méthodes indépendantes

		BmpBody bmpBody = (BmpBody) bmpPart;

		// Remplacement des octets pour insérer le code STEG servant de
		// reconnaissance

		ByteBuffer steganographyInBytes = bmpBody.getMetaDataAreaStore().getSteganographyInBytes();

		String steg = "STEG";
		byte[] stegBytes = steg.getBytes();

		ByteConvertor bc = new ByteConvertor(stegBytes);
		List<String> decomposedStegBinary = bc.decomposeByteArray();
		steganographyInBytes = ByteBuffer.wrap(replaceBytes(steganographyInBytes.array(), decomposedStegBinary));

		// Remplacement des octets pour insérer la taille du fichier à cacher

		ByteBuffer sizeInBytes = bmpBody.getMetaDataAreaStore().getSizeInBytes();
		int size = (int) hiddenFile.getFileSize();
		ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
		sizeBuffer.putInt(size);
		byte[] sizeArray = sizeBuffer.array();
		ByteConvertor bc2 = new ByteConvertor(sizeArray);
		List<String> decomposedSizeBinary = bc2.decomposeByteArray();
		sizeInBytes = ByteBuffer.wrap(replaceBytes(sizeInBytes.array(), decomposedSizeBinary));

		// Remplacement des octets pour insérer l'extension du fichier à cacher

		ByteBuffer extensionInBytes = bmpBody.getMetaDataAreaStore().getExtensionInBytes();
		String extension = hiddenFile.getFileExtension();
		extension = String.format("%4s", extension); // .replace(' ','0')
		byte[] extensionBytes = extension.getBytes();
		ByteConvertor bc3 = new ByteConvertor(extensionBytes);
		List<String> decomposedExtBinary = bc3.decomposeByteArray();
		extensionInBytes = ByteBuffer.wrap(replaceBytes(extensionInBytes.array(), decomposedExtBinary));

		// Remplacement des octets pour insérer les octets du fichier à cacher

		ByteBuffer dataInBytes = bmpBody.getDataAreaStore().getFileInBytes();
		List<String> decomposedBinary = hiddenFile.getDecomposedBinary();
		dataInBytes = ByteBuffer.wrap(replaceBytes(dataInBytes.array(), decomposedBinary));

		// Remplacement du ByteBuffer de bmpBody

		ByteBuffer totalByteBuffer = bmpPart.getByteBuffer();
		totalByteBuffer.put(steganographyInBytes).put(sizeInBytes).put(extensionInBytes).put(dataInBytes);
		bmpPart.setByteBuffer(totalByteBuffer);

	}

	public ByteBuffer extractBufferFromBody(int hiddenFileSize) {

		BmpBody bmpBody = (BmpBody) bmpPart;

		ByteBuffer dataInBytes = bmpBody.getDataAreaStore().getFileInBytes();

		byte[] bytes = dataInBytes.array();
		ByteConvertor bc = new ByteConvertor(bytes);
		List<String> decomposedBinary = bc.decomposeByteArray(hiddenFileSize);
		
		byte[] recomposedBytes = bc.recomposeByteArray(decomposedBinary);
		
		ByteBuffer buffer = ByteBuffer.wrap(recomposedBytes);

		return buffer;
	}

	public byte[] replaceBytes(byte[] byteArray, List<String> decomposedBinary) {

		int lineCounter = 0;

		for (byte b : byteArray) {
			if (lineCounter < decomposedBinary.size()) {
				StringBuilder sb = new StringBuilder();
				String binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
				sb.append(binaryByte.substring(0, 6));
				sb.append(decomposedBinary.get(lineCounter));
				byteArray[lineCounter] = (byte) Integer.parseInt(sb.toString(), 2);
			}
			lineCounter++;
		}
		return byteArray;
	}

	public HiddenFileMetaData extractMetaData() {

		BmpBody bmpBody = (BmpBody) bmpPart;

		ByteBuffer steganographyInBytes = bmpBody.getMetaDataAreaStore().getSteganographyInBytes();
		String steg = extractString(steganographyInBytes);

		ByteBuffer sizeInBytes = bmpBody.getMetaDataAreaStore().getSizeInBytes();
		int size = extractInt(sizeInBytes);

		ByteBuffer extensionInBytes = bmpBody.getMetaDataAreaStore().getExtensionInBytes();
		String extension = extractString(extensionInBytes);
		// Elimination des espaces potentiels
		StringBuilder sb = new StringBuilder();
		for (char c : extension.toCharArray()) {
			if (c == ' ') {
			} else {
				sb.append(c);
			}
		}
		extension = sb.toString();

		HiddenFileMetaData hfmd = new HiddenFileMetaData();
		hfmd.setExtension(extension);
		hfmd.setSize(size);
		hfmd.setSteg(steg);

		return hfmd;

	}

	public String extractString(ByteBuffer byteBuffer) {

		byte[] bytes = byteBuffer.array();
		
		ByteConvertor bc = new ByteConvertor(bytes);
		List<String> decomposedBinary = bc.decomposeByteArray();
		
		byte[] recomposedBytes = bc.recomposeByteArray(decomposedBinary);
		ByteBuffer stegBuffer = ByteBuffer.wrap(recomposedBytes);

		String str = new String(stegBuffer.array(), StandardCharsets.UTF_8);

		return str;
	}

	public int extractInt(ByteBuffer byteBuffer) {

		byte[] bytes = byteBuffer.array();
		ByteConvertor bc = new ByteConvertor(bytes);
		List<String> decomposedBinary = bc.decomposeByteArray();
		byte[] recomposedBytes = bc.recomposeByteArray(decomposedBinary);
		ByteBuffer buffer = ByteBuffer.wrap(recomposedBytes);

		int integer = buffer.getInt();

		return integer;
	}

}
