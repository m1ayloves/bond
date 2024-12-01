package cyou.mayloves.bond.handler;

import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class CellStyleHandler extends AbstractCellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (context.getHead()) { // 设置表头样式
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setBold(true);
            headStyle.setWriteFont(headFont);

            Cell cell = context.getCell();
            CellStyle cellStyle = context.getWriteWorkbookHolder().getWorkbook().createCellStyle();
            cellStyle.setFont(context.getWriteWorkbookHolder().getWorkbook().createFont());
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
        }
    }
}
