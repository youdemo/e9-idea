package APPDEV.HQ.FNA.UTIL;

import weaver.conn.RecordSet;
import weaver.general.Util;


public class SeqUtil {
    /**
     * 获取差旅申请流水号
     */
    public String getTLAPSeq(){
        RecordSet rs = new RecordSet();
        String seqno = "";
        String sql = "select nvl(seqno,0)+1 as seqno from uf_fna_seqno where type = 'TLAP'";
        rs.execute(sql);
        if(rs.next()){
            seqno = Util.null2String(rs.getString("seqno"));
        }
        sql = "update uf_fna_seqno set seqno="+seqno+" where type='TLAP'";
        rs.execute(sql);
        return seqno;
    }
}
