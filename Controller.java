package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import static sample.Main.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import static sample.Main.primaryStage;
import static sample.Union.find;
import static sample.Union.union;


public class Controller {
    static WritableImage wImage;
    static int width;
    static int height;
    static int widthTriColour;
    static int heightTriColour;
    static int[] RedCellArray;
    static int[] whiteCellArray;
    static int [] rootArray;
    private static  HashSet<Integer> whiteCells = new HashSet();
    private static  HashSet<Integer> redCells = new HashSet();
    private static  HashSet<Integer> removeRedCells = new HashSet();
    private static  HashSet<Integer> removeWhitecells = new HashSet();
    private static HashMap<Integer, Integer> whiteCount = new HashMap<>();
    private static HashMap<Integer, Integer> redCount = new HashMap<>();
    private static HashMap<Integer, Integer> countTotal = new HashMap<>();
    @FXML
    ImageView pic1, pic2;
    @FXML
    Label totalLabel,redLabel,whiteLabel;
    @FXML
    Slider noise;



    public void openImage(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        FileInputStream inputstream = new FileInputStream(file.getAbsoluteFile());
        image = new Image(inputstream);
        pic1.setPreserveRatio(true);
        pic1.setImage(image);
        reader = image.getPixelReader();
    }

    public void triColour() {
        wImage = new WritableImage(
                width = (int) image.getWidth(),
                height = (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        widthTriColour = (int) wImage.getWidth();
        heightTriColour = (int) wImage.getHeight();
        for (int y = 0; y < wImage.getHeight(); y++) {

            for (int x = 0; x < wImage.getWidth(); x++) {
                Color color = reader.getColor(x, y);

                if (color.getGreen() * 255 >= 150) {
                    pixelWriter.setColor(x, y, Color.WHITE);

                } else if (color.getRed() > color.getBlue()) {
                    pixelWriter.setColor(x, y, Color.RED);
                } else pixelWriter.setColor(x, y, Color.PURPLE);
            }

        }
        pic2.setImage(wImage);
        triColourReader = wImage.getPixelReader();
    }

    public void valueSetter() {
        rootArray = new int[(width * height)];
        RedCellArray = new int[(width * height)];
        whiteCellArray = new int[(width * height)];

        for (int i = 0; i < RedCellArray.length; i++) {
            RedCellArray[i] = i;
        }

        for (int i = 0; i < whiteCellArray.length; i++) {
            whiteCellArray[i] = i;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = triColourReader.getColor(x, y);
                if (color.equals(Color.WHITE)) {
                    RedCellArray[(y * width) + x] = -111;
                    whiteCellArray[(y * width) + x] = -111;
                } else if (color.equals(Color.RED)) {
                    RedCellArray[(y * width) + x] = (y * width) + x;
                    whiteCellArray[(y * width) + x] = -111;
                } else if (color.equals(Color.PURPLE)) {
                    RedCellArray[(y * width) + x] = -111;
                    whiteCellArray[(y * width) + x] = (y * width) + x;
                }
            }
        }
        for (int i = 0; i < RedCellArray.length; i++) {
            if (i % width == 0)
                System.out.println();
            System.out.print(RedCellArray[i] + " ");
        }
    }
    public void Union() {

        for (int i = 0; i < RedCellArray.length; i++) {
            if (RedCellArray[i] != -111) {
                if ((i+1)< RedCellArray.length && RedCellArray[i+1] != -111 && !(i % width==0)) {
                    union(RedCellArray, i, i + 1);
                    //System.out.println("Union Left -- The value of " + i + " is " + find(array, i));
                }
                if ((i+width) < RedCellArray.length && RedCellArray[i+width] != -111) {
                    union(RedCellArray, i, i + width);
                    //System.out.println("Union Down -- The value of " + i + " is " + find(array, i));
                }
                if(RedCellArray[i] != -111){
                    redCells.add(find(RedCellArray,i));
                }



            }
        }
        for (int j = 0; j < whiteCellArray.length; j++) {
            if (whiteCellArray[j] != -111) {
                if ((j+1)< whiteCellArray.length && whiteCellArray[j+1] != -111 && !(j % width==0)) {
                    union(whiteCellArray, j, j + 1);
                    //System.out.println("Union Left -- The value of " + i + " is " + find(array, i));
                }
                if ((j+width) < whiteCellArray.length && whiteCellArray[j+width] != -111) {
                    union(whiteCellArray, j, j + width);
                    //System.out.println("Union Down -- The value of " + i + " is " + find(array, i));
                }
                if(whiteCellArray[j] != -111&& whiteCellArray[j]<=50){
                    whiteCells.add(find(whiteCellArray,j));
                }



            }
        }

    }
    public void pixelPerCell(){
        for (int i = 0; i < RedCellArray.length; i++){
            int rI = find(RedCellArray,i);
            if (redCount.containsKey(rI)&& RedCellArray[i] != -111){
                redCount.put(rI,redCount.get(rI)+i);
            }
            else{
                redCount.put(rI,i);
                redCount.remove(-111);
            }
        }

        for (int i = 0;i < whiteCellArray.length; i++){
            int wI = find(whiteCellArray,i);
            if (whiteCount.containsKey(wI)&&whiteCellArray[i] != -111){
                whiteCount.put(wI,whiteCount.get(wI)+i);
            }
            else{
                whiteCount.put(wI,i);
                whiteCount.remove(-111);
            }
        }

        for (Integer entry : redCount.keySet()){
            countTotal.putAll(redCount);
        }

        for (Integer entry : whiteCount.keySet()){
            countTotal.putAll(whiteCount);
        }
    }
    public void cellStats(){
        totalLabel.setText("Total number of cells:"+ countTotal.size());
        whiteLabel.setText("Total number of  White cells:"+ whiteCount.size());
        redLabel.setText("Total number of  Red cells:"+ redCount.size());
    }
    public WritableImage NoiseReduction(){

        PixelWriter redwriter = wImage.getPixelWriter();
        int width = (int) wImage.getWidth();
        int height = (int) wImage.getHeight();

        for(int y = 0;y<height; y++) {
            for (int x = 0; x < width; x++) {
                if (RedCellArray[(y * width) + x] != -111) {
                    int rI = find(RedCellArray, (y * width) + x);

                    if (redCount.containsKey(rI) && redCount.get(rI) < noise.getValue()*10000) {
                        redwriter.setColor(x, y, Color.WHITE);
                        removeRedCells.add(rI);
                    }
                }

                if (whiteCellArray[(y * width) + x] != -111) {
                    int wI = find(whiteCellArray, (y * width) + x);

                    if (whiteCount.containsKey(wI) && whiteCount.get(wI) < noise.getValue()*10000) {
                        redwriter.setColor(x, y, Color.WHITE);
                        removeWhitecells.add(wI);
                    }
                }

            }
        }
        for (int i: removeRedCells){
            redCount.remove(i);
        }
        for (int i:removeWhitecells){
            whiteCount.remove(i);
        }
        pic2.setImage(wImage);
        totalLabel.setText("Total number of cells:"+countTotal.size());
        whiteLabel.setText("Total number of White cells"+ whiteCount.size());
        redLabel.setText("Total number of  Red cells:"+ redCount.size());
        return wImage;
    }
    public Rectangle pixelBoundaries(int [] a,int id){
        Rectangle rect = null;
        int width = (int) pic2.getImage().getWidth();
        for (int i = 0; i<a.length;i++){
            if (find(a,i)==id){
                int x = i%width;
                int y = i/width;
                if (rect==null) rect= new Rectangle(x,y,1,1);
                else {
                    if (x< rect.getX()){
                        rect.setWidth(rect.getWidth()+(rect.getX()-x));
                        rect.setX(x);
                    }
                    if (x>rect.getX()+rect.getWidth()){
                        rect.setWidth(x-rect.getX());
                    }
                    if (y>rect.getY()+rect.getHeight()){
                        rect.setHeight(y-rect.getY());
                    }
                }
            }
        }
        return rect;
    }
    public void identifycells(){
        valueSetter();
        Union();
        pixelPerCell();

        for(int i = 0; i< RedCellArray.length; i++){
            rootArray[i] =0;
        }
        redCells.clear();
        whiteCells.clear();
        for (int i = 0; i < RedCellArray.length; i++){
            if(RedCellArray[i] >= 0){
                redCells.add(find(RedCellArray,i));
            }
        }
        for (int i = 0; i <whiteCellArray.length;i++){
            if(whiteCellArray[i] >= 0){
                whiteCells.add(find(whiteCellArray,i));
            }
        }

        for (int i : redCells){
            Rectangle rect = pixelBoundaries(RedCellArray,i);
            rect.setTranslateX(pic2.getLayoutX());
            rect.setTranslateY(pic2.getLayoutY());
            rect.setStroke(Color.GREEN);
            rect.setFill(Color.TRANSPARENT);

            ((Pane)pic2.getParent()).getChildren().addAll(rect);
        }
        for (int i : whiteCells){
            Rectangle rect = pixelBoundaries(whiteCellArray,i);
            rect.setTranslateX(pic2.getLayoutX());
            rect.setTranslateY(pic2.getLayoutY());
            rect.setStroke(Color.BLUE);
            rect.setFill(Color.TRANSPARENT);
            ((Pane)pic2.getParent()).getChildren().addAll(rect);
        }
    }
    public void test(){
     valueSetter();
        Union();
        pixelPerCell();
        cellStats();
    }
}