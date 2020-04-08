create or replace view v_usedrequest_list as
select to_date(BTRBEGDA ||' '|| BTRBEGTIME ,'yyyy-MM-dd HH24:mi:ss') beginDateTime ,
to_date(BTRENDDA ||' '|| BTRENDTIME ,'yyyy-MM-dd HH24:mi:ss') endDateTime ,
requestid,wfstatus ,BtrPER
from formtable_main_1122
where BTRBEGTIME is  not null and BTRBEGTIME is not null  and wfstatus  in ('Y','C','F','T');
