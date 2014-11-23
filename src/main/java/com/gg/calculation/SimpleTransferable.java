package com.gg.calculation;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SimpleTransferable implements Transferable {
	private Object source;
	private DataFlavor[] flavors;

	public SimpleTransferable(Object object) {
		flavors = new DataFlavor[1];
		flavors[0] = new DataFlavor(object.getClass(), "beaker object");

		source = object;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavor.match(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return source;
	}
}
