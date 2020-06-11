create or replace function f_fna_tl_getMealOtherCost(i_begindate in varchar2,i_enddate in varchar2,i_travelgroup  in varchar2,
i_gncldj in varchar2,i_gwcldj in varchar2,i_mdd in varchar2,i_clfa in varchar2,i_type in varchar2,is_SENDCAR in varchar2,i_mainenddate in varchar2,i_mainstartdate in varchar2,i_mainstarttime in varchar2,i_mainendtime in varchar2,i_per in varchar2)
--i_begindate 开始日期 i_enddate 结束日期 i_travelgroup 差旅组 i_gnw 国内外 i_gncldj国内差旅等级
--i_gwcldj 国外差旅等级 i_mdd 城市别 i_rswf 人事范围 i_clfa 差旅方案 i_type类型 0 单日 1 汇总 is_SENDCAR 是否全程派车 0否 1是  i_mainenddate主表差旅结束日期 i_mainstartdate主表差旅开始日期  i_mainstarttime 开始时间 i_mainendtime结束时间 i_per出差人
return  number is
v_result number(10,2):=0.00;
v_rgion varchar2(10):='';
v_ergru varchar2(10):='';--差旅等级
v_count integer:=0;
v_betrg number(10,2):=0.00;
v_country varchar2(100):='';
v_curr varchar2(10) := ''; -- 币种
v_exchange_rate number(18,6) := 1.0; -- 汇率
v_cutmoney  number(10,2):=0.00; -- 扣减金额
v_cutmoney_start  number(10,2):=0.00; -- 扣减金额
v_cutmoney_end  number(10,2):=0.00; -- 扣减金额
begin
  if i_begindate = '' or i_enddate = '' or i_travelgroup= '' or i_gncldj = '' or i_gwcldj = '' or i_mdd=''  or i_clfa='' or is_SENDCAR='' then
    v_result :=0.00;
  else
    select count(1) into v_count from (SELECT REGEXP_SUBSTR(citys, '[^,]+', 1, LEVEL, 'i') item
            FROM uf_fna_PerCNosubsi where  per=i_per
          CONNECT BY rownum <= regexp_count(citys, ',') + 1) a where a.item=i_mdd;
    if v_count>0 then
      return v_result;
    end if;

    select count(1) into v_count from uf_fna_LOC where city=i_mdd;
    if v_count >0 then
      select rgion into v_rgion from uf_fna_LOC where city=i_mdd;
    else
      v_rgion := '';
    end if;
    if v_rgion = '' then
       v_result :=0.00;
    end if;



    select COUNTRYNAME into v_country from ctrip_hotelcity where citystate = '1' and citykey=i_mdd;
    v_count := 0;
     if v_country = '中国' then
      v_ergru := i_gncldj;
    else
      v_ergru := i_gwcldj;
    end if;
    select count(1) into v_count from uf_fna_city_special where speforcity=i_mdd;
    if v_count>0 then
         v_ergru := i_gwcldj;
    end if;
     v_count := 0;
    select count(1) into v_count from uf_fna_BETFZ where morei=i_travelgroup and country=v_country
           and ACTICITY=i_clfa and RGION=v_rgion and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
    if v_count>0 then
        if i_clfa ='20' then
            if is_SENDCAR ='1' then
                 select nvl(BETFZ,0) into v_betrg from uf_fna_BETFZ
                  where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                  and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
            else
                select nvl(BETFZ,0)+nvl(Trasubsidies,0) into v_betrg from uf_fna_BETFZ
                  where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                  and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
            end if;

       else
            if is_SENDCAR ='1' then
                select nvl(F,0)+nvl(M,0)+nvl(A,0)+nvl(BETFZ,0) into v_betrg from uf_fna_BETFZ
                  where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                  and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
            else
                select nvl(F,0)+nvl(M,0)+nvl(A,0)+nvl(BETFZ,0)+nvl(Trasubsidies,0) into v_betrg from uf_fna_BETFZ
                  where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                  and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
            end if;

       end if;
       select curr into v_curr from uf_fna_BETFZ
              where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
              and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
    end if;

     --汇率抓取
    select rate into v_exchange_rate from uf_fna_Curr where curr = v_curr and tcurr='RMB';

    -- 人事范围系数
   -- select ratio into v_ratio from uf_fna_ratio where werks=i_rswf;
    if i_begindate = i_mainstartdate and i_mainstarttime>'12:00' then
			if i_clfa ='20' then
                if is_SENDCAR ='1' then
                     select nvl(BETFZ,0)/2 into v_cutmoney_start from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                else
                    select (nvl(BETFZ,0)+nvl(Trasubsidies,0))/2 into v_cutmoney_start from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                end if;

            else
                if is_SENDCAR ='1' then
                    select nvl(F,0)+nvl(M,0)+nvl(BETFZ,0)/2 into v_cutmoney_start from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                else
                    select nvl(F,0)+nvl(M,0)+(nvl(BETFZ,0)+nvl(Trasubsidies,0))/2 into v_cutmoney_start from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                end if;

            end if;
	 end if;
    if i_enddate = i_mainenddate and i_mainendtime<='12:00' then
			if i_clfa ='20' then
                if is_SENDCAR ='1' then
                     select nvl(BETFZ,0)/2 into v_cutmoney_end from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                else
                    select (nvl(BETFZ,0)+nvl(Trasubsidies,0))/2 into v_cutmoney_end from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                end if;

             else
                if is_SENDCAR ='1' then
                    select nvl(M,0)+nvl(A,0)+nvl(BETFZ,0)/2 into v_cutmoney_end from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                else
                    select nvl(M,0)+nvl(A,0)+(nvl(BETFZ,0)+nvl(Trasubsidies,0))/2 into v_cutmoney_end from uf_fna_BETFZ
                      where morei=i_travelgroup and country=v_country and ACTICITY=i_clfa and RGION=v_rgion
                      and ergru=v_ergru and begda<=i_begindate and endda>=i_begindate;
                end if;

        end if;
	end if;
    v_cutmoney:= v_cutmoney_end+v_cutmoney_start;
    v_cutmoney:=v_cutmoney*v_exchange_rate;
    if i_type = '0' then
       -- v_result:= v_betrg*v_exchange_rate  ;
        v_result:= v_betrg  ;
    else
        if i_mainenddate = i_enddate then
            select round(v_exchange_rate*v_betrg*(TO_DATE(i_enddate, 'YYYY-MM-DD') - TO_DATE(i_begindate, 'YYYY-MM-DD')+1),2) into v_result from dual;
        else
            select round(v_exchange_rate*v_betrg*(TO_DATE(i_enddate, 'YYYY-MM-DD') - TO_DATE(i_begindate, 'YYYY-MM-DD')),2) into v_result from dual;
        end if;
        if v_result>0 then
            v_result := v_result-v_cutmoney;
        end if;
    end if;

  end if;
  return v_result;
end;
