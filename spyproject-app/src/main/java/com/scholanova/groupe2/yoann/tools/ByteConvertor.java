package com.scholanova.groupe2.yoann.tools;

import java.util.ArrayList;
import java.util.List;

public class ByteConvertor {

	private byte[] byteArray;

	public static void main(String[] args) {

		// List<String> decomposedBinary = new ArrayList<>();
		// decomposedBinary.add("11");
		// decomposedBinary.add("11");
		// decomposedBinary.add("01");
		// decomposedBinary.add("01");
		// decomposedBinary.add("01");
		// decomposedBinary.add("01");
		// decomposedBinary.add("01");
		// decomposedBinary.add("11");
		// decomposedBinary.add("01");
		// decomposedBinary.add("00");
		// decomposedBinary.add("00");
		// decomposedBinary.add("11");
		//
		// byte[] b = recomposeByteArray(decomposedBinary);
		// for (byte c : b)
		// System.out.println(c);

	}

	public ByteConvertor(byte[] byteArray_) {

		byteArray = byteArray_;

	}

	public List<String> decomposeByteArray() {

		List<String> decomposedBytesList = new ArrayList<>();
		String binaryByte;
		String pieceOfByte;

		for (byte b : byteArray) {
			binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
			for (int j = 0; j < binaryByte.length(); j += 2) {
				pieceOfByte = binaryByte.substring(j, j + 2);
				decomposedBytesList.add(pieceOfByte);
			}
		}
		return decomposedBytesList;
	}

	public List<String> decomposeByteArray(int hiddenFileSize) {

		List<String> decomposedBytesList = new ArrayList<>();
		String binaryByte;
		String pieceOfByte;
		int lineCounter = 0;

		for (byte b : byteArray) {

			if (lineCounter < 4*hiddenFileSize) {

				binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');

				for (int j = 0; j < binaryByte.length(); j += 2) {
					pieceOfByte = binaryByte.substring(j, j + 2);
					decomposedBytesList.add(pieceOfByte);
				}
				lineCounter++;
			}
		}
		return decomposedBytesList;
	}

	public byte[] recomposeByteArray(List<String> decomposedBinary) {

		byte[] byteArray = new byte[decomposedBinary.size() / 16];
		int arrayCounter = 0;
		int listCounter = 0;
		StringBuilder sb = new StringBuilder();

		for (String s : decomposedBinary) {// TODO changer la boucle

			if ((listCounter + 1) % 4 == 0) {
				sb.append(s);

				if ((listCounter + 1) % 16 == 0) {
					byteArray[arrayCounter] = (byte) Integer.parseInt(sb.toString(), 2);
					sb = new StringBuilder();
					arrayCounter++;
				}
			}
			listCounter++;
		}
		return byteArray;
	}
}
