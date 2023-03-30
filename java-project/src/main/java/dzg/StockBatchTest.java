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
            assert orderInfo.getQty().equals(batchInfo.getQty());
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
            batchInfo.setQty(Integer.valueOf(batchInfoArr[1]));
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
      select  d.sku,sum(r.frozen_quantity),d.batch_no,d.inbound_time,d.storage_age
      from t_inventory_detail_outbound_rela r
      left join t_inventory_detail d on r.inventory_detail_id = d.id
      where r.doc_no in('WWLOUT-2303281800003')
      group by d.sku,d.batch_no
     * @return
     */
//    public static String getBatchInfoStr(){
//        //WWLOUT-2303281800006
//        return "AMB02-HDX-X6-02,1,20230207,2023-02-07 09:00:00,50\n" +
//                "AMB02-ZLT6-R-01,2,20221130,2022-11-30 00:35:48.268000,119";
//    }
//    public static String getBatchInfoStr(){
//        //WWLOUT-2303281800004
//        return "AA01016ZZE001,124,20221124,2022-11-24 22:24:21.230000,125\n" +
//                "ZYKJ-US-LJ163PS011-WD,330,20221123,2022-11-23 23:50:28.021000,126";
//    }
//
    public static String getBatchInfoStr(){
        return "AMKF150136-02,7,20230125,2023-01-25 20:55:30.093000,63\n" +
                "AMKF150136-03,15,20230125,2023-01-25 21:05:21.775000,63\n" +
                "AMKF150157-01-03,37,20221220,2022-12-20 19:26:24.453000,99\n" +
                "AMKF150157-02-01,23,20230107,2023-01-07 17:51:24.418000,81\n" +
                "AMKF150157-02-03,44,20230105,2023-01-05 21:27:29.166000,83\n" +
                "AMKF170215-03,47,20230116,2023-01-16 17:30:26.238000,72\n" +
                "AMKF170215-04,32,20230116,2023-01-16 17:30:25.753000,72\n" +
                "AMKF170269-01,40,20230125,2023-01-25 20:55:31.733000,63\n" +
                "AMKF170269-02,13,20230125,2023-01-25 20:55:31.460000,63\n" +
                "AMKF170269-03,15,20230125,2023-01-25 21:05:22.259000,63\n" +
                "AMKF180101-02,10,20221221,2022-12-21 22:25:33.288000,98\n" +
                "AMKF180103-01,29,20230116,2023-01-16 17:35:18.726000,72\n" +
                "AMKF180105-01,25,20230105,2023-01-05 21:57:31.204000,83\n" +
                "AMKF180106-01,23,20230105,2023-01-05 21:47:43.905000,83\n" +
                "KF020208-01,1,20230215,2023-02-15 08:00:00,42\n" +
                "KF020209-01,19,20221031,2022-10-31 03:55:20.626000,149\n" +
                "KF020217-01,1,20230217,2023-02-17 18:10:23.930000,40\n" +
                "KF020243A-18,42,20230209,2023-02-09 19:35:04.057000,48\n" +
                "KF020249-01,11,20230115,2023-01-15 22:50:38.350000,73\n" +
                "KF020282-01,23,20221220,2022-12-20 17:40:50.561000,99\n" +
                "KF020284-01,3,20221220,2022-12-20 17:40:51.204000,99\n" +
                "KF020312-01,8,20230215,2023-02-15 15:55:24.323000,42\n" +
                "KF020312-02,7,20230215,2023-02-15 16:05:26.175000,42\n" +
                "KF020328-01,31,20230215,2023-02-15 15:55:23.236000,42\n" +
                "KF200053-01CG,28,20221116,2022-11-16 23:25:35.424000,133\n" +
                "KF200053-02SY,24,20230106,2023-01-06 17:50:55.313000,82\n" +
                "KF200054-01SY,24,20221130,2022-11-30 01:00:27.475000,119\n" +
                "KF200067-02MH,1,20221229,2022-12-29 19:30:33.451000,90\n" +
                "KF200106-02DR,1,20221124,2022-11-24 19:36:09.183000,125\n" +
                "KF200106-02DR,1,20221207,2022-12-07 09:00:00,112\n" +
                "KF200106-02DR,1,20230303,2023-03-03 06:00:00,26\n" +
                "KF200106-03SY,16,20230103,2023-01-03 17:56:17.761000,85\n" +
                "KF200106-03SY,2,20230201,2023-02-01 19:20:20.001000,56\n" +
                "KF200106-04SY,5,20230103,2023-01-03 19:50:54.767000,85\n" +
                "KF200106-04SY,1,20230115,2023-01-15 18:00:17.218000,73\n" +
                "KF200106-04SY,2,20230201,2023-02-01 17:15:19.722000,56\n" +
                "KF200107-02SY,13,20221223,2022-12-23 20:10:50.861000,96\n" +
                "KF200108-02SY,33,20221117,2022-11-17 02:25:31.778000,132\n" +
                "KF200144-03SY,14,20221124,2022-11-24 20:56:10.195000,125\n" +
                "KF200146-02SY,27,20221122,2022-11-22 00:55:36.501000,127\n" +
                "KF200148-04SY,2,20230107,2023-01-07 22:22:18.580000,81\n" +
                "KF200156-02SY,1,20221015,2022-10-15 02:00:58.659000,165\n" +
                "KF200161-01,31,20221228,2022-12-28 21:01:01.945000,91\n" +
                "KF200161-02,2,20221222,2022-12-22 20:56:36.569000,97\n" +
                "KF200198-01,32,20230131,2023-01-31 22:15:19.879000,57\n" +
                "KF210095-03-999,8,20230112,2023-01-12 16:21:35.516000,76\n" +
                "KF210106-01-999,3,20230113,2023-01-13 21:31:11.532000,75\n" +
                "KF210120-01,1,20230217,2023-02-17 20:25:47.852000,40\n" +
                "KF210127-05,7,20230116,2023-01-16 21:45:28.406000,72\n" +
                "KF210150-04,2,20230223,2023-02-23 15:45:38.667000,34\n" +
                "KF210168-02,41,20230223,2023-02-23 21:00:24.727000,34\n" +
                "KF210168-03,12,20230223,2023-02-23 21:00:25.327000,34\n" +
                "KF210192-01,16,20230129,2023-01-29 16:40:15.198000,59\n" +
                "KF210192-02,16,20230129,2023-01-29 16:55:17.637000,59\n" +
                "KF260026-01SY,2,20221012,2022-10-12 16:49:46.149000,168\n" +
                "KF260026-03XN,2,20221116,2022-11-16 23:40:22.317000,133\n" +
                "KF260027-02XN,10,20221116,2022-11-16 19:50:18.767000,133\n" +
                "KF260033-02-909,5,20221201,2022-12-01 02:20:42.091000,118\n" +
                "KF260044-01TR-901,3,20221222,2022-12-22 21:51:33.688000,97\n" +
                "KF260044-01TR-901,2,20230217,2023-02-17 15:05:42.930000,40\n" +
                "KF260044-01TR-902,3,20221222,2022-12-22 21:46:42.219000,97\n" +
                "KF260044-01TR-902,2,20230217,2023-02-17 15:05:43.158000,40\n" +
                "KF260044-01TR-903,2,20221222,2022-12-22 21:51:34.258000,97\n" +
                "KF260044-01TR-903,2,20230217,2023-02-17 15:05:43.593000,40\n" +
                "KF260044-01TR-906,3,20221222,2022-12-22 21:46:42.617000,97\n" +
                "KF260044-01TR-906,2,20230217,2023-02-17 15:05:43.379000,40\n" +
                "KF260044-02TR,4,20230131,2023-01-31 20:55:35.055000,57\n" +
                "KF260044-02TR-901,1,20230217,2023-02-17 15:15:55.435000,40\n" +
                "KF260044-02TR-902,2,20230217,2023-02-17 15:05:43.806000,40\n" +
                "KF260044-02TR-903,2,20230217,2023-02-17 15:10:46.713000,40\n" +
                "KF260044-02TR-906,2,20230217,2023-02-17 15:05:42.670000,40\n" +
                "KF260054-01-901,3,20230106,2023-01-06 19:41:33.801000,82\n" +
                "KF260054-01-902,3,20230106,2023-01-06 19:41:34.077000,82\n" +
                "KF260054-01-903,3,20230107,2023-01-07 17:31:28.990000,81\n" +
                "KF260054-01-904,3,20230107,2023-01-07 17:26:39.225000,81\n" +
                "KF260054-01-905,3,20230106,2023-01-06 19:41:33.520000,82\n" +
                "KF260054-01-9145,3,20230107,2023-01-07 17:26:39.870000,81\n" +
                "KF260054-01-916,3,20230107,2023-01-07 17:31:29.760000,81\n" +
                "KF260054-01-917,3,20230107,2023-01-07 17:21:36.310000,81\n" +
                "KF260054-01-997,3,20230106,2023-01-06 21:36:38.435000,82\n" +
                "KF260054-01-998,3,20230107,2023-01-07 17:36:29.604000,81\n" +
                "KF260062-01,45,20230107,2023-01-07 22:12:17.374000,81\n" +
                "KF260063-01,36,20230110,2023-01-10 15:20:27.539000,78\n" +
                "KF330017-02,34,20230110,2023-01-10 14:51:14.199000,78\n" +
                "KF330036-02,10,20230105,2023-01-05 22:00:38.886000,83\n" +
                "KF330069-01,7,20230116,2023-01-16 19:45:17.091000,72\n" +
                "KF330069-02,7,20230116,2023-01-16 19:45:17.701000,72\n" +
                "KF390012-01,45,20221221,2022-12-21 04:21:43.151000,98\n" +
                "KF390027-01,13,20230105,2023-01-05 17:17:53.891000,83\n" +
                "WFKF170054,5,20230227,2023-02-27 22:05:21.896000,30\n" +
                "WFKF170141,47,20230227,2023-02-27 22:05:22.626000,30\n" +
                "WFKF180109-01,5,20230105,2023-01-05 21:27:27.419000,83\n" +
                "WFKF180115-01,49,20230105,2023-01-05 22:27:34.285000,83\n" +
                "WFKF180115-01,1,20230201,2023-02-01 22:25:26.274000,56\n" +
                "WFKF180115-02,40,20230105,2023-01-05 22:22:41.757000,83\n" +
                "WFKF180115-02,10,20230106,2023-01-06 22:01:58.075000,82\n" +
                "WFKF180116-01,50,20230107,2023-01-07 14:46:38.182000,81\n" +
                "WFKF210013-01,57,20230113,2023-01-13 18:01:01.315000,75\n" +
                "WFKF210013-02,28,20230115,2023-01-15 17:50:47.937000,73\n" +
                "WFKF210106-03-999,2,20230113,2023-01-13 21:46:09.608000,75";
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
            orderInfo.setQty(Integer.parseInt(orderInfoArr[2]));
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
        return "AMKF150136-02,20230125,7,WWLIN-2303281900002\n" +
                "AMKF150136-03,20230125,15,WWLIN-2303281900002\n" +
                "AMKF150157-01-03,20221220,37,WWLIN-2303281900002\n" +
                "AMKF150157-02-01,20230107,23,WWLIN-2303281900002\n" +
                "AMKF150157-02-03,20230105,44,WWLIN-2303281900002\n" +
                "AMKF170215-03,20230116,47,WWLIN-2303281900002\n" +
                "AMKF170215-04,20230116,32,WWLIN-2303281900002\n" +
                "AMKF170269-01,20230125,40,WWLIN-2303281900002\n" +
                "AMKF170269-02,20230125,13,WWLIN-2303281900002\n" +
                "AMKF170269-03,20230125,15,WWLIN-2303281900002\n" +
                "AMKF180101-02,20221221,10,WWLIN-2303281900002\n" +
                "AMKF180103-01,20230116,29,WWLIN-2303281900002\n" +
                "AMKF180105-01,20230105,25,WWLIN-2303281900002\n" +
                "AMKF180106-01,20230105,23,WWLIN-2303281900002\n" +
                "KF020208-01,20230215,1,WWLIN-2303281900002\n" +
                "KF020209-01,20221031,19,WWLIN-2303281900002\n" +
                "KF020217-01,20230217,1,WWLIN-2303281900002\n" +
                "KF020243A-18,20230209,42,WWLIN-2303281900002\n" +
                "KF020249-01,20230115,11,WWLIN-2303281900002\n" +
                "KF020282-01,20221220,23,WWLIN-2303281900002\n" +
                "KF020284-01,20221220,3,WWLIN-2303281900002\n" +
                "KF020312-01,20230215,8,WWLIN-2303281900002\n" +
                "KF020312-02,20230215,7,WWLIN-2303281900002\n" +
                "KF020328-01,20230215,31,WWLIN-2303281900002\n" +
                "KF200053-01CG,20221116,28,WWLIN-2303281900002\n" +
                "KF200053-02SY,20230106,24,WWLIN-2303281900002\n" +
                "KF200054-01SY,20221130,24,WWLIN-2303281900002\n" +
                "KF200067-02MH,20221229,1,WWLIN-2303281900002\n" +
                "KF200106-02DR,20221124,1,WWLIN-2303281900002\n" +
                "KF200106-02DR,20221207,1,WWLIN-2303282000000\n" +
                "KF200106-02DR,20230303,1,WWLIN-2303282000001\n" +
                "KF200106-03SY,20230103,16,WWLIN-2303281900002\n" +
                "KF200106-03SY,20230201,2,WWLIN-2303282000000\n" +
                "KF200106-04SY,20230103,5,WWLIN-2303281900002\n" +
                "KF200106-04SY,20230115,1,WWLIN-2303282000000\n" +
                "KF200106-04SY,20230201,2,WWLIN-2303282000001\n" +
                "KF200107-02SY,20221223,13,WWLIN-2303281900002\n" +
                "KF200108-02SY,20221117,33,WWLIN-2303281900002\n" +
                "KF200144-03SY,20221124,14,WWLIN-2303281900002\n" +
                "KF200146-02SY,20221122,27,WWLIN-2303281900002\n" +
                "KF200148-04SY,20230107,2,WWLIN-2303281900002\n" +
                "KF200156-02SY,20221015,1,WWLIN-2303281900002\n" +
                "KF200161-01,20221228,31,WWLIN-2303281900002\n" +
                "KF200161-02,20221222,2,WWLIN-2303281900002\n" +
                "KF200198-01,20230131,32,WWLIN-2303281900002\n" +
                "KF210095-03-999,20230112,8,WWLIN-2303281900002\n" +
                "KF210106-01-999,20230113,3,WWLIN-2303281900002\n" +
                "KF210120-01,20230217,1,WWLIN-2303281900002\n" +
                "KF210127-05,20230116,7,WWLIN-2303281900002\n" +
                "KF210150-04,20230223,2,WWLIN-2303281900002\n" +
                "KF210168-02,20230223,41,WWLIN-2303281900002\n" +
                "KF210168-03,20230223,12,WWLIN-2303281900002\n" +
                "KF210192-01,20230129,16,WWLIN-2303281900002\n" +
                "KF210192-02,20230129,16,WWLIN-2303281900002\n" +
                "KF260026-01SY,20221012,2,WWLIN-2303281900002\n" +
                "KF260026-03XN,20221116,2,WWLIN-2303281900002\n" +
                "KF260027-02XN,20221116,10,WWLIN-2303281900002\n" +
                "KF260033-02-909,20221201,5,WWLIN-2303281900002\n" +
                "KF260044-01TR-901,20221222,3,WWLIN-2303281900002\n" +
                "KF260044-01TR-901,20230217,2,WWLIN-2303282000000\n" +
                "KF260044-01TR-902,20221222,3,WWLIN-2303281900002\n" +
                "KF260044-01TR-902,20230217,2,WWLIN-2303282000000\n" +
                "KF260044-01TR-903,20221222,2,WWLIN-2303281900002\n" +
                "KF260044-01TR-903,20230217,2,WWLIN-2303282000000\n" +
                "KF260044-01TR-906,20221222,3,WWLIN-2303281900002\n" +
                "KF260044-01TR-906,20230217,2,WWLIN-2303282000000\n" +
                "KF260044-02TR,20230131,4,WWLIN-2303281900002\n" +
                "KF260044-02TR-901,20230217,1,WWLIN-2303281900002\n" +
                "KF260044-02TR-902,20230217,2,WWLIN-2303281900002\n" +
                "KF260044-02TR-903,20230217,2,WWLIN-2303281900002\n" +
                "KF260044-02TR-906,20230217,2,WWLIN-2303281900002\n" +
                "KF260054-01-901,20230106,3,WWLIN-2303281900002\n" +
                "KF260054-01-902,20230106,3,WWLIN-2303281900002\n" +
                "KF260054-01-903,20230107,3,WWLIN-2303281900002\n" +
                "KF260054-01-904,20230107,3,WWLIN-2303281900002\n" +
                "KF260054-01-905,20230106,3,WWLIN-2303281900002\n" +
                "KF260054-01-9145,20230107,3,WWLIN-2303281900002\n" +
                "KF260054-01-916,20230107,3,WWLIN-2303281900002\n" +
                "KF260054-01-917,20230107,3,WWLIN-2303281900002\n" +
                "KF260054-01-997,20230106,3,WWLIN-2303281900002\n" +
                "KF260054-01-998,20230107,3,WWLIN-2303281900002\n" +
                "KF260062-01,20230107,45,WWLIN-2303281900002\n" +
                "KF260063-01,20230110,36,WWLIN-2303281900002\n" +
                "KF330017-02,20230110,34,WWLIN-2303281900002\n" +
                "KF330036-02,20230105,10,WWLIN-2303281900002\n" +
                "KF330069-01,20230116,7,WWLIN-2303281900002\n" +
                "KF330069-02,20230116,7,WWLIN-2303281900002\n" +
                "KF390012-01,20221221,45,WWLIN-2303281900002\n" +
                "KF390027-01,20230105,13,WWLIN-2303281900002\n" +
                "WFKF170054,20230227,5,WWLIN-2303281900002\n" +
                "WFKF170141,20230227,47,WWLIN-2303281900002\n" +
                "WFKF180109-01,20230105,5,WWLIN-2303281900002\n" +
                "WFKF180115-01,20230105,49,WWLIN-2303281900002\n" +
                "WFKF180115-01,20230201,1,WWLIN-2303282000000\n" +
                "WFKF180115-02,20230105,40,WWLIN-2303281900002\n" +
                "WFKF180115-02,20230106,10,WWLIN-2303282000000\n" +
                "WFKF180116-01,20230107,50,WWLIN-2303281900002\n" +
                "WFKF210013-01,20230113,57,WWLIN-2303281900002\n" +
                "WFKF210013-02,20230115,28,WWLIN-2303281900002\n" +
                "WFKF210106-03-999,20230113,2,WWLIN-2303281900002";
    }


    public static class OrderInfo{
        public String orderNo;
        public String batchNo;
        public String sku;
        public Integer qty;

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

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
        public Integer qty;

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

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
