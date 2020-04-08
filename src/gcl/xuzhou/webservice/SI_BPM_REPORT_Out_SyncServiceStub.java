
/**
 * SI_BPM_REPORT_Out_SyncServiceStub.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.6  Built on : Aug 30, 2011 (10:00:16 CEST)
 */
package gcl.xuzhou.webservice;



/*
 *  SI_BPM_REPORT_Out_SyncServiceStub java implementation
 */


public class SI_BPM_REPORT_Out_SyncServiceStub extends org.apache.axis2.client.Stub {
    protected org.apache.axis2.description.AxisOperation[] _operations;

    //hashmaps to keep the fault mapping
    private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
    private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
    private java.util.HashMap faultMessageMap = new java.util.HashMap();

    private static int counter = 0;

    private static synchronized String getUniqueSuffix() {
        // reset the counter if it is greater than 99999
        if (counter > 99999) {
            counter = 0;
        }
        counter = counter + 1;
        return Long.toString(System.currentTimeMillis()) + "_" + counter;
    }


    private void populateAxisService() throws org.apache.axis2.AxisFault {

        //creating the Service with a unique name
        _service = new org.apache.axis2.description.AxisService("SI_BPM_REPORT_Out_SyncService" + getUniqueSuffix());
        addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];

        __operation = new org.apache.axis2.description.OutInAxisOperation();


        __operation.setName(new javax.xml.namespace.QName("http://gpdap01.gcl-semi.com/bpm", "sI_BPM_REPORT_Out_Sync"));
        _service.addOperation(__operation);


        _operations[0] = __operation;


    }

    //populates the faults
    private void populateFaults() {


    }

    /**
     *Constructor that takes in a configContext
     */

    public SI_BPM_REPORT_Out_SyncServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
                                             String targetEndpoint)
            throws org.apache.axis2.AxisFault {
        this(configurationContext, targetEndpoint, false);
    }


    /**
     * Constructor that takes in a configContext  and useseperate listner
     */
    public SI_BPM_REPORT_Out_SyncServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
                                             String targetEndpoint, boolean useSeparateListener)
            throws org.apache.axis2.AxisFault {
        //To populate AxisService
        populateAxisService();
        populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext, _service);


        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);


    }

    /**
     * Default Constructor
     */
    public SI_BPM_REPORT_Out_SyncServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

        this(configurationContext, "http://gppap02.gcl-semi.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BPM_PRD&receiverParty=&receiverService=&interface=SI_BPM_REPORT_Out_Sync&interfaceNamespace=http%3A%2F%2Fgpdap01.gcl-semi.com%2Fbpm");

    }

    /**
     * Default Constructor
     */
    public SI_BPM_REPORT_Out_SyncServiceStub() throws org.apache.axis2.AxisFault {

        this("http://gppap02.gcl-semi.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BPM_PRD&receiverParty=&receiverService=&interface=SI_BPM_REPORT_Out_Sync&interfaceNamespace=http%3A%2F%2Fgpdap01.gcl-semi.com%2Fbpm");

    }

    /**
     * Constructor taking the target endpoint
     */
    public SI_BPM_REPORT_Out_SyncServiceStub(String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null, targetEndpoint);
    }


    /**
     * Auto generated method signature
     *
     * @see gcl.xuzhou.webservice.SI_BPM_REPORT_Out_SyncService#sI_BPM_REPORT_Out_Sync
     * @param mT_BPM_REPORT0

     */


    public MT_BPM_REPORT_RESPONSE sI_BPM_REPORT_Out_Sync(

            MT_BPM_REPORT mT_BPM_REPORT0)


            throws java.rmi.RemoteException {
        org.apache.axis2.context.MessageContext _messageContext = null;
        try {
            org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
            _operationClient.getOptions().setAction("http://sap.com/xi/WebService/soap1.1");
            _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);


            addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");


            // create a message context
            _messageContext = new org.apache.axis2.context.MessageContext();


            // create SOAP envelope with that payload
            org.apache.axiom.soap.SOAPEnvelope env = null;


            env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                    mT_BPM_REPORT0,
                    optimizeContent(new javax.xml.namespace.QName("http://gpdap01.gcl-semi.com/bpm",
                            "sI_BPM_REPORT_Out_Sync")));

            //adding SOAP soap_headers
            _serviceClient.addHeadersToEnvelope(env);
            // set the message context with that soap envelope
            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

            //execute the operation client
            _operationClient.execute(true);


            org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                    org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();


            Object object = fromOM(
                    _returnEnv.getBody().getFirstElement(),
                    MT_BPM_REPORT_RESPONSE.class,
                    getEnvelopeNamespaces(_returnEnv));


            return (MT_BPM_REPORT_RESPONSE) object;

        } catch (org.apache.axis2.AxisFault f) {

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt != null) {
                if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
                    //make the fault by reflection
                    try {
                        String exceptionClassName = (String) faultExceptionClassNameMap.get(faultElt.getQName());
                        Class exceptionClass = Class.forName(exceptionClassName);
                        Exception ex =
                                (Exception) exceptionClass.newInstance();
                        //message class
                        String messageClassName = (String) faultMessageMap.get(faultElt.getQName());
                        Class messageClass = Class.forName(messageClassName);
                        Object messageObject = fromOM(faultElt, messageClass, null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                new Class[]{messageClass});
                        m.invoke(ex, new Object[]{messageObject});


                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    } catch (ClassCastException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                } else {
                    throw f;
                }
            } else {
                throw f;
            }
        } finally {
            if (_messageContext.getTransportOut() != null) {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
    }

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @see gcl.xuzhou.webservice.SI_BPM_REPORT_Out_SyncService#startsI_BPM_REPORT_Out_Sync
     * @param mT_BPM_REPORT0

     */
    public void startsI_BPM_REPORT_Out_Sync(

            MT_BPM_REPORT mT_BPM_REPORT0,

            final gcl.xuzhou.webservice.SI_BPM_REPORT_Out_SyncServiceCallbackHandler callback)

            throws java.rmi.RemoteException {

        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
        _operationClient.getOptions().setAction("http://sap.com/xi/WebService/soap1.1");
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);


        addPropertyToOperationClient(_operationClient, org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR, "&");


        // create SOAP envelope with that payload
        org.apache.axiom.soap.SOAPEnvelope env = null;
        final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


        //Style is Doc.


        env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                mT_BPM_REPORT0,
                optimizeContent(new javax.xml.namespace.QName("http://gpdap01.gcl-semi.com/bpm",
                        "sI_BPM_REPORT_Out_Sync")));

        // adding SOAP soap_headers
        _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                try {
                    org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                    Object object = fromOM(resultEnv.getBody().getFirstElement(),
                            MT_BPM_REPORT_RESPONSE.class,
                            getEnvelopeNamespaces(resultEnv));
                    callback.receiveResultsI_BPM_REPORT_Out_Sync(
                            (MT_BPM_REPORT_RESPONSE) object);

                } catch (org.apache.axis2.AxisFault e) {
                    callback.receiveErrorsI_BPM_REPORT_Out_Sync(e);
                }
            }

            public void onError(Exception error) {
                if (error instanceof org.apache.axis2.AxisFault) {
                    org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                    org.apache.axiom.om.OMElement faultElt = f.getDetail();
                    if (faultElt != null) {
                        if (faultExceptionNameMap.containsKey(faultElt.getQName())) {
                            //make the fault by reflection
                            try {
                                String exceptionClassName = (String) faultExceptionClassNameMap.get(faultElt.getQName());
                                Class exceptionClass = Class.forName(exceptionClassName);
                                Exception ex =
                                        (Exception) exceptionClass.newInstance();
                                //message class
                                String messageClassName = (String) faultMessageMap.get(faultElt.getQName());
                                Class messageClass = Class.forName(messageClassName);
                                Object messageObject = fromOM(faultElt, messageClass, null);
                                java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                        new Class[]{messageClass});
                                m.invoke(ex, new Object[]{messageObject});


                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(new java.rmi.RemoteException(ex.getMessage(), ex));
                            } catch (ClassCastException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (ClassNotFoundException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (NoSuchMethodException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (java.lang.reflect.InvocationTargetException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (IllegalAccessException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (InstantiationException e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            } catch (org.apache.axis2.AxisFault e) {
                                // we cannot intantiate the class - throw the original Axis fault
                                callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                            }
                        } else {
                            callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                        }
                    } else {
                        callback.receiveErrorsI_BPM_REPORT_Out_Sync(f);
                    }
                } else {
                    callback.receiveErrorsI_BPM_REPORT_Out_Sync(error);
                }
            }

            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                onError(fault);
            }

            public void onComplete() {
                try {
                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                } catch (org.apache.axis2.AxisFault axisFault) {
                    callback.receiveErrorsI_BPM_REPORT_Out_Sync(axisFault);
                }
            }
        });


        org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if (_operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
            _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
            _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

        //execute the operation client
        _operationClient.execute(false);

    }


    /**
     *  A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env) {
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }
        return returnMap;
    }


    private javax.xml.namespace.QName[] opNameArray = null;

    private boolean optimizeContent(javax.xml.namespace.QName opName) {


        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;
            }
        }
        return false;
    }

    //http://gppap02.gcl-semi.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BPM_PRD&receiverParty=&receiverService=&interface=SI_BPM_REPORT_Out_Sync&interfaceNamespace=http%3A%2F%2Fgpdap01.gcl-semi.com%2Fbpm
    public static class DT_BPM_REPORT
            implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
                name = DT_BPM_REPORT
                Namespace URI = http://gpdap01.gcl-semi.com/bpm
                Namespace Prefix = ns1
                */


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for WorkflowCode
         */


        protected String localWorkflowCode;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getWorkflowCode() {
            return localWorkflowCode;
        }


        /**
         * Auto generated setter method
         * @param param WorkflowCode
         */
        public void setWorkflowCode(String param) {

            this.localWorkflowCode = param;


        }


        /**
         * field for DocId
         */


        protected String localDocId;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDocIdTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getDocId() {
            return localDocId;
        }


        /**
         * Auto generated setter method
         * @param param DocId
         */
        public void setDocId(String param) {

            if (param != null) {
                //update the setting tracker
                localDocIdTracker = true;
            } else {
                localDocIdTracker = false;

            }

            this.localDocId = param;


        }


        /**
         * field for DocNumber
         */


        protected String localDocNumber;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDocNumberTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getDocNumber() {
            return localDocNumber;
        }


        /**
         * Auto generated setter method
         * @param param DocNumber
         */
        public void setDocNumber(String param) {

            if (param != null) {
                //update the setting tracker
                localDocNumberTracker = true;
            } else {
                localDocNumberTracker = false;

            }

            this.localDocNumber = param;


        }


        /**
         * field for Status
         */


        protected String localStatus;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localStatusTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getStatus() {
            return localStatus;
        }


        /**
         * Auto generated setter method
         * @param param Status
         */
        public void setStatus(String param) {

            if (param != null) {
                //update the setting tracker
                localStatusTracker = true;
            } else {
                localStatusTracker = false;

            }

            this.localStatus = param;


        }


        /**
         * field for OtherData
         */


        protected String localOtherData;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localOtherDataTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getOtherData() {
            return localOtherData;
        }


        /**
         * Auto generated setter method
         * @param param OtherData
         */
        public void setOtherData(String param) {

            if (param != null) {
                //update the setting tracker
                localOtherDataTracker = true;
            } else {
                localOtherDataTracker = false;

            }

            this.localOtherData = param;


        }


        /**
         * field for DataSet
         * This was an Array!
         */


        protected DataSet_type1[] localDataSet;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDataSetTracker = false;


        /**
         * Auto generated getter method
         * @return DataSet_type1[]
         */
        public DataSet_type1[] getDataSet() {
            return localDataSet;
        }


        /**
         * validate the array for DataSet
         */
        protected void validateDataSet(DataSet_type1[] param) {

        }


        /**
         * Auto generated setter method
         * @param param DataSet
         */
        public void setDataSet(DataSet_type1[] param) {

            validateDataSet(param);


            if (param != null) {
                //update the setting tracker
                localDataSetTracker = true;
            } else {
                localDataSetTracker = false;

            }

            this.localDataSet = param;
        }


        /**
         * Auto generated add method for the array for convenience
         * @param param DataSet_type1
         */
        public void addDataSet(DataSet_type1 param) {
            if (localDataSet == null) {
                localDataSet = new DataSet_type1[]{};
            }


            //update the setting tracker
            localDataSetTracker = true;


            java.util.List list =
                    org.apache.axis2.databinding.utils.ConverterUtil.toList(localDataSet);
            list.add(param);
            this.localDataSet =
                    (DataSet_type1[]) list.toArray(
                            new DataSet_type1[list.size()]);

        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, parentQName) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            DT_BPM_REPORT.this.serialize(parentQName, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    parentQName, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            String prefix = null;
            String namespace = null;


            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();

            if ((namespace != null) && (namespace.trim().length() > 0)) {
                String writerPrefix = xmlWriter.getPrefix(namespace);
                if (writerPrefix != null) {
                    xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                } else {
                    if (prefix == null) {
                        prefix = generatePrefix(namespace);
                    }

                    xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
            } else {
                xmlWriter.writeStartElement(parentQName.getLocalPart());
            }

            if (serializeType) {


                String namespacePrefix = registerPrefix(xmlWriter, "http://gpdap01.gcl-semi.com/bpm");
                if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            namespacePrefix + ":DT_BPM_REPORT",
                            xmlWriter);
                } else {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            "DT_BPM_REPORT",
                            xmlWriter);
                }


            }

            namespace = "";
            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "workflowCode", namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);

                } else {
                    xmlWriter.writeStartElement(namespace, "workflowCode");
                }

            } else {
                xmlWriter.writeStartElement("workflowCode");
            }


            if (localWorkflowCode == null) {
                // write the nil attribute

                throw new org.apache.axis2.databinding.ADBException("workflowCode cannot be null!!");

            } else {


                xmlWriter.writeCharacters(localWorkflowCode);

            }

            xmlWriter.writeEndElement();
            if (localDocIdTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "docId", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "docId");
                    }

                } else {
                    xmlWriter.writeStartElement("docId");
                }


                if (localDocId == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("docId cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localDocId);

                }

                xmlWriter.writeEndElement();
            }
            if (localDocNumberTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "docNumber", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "docNumber");
                    }

                } else {
                    xmlWriter.writeStartElement("docNumber");
                }


                if (localDocNumber == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("docNumber cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localDocNumber);

                }

                xmlWriter.writeEndElement();
            }
            if (localStatusTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "status", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "status");
                    }

                } else {
                    xmlWriter.writeStartElement("status");
                }


                if (localStatus == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localStatus);

                }

                xmlWriter.writeEndElement();
            }
            if (localOtherDataTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "otherData", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "otherData");
                    }

                } else {
                    xmlWriter.writeStartElement("otherData");
                }


                if (localOtherData == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("otherData cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localOtherData);

                }

                xmlWriter.writeEndElement();
            }
            if (localDataSetTracker) {
                if (localDataSet != null) {
                    for (int i = 0; i < localDataSet.length; i++) {
                        if (localDataSet[i] != null) {
                            localDataSet[i].serialize(new javax.xml.namespace.QName("", "dataSet"),
                                    factory, xmlWriter);
                        } else {

                            // we don't have to do any thing since minOccures is zero

                        }

                    }
                } else {

                    throw new org.apache.axis2.databinding.ADBException("dataSet cannot be null!!");

                }
            }
            xmlWriter.writeEndElement();


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            java.util.ArrayList elementList = new java.util.ArrayList();
            java.util.ArrayList attribList = new java.util.ArrayList();


            elementList.add(new javax.xml.namespace.QName("",
                    "workflowCode"));

            if (localWorkflowCode != null) {
                elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkflowCode));
            } else {
                throw new org.apache.axis2.databinding.ADBException("workflowCode cannot be null!!");
            }
            if (localDocIdTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "docId"));

                if (localDocId != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocId));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("docId cannot be null!!");
                }
            }
            if (localDocNumberTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "docNumber"));

                if (localDocNumber != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("docNumber cannot be null!!");
                }
            }
            if (localStatusTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "status"));

                if (localStatus != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                }
            }
            if (localOtherDataTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "otherData"));

                if (localOtherData != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOtherData));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("otherData cannot be null!!");
                }
            }
            if (localDataSetTracker) {
                if (localDataSet != null) {
                    for (int i = 0; i < localDataSet.length; i++) {

                        if (localDataSet[i] != null) {
                            elementList.add(new javax.xml.namespace.QName("",
                                    "dataSet"));
                            elementList.add(localDataSet[i]);
                        } else {

                            // nothing to do

                        }

                    }
                } else {

                    throw new org.apache.axis2.databinding.ADBException("dataSet cannot be null!!");

                }

            }

            return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());


        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static DT_BPM_REPORT parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                DT_BPM_REPORT object =
                        new DT_BPM_REPORT();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");
                        if (fullTypeName != null) {
                            String nsPrefix = null;
                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                            }
                            nsPrefix = nsPrefix == null ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

                            if (!"DT_BPM_REPORT".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DT_BPM_REPORT) ExtensionMapper.getTypeObject(
                                        nsUri, type, reader);
                            }


                        }


                    }


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    reader.next();

                    java.util.ArrayList list6 = new java.util.ArrayList();


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "workflowCode").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setWorkflowCode(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {
                        // A start element we are not expecting indicates an invalid parameter was passed
                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "docId").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setDocId(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "docNumber").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setDocNumber(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "status").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setStatus(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "otherData").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setOtherData(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "dataSet").equals(reader.getName())) {


                        // Process the array and step past its final element's end.
                        list6.add(DataSet_type1.Factory.parse(reader));

                        //loop until we find a start element that is not part of this array
                        boolean loopDone6 = false;
                        while (!loopDone6) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();
                            // Step out of this element
                            reader.next();
                            // Step to next element event.
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone6 = true;
                            } else {
                                if (new javax.xml.namespace.QName("", "dataSet").equals(reader.getName())) {
                                    list6.add(DataSet_type1.Factory.parse(reader));

                                } else {
                                    loopDone6 = true;
                                }
                            }
                        }
                        // call the converter utility  to convert and set the array

                        object.setDataSet((DataSet_type1[])
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                        DataSet_type1.class,
                                        list6));

                    }  // End of if for expected property start element

                    else {

                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement())
                        // A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    public static class DataSet_type1
            implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
                name = dataSet_type1
                Namespace URI = http://gpdap01.gcl-semi.com/bpm
                Namespace Prefix = ns1
                */


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for DocId
         */


        protected String localDocId;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDocIdTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getDocId() {
            return localDocId;
        }


        /**
         * Auto generated setter method
         * @param param DocId
         */
        public void setDocId(String param) {

            if (param != null) {
                //update the setting tracker
                localDocIdTracker = true;
            } else {
                localDocIdTracker = false;

            }

            this.localDocId = param;


        }


        /**
         * field for DocNumber
         */


        protected String localDocNumber;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDocNumberTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getDocNumber() {
            return localDocNumber;
        }


        /**
         * Auto generated setter method
         * @param param DocNumber
         */
        public void setDocNumber(String param) {

            if (param != null) {
                //update the setting tracker
                localDocNumberTracker = true;
            } else {
                localDocNumberTracker = false;

            }

            this.localDocNumber = param;


        }


        /**
         * field for Status
         */


        protected String localStatus;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localStatusTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getStatus() {
            return localStatus;
        }


        /**
         * Auto generated setter method
         * @param param Status
         */
        public void setStatus(String param) {

            if (param != null) {
                //update the setting tracker
                localStatusTracker = true;
            } else {
                localStatusTracker = false;

            }

            this.localStatus = param;


        }


        /**
         * field for OtherData
         */


        protected String localOtherData;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localOtherDataTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getOtherData() {
            return localOtherData;
        }


        /**
         * Auto generated setter method
         * @param param OtherData
         */
        public void setOtherData(String param) {

            if (param != null) {
                //update the setting tracker
                localOtherDataTracker = true;
            } else {
                localOtherDataTracker = false;

            }

            this.localOtherData = param;


        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, parentQName) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            DataSet_type1.this.serialize(parentQName, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    parentQName, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            String prefix = null;
            String namespace = null;


            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();

            if ((namespace != null) && (namespace.trim().length() > 0)) {
                String writerPrefix = xmlWriter.getPrefix(namespace);
                if (writerPrefix != null) {
                    xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                } else {
                    if (prefix == null) {
                        prefix = generatePrefix(namespace);
                    }

                    xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
            } else {
                xmlWriter.writeStartElement(parentQName.getLocalPart());
            }

            if (serializeType) {


                String namespacePrefix = registerPrefix(xmlWriter, "http://gpdap01.gcl-semi.com/bpm");
                if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            namespacePrefix + ":dataSet_type1",
                            xmlWriter);
                } else {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            "dataSet_type1",
                            xmlWriter);
                }


            }
            if (localDocIdTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "docId", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "docId");
                    }

                } else {
                    xmlWriter.writeStartElement("docId");
                }


                if (localDocId == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("docId cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localDocId);

                }

                xmlWriter.writeEndElement();
            }
            if (localDocNumberTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "docNumber", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "docNumber");
                    }

                } else {
                    xmlWriter.writeStartElement("docNumber");
                }


                if (localDocNumber == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("docNumber cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localDocNumber);

                }

                xmlWriter.writeEndElement();
            }
            if (localStatusTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "status", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "status");
                    }

                } else {
                    xmlWriter.writeStartElement("status");
                }


                if (localStatus == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localStatus);

                }

                xmlWriter.writeEndElement();
            }
            if (localOtherDataTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "otherData", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "otherData");
                    }

                } else {
                    xmlWriter.writeStartElement("otherData");
                }


                if (localOtherData == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("otherData cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localOtherData);

                }

                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            java.util.ArrayList elementList = new java.util.ArrayList();
            java.util.ArrayList attribList = new java.util.ArrayList();

            if (localDocIdTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "docId"));

                if (localDocId != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocId));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("docId cannot be null!!");
                }
            }
            if (localDocNumberTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "docNumber"));

                if (localDocNumber != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("docNumber cannot be null!!");
                }
            }
            if (localStatusTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "status"));

                if (localStatus != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                }
            }
            if (localOtherDataTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "otherData"));

                if (localOtherData != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOtherData));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("otherData cannot be null!!");
                }
            }

            return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());


        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static DataSet_type1 parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                DataSet_type1 object =
                        new DataSet_type1();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");
                        if (fullTypeName != null) {
                            String nsPrefix = null;
                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                            }
                            nsPrefix = nsPrefix == null ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

                            if (!"dataSet_type1".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DataSet_type1) ExtensionMapper.getTypeObject(
                                        nsUri, type, reader);
                            }


                        }


                    }


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    reader.next();


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "docId").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setDocId(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "docNumber").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setDocNumber(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "status").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setStatus(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "otherData").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setOtherData(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement())
                        // A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    public static class MT_BPM_REPORT_RESPONSE
            implements org.apache.axis2.databinding.ADBBean {

        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://gpdap01.gcl-semi.com/bpm",
                "MT_BPM_REPORT_RESPONSE",
                "ns1");


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for MT_BPM_REPORT_RESPONSE
         */


        protected DT_BPM_REPORT_RESPONSE localMT_BPM_REPORT_RESPONSE;


        /**
         * Auto generated getter method
         * @return DT_BPM_REPORT_RESPONSE
         */
        public DT_BPM_REPORT_RESPONSE getMT_BPM_REPORT_RESPONSE() {
            return localMT_BPM_REPORT_RESPONSE;
        }


        /**
         * Auto generated setter method
         * @param param MT_BPM_REPORT_RESPONSE
         */
        public void setMT_BPM_REPORT_RESPONSE(DT_BPM_REPORT_RESPONSE param) {

            this.localMT_BPM_REPORT_RESPONSE = param;


        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            MT_BPM_REPORT_RESPONSE.this.serialize(MY_QNAME, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    MY_QNAME, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            //We can safely assume an element has only one type associated with it

            if (localMT_BPM_REPORT_RESPONSE == null) {
                throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
            }
            localMT_BPM_REPORT_RESPONSE.serialize(MY_QNAME, factory, xmlWriter);


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            //We can safely assume an element has only one type associated with it
            return localMT_BPM_REPORT_RESPONSE.getPullParser(MY_QNAME);

        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static MT_BPM_REPORT_RESPONSE parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                MT_BPM_REPORT_RESPONSE object =
                        new MT_BPM_REPORT_RESPONSE();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {

                            if (reader.isStartElement() && new javax.xml.namespace.QName("http://gpdap01.gcl-semi.com/bpm", "MT_BPM_REPORT_RESPONSE").equals(reader.getName())) {

                                object.setMT_BPM_REPORT_RESPONSE(DT_BPM_REPORT_RESPONSE.Factory.parse(reader));

                            }  // End of if for expected property start element

                            else {
                                // A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            }

                        } else {
                            reader.next();
                        }
                    }  // end of while loop


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    public static class MT_BPM_REPORT
            implements org.apache.axis2.databinding.ADBBean {

        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://gpdap01.gcl-semi.com/bpm",
                "MT_BPM_REPORT",
                "ns1");


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for MT_BPM_REPORT
         */


        protected DT_BPM_REPORT localMT_BPM_REPORT;


        /**
         * Auto generated getter method
         * @return DT_BPM_REPORT
         */
        public DT_BPM_REPORT getMT_BPM_REPORT() {
            return localMT_BPM_REPORT;
        }


        /**
         * Auto generated setter method
         * @param param MT_BPM_REPORT
         */
        public void setMT_BPM_REPORT(DT_BPM_REPORT param) {

            this.localMT_BPM_REPORT = param;


        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, MY_QNAME) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            MT_BPM_REPORT.this.serialize(MY_QNAME, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    MY_QNAME, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            //We can safely assume an element has only one type associated with it

            if (localMT_BPM_REPORT == null) {
                throw new org.apache.axis2.databinding.ADBException("Property cannot be null!");
            }
            localMT_BPM_REPORT.serialize(MY_QNAME, factory, xmlWriter);


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            //We can safely assume an element has only one type associated with it
            return localMT_BPM_REPORT.getPullParser(MY_QNAME);

        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static MT_BPM_REPORT parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                MT_BPM_REPORT object =
                        new MT_BPM_REPORT();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {

                            if (reader.isStartElement() && new javax.xml.namespace.QName("http://gpdap01.gcl-semi.com/bpm", "MT_BPM_REPORT").equals(reader.getName())) {

                                object.setMT_BPM_REPORT(DT_BPM_REPORT.Factory.parse(reader));

                            }  // End of if for expected property start element

                            else {
                                // A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            }

                        } else {
                            reader.next();
                        }
                    }  // end of while loop


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    public static class ExtensionMapper {

        public static Object getTypeObject(String namespaceURI,
                                           String typeName,
                                           javax.xml.stream.XMLStreamReader reader) throws Exception {


            if (
                    "http://gpdap01.gcl-semi.com/bpm".equals(namespaceURI) &&
                            "DT_BPM_REPORT".equals(typeName)) {

                return DT_BPM_REPORT.Factory.parse(reader);


            }


            if (
                    "http://gpdap01.gcl-semi.com/bpm".equals(namespaceURI) &&
                            "dataSet_type1".equals(typeName)) {

                return DataSet_type1.Factory.parse(reader);


            }


            if (
                    "http://gpdap01.gcl-semi.com/bpm".equals(namespaceURI) &&
                            "dataSet_type0".equals(typeName)) {

                return DataSet_type0.Factory.parse(reader);


            }


            if (
                    "http://gpdap01.gcl-semi.com/bpm".equals(namespaceURI) &&
                            "DT_BPM_REPORT_RESPONSE".equals(typeName)) {

                return DT_BPM_REPORT_RESPONSE.Factory.parse(reader);


            }


            throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
        }

    }

    public static class DataSet_type0
            implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
                name = dataSet_type0
                Namespace URI = http://gpdap01.gcl-semi.com/bpm
                Namespace Prefix = ns1
                */


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for Type
         */


        protected String localType;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localTypeTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getType() {
            return localType;
        }


        /**
         * Auto generated setter method
         * @param param Type
         */
        public void setType(String param) {

            if (param != null) {
                //update the setting tracker
                localTypeTracker = true;
            } else {
                localTypeTracker = false;

            }

            this.localType = param;


        }


        /**
         * field for Id
         */


        protected String localId;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localIdTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getId() {
            return localId;
        }


        /**
         * Auto generated setter method
         * @param param Id
         */
        public void setId(String param) {

            if (param != null) {
                //update the setting tracker
                localIdTracker = true;
            } else {
                localIdTracker = false;

            }

            this.localId = param;


        }


        /**
         * field for Number
         */


        protected String localNumber;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localNumberTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getNumber() {
            return localNumber;
        }


        /**
         * Auto generated setter method
         * @param param Number
         */
        public void setNumber(String param) {

            if (param != null) {
                //update the setting tracker
                localNumberTracker = true;
            } else {
                localNumberTracker = false;

            }

            this.localNumber = param;


        }


        /**
         * field for Message
         */


        protected String localMessage;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localMessageTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getMessage() {
            return localMessage;
        }


        /**
         * Auto generated setter method
         * @param param Message
         */
        public void setMessage(String param) {

            if (param != null) {
                //update the setting tracker
                localMessageTracker = true;
            } else {
                localMessageTracker = false;

            }

            this.localMessage = param;


        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, parentQName) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            DataSet_type0.this.serialize(parentQName, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    parentQName, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            String prefix = null;
            String namespace = null;


            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();

            if ((namespace != null) && (namespace.trim().length() > 0)) {
                String writerPrefix = xmlWriter.getPrefix(namespace);
                if (writerPrefix != null) {
                    xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                } else {
                    if (prefix == null) {
                        prefix = generatePrefix(namespace);
                    }

                    xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
            } else {
                xmlWriter.writeStartElement(parentQName.getLocalPart());
            }

            if (serializeType) {


                String namespacePrefix = registerPrefix(xmlWriter, "http://gpdap01.gcl-semi.com/bpm");
                if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            namespacePrefix + ":dataSet_type0",
                            xmlWriter);
                } else {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            "dataSet_type0",
                            xmlWriter);
                }


            }
            if (localTypeTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "type", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "type");
                    }

                } else {
                    xmlWriter.writeStartElement("type");
                }


                if (localType == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localType);

                }

                xmlWriter.writeEndElement();
            }
            if (localIdTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "id", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "id");
                    }

                } else {
                    xmlWriter.writeStartElement("id");
                }


                if (localId == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localId);

                }

                xmlWriter.writeEndElement();
            }
            if (localNumberTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "number", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "number");
                    }

                } else {
                    xmlWriter.writeStartElement("number");
                }


                if (localNumber == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("number cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localNumber);

                }

                xmlWriter.writeEndElement();
            }
            if (localMessageTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "message", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "message");
                    }

                } else {
                    xmlWriter.writeStartElement("message");
                }


                if (localMessage == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("message cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localMessage);

                }

                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            java.util.ArrayList elementList = new java.util.ArrayList();
            java.util.ArrayList attribList = new java.util.ArrayList();

            if (localTypeTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "type"));

                if (localType != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                }
            }
            if (localIdTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "id"));

                if (localId != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                }
            }
            if (localNumberTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "number"));

                if (localNumber != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("number cannot be null!!");
                }
            }
            if (localMessageTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "message"));

                if (localMessage != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessage));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("message cannot be null!!");
                }
            }

            return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());


        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static DataSet_type0 parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                DataSet_type0 object =
                        new DataSet_type0();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");
                        if (fullTypeName != null) {
                            String nsPrefix = null;
                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                            }
                            nsPrefix = nsPrefix == null ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

                            if (!"dataSet_type0".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DataSet_type0) ExtensionMapper.getTypeObject(
                                        nsUri, type, reader);
                            }


                        }


                    }


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    reader.next();


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "type").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setType(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "id").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setId(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "number").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setNumber(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "message").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setMessage(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement())
                        // A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    public static class DT_BPM_REPORT_RESPONSE
            implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
                name = DT_BPM_REPORT_RESPONSE
                Namespace URI = http://gpdap01.gcl-semi.com/bpm
                Namespace Prefix = ns1
                */


        private static String generatePrefix(String namespace) {
            if (namespace.equals("http://gpdap01.gcl-semi.com/bpm")) {
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }


        /**
         * field for Type
         */


        protected String localType;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localTypeTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getType() {
            return localType;
        }


        /**
         * Auto generated setter method
         * @param param Type
         */
        public void setType(String param) {

            if (param != null) {
                //update the setting tracker
                localTypeTracker = true;
            } else {
                localTypeTracker = false;

            }

            this.localType = param;


        }


        /**
         * field for Id
         */


        protected String localId;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localIdTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getId() {
            return localId;
        }


        /**
         * Auto generated setter method
         * @param param Id
         */
        public void setId(String param) {

            if (param != null) {
                //update the setting tracker
                localIdTracker = true;
            } else {
                localIdTracker = false;

            }

            this.localId = param;


        }


        /**
         * field for Number
         */


        protected String localNumber;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localNumberTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getNumber() {
            return localNumber;
        }


        /**
         * Auto generated setter method
         * @param param Number
         */
        public void setNumber(String param) {

            if (param != null) {
                //update the setting tracker
                localNumberTracker = true;
            } else {
                localNumberTracker = false;

            }

            this.localNumber = param;


        }


        /**
         * field for Message
         */


        protected String localMessage;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localMessageTracker = false;


        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getMessage() {
            return localMessage;
        }


        /**
         * Auto generated setter method
         * @param param Message
         */
        public void setMessage(String param) {

            if (param != null) {
                //update the setting tracker
                localMessageTracker = true;
            } else {
                localMessageTracker = false;

            }

            this.localMessage = param;


        }


        /**
         * field for DataSet
         * This was an Array!
         */


        protected DataSet_type0[] localDataSet;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localDataSetTracker = false;


        /**
         * Auto generated getter method
         * @return DataSet_type0[]
         */
        public DataSet_type0[] getDataSet() {
            return localDataSet;
        }


        /**
         * validate the array for DataSet
         */
        protected void validateDataSet(DataSet_type0[] param) {

        }


        /**
         * Auto generated setter method
         * @param param DataSet
         */
        public void setDataSet(DataSet_type0[] param) {

            validateDataSet(param);


            if (param != null) {
                //update the setting tracker
                localDataSetTracker = true;
            } else {
                localDataSetTracker = false;

            }

            this.localDataSet = param;
        }


        /**
         * Auto generated add method for the array for convenience
         * @param param DataSet_type0
         */
        public void addDataSet(DataSet_type0 param) {
            if (localDataSet == null) {
                localDataSet = new DataSet_type0[]{};
            }


            //update the setting tracker
            localDataSetTracker = true;


            java.util.List list =
                    org.apache.axis2.databinding.utils.ConverterUtil.toList(localDataSet);
            list.add(param);
            this.localDataSet =
                    (DataSet_type0[]) list.toArray(
                            new DataSet_type0[list.size()]);

        }


        /**
         * isReaderMTOMAware
         * @return true if the reader supports MTOM
         */
        public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
            boolean isReaderMTOMAware = false;

            try {
                isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
            } catch (IllegalArgumentException e) {
                isReaderMTOMAware = false;
            }
            return isReaderMTOMAware;
        }


        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
                final javax.xml.namespace.QName parentQName,
                final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {


            org.apache.axiom.om.OMDataSource dataSource =
                    new org.apache.axis2.databinding.ADBDataSource(this, parentQName) {

                        public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                            DT_BPM_REPORT_RESPONSE.this.serialize(parentQName, factory, xmlWriter);
                        }
                    };
            return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
                    parentQName, factory, dataSource);

        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
            serialize(parentQName, factory, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
                              final org.apache.axiom.om.OMFactory factory,
                              org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                              boolean serializeType)
                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {


            String prefix = null;
            String namespace = null;


            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();

            if ((namespace != null) && (namespace.trim().length() > 0)) {
                String writerPrefix = xmlWriter.getPrefix(namespace);
                if (writerPrefix != null) {
                    xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                } else {
                    if (prefix == null) {
                        prefix = generatePrefix(namespace);
                    }

                    xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }
            } else {
                xmlWriter.writeStartElement(parentQName.getLocalPart());
            }

            if (serializeType) {


                String namespacePrefix = registerPrefix(xmlWriter, "http://gpdap01.gcl-semi.com/bpm");
                if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            namespacePrefix + ":DT_BPM_REPORT_RESPONSE",
                            xmlWriter);
                } else {
                    writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
                            "DT_BPM_REPORT_RESPONSE",
                            xmlWriter);
                }


            }
            if (localTypeTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "type", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "type");
                    }

                } else {
                    xmlWriter.writeStartElement("type");
                }


                if (localType == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localType);

                }

                xmlWriter.writeEndElement();
            }
            if (localIdTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "id", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "id");
                    }

                } else {
                    xmlWriter.writeStartElement("id");
                }


                if (localId == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localId);

                }

                xmlWriter.writeEndElement();
            }
            if (localNumberTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "number", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "number");
                    }

                } else {
                    xmlWriter.writeStartElement("number");
                }


                if (localNumber == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("number cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localNumber);

                }

                xmlWriter.writeEndElement();
            }
            if (localMessageTracker) {
                namespace = "";
                if (!namespace.equals("")) {
                    prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, "message", namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, "message");
                    }

                } else {
                    xmlWriter.writeStartElement("message");
                }


                if (localMessage == null) {
                    // write the nil attribute

                    throw new org.apache.axis2.databinding.ADBException("message cannot be null!!");

                } else {


                    xmlWriter.writeCharacters(localMessage);

                }

                xmlWriter.writeEndElement();
            }
            if (localDataSetTracker) {
                if (localDataSet != null) {
                    for (int i = 0; i < localDataSet.length; i++) {
                        if (localDataSet[i] != null) {
                            localDataSet[i].serialize(new javax.xml.namespace.QName("", "dataSet"),
                                    factory, xmlWriter);
                        } else {

                            // we don't have to do any thing since minOccures is zero

                        }

                    }
                } else {

                    throw new org.apache.axis2.databinding.ADBException("dataSet cannot be null!!");

                }
            }
            xmlWriter.writeEndElement();


        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix, String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);

            }

            xmlWriter.writeAttribute(namespace, attName, attValue);

        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace, String attName,
                                    String attValue, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attValue);
            }
        }


        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace, String attName,
                                         javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }
            String attributeValue;
            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace, attName, attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }


        /**
         * databinding method to get an XML representation of this object
         *
         */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                throws org.apache.axis2.databinding.ADBException {


            java.util.ArrayList elementList = new java.util.ArrayList();
            java.util.ArrayList attribList = new java.util.ArrayList();

            if (localTypeTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "type"));

                if (localType != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                }
            }
            if (localIdTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "id"));

                if (localId != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                }
            }
            if (localNumberTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "number"));

                if (localNumber != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumber));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("number cannot be null!!");
                }
            }
            if (localMessageTracker) {
                elementList.add(new javax.xml.namespace.QName("",
                        "message"));

                if (localMessage != null) {
                    elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMessage));
                } else {
                    throw new org.apache.axis2.databinding.ADBException("message cannot be null!!");
                }
            }
            if (localDataSetTracker) {
                if (localDataSet != null) {
                    for (int i = 0; i < localDataSet.length; i++) {

                        if (localDataSet[i] != null) {
                            elementList.add(new javax.xml.namespace.QName("",
                                    "dataSet"));
                            elementList.add(localDataSet[i]);
                        } else {

                            // nothing to do

                        }

                    }
                } else {

                    throw new org.apache.axis2.databinding.ADBException("dataSet cannot be null!!");

                }

            }

            return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());


        }


        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {


            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static DT_BPM_REPORT_RESPONSE parse(javax.xml.stream.XMLStreamReader reader) throws Exception {
                DT_BPM_REPORT_RESPONSE object =
                        new DT_BPM_REPORT_RESPONSE();

                int event;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";
                try {

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();


                    if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");
                        if (fullTypeName != null) {
                            String nsPrefix = null;
                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
                            }
                            nsPrefix = nsPrefix == null ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

                            if (!"DT_BPM_REPORT_RESPONSE".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (DT_BPM_REPORT_RESPONSE) ExtensionMapper.getTypeObject(
                                        nsUri, type, reader);
                            }


                        }


                    }


                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();


                    reader.next();

                    java.util.ArrayList list5 = new java.util.ArrayList();


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "type").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setType(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "id").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setId(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "number").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setNumber(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "message").equals(reader.getName())) {

                        String content = reader.getElementText();

                        object.setMessage(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                        reader.next();

                    }  // End of if for expected property start element

                    else {

                    }


                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

                    if (reader.isStartElement() && new javax.xml.namespace.QName("", "dataSet").equals(reader.getName())) {


                        // Process the array and step past its final element's end.
                        list5.add(DataSet_type0.Factory.parse(reader));

                        //loop until we find a start element that is not part of this array
                        boolean loopDone5 = false;
                        while (!loopDone5) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();
                            // Step out of this element
                            reader.next();
                            // Step to next element event.
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone5 = true;
                            } else {
                                if (new javax.xml.namespace.QName("", "dataSet").equals(reader.getName())) {
                                    list5.add(DataSet_type0.Factory.parse(reader));

                                } else {
                                    loopDone5 = true;
                                }
                            }
                        }
                        // call the converter utility  to convert and set the array

                        object.setDataSet((DataSet_type0[])
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                        DataSet_type0.class,
                                        list5));

                    }  // End of if for expected property start element

                    else {

                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement())
                        // A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());


                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }

        }//end of factory class


    }


    private org.apache.axiom.om.OMElement toOM(MT_BPM_REPORT param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


        try {
            return param.getOMElement(MT_BPM_REPORT.MY_QNAME,
                    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }


    }

    private org.apache.axiom.om.OMElement toOM(MT_BPM_REPORT_RESPONSE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


        try {
            return param.getOMElement(MT_BPM_REPORT_RESPONSE.MY_QNAME,
                    org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }


    }


    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, MT_BPM_REPORT param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {


        try {

            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody().addChild(param.getOMElement(MT_BPM_REPORT.MY_QNAME, factory));
            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }


    }


    /* methods to provide back word compatibility */


    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }


    private Object fromOM(
            org.apache.axiom.om.OMElement param,
            Class type,
            java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault {

        try {

            if (MT_BPM_REPORT.class.equals(type)) {

                return MT_BPM_REPORT.Factory.parse(param.getXMLStreamReaderWithoutCaching());


            }

            if (MT_BPM_REPORT_RESPONSE.class.equals(type)) {

                return MT_BPM_REPORT_RESPONSE.Factory.parse(param.getXMLStreamReaderWithoutCaching());


            }

        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
        return null;
    }


}
