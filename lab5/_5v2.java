package _5v2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class _5v2  extends Application{
	
	public ArrayList<double[]> x = new ArrayList<double[]>();
	public ArrayList<String> nameOfSymb = new ArrayList<String>();
	
	String check;
	
	int amountOfWeights = 140, amountOfNeurs, success, matr_D[][], amountOfSymbs;
	Neur pers1[], pers2[];
	String str = "";
	Scene scene;
	Group group;
	HBox hb;
	VBox vb;
	Image image;
	ImageView imageView = null;
	TextField TF;
	Label LSymb, LYs;
	Button BAddAll, BAdd, BStudy, BCheck;
	String link = "";
	double e = 2.71828, y, del, S, nu = 0.1, eps;
	
	Layer HiddenL, OutterL;
	
	public static void main(String args[]) {
		Application.launch();
	}
	
	public void AddAll() {
		for (int i = 0; i < 10; i++)
			Add("" + i);
		for (char c = 97; c < 123; c++)
			Add("" + c);
	}
	public void Add(String fileName) {
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_5\\img\\" + fileName + ".png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		
		double inp[] = new double[14*10];
		for (int j = 0; j < 14; j++) 
			next: for (int i = 0; i < 10; i++) {
				for (int v = (int)(j*image.getHeight()/14); v < (j+1)*image.getHeight()/14; v++) 
					for (int h = (int)(i*image.getWidth()/10); h < (i+1)*image.getWidth()/10; h++) {
						Color col = pixelReader.getColor(h, v);
						
						if (col.equals(Color.BLACK)) {
							inp[i + 10*j] = 1.0;
							continue next;
						}
					}
				inp[i + 10*j] = 0.0;
			}
		/*for (int i = 0; i < 140; i++)
			System.out.print(inp[i]);
		System.out.println();*/
		x.add(inp);
		nameOfSymb.add(fileName);
		amountOfNeurs++;
	}
	public void Add() {
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_5\\img\\" + TF.getText() + ".png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		
		double inp[] = new double[14*10];
		for (int j = 0; j < 14; j++) 
			next: for (int i = 0; i < 10; i++) {
				for (int v = (int)(j*image.getHeight()/14); v < (j+1)*image.getHeight()/14; v++) 
					for (int h = (int)(i*image.getWidth()/10); h < (i+1)*image.getWidth()/10; h++) {
						Color col = pixelReader.getColor(h, v);
						if (col.equals(Color.BLACK)) {
							inp[i + 10*j] = 1.0;
							continue next;
						}
					}
				inp[i + 10*j] = 0.0;
			}
		/*for (int i = 0; i < 140; i++)
			System.out.print(inp[i]);
		System.out.println();*/
		x.add(inp);
		nameOfSymb.add(TF.getText());
		amountOfNeurs++;
	}
	
 	public void Study() {
 		HiddenL = new Layer(true, amountOfWeights, amountOfNeurs);
 		OutterL = new Layer(false, amountOfNeurs, amountOfNeurs);
 		
 		HiddenL.x.addAll(x);
 		HiddenL.nameOfSymb.addAll(nameOfSymb);
 		OutterL.nameOfSymb.addAll(nameOfSymb);
 		int gen = 0;
		while(Layer.successS <= amountOfNeurs) {
			for (int nS = 0; nS < amountOfNeurs; nS++) {
				Layer.successN = 0;
				while(true) {
					HiddenL.Y(OutterL, nS);
					OutterL.Y(nS);
					OutterL.Delta(nS);
					if (Layer.successN >= amountOfNeurs) {
						Layer.successS++;
						break;
					}
					Layer.successS = 0;
					OutterL.newW(nS);
					HiddenL.Delta(OutterL, nS);
					HiddenL.newW(nS);
				}
			}
			gen++;
		}
		System.out.println(gen);
		BCheck.setDisable(false);
	}
	
	public void Check() throws IOException {
		
		System.out.println("__________________________________");
		
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_5\\img\\" + TF.getText() + ".png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		
		double check[] = new double[14*10];
		for (int j = 0; j < 14; j++) {
			next: for (int i = 0; i < 10; i++) {
				for (int v = (int)(j*image.getHeight()/14); v < (j+1)*image.getHeight()/14; v++) {
					for (int h = (int)(i*image.getWidth()/10); h < (i+1)*image.getWidth()/10; h++) {
						Color col = pixelReader.getColor(h, v);
						if (col.equals(Color.BLACK)) {
							check[i + 10*j] = 1.0;
							continue next;
						}
					}
				}
				check[i + 10*j] = 0.0;
			}
		}
		
		LSymb.setText("");
		int symb = 0;

		if ((symb = HiddenL.Check(OutterL, check)) != -1)
			LSymb.setText("Symbol is '" + OutterL.nameOfSymb.get(symb) + "'");
		else
			LSymb.setText("Unknown symbol");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		group = new Group();
		hb = new HBox(); 
		hb.setPrefSize(500, 350);
		
		vb = new VBox(); 
		vb.setPrefSize(250, 350);	
		//vb.setAlignment(Pos.CENTER);
		
		TF = new TextField();
		BAddAll = new Button("Add All");
		BAdd = new Button("Add");
		BStudy = new Button("Study AI");
		BCheck = new Button("Check");
		LSymb = new Label("");
		LYs = new Label("");
		
		BAddAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	AddAll();
            }
        });
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
            	try {
            		Check();
            	} catch(IOException exc) {
            		System.out.println(exc);
            	}
            }
        });
		
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_5\\img\\def.png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		BCheck.setDisable(true);
		
		vb.getChildren().add(BAddAll);
		vb.getChildren().add(BAdd);
		vb.getChildren().add(BStudy);
		vb.getChildren().add(BCheck); 
		vb.getChildren().add(TF);
		vb.getChildren().add(LSymb);
		vb.getChildren().add(LYs);
		
		hb.getChildren().add(vb);
		hb.getChildren().add(imageView);
		
		
		group.getChildren().add(hb);
		scene = new Scene(group, 500, 350);
		
		primaryStage.setTitle("OpenAI");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}