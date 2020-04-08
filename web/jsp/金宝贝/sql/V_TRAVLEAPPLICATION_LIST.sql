create or replace view V_TRAVLEAPPLICATION_LIST as
select a.requestid lcid,a.BTRBEGDA,a.BTRENDDA,a.HanPER,a.BtrPER,a.Status,
case BtrPER when 1 then '管理员' else (select lastname from hrmresource where id = BtrPER ) end  as lastname ,
 requestname, createdate||createtime as  createfate,requestmark
from formtable_main_932 a,workflow_requestbase b where a.REQUESTID=b.REQUESTID and a.WFStatus='F'