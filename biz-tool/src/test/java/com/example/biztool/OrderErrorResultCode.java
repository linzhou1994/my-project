package com.example.biztool;



import java.util.EnumSet;

/**
 * @date : 2022/5/10 14:29
 * @author: linzhou
 * @description : ErrorResultCode
 */
public enum OrderErrorResultCode implements ResultCode {

  /**********************************订单取消相关00*************************/
  ID_NOT_NULL("00000", "id不能为null", null),
  ORDER_OUT_BOUND_CANCEL_NOT_NULL("00001", "取消记录不能为空", null),
  CANCEL_STATUS_ERROR("00002", "不是取消中的[]订单无法取消", null),
  ORDER_CANCEL_ING("00003", "订单取消中，请不要重复取消", null),
  ;

  private static String SYS_FIX = "01";


  private String code;
  private String zhMessage;
  private String enMessage;

  OrderErrorResultCode(String code, String zhMessage, String enMessage) {
    this.code = code;
    this.zhMessage = zhMessage;
    this.enMessage = enMessage;
  }

  public String getCode() {
    return SYS_FIX + this.code;
  }

  public String getMessage() {

    return zhMessage;
  }

  public String getZhMessage() {
    return this.zhMessage;
  }

  public String getEnMessage() {
    return this.enMessage;
  }

  public static void main(String[] args) {
    Class<?> cls = null;
    try {
      cls = Class.forName("com.example.biztool.OrderErrorResultCode");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    if(cls.getSuperclass()!=null && Enum.class.getName().equals(cls.getSuperclass().getName())){
      Class<Enum> enumClass = (Class<Enum>)cls;

      //获取枚举的所有属性对象集合
      EnumSet es = EnumSet.allOf(enumClass);
      Object[] objs = es.toArray();

      for (int i = 0; i < objs.length; i++) {
        Object object = objs[i];
        Enum e = (Enum) object;
        ResultCode resultCode = (ResultCode) object;


      }
    }
  }
}
