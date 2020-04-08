package kstjj.doc.send;

/**
 * 公文对象
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-11-04 3:16 下午
 */

public class ECDocument {
    private String title; // 公文标题
    private String code; // 发文文号
    private String subjectWords; // 主题词
    private String sendUnitName; // 发文部门
    private String draftDate; // 发文时间
    private String urgent; // 公文紧急程度
    private String mainSend; // 主送信息
    private String unitCode; // 收文部门
    private String unitName; // 收文部门名称
    private String type; // 公文类别信息
    private String copySend; // 抄送信息
    private String secret; // 公文密级

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getSubjectWords() {
        return subjectWords;
    }

    public String getCopySend() {
        return copySend;
    }

    public String getDraftDate() {
        return draftDate;
    }

    public String getMainSend() {
        return mainSend;
    }

    public String getSecret() {
        return secret;
    }

    public String getSendUnitName() {
        return sendUnitName;
    }

    public String getType() {
        return type;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCopySend(String copySend) {
        this.copySend = copySend;
    }

    public void setDraftDate(String draftDate) {
        this.draftDate = draftDate;
    }

    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setSendUnitName(String sendUnitName) {
        this.sendUnitName = sendUnitName;
    }

    public void setSubjectWords(String subjectWords) {
        this.subjectWords = subjectWords;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
}
