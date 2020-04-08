create view v_jjlcgsq_flow as
select a.requestid,b.requestname,case when b.creater=1 then '系统管理员' else (select lastname from hrmresource where id=b.creater) end as cjr,b.createdate+' '+b.createtime as createtime,a.sqrbm from formtable_main_42 a,workflow_requestbase b where a.requestid=b.requestid  and b.currentnodetype>=3
