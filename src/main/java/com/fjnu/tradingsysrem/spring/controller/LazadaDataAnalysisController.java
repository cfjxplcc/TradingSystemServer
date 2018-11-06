package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.service.LazadaDataAnalysisService;
import com.fjnu.tradingsysrem.spring.service.LazadaShopInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by LCC on 2018/8/13.
 */
@RestController
public class LazadaDataAnalysisController {

    @Autowired
    private LazadaDataAnalysisService lazadaDataAnalysisService;
    @Autowired
    private LazadaShopInfoService lazadaShopInfoService;

    @GetMapping("/lazada/data-analysis/generate-excel")
    public void generateExcel(@RequestParam("shopId") String shopId,
                              @RequestParam("beginTime") String beginTime,
                              @RequestParam("endTime") String endTime,
                              HttpServletResponse response) throws IOException {
        String shopName = lazadaShopInfoService.get(shopId).getShopName();
        String fileName = shopName + "(" + beginTime.split(" ")[0].replaceAll("-", "")
                + "-" + endTime.split(" ")[0].replaceAll("-", "") + ")";
        HSSFWorkbook workbook = lazadaDataAnalysisService.generateExcelData(shopId, beginTime, endTime);
        if (workbook == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            response.getWriter().print("无数据");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        }
    }
}
