package sample;

import java.io.IOException;

abstract public class abstract_Controller {
 // abstract class
    abstract void makeButton() throws IOException ;
    public abstract void makeIT(int fontSize, int x, int y, String line, String pdfItself) throws IOException;
    public abstract void getNameListPath();
    public abstract void getPdfPath();
    public abstract String makeString(String selection, String line);
    public abstract String makeString(String line);
    public abstract String toString(String absolutePath);
}
