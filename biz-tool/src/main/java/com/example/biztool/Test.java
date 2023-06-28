package com.example.biztool;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-02-24 13:47
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@NoArgsConstructor
@Data
public class Test {


    @JsonProperty("path")
    private String path;
    @JsonProperty("flatMenu")
    private Boolean flatMenu;
    @JsonProperty("needAuth")
    private Boolean needAuth;
    @JsonProperty("routes")
    private List<RoutesDTO> routes;

    public static void main(String[] args) {


        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(0, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());


        Test test = JSON.parseObject(getJson(), Test.class);

        StringBuilder sb = new StringBuilder();

        List<RoutesDTO> routes = test.getRoutes();
        exeRoutes(sb, routes, null,null);

        System.out.println(sb.toString());

    }

    public static void exeRoutes(StringBuilder sb, List<RoutesDTO> routes, String parentCode, String module) {

        if (CollectionUtils.isEmpty(routes)) {
            return;
        }

        for (RoutesDTO route : routes) {
            exeRoute(sb, route, parentCode, module);
        }
    }

    public static void exeRoute(StringBuilder sb, RoutesDTO route, String parentCode, String module) {
        String name = route.getName();
        if (StringUtils.isBlank(name)){
            return;
        }
        String code = route.getPath();
        module = Objects.isNull(module) ? name : module;
        parentCode = Objects.isNull(parentCode) ? "NULL" : "'" + parentCode + "'";

        String mem_approle = "INSERT INTO\n" +
                "    mem_approle (\n" +
                "        APPROLE_ID,\n" +
                "        APP_ID,\n" +
                "        APPROLE_CODE,\n" +
                "        APPROLE_NAME,\n" +
                "        APPROLE_TYPE,\n" +
                "        PARENT_APPROLE_CODE,\n" +
                "        APPROLE_NAME_EN\n" +
                "    )\n" +
                "VALUES (\n" +
                "        nextindex('mem_approle'),\n" +
                "        '67',\n" +
                "        '" + code + "',\n" +
                "        '" + name + "',\n" +
                "        'application',\n" +
                "        " + parentCode + ",\n" +
                "        NULL\n" +
                "    );";
        sb.append(mem_approle).append("\n");


        String mem_role_base = "INSERT INTO\n" +
                "    mem_role_base (\n" +
                "        ROLE_ID,\n" +
                "        ORG_ID,\n" +
                "        APP_ID,\n" +
                "        ROLE_CODE,\n" +
                "        ROLE_NAME,\n" +
                "        ROLE_TYPE,\n" +
                "        PARENT_ROLE_CODE,\n" +
                "        ROLE_DESCRIPTION\n" +
                "    )\n" +
                "select\n" +
                "    nextindex('mem_role_base'),\n" +
                "    org_id,\n" +
                "    '67',\n" +
                "    '" + code + "',\n" +
                "    '" + name + "',\n" +
                "    'application',\n" +
                "    " + parentCode + ",\n" +
                "    NULL\n" +
                "from mem_organization;";
        sb.append(mem_role_base).append("\n");

        String mem_app_model_role_rela = "INSERT INTO\n" +
                "    mem_app_model_role_rela (\n" +
                "        APP_ID,\n" +
                "        APPMODEL_ID,\n" +
                "        APPROLE_ID,\n" +
                "        MEMO,\n" +
                "        STATUS\n" +
                "    )\n" +
                "SELECT\n" +
                "    '67',\n" +
                "    appmodel_id,\n" +
                "    APPROLE_ID,\n" +
                "    NULL,\n" +
                "    1\n" +
                "FROM\n" +
                "    mem_approle a,\n" +
                "    mem_appmodel m\n" +
                "WHERE\n" +
                "    a.APPROLE_CODE = '" + code + "'\n" +
                "    AND m.appmodel_name = '"+module+"'\n" +
                "    AND m.app_id = '67';";
        sb.append(mem_app_model_role_rela).append("\n -- --------------------分隔符---------------------\n");

        List<RoutesDTO> routes = route.getRoutes();
        exeRoutes(sb,routes,code,module);
    }


    @NoArgsConstructor
    @Data
    public static class RoutesDTO {
        @JsonProperty("path")
        private String path;
        @JsonProperty("name")
        private String name;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("routes")
        private List<RoutesDTO> routes;
        @JsonProperty("redirect")
        private String redirect;
        @JsonProperty("exact")
        private Boolean exact;
        @JsonProperty("locale")
        private Boolean locale;
        @JsonProperty("hideInMenu")
        private Boolean hideInMenu;
    }

    public static String getJson() {
        return "{\n" +
                "  \"path\": \"/\",\n" +
                "  \"flatMenu\": true,\n" +
                "  \"needAuth\": true,\n" +
                "  \"routes\": [\n" +
                "    {\n" +
                "      \"path\": \"/customer-manage\",\n" +
                "      \"name\": \"客户管理\",\n" +
                "      \"icon\": \"IdcardOutlined\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/customer-manage/customer-list\",\n" +
                "          \"name\": \"客户列表\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"funds-account-list\",\n" +
                "          \"name\": \"账户列表\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/customer-manage/customer-charge-record\",\n" +
                "          \"name\": \"客户入账\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/goods-manage\",\n" +
                "      \"name\": \"商品管理\",\n" +
                "      \"icon\": \"icon-shangpin\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/goods-manage/goods-list\",\n" +
                "          \"name\": \"商品列表\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/goods-manage/goods-group-list\",\n" +
                "          \"name\": \"组合商品列表\",\n" +
                "          \"exact\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/order-manage\",\n" +
                "      \"name\": \"订单管理\",\n" +
                "      \"icon\": \"icon-quanbudingdan\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/order-manage/customer-enter-order-list\",\n" +
                "          \"name\": \"入库订单列表\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/order-manage/customer-out-order-list\",\n" +
                "          \"name\": \"出库订单列表\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/order-manage/out-logistics-supplement\",\n" +
                "          \"name\": \"出库物流补充\",\n" +
                "          \"locale\": false,\n" +
                "          \"exact\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/inventory-manage\",\n" +
                "      \"name\": \"库存管理\",\n" +
                "      \"icon\": \"icon-cangkukucun\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/inventory-manage/inventory-list\",\n" +
                "          \"name\": \"库存管理\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/inventory-manage/transaction-flow\",\n" +
                "          \"name\": \"库存交易流水\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/inventory-manage/inventory-age\",\n" +
                "          \"name\": \"库龄管理\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/settlement-manage\",\n" +
                "      \"redirect\": \"/settlement-manage/charge-bill\",\n" +
                "      \"exact\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/settlement-manage\",\n" +
                "      \"name\": \"财务管理\",\n" +
                "      \"icon\": \"icon-feiyong\",\n" +
                "      \"locale\": false,\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/charge-bill\",\n" +
                "          \"name\": \"费用清单\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/automatic-recharge-list\",\n" +
                "          \"name\": \"充值记录\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/charge-confirm\",\n" +
                "          \"name\": \"收款确认\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/payment-confirm\",\n" +
                "          \"name\": \"付款确认\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/independent-settlement-list\",\n" +
                "          \"name\": \"独立结算单\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/warehouse-receivable-quote-list\",\n" +
                "          \"name\": \"库内应收报价\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/tail-distance-receivable-quote-list\",\n" +
                "          \"name\": \"尾程应收报价\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/warehouse-payable-quote-list\",\n" +
                "          \"name\": \"库内应付报价\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/settlement-manage/tail-distance-payable-quote-list\",\n" +
                "          \"name\": \"尾程应付报价\",\n" +
                "          \"locale\": false,\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/dict-manage\",\n" +
                "      \"icon\": \"icon-quanbudingdan\",\n" +
                "      \"name\": \"字典管理\",\n" +
                "      \"hideInMenu\": true,\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/dict-manage/dict-list\",\n" +
                "          \"name\": \"字典列表\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/dtc-report\",\n" +
                "      \"icon\": \"icon-quanbudingdan\",\n" +
                "      \"name\": \"数据报表\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-list\",\n" +
                "          \"name\": \"利润统计\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-list/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_1_2\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-basic\",\n" +
                "          \"name\": \"商品信息\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-basic/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_1\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-in-order\",\n" +
                "          \"name\": \"入库履约\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-in-order/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_2\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-out-order\",\n" +
                "          \"name\": \"出库履约\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-out-order/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_3\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-in-stock\",\n" +
                "          \"name\": \"库存存量\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-in-stock/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_4\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-library-age\",\n" +
                "          \"name\": \"库存库龄\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-library-age/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_5\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-warehouse-cost\",\n" +
                "          \"name\": \"仓库堆存费\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-warehouse-cost/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_6\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-distribution-cost\",\n" +
                "          \"name\": \"尾程配送费\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-distribution-cost/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_7\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-out-cost\",\n" +
                "          \"name\": \"出库操作费\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-out-cost/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_8\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-enter-cost\",\n" +
                "          \"name\": \"卸货附加费\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-enter-cost/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_9\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-tally-cost\",\n" +
                "          \"name\": \"理货上架费\",\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/dtc-report/dtc-tally-cost/\",\n" +
                "              \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_10\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/dtc-report/dtc-independent-settlement\",\n" +
                "          \"name\": \"独立结算费用\",\n" +
                "          \"dtcReportPath\": \"/gm2-datacenter-front/#/external/report/DTC_BASIC_REPORT_11\",\n" +
                "          \"exact\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/basic-config\",\n" +
                "      \"name\": \"基础配置\",\n" +
                "      \"icon\": \"setting\",\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/basic-config/warehouse-config\",\n" +
                "          \"name\": \"仓库配置\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/basic-config/warehouse-area-config\",\n" +
                "          \"name\": \"仓库服务配置\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/basic-config/dispatch-channel-config\",\n" +
                "          \"name\": \"配送渠道配置\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/basic-config/fuel-rate\",\n" +
                "          \"name\": \"燃油费率\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"path\": \"/basic-config/fuel-rate-log\",\n" +
                "          \"name\": \"燃油费率记录\",\n" +
                "          \"hideChildrenInMenu\": true\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/system-settings\",\n" +
                "      \"name\": \"系统配置\",\n" +
                "      \"icon\": \"setting\",\n" +
                "      \"locale\": false,\n" +
                "      \"routes\": [\n" +
                "        {\n" +
                "          \"path\": \"/system-settings/error-code-list\",\n" +
                "          \"name\": \"异常信息维护\",\n" +
                "          \"locale\": false,\n" +
                "          \"routes\": [\n" +
                "            {\n" +
                "              \"path\": \"/system-settings/error-code-list/\",\n" +
                "              \"exact\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"path\": \"/export-manage\",\n" +
                "      \"icon\": \"icon-quanbudingdan\",\n" +
                "      \"name\": \"导出列表\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "\n";
    }
}
