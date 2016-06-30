package eu.bioimage.imagej.plugins.contractilityanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.measure.ResultsTable;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class HoleSizePlugin implements PlugInFilter {
	List<Integer>[] data;
	int minSize = 1000;
	boolean isDark = true;
	boolean needStat = true;
	boolean needText = true;

	@Override
	public int setup(String arg, ImagePlus imp) {
		if (imp == null) {
			IJ.noImage();
			return DONE;
		}
		if (arg.equals("final")) {
			imp.updateAndDraw();

			if (needStat) {
				ResultsTable table = new ResultsTable();
				for (int i = 0; i < data.length; i++) {

					DataPoint dp = new DataPoint(data[i]);

					table.incrementCounter();
					table.addValue("Frame", (i + 1));
					table.addValue("Mean", dp.getMean());
					table.addValue("SD", dp.getSd());
					table.addValue("SEM", dp.getSem());
					table.addValue("Count", dp.getN());
				}
				table.show("Results");
			}

			if (needText) {
				String path = IOtoolBox.commonSaveDialog("Save holes", null, "holes.txt", ".txt");
				if (path == null)
					return DONE;

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < data.length; i++) {
					for (Integer s : data[i]) {
						sb.append(s + " ");
					}
					sb.append(System.getProperty("line.separator"));
					IOtoolBox.saveTextFile(sb, path);
				}
			}

			return DONE;
		}

		if (imp != null)
			data = (ArrayList<Integer>[]) new ArrayList[imp.getStackSize()];

		GenericDialog gd = new GenericDialog("Settings");
		gd.addNumericField("Size limit (pixel):", minSize, 0);
		gd.addCheckbox("Dark background", isDark);
		gd.addCheckbox("Show statistic", needStat);
		gd.addCheckbox("Save holes as a textfile", needText);

		gd.showDialog();
		if (gd.wasCanceled())
			return DONE;
		minSize = (int) gd.getNextNumber();
		isDark = gd.getNextBoolean();
		needStat = gd.getNextBoolean();
		needText = gd.getNextBoolean();

		return DOES_8G | DOES_STACKS | FINAL_PROCESSING | PARALLELIZE_STACKS;
	}

	@Override
	public void run(ImageProcessor ip) {
		int frame = ip.getSliceNumber();
		BooleanImage bin = ImageBinarizator.makeBinary(ip, isDark);
		List<AbstractBlob> holes = new FloodFill(bin).getBlobs();
		List<Integer> nums = new ArrayList<Integer>();
		for (AbstractBlob blob : holes) {
			if (blob.getSize() > minSize)
				nums.add(blob.getSize());
		}

		Collections.reverse(nums);

		data[frame - 1] = nums;
		System.out.println(frame);
	}
}
