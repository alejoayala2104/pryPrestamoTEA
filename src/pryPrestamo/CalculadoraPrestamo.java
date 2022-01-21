package pryPrestamo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;



public class CalculadoraPrestamo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {		

		//LAYOUT
		//------------------------------------------------------------------------------------------
		Label lblTasaef = new Label("Tasa Efectiva Anual:");
		TextField txfTasaef = new TextField();
		
		Label lblNumMeses = new Label("Numero de meses:");
		TextField txfNumMeses = new TextField();
		
		Label lblCantPrest = new Label("Cantidad del préstamo:");
		TextField txfCantPrest = new TextField();
		
		Label lblMensualidad = new Label("Mensualidad:");
		TextField txfMensualidad = new TextField();
		txfMensualidad.setEditable(false);
		
		Label lblPagoTotal = new Label("Pago total:");
		TextField txfPagoTotal = new TextField();	
		txfPagoTotal.setEditable(false);
		
		Button btnCalcular = new Button("Calcular");
		btnCalcular.setMaxWidth(Double.MAX_VALUE);
		
		BorderPane root = new BorderPane();
		VBox left = new VBox(20,lblTasaef,lblNumMeses,lblCantPrest,lblMensualidad,lblPagoTotal);
		left.setAlignment(Pos.TOP_RIGHT);
		root.setLeft(left);
		root.setCenter(new VBox(13,txfTasaef,txfNumMeses,txfCantPrest,txfMensualidad,txfPagoTotal));
		BorderPane.setMargin(root.getLeft(), new Insets(10, 0, 0, 20));		
		BorderPane.setMargin(root.getCenter(), new Insets(5, 5, 10, 20));
		root.setBottom(new VBox(btnCalcular));
		//------------------------------------------------------------------------------------------
		
		btnCalcular.setOnAction(event ->{
			
			double tasaEA,tasaMensual,mensualidad,cantPrest,numMeses,pagoTotal;
			
			try{
			if(txfTasaef.getText().isEmpty() || txfCantPrest.getText().isEmpty() || txfNumMeses.getText().isEmpty()) {				
				Alert alertaSinCampos = new Alert(AlertType.ERROR);
				alertaSinCampos.setTitle("ERROR");
				alertaSinCampos.setHeaderText("Campos vacíos");
				alertaSinCampos.setContentText("Por favor rellene todos los campos necesarios.");				
				alertaSinCampos.showAndWait();
				txfMensualidad.clear();
				txfPagoTotal.clear();
				return;
			}
			
			if(txfTasaef.getText().equals("0") || txfNumMeses.getText().equals("0")) {
				
				Alert alertaNoCeros = new Alert(AlertType.ERROR);
				alertaNoCeros.setTitle("ERROR");
				alertaNoCeros.setHeaderText("División por cero");
				alertaNoCeros.setContentText("La TEA y el número de meses no pueden ser 0. Por favor ingrese un dato válido.");				
				alertaNoCeros.showAndWait();
				txfMensualidad.clear();
				txfPagoTotal.clear();
				return;
			}
			
			tasaEA = Double.parseDouble(txfTasaef.getText());
			cantPrest = Double.parseDouble(txfCantPrest.getText());
			numMeses = Double.parseDouble(txfNumMeses.getText());			

			//FORMULA
			//--------------------------------------------------------------------------------------
			tasaMensual = (Math.pow(tasaEA+1, 1.0/12.0)) - 1.0;	
			//System.out.println(tasaMensual);
			//mensualidad = cantPrest * ((tasaMensual*(Math.pow(1+tasaMensual, numMeses)))/((Math.pow(1+tasaMensual, numMeses)) - 1.0));			
//			mensualidad= (tasaMensual*cantPrest)/(1.0 - (Math.pow((1+tasaMensual),numMeses))); //Está mal.
			mensualidad= (tasaMensual*cantPrest)/(1.0 - (Math.pow((1+tasaMensual),-numMeses))); //Está mal.
			pagoTotal = mensualidad * numMeses;
			//--------------------------------------------------------------------------------------
			
			//RESULTADO
			//--------------------------------------------------------------------------------------			
			txfMensualidad.setText(FormatearResultados.formatear("$ ###,###.###", mensualidad));
			txfPagoTotal.setText(FormatearResultados.formatear("$ ###,###.###", pagoTotal));
			//--------------------------------------------------------------------------------------
			}catch(NumberFormatException e) {
				Alert alertaNoLetras = new Alert(AlertType.ERROR);
				alertaNoLetras.setTitle("ERROR");
				alertaNoLetras.setHeaderText("Datos inválidos");
				alertaNoLetras.setContentText("Por favor ingrese un número válido.");				
				alertaNoLetras.showAndWait();
				txfMensualidad.clear();
				txfPagoTotal.clear();
			}

		});

		Scene scene = new Scene(root);		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Calcular Préstamo");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
