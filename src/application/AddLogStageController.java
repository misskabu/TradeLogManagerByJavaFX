package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;
import sqlPublication.SQLAddTradeLog;
import sqlPublication.SQLReadAllBookInfo;

public class AddLogStageController implements Initializable{

	@FXML DatePicker datePicker;
	@FXML ComboBox<String> codeCombo;
	@FXML TextField purchasePriceText;
	@FXML TextField purchaseNumText;
	@FXML TextField sellingPriceText;
	@FXML TextField sellingNumText;
	//@FXML TextField plText;
	@FXML TextArea memoArea;
	
	//ここにテキストボックスに入力されたデータを吸い上げてデータベースに書き込む処理を書く
	@FXML protected void onAddButtonClick(ActionEvent evt){
	
		System.out.println("AddButton was Clicked from AddLogStage");
		System.out.println(this.datePicker.getValue());
		System.out.println(codeCombo.getValue());
		System.out.println(purchasePriceText.getText());
		
		String code = this.codeCombo.getValue().substring(1,5);
		System.out.println("code="+code);
		ISQLExecutable sqlExecutable= 
				new SQLAddTradeLog(	
									java.sql.Date.valueOf(this.datePicker.getValue()),
									Integer.parseInt(code),
									Integer.parseInt(this.purchasePriceText.getTextFormatter().getValue().toString()),
									Integer.parseInt(this.purchaseNumText.getText()),
									Integer.parseInt(this.sellingPriceText.getTextFormatter().getValue().toString()), 
									Integer.parseInt(this.sellingNumText.getText()),
									0,//PLTextField is not implemented.so I use direct number 0　for now.
									this.memoArea.getText()
									);
    	@SuppressWarnings("unused")
		H2DBConnector mysqlConnector = new H2DBConnector(sqlExecutable);
		TradeLogTableStageController controller;
		controller = Main.tradeLogTableStageController;
		controller.printRecord();
	}

	
	/**
	 * This function initialize context of ComboBox for inputing BookCode.
	 * @return
	 */
	private ArrayList<String> getSecuritiesCodeList(){
		ArrayList<String> list = new ArrayList<>();
		SQLReadAllBookInfo sqlReadAllbookInfo = new SQLReadAllBookInfo();
		@SuppressWarnings("unused")
		H2DBConnector connector = new H2DBConnector(sqlReadAllbookInfo);
		sqlReadAllbookInfo.recordList.forEach(e->{
			String temp;
			temp = "["+ e.securitiesCodeProperty().get() +"] "+ e.bookNameProperty().get();
			list.add(temp);
		});
		return list;
  
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.codeCombo.getItems().addAll((this.getSecuritiesCodeList()));
		this.setNumberStringFormatter(this.purchaseNumText);
		this.setNumberStringFormatter(this.purchasePriceText);
		this.setNumberStringFormatter(this.sellingNumText);
		this.setNumberStringFormatter(this.sellingPriceText);
	}
	private void setNumberStringFormatter(TextField textField){
		NumberStringConverter converter = new NumberStringConverter();
		TextFormatter<Number> formatter = new TextFormatter<>(converter);
		textField.setTextFormatter(formatter);
		formatter.setValue(0);
	}

}
