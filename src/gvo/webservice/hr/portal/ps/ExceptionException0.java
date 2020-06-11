
/**
 * ExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package gvo.webservice.hr.portal.ps;

public class ExceptionException0 extends Exception{
    
    private gvo.webservice.hr.portal.ps.AttendanceServiceStub.ExceptionE faultMessage;
    
    public ExceptionException0() {
        super("ExceptionException0");
    }
           
    public ExceptionException0(String s) {
       super(s);
    }
    
    public ExceptionException0(String s, Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(gvo.webservice.hr.portal.ps.AttendanceServiceStub.ExceptionE msg){
       faultMessage = msg;
    }
    
    public gvo.webservice.hr.portal.ps.AttendanceServiceStub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    