package com.fjnu.tradingsysrem.spring.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by LCC on 2018/8/13.
 */
public interface LazadaDataAnalysisService {

    /**
     * 生成excel表格
     *
     * @param shopId    店铺id
     * @param beginTime 数据起始时间 默认格式 yyyy-MM-dd HH:mm:ss
     * @param endTime   数据结束时间 默认格式 yyyy-MM-dd HH:mm:ss
     * @return 没有数据时返回null
     */
    HSSFWorkbook generateExcelData(String shopId, String beginTime, String endTime);

}
