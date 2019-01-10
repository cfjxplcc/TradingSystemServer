package com.fjnu.tradingsysrem.spring.service.lazada.imp;

import com.fjnu.tradingsysrem.spring.dao.LazadaOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaExcelDataBean;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaDataAnalysisService;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCC on 2018/8/13.
 */
@Service
@Transactional(readOnly = true)
public class LazadaDataAnalysisServiceImp implements LazadaDataAnalysisService {

    @Autowired
    private LazadaOrderInfoDao lazadaOrderInfoDao;
    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;

    @Override
    public HSSFWorkbook generateExcelData(String shopId, String beginTime, String endTime) {
        LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.getById(shopId);

        List<LazadaOrderInfo> lazadaOrderInfoList = lazadaOrderInfoDao.findAllByLazadaShopInfoAndCreateTimeBetweenOrderByCreateTime(lazadaShopInfo,
                DateUtils.strToSqlDate(beginTime), DateUtils.strToSqlDate(endTime));

        if (lazadaOrderInfoList.isEmpty()) {
            return null;
        }

        List<LazadaExcelDataBean> lazadaExcelDataBeanList = new ArrayList<>();
        for (LazadaOrderInfo lazadaOrderInfo : lazadaOrderInfoList) {
            lazadaExcelDataBeanList.add(new LazadaExcelDataBean(lazadaOrderInfo));
        }

        return generateExcel(lazadaShopInfo.getShopName(), lazadaExcelDataBeanList);
    }


    private HSSFWorkbook generateExcel(String sheetName, List<LazadaExcelDataBean> lazadaExcelDataBeanList) {
        // 创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle cellTitleStyle = workbook.createCellStyle();
        cellTitleStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        // 设置默认列宽
        sheet.setDefaultColumnWidth(25);

        // 声明列对象
        HSSFCell cell;
        // 创建标题
        for (int i = 0; i < LazadaExcelDataBean.EXCEL_LABEL_TITLES.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(LazadaExcelDataBean.EXCEL_LABEL_TITLES[i]);
            cell.setCellStyle(cellTitleStyle);
        }

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 填充内容
        int index = 0;
        for (LazadaExcelDataBean dataBean : lazadaExcelDataBeanList) {
            index++;
            int itemsSum = dataBean.getOrderItemsSum();
            int purchaseSum = dataBean.getPurchaseOrderSum();
            int maxNum = itemsSum > purchaseSum ? itemsSum : purchaseSum;

            row = sheet.createRow(index);
            // 填充LazadaOrderInfo数据
            String[] orderData = dataBean.getOrderDataArrays();
            for (int j = 0; j < LazadaExcelDataBean.ORDER_COLUMN_NUM; j++) {
                cell = row.createCell(j);
                cell.setCellValue(orderData[j]);
                cell.setCellStyle(cellStyle);
                handleHSSFCellFormat(workbook, cell);
            }

            String[][] orderItemsData = dataBean.getOrderItemsDataArrays();
            String[][] purchaseData = dataBean.getPurchaseOrderDataArrays();
            for (int z = 0; z < maxNum; z++) {
                if (z != 0) {
                    index++;
                    row = sheet.createRow(index);
                }
                // 填充订单信息信息
                if (z < itemsSum) {
                    for (int j = LazadaExcelDataBean.ORDER_COLUMN_NUM, k = 0; k < LazadaExcelDataBean.ORDER_ITEMS_COLUMN_NUM; k++, j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(orderItemsData[z][k]);
                        handleHSSFCellFormat(workbook, cell);
                    }
                }
                // 填充采购信息
                if (z < purchaseSum) {
                    for (int j = LazadaExcelDataBean.ORDER_COLUMN_NUM + LazadaExcelDataBean.ORDER_ITEMS_COLUMN_NUM, k = 0;
                         k < LazadaExcelDataBean.PURCHASE_COLUMN_NUM; k++, j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(purchaseData[z][k]);
                        handleHSSFCellFormat(workbook, cell);
                    }
                }
            }
            if (maxNum > 1) {
                // 合并单元格，参数说明：1：开始行 2：结束行  3：开始列 4：结束列
                int firstRow = index - maxNum + 1;
                for (int i = 0; i < LazadaExcelDataBean.ORDER_COLUMN_NUM; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(firstRow, index, i, i));
                }
            }
        }
        return workbook;
    }

    private void handleHSSFCellFormat(HSSFWorkbook workbook, HSSFCell cell) {
        String data = cell.getStringCellValue();
        HSSFCellStyle contextStyle = cell.getCellStyle();
        Boolean isNum = false;//data是否为数值型
        Boolean isInteger = false;//data是否为整数
        Boolean isPercent = false;//data是否为百分数
        if (data != null && data.length() <= 10) {
            //判断data是否为数值型
            isNum = data.matches("^(-?\\d+)(\\.\\d+)?$");
            //判断data是否为整数（小数部分是否为0）
            isInteger = data.matches("^[-\\+]?[\\d]*$");
            //判断data是否为百分数（是否包含“%”）
            isPercent = data.contains("%");
        }

        //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
        if (isNum && !isPercent) {
            HSSFDataFormat df = workbook.createDataFormat(); // 此处设置数据格式
            if (isInteger) {
                contextStyle.setDataFormat(df.getBuiltinFormat("#,#0"));//数据格式只显示整数
            } else {
                if (data.split("\\.")[1].length() <= 2) {
                    contextStyle.setDataFormat(df.getBuiltinFormat("#,##0.00"));//保留两位小数点
                    // 设置单元格内容为double类型
                    cell.setCellValue(Double.parseDouble(data));
                }
            }
            // 设置单元格格式
            cell.setCellStyle(contextStyle);
        }
    }
}
