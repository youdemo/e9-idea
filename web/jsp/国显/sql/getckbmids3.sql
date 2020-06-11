create or replace function getckbmids3(i_deptname in varchar2) return hr_table 
--根据名称获取本级和下级部门
  pipelined as
  v_bmcode_table hr_orgcode_table;
begin
  for myrow in (select distinct orgcode
  from uf_hr_orgdata
 where status = '0'
 start with orgcode in
            (select orgcode
               from uf_hr_orgdata
              where status = '0'
                and Upper(orgname) like Upper('%'||i_deptname||'%'))
connect by prior orgcode = suporgcode) loop
    v_bmcode_table := hr_orgcode_table(myrow.orgcode);
    pipe row(v_bmcode_table);
  end loop;
  return;
end;