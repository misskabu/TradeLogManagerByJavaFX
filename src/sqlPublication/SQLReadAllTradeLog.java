/**
 * 
 */
package sqlPublication;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.ISQLExecutable;

/**
 * @author misskabu
 * TRADE_LOG TABLE から　読み出したデータを表に表示するためのSQL
 */
public class SQLReadAllTradeLog implements ISQLExecutable {

	/* (非 Javadoc)
	 * @see application.ISQLExcutable#excuteQuery()
	 */
	public List<TradeLogRecord> recordList;
	final String SQL = "SELECT * FROM TRADE_LOG LEFT JOIN BOOK_INFO ON TRADE_LOG.SECURITIES_CODE = BOOK_INFO.SECURITIES_CODE ORDER BY ID DESC";
	@Override
	public void executeQuery(Connection con) {
		this.recordList = new ArrayList<TradeLogRecord>();
		System.out.println("executeQuery");
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(this.SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
			try {
				while(rs.next()){
					Integer id=rs.getInt("ID");
					Date date=rs.getDate("TRADE_DATE");
					Integer code=rs.getInt("SECURITIES_CODE");
					String name=rs.getString("BOOK_NAME");
					String marcket = rs.getString("MARCKET");
					Integer purchasePrice=rs.getInt("PURCHASE_PRICE");
					Integer purchaseNum=rs.getInt("PURCHASE_NUMBER");
					Integer sellingPrice=rs.getInt("SELLING_PRICE");
					Integer sellingNum=rs.getInt("SELLING_NUMBER");
					System.out.println(id+date.toString()+code+name+purchasePrice+purchaseNum+sellingPrice+sellingNum);

					TradeLogRecord record = new TradeLogRecord(
							id,
							date.toString(),
							code,
							name,
							marcket,
							purchasePrice,
							purchaseNum,
							sellingPrice,
							sellingNum);
					recordList.add(record);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
}