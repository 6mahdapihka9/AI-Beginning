package _4;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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

public class _4  extends Application{
	
	ArrayList<String> x = new ArrayList<String>();
	ArrayList<String> nameOfSymb = new ArrayList<String>();
	Map<String, Double> Ys = new HashMap<String, Double>();
	
	String check;
	
	int amountOfWeights = 35, amountOfPers, success, matr_D[][];
	_4Pers pers[];
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
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_4\\img\\" + fileName + ".png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		
		hb.getChildren().remove(imageView);
		hb.getChildren().add(imageView);
		PixelReader pixelReader = image.getPixelReader();
		hb.getChildren().remove(1);
		
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
		x.add(str);
		//nameOfSymb.add(TF.getText());
		nameOfSymb.add(fileName);
		amountOfPers++;
	}
	
 	public void Study() {
		S = y = del = eps = success = 0;
		pers = new _4Pers[amountOfPers];
		matr_D = new int[amountOfPers][amountOfPers];
		
		for (int numberOfPers = 0; numberOfPers < amountOfPers; numberOfPers++) {
			pers[numberOfPers] = new _4Pers(amountOfWeights);
			for (int numberOfSymb = 0; numberOfSymb < amountOfPers; numberOfSymb++) 
				matr_D[numberOfPers][numberOfSymb] = (numberOfPers == numberOfSymb)? 1 : 0;
			
		}
		
		for (int nPers = 0; nPers < amountOfPers; nPers++) {
			int nSymb = 0;
			success = 0;
			while(true) {
				eps = 0;
				for (int nW = 0; nW < amountOfWeights; nW++) 
					S += ((x.get(nSymb).charAt(nW) == '1')? 1 : 0) * pers[nPers].w[nW];
					S += pers[nPers].w[amountOfWeights];

				y = 1/(1 + Math.pow(1/e, S));
				del = y*(1 - y)*(matr_D[nPers][nSymb] - y);
				del = Math.round(del * 1000000.0)/1000000.0;
				
				eps = matr_D[nPers][nSymb] - y;
				
				if (Math.abs(eps) <= 0.1) {
					success++;
					if (success >= amountOfPers)
						break;
					nSymb = (nSymb < amountOfPers-1)? nSymb+1 : 0;
				} else {
					success = 0;
					for (int nW = 0; nW < amountOfWeights; nW++)
						pers[nPers].w[nW] += nu*del*((x.get(nSymb).charAt(nW) == '1')? 1 : 0);
						pers[nPers].w[amountOfWeights] += nu*del;
				} 
				S = 0;
			}
		}
		LSymb.setText("Ready");
		BCheck.setDisable(false);
	}
	
	public void Check() {
		
		try {
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_4\\img\\" + TF.getText() + ".png"));
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
		
		for (int nPers = 0; nPers < amountOfPers; nPers++) {
			S = 0;
			for (int nW = 0; nW < amountOfWeights; nW++) 
				S += ((check.charAt(nW) == '1')? 1 : 0) * pers[nPers].w[nW];
				S += pers[nPers].w[amountOfWeights];
			y = 1/(1 + Math.pow(1/e, S));			
			Ys.put(nameOfSymb.get(nPers), y);
		}
		
		Ys.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).forEach(System.out::println);
		
		LSymb.setText("");
		for (int nPers = 0; nPers < amountOfPers; nPers++) {
			S = 0;
			for (int nW = 0; nW < amountOfWeights; nW++) 
				S += ((check.charAt(nW) == '1')? 1 : 0) * pers[nPers].w[nW];
				S += pers[nPers].w[amountOfWeights];
			
				
			y = 1/(1 + Math.pow(1/e, S));
			if (y > 0.9) {
				LSymb.setText("Symbol is '" + nameOfSymb.get(nPers) + "'");
				
				break;
			}
			if (nPers == amountOfPers-1)
				LSymb.setText("Unknown symbol");
		}
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
            	try {
            		Add(TF.getText());
            	} catch(Exception exc) {
            		
            	}
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
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_4\\img\\def.png"));
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