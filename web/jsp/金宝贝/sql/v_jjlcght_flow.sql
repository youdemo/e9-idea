create view v_jjlcght_flow as
select a.requestid,b.requestname,case when b.creater=1 then '系统管理员' else (select lastname from hrmresource where id=b.creater) end as cjr,b.createdate+' '+b.createtime as createtime,a.sqr from formtable_main_41 a,workflow_requestbase b where a.requestid=b.requestid  and b.currentnodetype>=3
