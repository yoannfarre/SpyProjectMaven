package com.scholanova.groupe2.yoann.files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HiddenFile extends AbstractFile {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private List<String> decomposedBinary = new ArrayList<>();
	private static final long serialVersionUID = 6189434886013893421L;

	/********************************
	 *********** Test Main **********
	 ********************************/

	/********************************
	 *********** Builders ***********
	 ********************************/

	public HiddenFile(String pathname) {
		super(pathname);
	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public List<String> getDecomposedBinary() {
		storeAndDecompose();
		return decomposedBinary;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setDecomposedBinary(List<String> decomposedbinary) {
		this.decomposedBinary = decomposedbinary;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public void storeAndDecompose() { // TODO Changer avec ByteConvertor

		List<String> decomposedBytesList = new ArrayList<>();
		String binaryByte;
		String pieceOfByte;

		try {
			byte[] fileBytesArray = Files.readAllBytes(this.toPath());
			for (byte b : fileBytesArray) {
				binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
				for (int j = 0; j < binaryByte.length(); j += 2) {
					pieceOfByte = binaryByte.substring(j, j + 2);
					decomposedBytesList.add(pieceOfByte);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDecomposedBinary(decomposedBytesList);
	}

}
