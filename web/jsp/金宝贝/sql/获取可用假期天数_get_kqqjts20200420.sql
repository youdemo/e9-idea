
ALTER function [dbo].[get_kqqjts](@qsrq varchar(20),@ryid varchar(20),@type varchar(20)) 	
--qsrq 起始日期 ryid 人员id type类型 0 年假 1病假
returns decimal(10,1)
as
begin
	declare @rzrq varchar(20),
	@result decimal(10,1),
	@dyts decimal(10,1),--当月天数
	@qnsynj decimal(10,1),--去年剩余年假
	@yynj decimal(10,1),--已用年假
	@dnzdts decimal(10,1),--当年最大天数
	@spzts decimal(10,1)--审批中天数
	set @dyts=0.0
	set @spzts=0.0
	set @qnsynj=0.0
	set @result=0.0
	set @yynj=0.0
	set @dnzdts=0.0
	if @qsrq='' or @ryid='' 
		begin
		 set @result=0.0
		end
	else
	   begin
	    if @type=0
			begin
				select @rzrq=companystartdate from hrmresource where id=@ryid
				select @dnzdts=baseAmount+extraAmount from kq_balanceOfLeave where leaverulesid=10 and resourceid=@ryid and belongyear=substring(@qsrq,0,5) and status=0		 
				if substring(@rzrq,0,5)< substring(@qsrq,0,5)
				 begin
					select @dyts=floor(cast(@dnzdts/12*convert(int, substring(@qsrq, 6, 2)) as numeric(10,2)))+case when (cast(@dnzdts/12*convert(int, substring(@qsrq, 6, 2)) as numeric(10,2))-floor(cast(@dnzdts/12*convert(int, substring(@qsrq, 6, 2)) as numeric(10,2))))>=0.5 then 0.5 else 0 end +2
				 end
				else
					begin
						select @dyts=floor(cast(@dnzdts/(13-convert(int, substring(@rzrq, 6, 2)))*(convert(int, substring(@qsrq, 6, 2))-convert(int, substring(@rzrq, 6, 2))+1) as numeric(10,2)))+case when (cast(@dnzdts/(13-convert(int, substring(@rzrq, 6, 2)))*(convert(int, substring(@qsrq, 6, 2))-convert(int, substring(@rzrq, 6, 2))+1) as numeric(10,2))-floor(cast(@dnzdts/(13-convert(int, substring(@rzrq, 6, 2)))*(convert(int, substring(@qsrq, 6, 2))-convert(int, substring(@rzrq, 6, 2))+1) as numeric(10,2))))>=0.5 then 0.5 else 0 end +2
					end
				
				  select @qnsynj=baseAmount+extraAmount-usedAmount from kq_balanceOfLeave where leaverulesid=10 and resourceid=@ryid and belongyear=substring(@qsrq,0,5)-1 and status=0 and (expirationDate>CONVERT(varchar(100), GETDATE(), 23) or expirationDate is null)
				  select @yynj=usedAmount from kq_balanceOfLeave where leaverulesid=10 and resourceid=@ryid and belongyear=substring(@qsrq,0,5) and status=0		  
				  
				  select @spzts=isnull(sum(qjts),0) from formtable_main_31 a,workflow_requestbase b where a.requestid=b.requestid and b.currentnodetype in(1,2) and a.sqr=@ryid and a.qjlx=10 and substring(a.qjrqqs,0,5)=substring(@qsrq,0,5)
				 if @dyts>@dnzdts 
					begin
						set @dyts=@dnzdts
					end
				  set @result=@dyts+@qnsynj-@yynj-@spzts
			 end
		else if @type=1
			begin
			 select @dyts=convert(int, substring(@qsrq, 6, 2))*0.5
			 select @dnzdts=baseAmount+extraAmount from kq_balanceOfLeave where leaverulesid=12 and resourceid=@ryid and belongyear=substring(@qsrq,0,5)	and status=0	 
			 select @spzts=isnull(sum(qjts),0) from formtable_main_31 a,workflow_requestbase b where a.requestid=b.requestid and b.currentnodetype in(1,2) and a.sqr=@ryid and a.qjlx=12 and substring(a.qjrqqs,0,5)=substring(@qsrq,0,5)

			 if @dyts>@dnzdts 
					begin
						set @dyts=@dnzdts
					end
			  select @yynj=usedAmount from kq_balanceOfLeave where leaverulesid=12 and resourceid=@ryid and belongyear=substring(@qsrq,0,5) and status=0	
			  set @result=@dyts-@yynj-@spzts
			end
	  
	   end
  return @result;
end
GO