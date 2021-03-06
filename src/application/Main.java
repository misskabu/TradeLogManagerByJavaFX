package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sqlPublication.SQLCreateBookInfoTable;
import sqlPublication.SQLCreateTradeLogTable;


public class Main extends Application {

	public static TradeLogTableStageController tradeLogTableStageController;
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TradeLogTableStage.fxml"));
			Scene scene = new Scene((BorderPane)fxmlLoader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setWidth(1100);
			primaryStage.setX(primaryStage.getX());
			primaryStage.setTitle("TradeLogTableStage");
			tradeLogTableStageController = fxmlLoader.getController();
		} 

		catch(Exception e) {
			e.printStackTrace();
			ISQLExecutable sqlCreateTradeLogTable= new SQLCreateTradeLogTable();
	    	@SuppressWarnings("unused")
			H2DBConnector Connector = new H2DBConnector(sqlCreateTradeLogTable);
	    	ISQLExecutable sqlCreateBookInfoTable = new SQLCreateBookInfoTable();
	    	@SuppressWarnings("unused")
			H2DBConnector Connector2 = new H2DBConnector(sqlCreateBookInfoTable);
	    	Platform.exit();
	
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
