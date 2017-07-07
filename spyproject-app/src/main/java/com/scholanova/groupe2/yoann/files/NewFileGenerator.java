package com.scholanova.groupe2.yoann.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.scholanova.groupe2.yoann.files.bmpfiles.AbstractBmpFilePart;
import com.scholanova.groupe2.yoann.files.bmpfiles.BmpBody;
import com.scholanova.groupe2.yoann.files.bmpfiles.BmpFile;
import com.scholanova.groupe2.yoann.tools.BmpPartConvertor;

public class NewFileGenerator {

	/********************************
	 ********** Attributes **********
	 ********************************/

	private BmpFile bmpFile;
	private HiddenFile hiddenfile;

	/********************************
	 *********** Test Main **********
	 ********************************/

	public static void main(String[] args) {

		File file = new File("C:\\tmp\\spy\\img\\panda.bmp");

		System.out.println(file.getParentFile());

	}

	/********************************
	 *********** Builders ***********
	 ********************************/

	public NewFileGenerator(BmpFile bmpfile, HiddenFile filetohide) {

		this.bmpFile = bmpfile;
		this.hiddenfile = filetohide;
	}

	public NewFileGenerator(BmpFile bmpfile) {

		this.bmpFile = bmpfile;

	}

	/********************************
	 *********** Getters ************
	 ********************************/

	public File getOldFile() {
		return bmpFile;
	}

	/********************************
	 *********** Setters ************
	 ********************************/

	public void setOldfile(BmpFile oldFile) {
		this.bmpFile = oldFile;
	}

	/********************************
	 *********** Methods ************
	 ********************************/

	public void generateNewHiddenFile() {

		bmpFile.splitBmpFile();
		ArrayList<AbstractBmpFilePart> listparts = bmpFile.getListbmppart();
		BmpPartConvertor convertor = new BmpPartConvertor();
		convertor.setBmpfile(bmpFile);
		for (AbstractBmpFilePart bfp : listparts) {
			convertor.setBmppart(bfp);
			if (bfp instanceof BmpBody) {
				HiddenFileMetaData hfmd = convertor.extractMetaData();
				if (hfmd.getSteg().equals("STEG")) {
					ByteBuffer bufferedFile = convertor.extractBufferFromBody(hfmd.getSize());
					buildNewHiddenFile(hfmd.getExtension(), bufferedFile);
				} else {
					System.out.println("There is no file hidden in this .bmp File");
				}
			}
		}
	}

	public void generateNewBmpFile() {

		bmpFile.splitBmpFile();
		ArrayList<AbstractBmpFilePart> listparts = bmpFile.getListbmppart();
		BmpPartConvertor convertor = new BmpPartConvertor();
		convertor.setFiletohide(hiddenfile);
		convertor.setBmpfile(bmpFile);
		for (AbstractBmpFilePart bfp : listparts) {
			convertor.setBmppart(bfp);
			if (bfp instanceof BmpBody) {
				convertor.hideFileInBody();
				listparts.set(listparts.indexOf(bfp), convertor.getBmppart());
			}
		}
		buildNewFile(listparts);
	}

	public void buildNewFile(ArrayList<AbstractBmpFilePart> listparts) {

		File out = new File(bmpFile.getParentFile() + System.getProperty("file.separator") + "new" + bmpFile.getName());
		try (FileOutputStream fos = new FileOutputStream(out)) {
			for (AbstractBmpFilePart part : listparts) {
				fos.write(part.getByteBuffer().array());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The new file is ready!");
		System.out.println("Path: " + out.getAbsolutePath());

	}

	public void buildNewHiddenFile(String extension, ByteBuffer byteBuffer) {

		File out = new File(bmpFile.getParentFile() + System.getProperty("file.separator") + "hiddenfile." + extension);

		try (FileOutputStream fos = new FileOutputStream(out)) {
			fos.write(byteBuffer.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The hidden file was reconstructed!");
		System.out.println("Path: " + out.getAbsolutePath());

	}

}
