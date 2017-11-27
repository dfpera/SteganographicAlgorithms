
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;

public class HistogramCreator {
    public Histogram createHistogram(BufferedImage image){
        ParameterBlock pb = new ParameterBlock();
        
        int[] bins = { 256 };
        double[] low = { 0.0D };
        double[] high = { 256.0D };

        pb.addSource(image);
        pb.add(null);
        pb.add(1);
        pb.add(1);
        pb.add(bins);
        pb.add(low);
        pb.add(high);

        RenderedOp op = JAI.create("histogram", pb, null);
        return  (Histogram) op.getProperty("histogram");
    }
    
}