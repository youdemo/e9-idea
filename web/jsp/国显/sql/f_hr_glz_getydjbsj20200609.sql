create or replace function f_hr_glz_getydjbsj(i_workcode varchar2,
                                           i_startdate  varchar2,i_worktype varchar2) return number as
  --获取月度加班时间
  v_result number(10,2):=0;
  v_enddate varchar2(20);
begin
  if i_worktype ='标准工时' then
    select to_char(last_day(to_date(i_startdate,'yyyy-mm-dd')),'yyyy-mm-dd') into v_enddate   from dual;
    SELECT nvl(SUM(nvl(a_hours, 0)), 0) into v_result
                  from formtable_main_744_dt1 aa
                 where cday >= i_startdate
                   and cday <= v_enddate
                   and aa.work_code =i_workcode
                   and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%');
  elsif i_worktype ='不定时' then

   select nvl(sum(psjb+zmjb+jjrjb),0) into v_result from uf_hr_jbsj_b where workcode=i_workcode and month=to_char(to_date(i_startdate,'yyyy-mm-dd'),'yyyy-mm');
 end if;
return v_result;

end;