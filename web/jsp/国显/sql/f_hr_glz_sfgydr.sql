create or replace function f_hr_glz_sfgydr(i_workcode     varchar2,
                                           i_recordid     varchar2,
                                           i_lastmonthday varchar2,
                                           i_dpcode  varchar2) return varchar2 as
  --判断该人员是否本月调入的
  v_result varchar2(10) := '0'; --0 不是 --1 是
  v_mainid integer;
  v_sfdc integer:=0;--是否调出
  v_sfdr integer:=0;--是否调入
begin
  select id
    into v_mainid
    from uf_hr_persondata
   where workcode = i_workcode
     and recordid = i_recordid;

  select count(1) into v_sfdc
    from uf_hr_persondata_dt2
   where mainid = v_mainid
     and workcode = i_workcode
     and recordid = i_recordid
     and changedate > i_lastmonthday
     and seqno='0'
     and beforedept=i_dpcode;

  select count(1) into v_sfdr
    from uf_hr_persondata_dt2
   where mainid = v_mainid
     and workcode = i_workcode
     and recordid = i_recordid
     and changedate > i_lastmonthday
     and afterdept=i_dpcode;

     if v_sfdc=0 and v_sfdr>0 then
       v_result:='1';
     end if;
     return v_result;

end;
