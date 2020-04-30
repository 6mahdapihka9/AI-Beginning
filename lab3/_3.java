package _3v2;

import java.io.FileInputStream;
import java.util.ArrayList;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class _3  extends Application{
	static  String[] x1 = 
		{"111101101101111",//0
		 "001011101001001",//1
		 "111001111100111",//2
		 "111001111001111",//3
		 "101101111001001",//4
		 "111100111001111",//5
		 "111100111101111",//6
		 "111001001001001",//7
		 "111101111101111",//8
		 "111101111001111"};//9
	static String[] X = 
		{"01110011111101111001110111111101110",//0
		"00010001100111001010000100001000010",//1
		"00000011100101000110011000111100000",//2
		"00000011100101000110000101111001100",//3
		"00000011100101001110000100001000010",//4
		"01110010000100001110000100111000000",//5
		"01110011100100011110110100111000110",//6
		"11111111110001001110011100100011000",//7
		"01110010100111001110010100111001110",//8
		"00110011101101001110000101111011110"};//9
	
	ArrayList<String> x = new ArrayList<String>();
	String check;
	
	int numberOfWeights = 35, numberOfPers, S, success, matr_D[][], eps;
	double nu = 0.1;
	_3Pers pers[];
	String str = "";
	Scene scene;
	Group group;
	HBox hb;
	VBox vb;
	Image image;
	ImageView imageView = null;
	TextField TF;
	Button BAdd, BStudy, BCheck;
	String link = "";
	
	public _3() {
		
	}
	
	public static void main(String args[]) {
		Application.launch();
	}
	
	public void Add() {
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_3v2\\img\\" + TF.getText() + ".png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		Color c = Color.valueOf("0xffffffff");
		
		str = "";
		for (int j = 0; j < 7; j++) {
			next: 
				for (int i = 0; i < 5; i++) {
				
				for (int v = (int)(j*image.getHeight()/7); v < (j+1)*image.getHeight()/7; v++) {
					for (int h = (int)(i*image.getWidth()/5); h < (i+1)*image.getWidth()/5; h++) {
						Color col = pixelReader.getColor(h, v);
						
						if (col.equals(Color.BLACK)) {
							str += "1";
							continue next;
						}
					}
				}
				str += "0";
			}
		}
		System.out.println(str);
		x.add(str);
		numberOfPers++;
	}
	
 	public void Study() {
		S = success = eps = 0;
		pers = new _3Pers[numberOfPers];
		matr_D = new int[numberOfPers][numberOfPers];
		
		for (int i = 0; i < numberOfPers; i++) {
			pers[i] = new _3Pers(numberOfWeights);
			for (int m = 0; m < numberOfPers; m++) 
				matr_D[i][m] = (i == m)? 1 : 0;
		}
		
		for (int m = 0; m < numberOfPers; m++) {//m - по каждому персептрону
			int i = 0;//i - по каждой букве
			while(true) {
				for (int j = 0; j < numberOfWeights; j++)//j - по каждому 0/1
					S += ((x.get(i).charAt(j) == '1')? 1 : 0) * pers[m].w[j];
					S += pers[m].w[numberOfWeights];
					
				eps = matr_D[m][i] - ((S >= 0)? 1 : 0);
				
				if (eps == 0) {
					success++;
					if (success == numberOfPers)
						break;
					i = (i < numberOfPers-1)? i+1 : 0;
				} else {
					success = 0;
					for (int j = 0; j < numberOfWeights; j++)//j - по каждому 0/1
						pers[m].w[j] += nu*eps*((x.get(i).charAt(j) == '1')? 1 : 0);
						pers[m].w[numberOfWeights] += nu*eps;
				} 
				S = 0;
			}
		}
		TF.setText(">>>Ready");
		BCheck.setDisable(false);
	}
	
	public void Check() {
		
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_3v2\\img\\check.png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		check = "";
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		
		for (int j = 0; j < 7; j++) {
			next: for (int i = 0; i < 5; i++) {
				for (int v = (int)(j*image.getHeight()/7); v < (j+1)*image.getHeight()/7; v++) {
					for (int h = (int)(i*image.getWidth()/5); h < (i+1)*image.getWidth()/5; h++) {
						Color col = pixelReader.getColor(h, v);
						if (col.equals(Color.BLACK)) {
							check += "1";
							continue next;
						}
					}
				}
				check += "0";
			}
		}
		
		
		for (int m = 0; m < numberOfPers; m++) {//m - по каждому персептрону
			S = 0;
			for (int j = 0; j < numberOfWeights; j++) //j - по каждому 0/1
				S += ((check.charAt(j) == '1')? 1 : 0) * pers[m].w[j];
				S += pers[m].w[numberOfWeights];
			if (S >= 0) {
				TF.setText(">>>Number is '" + m + "'");
				break;
			}
			if (m == numberOfPers-1)
				TF.setText(">>>Unknown symbol");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		group = new Group();
		hb = new HBox(); 
		hb.setPrefSize(500, 350);
		
		vb = new VBox(); 
		vb.setPrefSize(250, 250);	
		vb.setAlignment(Pos.CENTER);
		
		
		TF = new TextField();
		BAdd = new Button("Add ");
		BStudy = new Button("Study AI");
		BCheck = new Button("Check");
		
		BAdd.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	Add();
            }
        });
		
		BStudy.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Study();
            }
        });
		
		BCheck.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Check();
            }
        });
		
		
		
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_3v2\\img\\def.png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		BCheck.setDisable(true);
		
		vb.getChildren().add(BAdd);
		vb.getChildren().add(BStudy);
		vb.getChildren().add(BCheck); 
		vb.getChildren().add(TF);
		
		hb.getChildren().add(vb);
		hb.getChildren().add(imageView);
		
		
		group.getChildren().add(hb);
		scene = new Scene(group, 500, 350);
		
		
		primaryStage.setTitle("OpenAI");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}