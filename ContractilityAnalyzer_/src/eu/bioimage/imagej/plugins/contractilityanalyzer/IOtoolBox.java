package eu.bioimage.imagej.plugins.contractilityanalyzer;

import ij.io.OpenDialog;
import ij.io.SaveDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOtoolBox {

	public static <T> T as(Class<T> clazz, Object o) {
		if (clazz.isInstance(o)) {
			return clazz.cast(o);
		}
		return null;
	}

	public static void saveTextFile(StringBuilder sb, String path) {
		try {
			BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(path)));
			bwr.write(sb.toString());
			bwr.flush();
			bwr.close();
		} catch (Exception e) {

		}
	}

	public static String commonOpenDialog(String title, String defaultDir, String defaultName) {
		OpenDialog od = null;
		if (defaultName == null)
			od = new OpenDialog(title, defaultDir == null ? "" : defaultDir);
		else {
			od = new OpenDialog(title, defaultDir == null ? "" : defaultDir, defaultDir);
		}
		String directory = od.getDirectory();
		String fileName = od.getFileName();
		if (fileName == null)
			return null;
		return directory + File.separator + fileName;
	}

	public static String commonSaveDialog(String title, String defaultDir, String defaultName, String extension) {
		SaveDialog sd = null;
		if (defaultDir == null)
			defaultDir = "";
		if (extension == null)
			sd = new SaveDialog(title, defaultDir, defaultName);
		else {
			sd = new SaveDialog(title, defaultDir, defaultName, extension);
		}
		String directory = sd.getDirectory();
		String fileName = sd.getFileName();
		if (fileName == null)
			return null;
		return directory + File.separator + fileName;
	}

	public static boolean saveObject(String path, Object object) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
			return true;
		} catch (IOException i) {
			i.printStackTrace();
		}
		return false;
	}

	public static Object openObject(String path) {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object object = in.readObject();
			in.close();
			fileIn.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
