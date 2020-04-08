create function f_fna_tl_getMealOtherCost(i_begindate in varchar2,i_enddate in varchar2,i_travelgroup  in varchar2, i_gnw in varchar2,
i_gncldj in varchar2,i_gwcldj in varchar2,i_mdd in varchar2,i_clfa in varchar2,i_type in varchar2)
--i_begindate 开始日期 i_enddate 结束日期 i_travelgroup 差旅组 i_gnw 国内外 i_gncldj国内差旅等级
--i_gwcldj 国外差旅等级 i_mdd 城市别 i_rswf 人事范围 i_clfa 差旅方案 i_type类型 0 单日 1 汇总
return  number is
v_result number(10,2):=0.00;
v_rgion varchar2(10):='';
v_ergru varchar2(10):='';--差旅等级
v_count integer:=0;
v_betrg number(10,2):=0.00;
v_country varchar2(100):='';
v_curr varchar2(10) := ''; -- 币种
v_exchange_rate number(18,6) := 1.0; -- 汇率
begin
  if i_begindate = '' or i_enddate = '' or i_travelgroup= '' or i_gncldj = '' or i_gwcldj = '' or i_mdd=''  or i_clfa=''  then
    v_result :=0.00;
  else

    select count(1) into v_count from uf_fna_LOC where city=i_mdd;
    if v_count >0 then
      select rgion into v_rgion from uf_fna_LOC where city=i_mdd;
    else
      v_rgion := '';
    end if;
    if v_rgion = '' then
       v_result :=0.00;
    end if;

    if i_gnw = '0' then
      v_ergru := i_gncldj;
    elsif i_gnw = '1' then
      v_ergru := i_gwcldj;
    end if;

    select COUNTRYNAME into v_country from ctrip_hotelcity where citystate = '1' and citykey=i_mdd;

    v_count := 0;
    select count(1) into v_count from uf_fna_BETFZ where morei=i_travelgroup and country=v_country
           and ACTICITY=i_clfa and RGION=v_rgion and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
    if v_count>0 then
       select nvl(F,0)+nvl(M,0)+nvl(A,0)+nvl(BETFZ,0) into v_betrg from uf_fna_BETFZ
              where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
              and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
       select curr into v_curr from uf_fna_BETFZ
              where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
              and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
    end if;

    -- 汇率抓取
    select rate into v_exchange_rate from uf_fna_Curr where curr = v_curr;

    -- 人事范围系数
   -- select ratio into v_ratio from uf_fna_ratio where werks=i_rswf;

    if i_type = '0' then
        v_result:= v_betrg * v_exchange_rate ;
    else
        select round(v_exchange_rate*v_betrg*(TO_DATE(i_enddate, 'YYYY-MM-DD') - TO_DATE(i_begindate, 'YYYY-MM-DD')+1),2) into v_result from dual;
    end if;
  end if;
  return v_result;
end;
/

