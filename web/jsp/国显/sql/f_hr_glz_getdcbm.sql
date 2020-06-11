create or replace function f_hr_glz_getdcbm(i_workcode     varchar2,
                                           i_recordid     varchar2,
                                           i_lastmonthday varchar2) return varchar2 as
  --获取员工本月第一个调出部门
  v_result varchar2(10) := '';
begin


  select beforedept into v_result
  from (select beforedept
          from uf_hr_persondata_dt2
         where changetype = '部门调动'
           and beforedept <> afterdept
           and workcode = i_workcode
           and recordid = i_recordid
           and changedate > i_lastmonthday
         order by changedate asc, nvl(seqno, 0) asc)
 where rownum = 1;


     return v_result;

end;