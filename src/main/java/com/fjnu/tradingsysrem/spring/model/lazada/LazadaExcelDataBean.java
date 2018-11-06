package com.fjnu.tradingsysrem.spring.model.lazada;

import com.fjnu.tradingsysrem.lazada.platform.bean.OrderBean;
import com.fjnu.tradingsysrem.spring.utils.CalculateUtils;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by LCC on 2017/11/3.
 */
public class LazadaExcelDataBean {

    /** 表格列名 */
    public final static String[] EXCEL_LABEL_TITLES = {
            "系统订单ID", "订单日期", "Lazada平台订单号", "Status",
            "订单收金额（外币）", "订单收金额（人民币）", "汇率", "汇率日期",
            "海外快递单号", "海外快递运费（人民币）", "总采购成本", "销售成本（采购成本+海外快递运费）",
            "利润（订单收金额*0.85-销售成本）", "利润率（利润/销售成本）", "系统订单详细ID", "Lazada平台订单详细ID",
            "SKU", "商品描述", "商品单价（外币）", "商品快照地址",
            "系统采购订单ID", "关联SKU", "采购商品描述", "采购总价（包含国内运费）",
            "采购数量", "采购网址快照", "采购商品总重量（克）", "采购快递单号"};

    /** 订单信息栏数 */
    public static final int ORDER_COLUMN_NUM = 14;
    /** 订单详细信息栏数 */
    public static final int ORDER_ITEMS_COLUMN_NUM = 6;
    /** 订单信息栏数 */
    public static final int PURCHASE_COLUMN_NUM = 8;

    /** 订单数据 */
    private String[] orderDataArrays = new String[ORDER_COLUMN_NUM];
    /** 订单详细数据 */
    private String[][] orderItemsDataArrays;
    /** 采购订单数据 */
    private String[][] purchaseOrderDataArrays;
    /** 订单详细数量 */
    private int orderItemsSum;
    /** 采购订单数量 */
    private int purchaseOrderSum;

    public LazadaExcelDataBean(LazadaOrderInfo lazadaOrderInfo) {
        if (null == lazadaOrderInfo) {
            Arrays.fill(orderDataArrays, "");
            return;
        }

        orderDataArrays[0] = lazadaOrderInfo.getId();
        orderDataArrays[1] = DateUtils.dateToStr(lazadaOrderInfo.getCreateTime(), "yyyy-MM-dd");
        orderDataArrays[2] = String.valueOf(lazadaOrderInfo.getLazadaOrderId());
        orderDataArrays[3] = lazadaOrderInfo.getOrderStatus();

        orderDataArrays[4] = String.valueOf(lazadaOrderInfo.getPrice());
        orderDataArrays[5] = String.valueOf(CalculateUtils.convertPriceToRMB(lazadaOrderInfo.getPrice(),
                lazadaOrderInfo.getExchangeRate().getExchangeRate()));
        orderDataArrays[6] = String.valueOf(lazadaOrderInfo.getExchangeRate().getExchangeRate());
        orderDataArrays[7] = DateUtils.dateToStr(lazadaOrderInfo.getExchangeRate().getDate(), "yyyy-MM-dd");

        orderDataArrays[8] = lazadaOrderInfo.getOverseasExpressNumber();
        orderDataArrays[9] = String.valueOf(lazadaOrderInfo.getOverseasExpressPrice());
        // 计算采购总成本
        Set<PurchaseOrderInfo> purchaseOrderInfoSet = lazadaOrderInfo.getPurchaseOrderInfoSet();
        purchaseOrderSum = purchaseOrderInfoSet.size();
        float purchaseTotalPrice = 0f;
        if (purchaseOrderSum != 0) {
            for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoSet) {
                purchaseTotalPrice += purchaseOrderInfo.getTotalPrice();
            }
        }
        orderDataArrays[10] = String.format("%.2f", purchaseTotalPrice);
        // 计算销售成本（采购成本+海外快递运费）
        float totalCostPrice = purchaseTotalPrice + lazadaOrderInfo.getOverseasExpressPrice();
        orderDataArrays[11] = String.format("%.2f", totalCostPrice);

        // 计算利润（订单收金额*0.85-销售成本）
        float orderTotalPrice = 0.0f;
        if (OrderBean.Status.Canceled.getStatus().equals(lazadaOrderInfo.getOrderStatus())
                || OrderBean.Status.Returned.getStatus().equals(lazadaOrderInfo.getOrderStatus())) {
            // 取消或退货的订单没有利润
        } else {
            orderTotalPrice = CalculateUtils.convertPriceToRMB(lazadaOrderInfo.getPrice(),
                    lazadaOrderInfo.getExchangeRate().getExchangeRate());
        }
        float profit = CalculateUtils.multiply(orderTotalPrice, 0.85f) - totalCostPrice;
        orderDataArrays[12] = String.format("%.2f", profit);
        // 计算利润率（利润/销售成本）
        String profitRate = "";
        if (totalCostPrice != 0f) {
            profitRate = String.format("%d%%", (int) (CalculateUtils.divide(profit, totalCostPrice) * 100));
        }
        orderDataArrays[13] = profitRate;

        // 初始化订单详细数据
        Set<LazadaOrderItemsInfo> lazadaOrderItemsInfoSet = lazadaOrderInfo.getLazadaOrderItemsInfoSet();
        orderItemsSum = lazadaOrderItemsInfoSet.size();
        orderItemsDataArrays = new String[orderItemsSum][ORDER_ITEMS_COLUMN_NUM];
        int index = 0;
        for (LazadaOrderItemsInfo lazadaOrderItemsInfo : lazadaOrderItemsInfoSet) {
            orderItemsDataArrays[index][0] = lazadaOrderItemsInfo.getId();
            orderItemsDataArrays[index][1] = String.valueOf(lazadaOrderItemsInfo.getLazadaOrderItemId());
            orderItemsDataArrays[index][2] = lazadaOrderItemsInfo.getSku();
            orderItemsDataArrays[index][3] = lazadaOrderItemsInfo.getName();
            orderItemsDataArrays[index][4] = String.valueOf(lazadaOrderItemsInfo.getItemPrice());
            orderItemsDataArrays[index][5] = lazadaOrderItemsInfo.getOrderItemUrl();
            index++;
        }

        // 初始化采购订单数据
        purchaseOrderDataArrays = new String[purchaseOrderSum][PURCHASE_COLUMN_NUM];
        index = 0;
        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoSet) {
            purchaseOrderDataArrays[index][0] = purchaseOrderInfo.getId();
            purchaseOrderDataArrays[index][1] = purchaseOrderInfo.getLazadaOrderItemsInfo().getSku();
            purchaseOrderDataArrays[index][2] = purchaseOrderInfo.getDescription();
            purchaseOrderDataArrays[index][3] = String.valueOf(purchaseOrderInfo.getTotalPrice());
            purchaseOrderDataArrays[index][4] = String.valueOf(purchaseOrderInfo.getItemTotalMunber());
            purchaseOrderDataArrays[index][5] = purchaseOrderInfo.getOrderUrl();
            purchaseOrderDataArrays[index][6] = String.valueOf(purchaseOrderInfo.getWeight());
            purchaseOrderDataArrays[index][7] = purchaseOrderInfo.getOrderExpressNumber();
            index++;
        }
    }

    public int getOrderItemsSum() {
        return orderItemsSum;
    }

    public int getPurchaseOrderSum() {
        return purchaseOrderSum;
    }

    public String[] getOrderDataArrays() {
        return orderDataArrays;
    }

    public String[][] getOrderItemsDataArrays() {
        return orderItemsDataArrays;
    }

    public String[][] getPurchaseOrderDataArrays() {
        return purchaseOrderDataArrays;
    }
}
