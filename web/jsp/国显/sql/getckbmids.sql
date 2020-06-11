create or replace type hr_orgcode_table as object
(
 orgcode varchar2(50)
)
create or replace type hr_table is table of hr_orgcode_table;

create or replace function getckbmids(i_workcode varchar2,i_recordid varchar2,i_othercode varchar2) return hr_table
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
                and ((fgldcode = i_workcode and ('0'=i_recordid or fgldrcd = i_recordid)) or
                    (fzrcode = i_workcode and ('0'=i_recordid or fzrrcd = i_recordid))
                    or orgcode in(
          SELECT REGEXP_SUBSTR(i_othercode, '[^,]+', 1, LEVEL, 'i') item
            FROM dual
          CONNECT BY rownum <= regexp_count(i_othercode, ',') + 1)))
connect by prior orgcode = suporgcode) loop
    v_bmcode_table := hr_orgcode_table(myrow.orgcode);
    pipe row(v_bmcode_table);
  end loop;
  return;
end;