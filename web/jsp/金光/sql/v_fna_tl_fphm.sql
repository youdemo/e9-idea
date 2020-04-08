create or replace view v_fna_tl_fphm as
select b.TraINVNUM as fphm from formtable_main_1123 a,formtable_main_1123_dt2 b,workflow_requestbase c where a.id=b.mainid and a.REQUESTID=c.REQUESTID and c.CURRENTNODETYPE>0
union all
select b.OTHINVNUM as fphm from formtable_main_1123 a,formtable_main_1123_dt3 b,workflow_requestbase c where a.id=b.mainid and a.REQUESTID=c.REQUESTID and c.CURRENTNODETYPE>0
union all
select b.HOINVNUM as fphm from formtable_main_1123 a,formtable_main_1123_dt5 b,workflow_requestbase c where a.id=b.mainid and a.REQUESTID=c.REQUESTID and c.CURRENTNODETYPE>0;
