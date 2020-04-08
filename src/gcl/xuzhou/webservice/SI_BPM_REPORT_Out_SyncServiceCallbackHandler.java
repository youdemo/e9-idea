
/**
 * SI_BPM_REPORT_Out_SyncServiceCallbackHandler.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.6  Built on : Aug 30, 2011 (10:00:16 CEST)
 */

package gcl.xuzhou.webservice;

/**
 *  SI_BPM_REPORT_Out_SyncServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SI_BPM_REPORT_Out_SyncServiceCallbackHandler {


    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SI_BPM_REPORT_Out_SyncServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SI_BPM_REPORT_Out_SyncServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */

    public Object getClientData() {
        return clientData;
    }


    /**
     * auto generated Axis2 call back method for sI_BPM_REPORT_Out_Sync method
     * override this method for handling normal response from sI_BPM_REPORT_Out_Sync operation
     */
    public void receiveResultsI_BPM_REPORT_Out_Sync(
            SI_BPM_REPORT_Out_SyncServiceStub.MT_BPM_REPORT_RESPONSE result
    ) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sI_BPM_REPORT_Out_Sync operation
     */
    public void receiveErrorsI_BPM_REPORT_Out_Sync(Exception e) {
    }


}
