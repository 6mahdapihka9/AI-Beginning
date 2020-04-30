package _10v2;

import java.io.FileInputStream;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class _10v2 extends Application{
	Group group;
	Scene scene;
	TextField tfAmountOfGen, tfAOfMut;
	Label lAmountOfGen, lAOfMut;
	FlowPane fp;
	Canvas can;
	VBox vb;
	HBox hb;
	Button bstep, ball;
	Random r = new Random();
	
	int a = 0, b = 40, aOfCre = 100, aOfGens = 100, numberOfGen = 0, aOfChorm = 8, aOfMut = 1, sum;
	boolean firstRun = true; 
	
	Creature oldGen[], newGen[];
	
	public static void main(String args[]) {
		Application.launch();
	}
	
	
	public void allGens() {
		try {
			aOfGens = Integer.parseInt(tfAmountOfGen.getText());
		} catch (Exception exc) {}
		int localNumOfGen = numberOfGen + aOfGens;
		sum = 0;
		for (; numberOfGen < localNumOfGen;)
			step(numberOfGen);
		//System.out.println(sum/100);
	}
	
	public void step(int _numberOfGenerations) {
		grid();
		if (firstRun) {
			oldGen = new Creature[aOfCre];
			newGen = new Creature[aOfCre];
			for (int i = 0; i < aOfCre; i++) {
				oldGen[i] = new Creature(aOfChorm);
				newGen[i] = new Creature(aOfChorm);
			}
			firstRun = false;
		}
		
		can.getGraphicsContext2D().setStroke(Color.BLUE);
		for (int i = 0; i < aOfCre; i++) {
			can.getGraphicsContext2D().strokeOval((oldGen[i].x+1)*10, (-(-Math.abs(Math.sin(2*oldGen[i].x)+oldGen[i].x-25)+25))*10+490, 4, 4);
		}
		//sort
		int n = oldGen.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (oldGen[j].y < oldGen[j+1].y) 
                {
                	Creature temp = oldGen[j]; 
                    oldGen[j] = oldGen[j+1]; 
                    oldGen[j+1] = temp; 
                }
        
        
        //selection
        int chance = 0, numOfNewGen = 0;
        for (int i = 0; i < aOfCre; i++) {
        	if (chance < r.nextInt(100)) {
        		newGen[numOfNewGen] = oldGen[i];
        		numOfNewGen++;
        	}	
        	chance += 200/aOfCre;
        	
        }
        sum += numOfNewGen;
        //System.out.println(chance + " " + numOfNewGen);

        
        //crossover
        int fen1[] = new int[8], fen2[] = new int[8]; 
        for (int i = 0; i < aOfCre/4; i +=2) {
        	for (int k = 0; k < 6; k++) {
        		fen1[k] = newGen[i].fen[k];
        		fen2[k] = newGen[i+1].fen[k];
        	}
        	for (int k = 6; k < 8; k++) {
        		fen1[k] = newGen[i+1].fen[k];
        		fen2[k] = newGen[i].fen[k];
        	}
        	newGen[i+aOfCre/4].fen = fen1;
        	newGen[i+aOfCre/4].x = 0;
        	for (int j = 0, k = 128; j < 8; j++, k/=2) 
    			if (fen1[j] == 1)
    				newGen[i+aOfCre/4].x += k;
        	newGen[i+aOfCre/4].y = -Math.abs(Math.sin(2*newGen[i+aOfCre/4].x)+newGen[i+aOfCre/4].x-25)+25;
        	
        	newGen[i+1+aOfCre/4].fen = fen2;
        	newGen[i+1+aOfCre/4].x = 0;
        	for (int j = 0, k = 128; j < 8; j++, k/=2) 
    			if (fen2[j] == 1)
    				newGen[i+1+aOfCre/4].x += k;
        	newGen[i+1+aOfCre/4].y = -Math.abs(Math.sin(2*newGen[i+1+aOfCre/4].x)+newGen[i+1+aOfCre/4].x-25)+25;
        }
        
        for (int i = 0; i < aOfCre/2; i+=2) {
        	for (int k = 0; k < 6; k++) {
        		fen1[k] = newGen[i].fen[k];
        		fen2[k] = newGen[i+1].fen[k];
        	}
        	for (int k = 6; k < 8; k++) {
        		fen1[k] = newGen[i+1].fen[k];
        		fen2[k] = newGen[i].fen[k];
        	}
        	
        	newGen[i+aOfCre/2].fen = fen1;
        	newGen[i+aOfCre/2].x = 0;
        	for (int j = 0, k = 128; j < 8; j++, k/=2) 
    			if (fen1[j] == 1)
    				newGen[i+aOfCre/2].x += k;
        	newGen[i+aOfCre/2].y = -Math.abs(Math.sin(2*newGen[i+aOfCre/2].x)+newGen[i+aOfCre/2].x-25)+25;
        	
        	newGen[i+1+aOfCre/2].fen = fen2;
        	newGen[i+1+aOfCre/2].x = 0;
        	for (int j = 0, k = 128; j < 8; j++, k/=2) 
    			if (fen2[j] == 1)
    				newGen[i+1+aOfCre/2].x += k;
        	newGen[i+1+aOfCre/2].y = -Math.abs(Math.sin(2*newGen[i+1+aOfCre/2].x)+newGen[i+1+aOfCre/2].x-25)+25;
        }        
        
        System.out.println("/////////////" + _numberOfGenerations);
        for (int i = 0; i < aOfCre; i++) {
			System.out.print(oldGen[i].x + " ");
			for (int k = 0; k < 8; k++)
				System.out.print(oldGen[i].fen[k]);
			
			System.out.print(" " + newGen[i].x + " ");
			for (int k = 0; k < 8; k++)
				System.out.print(newGen[i].fen[k]);
			System.out.println();
		}
        
        //mutation
        try {
        	aOfMut = Integer.parseInt(tfAOfMut.getText());
        } catch (Exception exc) {}
        for(int mu = 0 ; mu < aOfMut ; mu++) {
	        int numOfMut = r.nextInt(aOfCre), numOfFen = r.nextInt(6)+2;
	        if (newGen[numOfMut].fen[numOfFen] == 1)
	        	newGen[numOfMut].fen[numOfFen] = 0;
	        else 
	        	newGen[numOfMut].fen[numOfFen] = 1;
	        
	        newGen[numOfMut].x = 0;
	    	for (int j = 0, k = 128; j < 8; j++, k/=2) 
				if (newGen[numOfMut].fen[j] == 1)
					newGen[numOfMut].x += k;
	    	newGen[numOfMut].y = -Math.abs(Math.sin(2*newGen[numOfMut].x)+newGen[numOfMut].x-25)+25;
        }
        //show result
        oldGen = newGen;
        can.getGraphicsContext2D().setStroke(Color.RED);
		for (int i = 0; i < aOfCre; i++) {
			can.getGraphicsContext2D().strokeOval((oldGen[i].x+1)*10, (-(-Math.abs(Math.sin(2*oldGen[i].x)+oldGen[i].x-25)+25))*10+490, 2, 2);
		}
		numberOfGen++;
	}
	
	public void grid() {
		can.getGraphicsContext2D().clearRect(0, 0, 500, 500);
		can.getGraphicsContext2D().setStroke(Color.BLACK);
		
		can.getGraphicsContext2D().setLineWidth(1);
		can.getGraphicsContext2D().strokeLine(0, 490, 500, 490);
		can.getGraphicsContext2D().strokeLine(10, 0, 10, 500);
		
		can.getGraphicsContext2D().setLineWidth(0.1);
		for (int i = 1; i < 49; i++)
			can.getGraphicsContext2D().strokeLine(0, i*10, 500, i*10);
		for (int i = 2; i < 50; i++)
			can.getGraphicsContext2D().strokeLine(i*10, 0, i*10, 500);
		
		can.getGraphicsContext2D().setLineWidth(0.3);
		for (int i = 6; i < 50; i+=5)
			if (i != 0)
				can.getGraphicsContext2D().strokeText("" + (i-1), (i)*10-5, 495);
		for (int i = -25, j = 50; i < 51; i+=5, j-=5) 
			if (j != 0)
				can.getGraphicsContext2D().strokeText("" + j, 5, (i+25)*10-5);
		
		can.getGraphicsContext2D().setLineWidth(1);
        //-|sin(2x)+x-25 |+25
		can.getGraphicsContext2D().setStroke(Color.GREEN);
		for (double i = 0; i < 50.1; i += 0.1)
			can.getGraphicsContext2D().strokeLine((i+1)*10, (-(-Math.abs(Math.sin(2*i)+i-25)+25))*10+490, (i+1.1)*10, (-(-Math.abs(Math.sin(2*(i+0.1))+i+0.1-25)+25))*10+490 );

	}
	
	public void start(Stage primaryStage) throws Exception {
		group = new Group();
		
		hb = new HBox();
		hb.setPrefSize(700, 500);
		
		vb = new VBox();
		vb.setPrefSize(200, 500);
		
		lAmountOfGen = new Label("Enter amount of generations:");				
		tfAmountOfGen = new TextField("");
		lAOfMut = new Label("Enter amount of creatures to mutate:");				
		tfAOfMut = new TextField("");
		bstep = new Button("One step");
		ball = new Button("Run all gens");
		
		can = new Canvas(500, 500);
		grid();
		vb.getChildren().addAll(lAmountOfGen, tfAmountOfGen, lAOfMut, tfAOfMut, bstep, ball);
		hb.getChildren().addAll(can, vb);
		
		bstep.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				step(numberOfGen);
			}
		});
		ball.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				allGens();
			}
		});
		
		group.getChildren().add(hb);
		
		scene = new Scene(group, 700, 500);
		primaryStage.getIcons().add(new Image(new FileInputStream("C:\\J\\ChNU_Java\\AI\\_10v2\\img\\BTNReplenishHealth.png")));
		primaryStage.setTitle("Genetic algorithm");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
/*
NumberAxis xAxis = new NumberAxis();
NumberAxis yAxis = new NumberAxis();
LineChart<Number, Number> lineChart= new LineChart<Number,Number>(xAxis,yAxis);
XYChart.Series series = new XYChart.Series();

for (double i =-25; i < 25.1; i += 0.1) {
	series.getData().add(new XYChart.Data(i,-Math.abs(Math.sin(2*i)+i-10)+10));
	series.dataProperty().s
}
lineChart.getData().add(series);
*/