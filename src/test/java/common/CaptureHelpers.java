package common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelpers extends ScreenRecorder{
    static String projectPath = System.getProperty("user.dir") + "/";
    private static Logger logger = LoggerHelpers.getLogger();

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static void CaptureScreenShot(WebDriver driver, String screenName) throws IOException {
        PropertiesHelpers.setPropertiesFile();
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                File theDir = new File(projectPath + PropertiesHelpers.getPropValue("exportCapturePath"));
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                //lấy tên của TCs để đặt tên ảnh theo format
                FileHandler.copy(source, new File(projectPath + PropertiesHelpers.getPropValue("exportCapturePath") + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
                logger.info("Capture..." + screenName);
            } catch (Exception e) {
                logger.error("Exception while taking screenshot " + e.getMessage());
            }
    }

    //-------------------Monte Media Library----------------------
    public static ScreenRecorder screenRecorder;
    public String name;

    public CaptureHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                         Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException{
        if (!movieFolder.exists()){
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "_" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    public static void StartRecord (String methodName) throws Exception{
        PropertiesHelpers.setPropertiesFile();
        File file = new File(projectPath + PropertiesHelpers.getPropValue("exportVideoPath") + "/" + methodName + "/");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Rectangle captureSize = new Rectangle(0,0,screenSize.width,screenSize.height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        screenRecorder = new CaptureHelpers(gc,captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);
        screenRecorder.start();
    }
    public static void StopRecord() throws Exception {
        screenRecorder.stop();
    }
}
