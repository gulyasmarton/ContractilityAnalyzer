package hu.elte.imagej.plugins.contractilityanalyzer;

import java.awt.Rectangle;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.GaussianBlur;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class SpheroidPerimeterPlugin implements PlugInFilter {
	int[] data;

	@Override
	public int setup(String arg, ImagePlus imp) {
		if (imp == null) {
			IJ.noImage();
			return DONE;
		}
		if (arg.equals("final")) {
			imp.updateAndDraw();
			ResultsTable table = new ResultsTable();
			for (int i = 0; i < data.length; i++) {
				table.incrementCounter();
				table.addValue("Frame", (i + 1));
				table.addValue("Perimeter (in pixel)", data[i]);
			}
			table.show("Results");
			return DONE;
		}

		if (imp != null)
			data = new int[imp.getStackSize()];
		return DOES_8G | DOES_STACKS | FINAL_PROCESSING | PARALLELIZE_STACKS;
	}

	@Override
	public void run(ImageProcessor ip) {
		int frame = ip.getSliceNumber();
		
//		System.out.println(frame);
//		ip.setSnapshotCopyMode(true);
//		ip.findEdges();
//		ip.setSnapshotCopyMode(false);
//		GaussianBlur bg = new GaussianBlur();
//		bg.blurGaussian(ip, 5, 5, 0.02);

		BooleanImage bin = ImageBinarizator.makeBinary(ip, true);

		List<AbstractBlob> aggrs = new FloodFill(bin).getBlobs();

		int max = -1;
		AbstractBlob blob = null;

		for (AbstractBlob aggr : aggrs) {
			if (aggr.getSize() > max) {
				max = aggr.getSize();
				blob = aggr;
			}
		}

		bin = new BooleanImage(bin.getWidth(), bin.getHeight());
		bin.add(blob);

		BooleanImage bin2 = bin.duplicate();
		bin2.invert();
		aggrs = new FloodFill(bin2).getBlobs();
		for (AbstractBlob aggr : aggrs) {
			Rectangle rec = aggr.getBound();
			if (rec.x == 0 || rec.y == 0 || rec.getMaxX() == bin.getWidth() || rec.getMaxY() == bin.getHeight())
				continue;

			bin.add(aggr);

		}

		data[frame - 1] = (int) (new AbstractBlob(bin)).getPerimeter();

	}

}
