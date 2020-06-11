create or replace function getckbmids2(i_workcode varchar2,i_recordid varchar2) return hr_table
  pipelined as
  --根据工号和记录号获取分管和负责部门级下级 区分兼岗
  v_bmcode_table hr_orgcode_table;
begin
  for myrow in (select distinct orgcode
  from uf_hr_orgdata
 where status = '0'
 start with orgcode in
            (select orgcode
               from uf_hr_orgdata
              where status = '0'
                and ((fgldcode = i_workcode and fgldrcd = i_recordid) or
                    (fzrcode = i_workcode and  fzrrcd = i_recordid)))
connect by prior orgcode = suporgcode) loop
    v_bmcode_table := hr_orgcode_table(myrow.orgcode);
    pipe row(v_bmcode_table);
  end loop;
  return;
end;