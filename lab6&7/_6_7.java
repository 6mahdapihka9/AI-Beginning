package _6_7;


import javafx.scene.control.CheckBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class _6_7  extends Application{
	
	public ArrayList<double[]> x = new ArrayList<double[]>();
	public ArrayList<String> nameOfSymb = new ArrayList<String>();
	
	String check;
	
	int amountOfWeights = 14, amountOfNeurs, success, matr_D[][], amountOfSymbs;
	Neur pers1[], pers2[];
	String str = "";
	Scene scene;
	Group group;
	HBox hb;
	VBox vb;
	FlowPane fp;
	Image image;
	ImageView imageView = null;
	TextField TF;
	Label LSymb;
	Button BAddAll, BAdd, BStudy, BCheck;
	String link = "";
	CheckBox CB[];
	double e = 2.71828, y, del, S, nu = 0.1, eps;
	
	Layer HiddenL, OutterL;
	
	public static void main(String args[]) {
		Application.launch();
	}
	
	public void AddAll() {
		//healthy
		double x1[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		x.add(x1);
		nameOfSymb.add("Healthy");
		//ill / runny nose
		double x2[] = {1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		x.add(x2);
		nameOfSymb.add("Runny nose");
		//OPZ / ARI
		double x3[] = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		x.add(x3);
		nameOfSymb.add("ARI");
		//problems with heart
		double x4[] = {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		x.add(x4);
		nameOfSymb.add("Problems with heart");
		//break / fracture
		double x5[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0};
		x.add(x5);
		nameOfSymb.add("Fracture");
		amountOfNeurs = 5;
	}
	
	public void Add() {
		double inp[] = new double[14];
		
		for (int i = 0; i < 14; i++) 
			inp[i] = (CB[i].isSelected())? 1.0 : 0.0;

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
		}
		BCheck.setDisable(false);
	}
	
	public void Check() throws IOException {
		
		System.out.println("__________________________________");
		
		double check[] = new double[14];
		for (int i = 0; i < 14; i++) 
			check[i] = (CB[i].isSelected())? 1.0 : 0.0;
		
		LSymb.setText("");
		int symb = 0;

		if ((symb = HiddenL.Check(OutterL, check)) != -1)
			LSymb.setText("Patient is '" + OutterL.nameOfSymb.get(symb) + "'");
		else
			LSymb.setText("Unknown illness");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		group = new Group();
		hb = new HBox(); 
		hb.setPrefSize(500, 350);
		
		vb = new VBox();
		vb.setPrefSize(250, 350);
		
		fp = new FlowPane(); 
		fp.setAlignment(Pos.CENTER);
		
		CB = new CheckBox[14];
		for (int i = 0; i < CB.length; i++)
			CB[i] = new CheckBox();
		CB[0].setText("Чихание");
		CB[1].setText("Кашель");
		CB[2].setText("Головная боль");
		CB[3].setText("Температура");
		CB[4].setText("Насморк");
		CB[5].setText("Слабость");
		CB[6].setText("Хрипы");
		CB[7].setText("Сухость горла");
		CB[8].setText("Головоркужение");
		CB[9].setText("Боль в груди");
		CB[10].setText("Боль в мышцах");
		CB[11].setText("Потеря сознания");
		CB[12].setText("Нарушение движения");
		CB[13].setText("Острая местная боль");
		TF = new TextField();
		BAddAll = new Button("Add All");
		BAdd = new Button("Add");
		BStudy = new Button("Study AI");
		BCheck = new Button("Check");
		LSymb = new Label("");
		
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
			image = new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_6_7\\img\\doc.png"));
		}catch(Exception exc) {
			System.out.println(exc);
		}
		imageView = new ImageView(image);
		BCheck.setDisable(true);
		
		fp.getChildren().add(BAddAll);
		fp.getChildren().add(BAdd);
		fp.getChildren().add(BStudy);
		fp.getChildren().add(BCheck);
		
		vb.getChildren().add(fp);
		vb.getChildren().add(TF);
		for (int i = 0; i < CB.length; i++)
			vb.getChildren().add(CB[i]);
		vb.getChildren().add(LSymb);
		
		hb.getChildren().add(vb);
		hb.getChildren().add(imageView);
		
		group.getChildren().add(hb);
		scene = new Scene(group, 500, 350);
		primaryStage.getIcons().add(new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_6_7\\img\\BTNReplenishHealth.png")));
		primaryStage.setTitle("OpenAI");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}