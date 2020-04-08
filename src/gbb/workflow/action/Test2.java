package gbb.workflow.action;

//import org.apache.axiom.om.*;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.client.ServiceClient;
//import org.apache.axis2.transport.http.HTTPConstants;
//import org.apache.axis2.transport.http.HttpTransportProperties;
import weaver.general.BaseBean;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

public class Test2 {

//    public void test(){
//        new BaseBean().writeLog("cccc");
//        try {
//            ServiceClient serviceClient = new ServiceClient();
//            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
//            auth.setUsername("GYMIF");
//            auth.setPassword("Gymif2019");
//            serviceClient.getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
//            //创建服务地址WebService的URL,注意不是WSDL的URL
//            String url = "http://GEOERPQAS.gymbomate.com:8000/sap/bc/srt/wsdl/flv_10002A111AD1/srvc_url/sap/bc/srt/rfc/sap/zws_fi_gos_vendor_pay/300/zws_fi_gos_vendor_pay/zws_fi_gos_vendor_pay";
//            EndpointReference targetEPR = new EndpointReference(url);
//            Options options = serviceClient.getOptions();
//            options.setTo(targetEPR);
//            //确定调用方法（wsdl 命名空间地址 (wsdl文档中的targetNamespace) 和 方法名称 的组合）
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            /*
//             * 指定命名空间，参数：
//             * uri--即为wsdl文档的targetNamespace，命名空间
//             * perfix--可不填
//             */
//            OMNamespace omNs = fac.createOMNamespace("urn:sap-com:document:sap:rfc:functions", "");
//            // 指定方法
//            OMElement method = fac.createOMElement("Z_GOS_VENDOR_PAY", omNs);
//            // 指定方法的参数
//            OMElement ET_RETURN = fac.createOMElement("ET_RETURN", omNs);
//            method.addChild(ET_RETURN);
//
//            OMElement IT_ITEM = fac.createOMElement("IT_ITEM", omNs);
//
//            String item = "<item>\n" +
//                    "               <XBLNR>1</XBLNR>\n" +
//                    "               <XBLNR2>1</XBLNR2>\n" +
//                    "               <BUKRS>1</BUKRS>\n" +
//                    "               <LIFNR>1</LIFNR>\n" +
//                    "               <RACCT>1</RACCT>\n" +
//                    "               <KOSTL>1</KOSTL>\n" +
//                    "               <PS_POSID>1</PS_POSID>\n" +
//                    "               <KUNNR>1</KUNNR>\n" +
//                    "               <BANFN>1</BANFN>\n" +
//                    "               <WAERS>1</WAERS>\n" +
//                    "               <TSL>1</TSL>\n" +
//                    "               <TSL2>1</TSL2>\n" +
//                    "               <TAX>1</TAX>\n" +
//                    "               <BKTXT>1</BKTXT>\n" +
//                    "               <BLDAT>2019-10-11</BLDAT>\n" +
//                    "               <USNAM>1</USNAM>\n" +
//                    "            </item>\n";
//
//            IT_ITEM.setText(item);
//            method.addChild(IT_ITEM);
//            OMElement I_SYSID = fac.createOMElement("I_SYSID", omNs);
//            I_SYSID.setText("GOS");
//            method.addChild(I_SYSID);
//
//            method.build();
//            //远程调用web服务
//            new BaseBean().writeLog("ddddd");
//            OMElement result = serviceClient.sendReceive(method);
//            new BaseBean().writeLog(result);
//        } catch(Exception e){
//            new BaseBean().writeLog(e);
//        }
//
//    }
//
//    public static void testDocument() {
//        try {
//            new BaseBean().writeLog("ddddd");
//            // String url = "http://localhost:8080/axis2ServerDemo/services/StockQuoteService";
//            String url = "http://GEOERPQAS.gymbomate.com:8000/sap/bc/srt/wsdl/flv_10002A111AD1/srvc_url/sap/bc/srt/rfc/sap/zws_fi_gos_vendor_pay/300/zws_fi_gos_vendor_pay/zws_fi_gos_vendor_pay?sap-client=300?wsdl";
//
//            Options options = new Options();
//            // 指定调用WebService的URL
//            EndpointReference targetEPR = new EndpointReference(url);
//            options.setTo(targetEPR);
//            // options.setAction("urn:getPrice");
//
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
//            auth.setUsername("GYMIF");
//            auth.setPassword("Gymif2019");
//            sender.getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
//
//
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            String tns = "urn:sap-com:document:sap:rfc:functions";
//            // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的
//            OMNamespace omNs = fac.createOMNamespace(tns, "");
//
//            OMElement method = fac.createOMElement("Z_GOS_VENDOR_PAY", omNs);
//            OMElement ET_RETURN = fac.createOMElement("ET_RETURN", omNs);
//            method.addChild(ET_RETURN);
//            OMElement IT_ITEM = fac.createOMElement("IT_ITEM", omNs);
//
//            String item = "<item>\n" +
//                    "               <XBLNR>1</XBLNR>\n" +
//                    "               <XBLNR2>1</XBLNR2>\n" +
//                    "               <BUKRS>1</BUKRS>\n" +
//                    "               <LIFNR>1</LIFNR>\n" +
//                    "               <RACCT>1</RACCT>\n" +
//                    "               <KOSTL>1</KOSTL>\n" +
//                    "               <PS_POSID>1</PS_POSID>\n" +
//                    "               <KUNNR>1</KUNNR>\n" +
//                    "               <BANFN>1</BANFN>\n" +
//                    "               <WAERS>1</WAERS>\n" +
//                    "               <TSL>1</TSL>\n" +
//                    "               <TSL2>1</TSL2>\n" +
//                    "               <TAX>1</TAX>\n" +
//                    "               <BKTXT>1</BKTXT>\n" +
//                    "               <BLDAT>2019-10-11</BLDAT>\n" +
//                    "               <USNAM>1</USNAM>\n" +
//                    "            </item>\n";
//
//            IT_ITEM.setText(item);
//            method.addChild(IT_ITEM);
//            OMElement I_SYSID = fac.createOMElement("I_SYSID", omNs);
//            I_SYSID.setText("GOS");
//            method.addChild(I_SYSID);
//            method.build();
//            OMElement result = sender.sendReceive(method);
//            new BaseBean().writeLog("eeee");
//            new BaseBean().writeLog(result);
//
//        } catch (AxisFault e) {
//            new BaseBean().writeLog(e);
//        }
//    }


    public void doSelectRiskReportForm(){
        //调用接口
        //方法一:直接AXIS调用远程的web service
        try {
            new BaseBean().writeLog("ccccccccc");
            String namespace = "urn:sap-com:document:sap:rfc:functions";
            String endpoint = "http://GEOERPQAS.gymbomate.com:8000/sap/bc/srt/wsdl/flv_10002A111AD1/srvc_url/sap/bc/srt/rfc/sap/zws_fi_gos_vendor_pay/300/zws_fi_gos_vendor_pay/zws_fi_gos_vendor_pay?sap-client=300?wsdl";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setUsername("GYMIF");
            call.setPassword("Gymif2019");
            call.addHeader(new SOAPHeaderElement("Authorization","username","GYMIF"));
            call.addHeader(new SOAPHeaderElement("Authorization","password","Gymif2019"));
           		// 调用的方法名//当这种调用不到的时候,可以使用下面的,加入命名空间名
            call.setOperationName(new QName(namespace, "Z_GOS_VENDOR_PAY"));// 调用的方法名
            Object[] param = new Object[]{};
            param = new Object[3];
            call.addParameter(new QName(namespace, "ET_RETURN"), XMLType.XSD_STRING, ParameterMode.IN);
            param[0] = "";
            call.addParameter(new QName(namespace, "IT_ITEM"), XMLType.XSD_STRING, ParameterMode.IN);
            String item = "<item>\n" +
                    "               <XBLNR>1</XBLNR>\n" +
                    "               <XBLNR2>1</XBLNR2>\n" +
                    "               <BUKRS>1</BUKRS>\n" +
                    "               <LIFNR>1</LIFNR>\n" +
                    "               <RACCT>1</RACCT>\n" +
                    "               <KOSTL>1</KOSTL>\n" +
                    "               <PS_POSID>1</PS_POSID>\n" +
                    "               <KUNNR>1</KUNNR>\n" +
                    "               <BANFN>1</BANFN>\n" +
                    "               <WAERS>1</WAERS>\n" +
                    "               <TSL>1</TSL>\n" +
                    "               <TSL2>1</TSL2>\n" +
                    "               <TAX>1</TAX>\n" +
                    "               <BKTXT>1</BKTXT>\n" +
                    "               <BLDAT>2019-10-11</BLDAT>\n" +
                    "               <USNAM>1</USNAM>\n" +
                    "            </item>\n";
            param[1] = item;
            call.addParameter(new QName(namespace, "I_SYSID"), XMLType.XSD_STRING, ParameterMode.IN);
            param[2] = "GOS";
            call.setReturnType(XMLType.XSD_STRING); 	// 返回值类型：String
            String result = (String) call.invoke(param);// 远程调用
            new BaseBean().writeLog(result);
        } catch (Exception e) {
            new BaseBean().writeLog(e);
        }


    }
}