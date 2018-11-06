/**
 * cron表达式详解：
 * <p>
 * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
 * <p>
 * 按顺序依次为
 * 1秒（0~59）
 * 2分钟（0~59）
 * 3小时（0~23）
 * 4天（0~31）
 * 5月（0~11）
 * 6星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
 * 7.年份（1970－2099）
 * 其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?.
 * 0 0 10,14,16**?每天上午10点，下午2点，4点
 * 0 0/30 9-17**?朝九晚五工作时间内每半小时
 * 0 0 12?*WED 表示每个星期三中午12点
 * "0 0 12 * * ?"每天中午12点触发
 * "0 15 10 ? * *"每天上午10:15触发
 * "0 15 10 * * ?"每天上午10:15触发
 * "0 15 10 * * ? *"每天上午10:15触发
 * "0 15 10 * * ? 2005"2005年的每天上午10:15触发
 * "0 * 14 * * ?"在每天下午2点到下午2:59期间的每1分钟触发
 * "0 0/5 14 * * ?"在每天下午2点到下午2:55期间的每5分钟触发
 * "0 0/5 14,18 * * ?"在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
 * "0 0-5 14 * * ?"在每天下午2点到下午2:05期间的每1分钟触发
 * "0 10,44 14 ? 3 WED"每年三月的星期三的下午2:10和2:44触发
 * "0 15 10 ? * MON-FRI"周一至周五的上午10:15触发
 * "0 15 10 15 * ?"每月15日上午10:15触发
 * "0 15 10 L * ?"每月最后一日的上午10:15触发
 * "0 15 10 ? * 6L"每月的最后一个星期五上午10:15触发
 * "0 15 10 ? * 6L 2002-2005"2002年至2005年的每月的最后一个星期五上午10:15触发
 * "0 15 10 ? * 6#3"每月的第三个星期五上午10:15触发
 * 有些子表达式能包含一些范围或列表
 * 例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT”
 * “*”字符代表所有可能的值
 * “/”字符用来指定数值的增量
 * 例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟
 * 在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样
 * “？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值
 * 当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”
 * “L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写
 * 如果在“L”前有具体的内容，它就具有其他的含义了。例如：“6L”表示这个月的倒数第６天
 * 注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题
 * W 字符代表着平日(Mon-Fri)，并且仅能用于日域中。它用来指定离指定日的最近的一个平日。大部分的商业处理都是基于工作周的，所以 W 字符可能是非常重要的。
 * 例如，日域中的 15W 意味着"离该月15号的最近一个平日。"假如15号是星期六，那么 trigger 会在14号(星期五)触发，因为星期四比星期一离15号更近。
 * C：代表“Calendar”的意思。它的意思是计划所关联的日期，如果日期没有被关联，则相当于日历中所有日期。例如5C在日期字段中就相当于日历5日以后的第一天。1C在星期字段中相当于星期日后的第一天。
 * 字段 允许值 允许的特殊字符
 * 秒 0-59,
 * 分 0-59,
 * 小时 0-23,
 * 日期 1-31,-*?/L W C
 * 月份 1-12或者 JAN-DEC,
 * 星期 1-7或者 SUN-SAT,-*?/L C #
 * 年（可选）           留空,1970-2099,
 */

package com.fjnu.tradingsysrem.spring.service.scheduledtasks;

import com.fjnu.tradingsysrem.lazada.platform.LazadaApiManager;
import com.fjnu.tradingsysrem.lazada.platform.bean.OrderBean;
import com.fjnu.tradingsysrem.lazada.platform.bean.OrderItemBean;
import com.fjnu.tradingsysrem.spring.dao.ExchangeRateDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaOrderItemsInfoDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.service.LazadaOrderInfoService;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;
import com.fjnu.tradingsysrem.spring.utils.TextUtils;
import com.lazada.lazop.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LCC on 2018/3/20.
 */
@Component
public class LazadaOrderScheduledTasks {

    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;
    @Autowired
    private LazadaOrderInfoDao lazadaOrderInfoDao;
    @Autowired
    private LazadaOrderItemsInfoDao lazadaOrderItemsInfoDao;
    @Autowired
    private ExchangeRateDao exchangeRateDao;
    @Autowired
    private LazadaOrderInfoService lazadaOrderInfoService;

    /**
     * 每个小时做一次,根据数据库中配置的店铺，抓取订单状态为pending的订单，添加本地数据库中没有的订单信息至数据库
     */
    @Scheduled(cron = "0 10 * * * *")//每个小时的整点10分执行一次
//    @Scheduled(fixedRate = 60 * 60 * 1000)//每间隔1小时执行一次
    @Transactional
    public void updatePendingLazadaOrderInfo() {
        System.out.println("-------------> updatePendingLazadaOrderInfo task begin <-------------");
        long methodBeginTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);

        long beginTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("一个小时10分钟前的时间：" + df.format(beginTime));
        System.out.println("当前的时间：" + df.format(endTime));

        LazadaApiManager lazadaApiManager = LazadaApiManager.getInstance();

        List<LazadaShopInfo> lazadaShopInfoList = lazadaShopInfoDao.findAllByOrderByShopNameAsc();
        for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
            if (TextUtils.isEmpty(lazadaShopInfo.getAccessToken())) {
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 店铺名称：" + lazadaShopInfo.getShopName() + " 未含有AccessToken");
                continue;
            }
            lazadaApiManager.initClient(lazadaShopInfo);

            try {
                List<OrderBean> orderList = lazadaApiManager.getOrders(OrderBean.Status.Pending, beginTime, endTime);
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 店铺名称：" + lazadaShopInfo.getShopName() + " 获取到状态为Pending的订单总数为" + orderList.size());
                for (OrderBean order : orderList) {
                    //先从数据库中根据lazada平台的orderId查询是否数据库中包含该数据
                    LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findByLazadaOrderId(order.getOrder_id());
                    if (lazadaOrderInfo != null) {
                        continue;
                    }
                    lazadaOrderInfo = new LazadaOrderInfo(lazadaShopInfo, order);
                    ExchangeRate exchangeRate = exchangeRateDao.findFirstByCountryCodeOrderByDateDesc(lazadaOrderInfo.getLazadaShopInfo().getCountryCode());
                    if (exchangeRate != null) {
                        lazadaOrderInfo.setExchangeRate(exchangeRate);
                    }
                    lazadaOrderInfo = lazadaOrderInfoDao.save(lazadaOrderInfo);

                    List<OrderItemBean> orderItemList = lazadaApiManager.getOrderItems(order.getOrder_id());
                    for (OrderItemBean orderItem : orderItemList) {
                        lazadaOrderItemsInfoDao.save(new LazadaOrderItemsInfo(lazadaOrderInfo, orderItem));
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }

        long methodEndTime = System.currentTimeMillis();
        System.out.println("updatePendingLazadaOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }

    /**
     * 每4个小时执行一次，同步同一天内的lazada服务器的数据
     */
    @Scheduled(cron = "0 0 0/4 * * *")//每4个小时执行一次
    @Transactional
    public void updateAllLazadaOrderInfo() {
        System.out.println("-------------> updateAllLazadaOrderInfo task begin <-------------");
        long methodBeginTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);

        long beginTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("开始时间：" + df.format(beginTime));
        System.out.println("结束时间：" + df.format(endTime));

        LazadaApiManager lazadaApiManager = LazadaApiManager.getInstance();

        List<LazadaShopInfo> lazadaShopInfoList = lazadaShopInfoDao.findAllByOrderByShopNameAsc();
        for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
            if (TextUtils.isEmpty(lazadaShopInfo.getAccessToken())) {
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 店铺名称：" + lazadaShopInfo.getShopName() + " 未含有AccessToken");
                continue;
            }
            lazadaApiManager.initClient(lazadaShopInfo);

            try {
                List<OrderBean> orderList = lazadaApiManager.getOrders(null, beginTime, endTime);
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 店铺名称：" + lazadaShopInfo.getShopName() + " 获取全部状态的订单总数为" + orderList.size());
                for (OrderBean order : orderList) {
                    //先从数据库中根据lazada平台的orderId查询是否数据库中包含该数据
                    LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findByLazadaOrderId(order.getOrder_id());
                    if (lazadaOrderInfo != null) {
                        //暂时只更新订单信息（状态、备注、UpdatedAt），不更新详细信息
                        lazadaOrderInfo.setOrderStatus(order.getStatuses().get(0));
                        lazadaOrderInfo.setLazadaOrderRemarks(order.getRemarks());
                        if (!TextUtils.isEmpty(order.getUpdated_at())) {
                            try {
                                lazadaOrderInfo.setLastUpdateTime(DateUtils.lazadaResponseDateStrToSqlDate(order.getUpdated_at()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
                    } else {
                        //如果不存在订单信息，则保存订单信息，并且获取详细信息
                        lazadaOrderInfo = new LazadaOrderInfo(lazadaShopInfo, order);
                        ExchangeRate exchangeRate = exchangeRateDao.findFirstByCountryCodeOrderByDateDesc(lazadaOrderInfo.getLazadaShopInfo().getCountryCode());
                        if (exchangeRate != null) {
                            lazadaOrderInfo.setExchangeRate(exchangeRate);
                        }
                        lazadaOrderInfo = lazadaOrderInfoDao.save(lazadaOrderInfo);

                        List<OrderItemBean> orderItemList = lazadaApiManager.getOrderItems(order.getOrder_id());
                        for (OrderItemBean orderItem : orderItemList) {
                            lazadaOrderItemsInfoDao.save(new LazadaOrderItemsInfo(lazadaOrderInfo, orderItem));
                        }
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }

        long methodEndTime = System.currentTimeMillis();
        System.out.println("updateAllLazadaOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }

    /**
     * 每天02:30 同步七天内的lazada服务器的数据
     */
    @Scheduled(cron = "0 30 2 * * *")
    @Transactional
    public void updateOneWeekLazadaOrderInfo() {
        System.out.println("-------------> updateOneWeekLazadaOrderInfo task begin <-------------");

        long methodBeginTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);

        long beginTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("开始时间：" + df.format(beginTime));
        System.out.println("结束时间：" + df.format(endTime));

        lazadaOrderInfoService.syncLazadaOrderInfo(df.format(beginTime), df.format(endTime));

        long methodEndTime = System.currentTimeMillis();
        System.out.println("updateOneWeekLazadaOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }

    /**
     * 每周天03:30 同步上周七天内的lazada服务器的数据
     */
    @Scheduled(cron = "0 30 3 * * SUN")
    @Transactional
    public void updateLastOneWeekLazadaOrderInfo() {
        System.out.println("-------------> updateLastOneWeekLazadaOrderInfo task begin <-------------");

        long methodBeginTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -14);

        long beginTime = calendar.getTimeInMillis();

        calendar.add(Calendar.DATE, 7);
        long endTime = calendar.getTimeInMillis();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("开始时间：" + df.format(beginTime));
        System.out.println("结束时间：" + df.format(endTime));

        lazadaOrderInfoService.syncLazadaOrderInfo(df.format(beginTime), df.format(endTime));

        long methodEndTime = System.currentTimeMillis();
        System.out.println("updateLastOneWeekLazadaOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }

}
