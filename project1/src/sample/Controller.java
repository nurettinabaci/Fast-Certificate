package sample;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.filechooser.*;
import java.lang.*;

public class Controller extends abstract_Controller implements Initializable {
    @FXML
    private Button txtButton;  // Opens a FileChooser to select text file

    @FXML
    private Button pdfButton; // Opens a FileChooser to select PDF file

    @FXML
    private Button makeTHISbutton; // Button for getting output

    @FXML
    private TextField txtPathField; // takes path of the selected text file

    @FXML
    private TextField pdfPathField; // takes path of the selected PDF file

    @FXML
    private TextField yCoord; // takes Y coordinate from user to write

    @FXML
    private TextField xCoord; // takes X coordinate from user to write

    private ObservableList<String> typeList= FXCollections.observableArrayList(  // Selection list for combo box
            "Example Name","EXAMPLE NAME", "example name"
    );

    @FXML
    private ComboBox<String> comboBOX; //

    @FXML
    private TextField sizeField; // takes font size from user to write at the desired size

   @Override
    public void initialize(URL location, ResourceBundle resources) { comboBOX.setItems(typeList); }

    public void makeButton() throws IOException {  // listener method
        String listPath=txtPathField.getText();    // gets path of choosen text file
        String pdfPath=pdfPathField.getText();     // gets path of choosen text file
        String whichRowSelected=comboBOX.getSelectionModel().getSelectedItem().toString();// gets choosen text type
        int x_coordinate=0; // initialize X cordinate
        int y_coordinate=0; // initialize X cordinate
        int getFontSize=1;  // initialize font size

        if(xCoord.getText().equals("")){  // if text field of X coordinate is empty, will show error message
            JOptionPane.showMessageDialog(null,"Please enter X coordinate","Error",JOptionPane.ERROR_MESSAGE);
        }else x_coordinate=Integer.parseInt(xCoord.getText());

        if(yCoord.getText().equals("")){ // if text field of Y coordinate is empty, will show error message
            JOptionPane.showMessageDialog(null,"Please enter Y coordinate","Error",JOptionPane.ERROR_MESSAGE);
        }else y_coordinate=Integer.parseInt(yCoord.getText());

        if(sizeField.getText().equals("")){ // if text field of font size is empty, will show error message and write at dimension of 1
            JOptionPane.showMessageDialog(null,"Please enter font size!","Error",JOptionPane.ERROR_MESSAGE);
        }else getFontSize=Integer.parseInt(sizeField.getText());

        if( listPath==""){
            JOptionPane.showMessageDialog(null,"Please select txt file!","Error",JOptionPane.ERROR_MESSAGE);
        }
        if( pdfPath==""){
            JOptionPane.showMessageDialog(null,"Please select pdf file!","Error",JOptionPane.ERROR_MESSAGE);
        }

        //Has text file content inside?
        File file = null;
        file = new File(listPath);  // create new text file object
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {

                System.out.println(whichRowSelected);                   // for debug
                String text1 = makeString(whichRowSelected, line);      // prepare the name to write
                System.out.println("The name is : " + text1);           // for debug
                makeIT(getFontSize,x_coordinate,y_coordinate,text1,pdfPath); // writer method
            }
        }
    }
    public void makeIT(int fontSize, int x, int y, String line, String pdfItself) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfItself));  // creates pdf file object
        PDPage page = document.getPage(0); // gets the first page of PDF file to write

        // implements the method to write inside the pdf file
        PDPageContentStream contentStream = new PDPageContentStream(document, page,true,false);
        contentStream.beginText(); // begins to write
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize); // adjusts the font type to Times New Roman, and font size
        contentStream.newLineAtOffset(x, y);    // initialize x,y coordinate to write
        String text = line;                     // set Name and Surname then begin to write
        System.out.println(text);               // show name in shell
        contentStream.showText(text);           // write to pdf
        contentStream.endText();                // finish to write the name
        System.out.println("Content added");    // inform user in shell
        contentStream.close();                  // close writing operation
        text = makeString(line);
        //String pathname = "C:\\Users\\#USERDIR#\\Desktop\\" + text + ".pdf"; // Save PDF file with its 'name'
        String pathname = System.getProperty("user.home") + "\\"+"Desktop\\" + text + ".pdf"; // Save PDF file with its 'name'

        document.save(new File(pathname));      // save the PDF File
        document.close();                       // and close
    }
    public void getNameListPath(){   // Opens JFileChooser to select just text documents
        JFileChooser txtChooser = new JFileChooser();
        txtChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        txtChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // takes text file filter with below code
        txtChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT Documents", "txt"));
        txtChooser.setAcceptAllFileFilterUsed(true);
        int result = txtChooser.showOpenDialog(txtChooser);
        if (result==JFileChooser.APPROVE_OPTION){
            File selectedTXTFile=txtChooser.getSelectedFile();
            txtPathField.setText(toString(selectedTXTFile.getAbsolutePath()));
        }
    }
    public void getPdfPath(){   // Opens JFileChooser to select just PDF documents
        JFileChooser pdfChooser = new JFileChooser();
        pdfChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        pdfChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // takes pdf file filter with below code
        pdfChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        pdfChooser.setAcceptAllFileFilterUsed(true);
        int result = pdfChooser.showOpenDialog(pdfChooser);
        if (result==JFileChooser.APPROVE_OPTION){
            File selectedPDFFile=pdfChooser.getSelectedFile();
            pdfPathField.setText(toString(selectedPDFFile.getAbsolutePath()));
        }
    }
    public String makeString(String selection, String line) { // prepares the name for choosen text style
        switch (selection){
            case "example name": line= line.toLowerCase(); return line;
            case "EXAMPLE NAME": line=line.toUpperCase(); return line;
            case "Example Name":
                char c = Character.toUpperCase(line.charAt(0)); // makes uppercase the first char of string
                line = c + line.substring(1);
                String whiteSpace = " ";
                for (int i = 1; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {                        // if saw whitespace...
                        c = Character.toUpperCase(line.charAt(i + 1));  // makes uppercase first char of another string
                        line = line.substring(0, i) + whiteSpace + c + line.substring(i + 2); // merges the words
                    }
                } return line;
            default: line=line.toUpperCase();
        }System.out.println(selection+"");
        return line;
    }
    public String makeString(String line) {  // makes operation in form of "Example Name" to give a name to saved file
        char c = Character.toUpperCase(line.charAt(0));
        line = c + line.substring(1);
        String whiteSpace = " ";
        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                c = Character.toUpperCase(line.charAt(i + 1));
                line = line.substring(0, i) + whiteSpace + c + line.substring(i + 2);
            }
        } return line;
    }
    public String toString(String absolutePath) { return absolutePath+""; }
}