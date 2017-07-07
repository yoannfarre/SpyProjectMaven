package com.scholanova.groupe2.yoann.spyproject.files.bmpfiles;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import com.scholanova.groupe2.yoann.spyproject.files.AbstractFile;

public class BmpFile extends AbstractFile {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private ArrayList<AbstractBmpFilePart> listBmpPart;
	private String filePath;
	private static final long serialVersionUID = 419059644169666152L;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {

		BmpFile file = new BmpFile("C:\\tmp\\spy\\img\\panda.bmp");

		file.splitBmpFile();

	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public BmpFile(String arg0) {
		super(arg0);
	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public ArrayList<AbstractBmpFilePart> getListbmppart() {
		return listBmpPart;
	}

	public String getFilepath() {
		return filePath;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setListbmppart(ArrayList<AbstractBmpFilePart> listBmpPart) {
		this.listBmpPart = listBmpPart;
	}

	public void setFilepath(String filePath) {
		this.filePath = filePath;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public void splitBmpFile() {

		ArrayList<AbstractBmpFilePart> listBmpPart = new ArrayList<AbstractBmpFilePart>();
		ByteBuffer byteBuffer;

		byte[] byteFileArray;

		try {

			// read all file

			byteFileArray = Files.readAllBytes(this.toPath());

			// Recover the fileheader

			byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(byteFileArray, 0, 14));
			BmpFileHead bfh = new BmpFileHead(byteBuffer);
			listBmpPart.add(bfh);

			// Recover the bmpheader

			byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(byteFileArray, 14, 54));
			BmpHead bh = new BmpHead(byteBuffer);
			listBmpPart.add(bh);

			// Recover the body in byte[] in ArrayList format

			byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(byteFileArray, 54, byteFileArray.length));
			BmpBody bb = new BmpBody(byteBuffer);
			listBmpPart.add(bb);

		} catch (IOException e) {
			e.printStackTrace();
		}

		setListbmppart(listBmpPart);

	}

}
