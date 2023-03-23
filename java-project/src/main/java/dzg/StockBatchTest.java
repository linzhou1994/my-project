package dzg;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-22 15:12
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class StockBatchTest {


    public static void main(String[] args) {

        Map<String,BatchInfo> batchInfoMap = getBatchInfoMap();
        List<OrderInfo> orderInfoList = getOrderInfo();
        StringBuilder sql = new StringBuilder();
        for (OrderInfo orderInfo : orderInfoList) {
            BatchInfo batchInfo = batchInfoMap.get(orderInfo.getBatchNo());
            buildSql(sql, orderInfo, batchInfo);
        }

        System.out.println(sql.toString());

    }

    private static void buildSql(StringBuilder sql, OrderInfo orderInfo, BatchInfo batchInfo) {
        sql.append("update t_inventory_detail").append("\n")
                .append("set batch_no = '").append(orderInfo.getBatchNo()).append("',").append("\n")
                .append("storage_age = ").append(batchInfo.age).append(",").append("\n")
                .append("inbound_time='").append(batchInfo.batchDate).append("'").append("\n")
                .append("where apply_doc_no='").append(orderInfo.getOrderNo()).append("';").append("\n")
                .append("\n");

    }

    public static Map<String,BatchInfo> getBatchInfoMap(){
        String[] batchInfoStrs = getBatchInfoStr().split("\n");
        Map<String,BatchInfo> batchInfoMap = Maps.newHashMap();
        for (String batchInfoStr : batchInfoStrs) {
            String[] batchInfoArr = batchInfoStr.split(",");
            BatchInfo batchInfo = new BatchInfo();
            batchInfo.setBatchNo(batchInfoArr[2]);
            batchInfo.setBatchDate(batchInfoArr[3]);
            batchInfo.setAge(batchInfoArr[4]);
            if (batchInfoMap.containsKey(batchInfo.getBatchNo())) {
                BatchInfo oldBatchInfo = batchInfoMap.get(batchInfo.getBatchNo());
                assert Objects.equals(oldBatchInfo.getAge(), batchInfo.getAge());
            } else {
                batchInfoMap.put(batchInfo.getBatchNo(), batchInfo);
            }
        }
        return batchInfoMap;
    }

    public static String getBatchInfoStr(){
        return "KF020316-01,243,20230120,2023-01-20 20:40:28.896000,63\n" +
                "KF020316-01,126,20230215,2023-02-15 15:55:23.761000,37\n" +
                "KF200053-01CG,236,20221116,2022-11-16 23:25:35.424000,128\n" +
                "KF200053-01CG,40,20221124,2022-11-24 17:30:50.028000,120\n" +
                "KF200053-02SY,238,20230106,2023-01-06 17:50:55.313000,77\n" +
                "KF200053-02SY,2,20230215,2023-02-15 08:00:00,37\n" +
                "KF200106-02SY,135,20221229,2022-12-29 19:46:09.414000,85\n" +
                "KF200106-02SY,46,20230224,2023-02-24 20:35:23.741000,28\n" +
                "KF200106-03SY,238,20230201,2023-02-01 19:20:20.001000,51\n" +
                "KF200106-04SY,238,20230201,2023-02-01 17:15:19.722000,51\n" +
                "KF200107-02SY,16,20221223,2022-12-23 20:10:50.861000,91\n" +
                "KF200107-02SY,248,20230103,2023-01-03 17:16:21.926000,80\n" +
                "KF200140-02,171,20221209,2022-12-09 20:56:20.244000,105\n" +
                "KF200140-02,87,20230115,2023-01-15 19:45:19.423000,68\n" +
                "KF200140-02,1,20230215,2023-02-15 08:00:00,37\n" +
                "KF200146-02SY,67,20221122,2022-11-22 00:55:36.501000,122\n" +
                "KF200146-02SY,3,20221124,2022-11-24 20:46:03.794000,120\n" +
                "KF200146-02SY,200,20230103,2023-01-03 18:12:01.115000,80\n" +
                "KF200157-02SY,41,20221015,2022-10-15 02:00:58.417000,160\n" +
                "KF200157-02SY,200,20221112,2022-11-12 08:30:13.043000,132\n" +
                "KF200157-02SY,1,20221207,2022-12-07 09:00:00,107\n" +
                "KF330016-01,133,20230223,2023-02-23 21:20:13.829000,29\n" +
                "WFKF170054,304,20230227,2023-02-27 22:05:21.896000,25\n" +
                "WFKF170141,152,20230227,2023-02-27 22:05:22.626000,25";
    }



    public static List<OrderInfo> getOrderInfo(){
        String[] orderInfoStrs = getOrderInfoStr().split("\n");
        List<OrderInfo> orderInfos = Lists.newArrayList();
        for (String orderInfoStr : orderInfoStrs) {
            String[] orderInfoArr = orderInfoStr.split(",");
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNo(orderInfoArr[2]);
            orderInfo.setBatchNo(orderInfoArr[0]);
            orderInfos.add(orderInfo);

        }
        return orderInfos;
    }

    public static String getOrderInfoStr(){
        return "20230120,-243,WWLIN-2303231600000\n" +
                "20230215,-126,WWLIN-2303231600001\n" +
                "20221116,-236,WWLIN-2303231600002\n" +
                "20221124,-40,WWLIN-2303231600003\n" +
                "20230106,-238,WWLIN-2303231600004\n" +
                "20230215,-2,WWLIN-2303231600006\n" +
                "20221229,-135,WWLIN-2303231600007\n" +
                "20230224,-46,WWLIN-2303231600008\n" +
                "20230201,-238,WWLIN-2303231600009\n" +
                "20230201,-238,WWLIN-230323160000a\n" +
                "20221223,-16,WWLIN-230323160000b\n" +
                "20230103,-248,WWLIN-230323160000c\n" +
                "20221209,-171,WWLIN-230323160000d\n" +
                "20230115,-87,WWLIN-230323160000e\n" +
                "20230215,-1,WWLIN-230323160000f\n" +
                "20221122,-67,WWLIN-230323160000g\n" +
                "20221124,-3,WWLIN-230323160000h\n" +
                "20230103,-200,WWLIN-230323160000i\n" +
                "20221015,-41,WWLIN-230323160000j\n" +
                "20221112,-200,WWLIN-230323160000k\n" +
                "20221207,-1,WWLIN-230323160000l\n" +
                "20230223,-133,WWLIN-230323160000m\n" +
                "20230227,-304,WWLIN-230323160000n\n" +
                "20230227,-152,WWLIN-230323160000o";
    }


    public static class OrderInfo{
        public String orderNo;
        public String batchNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }
    }

    public static class BatchInfo{
        public String batchNo;
        public String batchDate;
        public String age;

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getBatchDate() {
            return batchDate;
        }

        public void setBatchDate(String batchDate) {
            this.batchDate = batchDate;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }



}
