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
                .append("where apply_doc_no='").append(orderInfo.getOrderNo()).append("'\n")
                .append("and sku = '").append(orderInfo.getSku()).append("';").append("\n")
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

    /**
     * 出库批次信息
     * select  d.sku,sum(r.frozen_quantity),d.batch_no,d.inbound_time,d.storage_age
     * from t_inventory_detail_outbound_rela r
     * left join t_inventory_detail d on r.inventory_detail_id = d.id
     * where r.doc_no = 'WWLOUT-230324110001m-03'
     * group by d.sku,d.batch_no
     * @return
     */
    public static String getBatchInfoStr(){
        return "FH0005-A,4,20221010,2022-10-10 17:49:53.386000,166\n" +
                "FH0005-A,4,20221021,2022-10-21 18:10:32.831000,155\n" +
                "FH0005-B,15,20221010,2022-10-10 17:49:51.470000,166\n" +
                "FH0005-B,16,20221021,2022-10-21 18:10:31.842000,155\n" +
                "FH0005-P,5,20221010,2022-10-10 17:49:52.909000,166\n" +
                "FH0005-P,5,20221021,2022-10-21 18:10:32.631000,155\n" +
                "FH0006-A,4,20221010,2022-10-10 17:49:51.957000,166\n" +
                "FH0006-A,4,20221021,2022-10-21 18:10:32.046000,155\n" +
                "FH0006-B,16,20221010,2022-10-10 17:49:52.432000,166\n" +
                "FH0006-B,16,20221021,2022-10-21 18:10:32.426000,155\n" +
                "FH0006-P,5,20221010,2022-10-10 17:49:53.871000,166\n" +
                "FH0006-P,5,20221021,2022-10-21 18:10:33.024000,155\n" +
                "FH0006N,160,20221021,2022-10-21 18:10:31.485000,155\n" +
                "FH0007,1,20230224,2023-02-24 05:00:00,29\n" +
                "FH0007,1,20230301,2023-03-01 03:00:00,24\n" +
                "GW35-4,16,20221028,2022-10-28 18:25:38.321000,148\n" +
                "GW46-4,10,20221028,2022-10-28 18:25:38.632000,148\n" +
                "GW58-6-1,3,20221028,2022-10-28 18:25:38.841000,148\n" +
                "GW58-6-2,3,20221028,2022-10-28 18:25:39.022000,148\n" +
                "IBF101HP01,5,20221212,2022-12-12 21:47:03.535000,103\n" +
                "IBF101HP02,5,20221212,2022-12-12 21:47:03.780000,103\n" +
                "IBF101HP03,5,20221212,2022-12-12 21:47:02.859000,103\n" +
                "IBF101HP04,5,20221212,2022-12-12 21:47:03.274000,103\n" +
                "NFH0006,54,20221123,2022-11-23 23:55:48.054000,122\n" +
                "QTPF1-9196-BACKGY2-9076,3,20221028,2022-10-28 17:45:33.043000,148\n" +
                "QTPF1-9196-SEATGY2-9076,3,20221028,2022-10-28 17:45:32.653000,148\n" +
                "RF-DLRACK-BL5PAIR,8,20221102,2022-11-02 16:30:39.103000,143\n" +
                "RF-DLRACK-GY5PAIR,15,20221102,2022-11-02 16:30:37.156000,143\n" +
                "RF-DLRACK-RD5PAIR,12,20221102,2022-11-02 16:30:39.678000,143\n" +
                "RF-EMAT-BL48,30,20220924,2022-09-24 01:00:21.741000,182\n" +
                "RF-EMAT-BL48,14,20220930,2022-09-30 01:43:22.031000,176\n" +
                "RF-EMAT-BLK36,21,20221102,2022-11-02 16:30:39.293000,143\n" +
                "TF-FRPLATE-25LB,50,20221102,2022-11-02 16:45:11.368000,143";
    }



    public static List<OrderInfo> getOrderInfo(){
        String[] orderInfoStrs = getOrderInfoStr().split("\n");
        List<OrderInfo> orderInfos = Lists.newArrayList();
        for (String orderInfoStr : orderInfoStrs) {
            String[] orderInfoArr = orderInfoStr.split(",");
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNo(orderInfoArr[3]);
            orderInfo.setBatchNo(orderInfoArr[1]);
            orderInfo.setSku(orderInfoArr[0]);
            orderInfos.add(orderInfo);

        }
        return orderInfos;
    }

    /**
     * 入库单信息
     *
     * @return
     */
    public static String getOrderInfoStr(){
        return "FH0005-A,20221010,4,WWLIN-2303241600000\n" +
                "FH0005-A,20221021,4,WWLIN-2303241600003\n" +
                "FH0005-B,20221010,15,WWLIN-2303241600000\n" +
                "FH0005-B,20221021,16,WWLIN-2303241600003\n" +
                "FH0005-P,20221010,5,WWLIN-2303241600000\n" +
                "FH0005-P,20221021,5,WWLIN-2303241600003\n" +
                "FH0006-A,20221010,4,WWLIN-2303241600000\n" +
                "FH0006-A,20221021,4,WWLIN-2303241600003\n" +
                "FH0006-B,20221010,16,WWLIN-2303241600000\n" +
                "FH0006-B,20221021,16,WWLIN-2303241600003\n" +
                "FH0006-P,20221010,5,WWLIN-2303241600000\n" +
                "FH0006-P,20221021,5,WWLIN-2303241600003\n" +
                "FH0006N,20221021,160,WWLIN-2303241600000\n" +
                "FH0007,20230224,1,WWLIN-2303241600000\n" +
                "FH0007,20230301,1,WWLIN-2303241600003\n" +
                "GW35-4,20221028,16,WWLIN-2303241700006\n" +
                "GW46-4,20221028,10,WWLIN-2303241700006\n" +
                "GW58-6-1,20221028,3,WWLIN-2303241700006\n" +
                "GW58-6-2,20221028,3,WWLIN-2303241700006\n" +
                "IBF101HP01,20221212,5,WWLIN-2303241600007\n" +
                "IBF101HP02,20221212,5,WWLIN-2303241600007\n" +
                "IBF101HP03,20221212,5,WWLIN-2303241600007\n" +
                "IBF101HP04,20221212,5,WWLIN-2303241600007\n" +
                "NFH0006,20221123,54,WWLIN-230324160000a\n" +
                "QTPF1-9196-BACKGY2-9076,20221028,3,WWLIN-2303241700000\n" +
                "QTPF1-9196-SEATGY2-9076,20221028,3,WWLIN-2303241700000\n" +
                "RF-DLRACK-BL5PAIR,20221102,8,WWLIN-2303241700001\n" +
                "RF-DLRACK-GY5PAIR,20221102,15,WWLIN-2303241700001\n" +
                "RF-DLRACK-RD5PAIR,20221102,12,WWLIN-2303241700001\n" +
                "RF-EMAT-BL48,20220924,30,WWLIN-2303241700001\n" +
                "RF-EMAT-BL48,20220930,14,WWLIN-2303241700002\n" +
                "RF-EMAT-BLK36,20221102,21,WWLIN-2303241700001\n" +
                "TF-FRPLATE-25LB,20221102,50,WWLIN-2303241700004";
    }


    public static class OrderInfo{
        public String orderNo;
        public String batchNo;
        public String sku;

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

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
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
