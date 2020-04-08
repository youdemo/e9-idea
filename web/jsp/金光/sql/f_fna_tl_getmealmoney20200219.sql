create or replace function f_fna_tl_getmealmoney(i_zccheck in varchar2,i_wuccheck in varchar2,i_wanccheck  in varchar2, i_zfcheck in varchar2,
i_zc in number,i_wuc in number,i_wac in number,i_zf in number,i_cfzk in number,i_begintime in varchar2,i_endtime in varchar2)
--i_zccheck 早餐check i_wuccheck 午餐check i_wanccheck 晚餐check i_zfcheck 杂费chceck
--i_zc 早餐 i_wuc 午餐 i_wac 晚餐 i_zf 杂费 i_cfzk 杂费折扣 i_begintime 开始时间 i_endtime 结束时间
return  number is
    v_result number(10,2):=0;
    v_zfje number(10,2):=0;
begin
    if i_zccheck = '1' then
        v_result:=v_result+nvl(i_zc,0);
    end if;
     if i_wuccheck = '1' then
        v_result:=v_result+nvl(i_wuc,0);
    end if;
     if i_wanccheck = '1' then
        v_result:=v_result+nvl(i_wac,0);
    end if;
    if i_zfcheck = '1' then
        if i_begintime > '12:00' or i_endtime<='12:00' then
            v_zfje := nvl(i_zf,0.0)/2*nvl(i_cfzk,1);
        else
             v_zfje := nvl(i_zf,0.0)*nvl(i_cfzk,1);
        end if;
    end if;
    return v_result+v_zfje;
end;