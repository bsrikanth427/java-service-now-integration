CREATE PROCEDURE getResponseSlaConfig()
BEGIN  
	select *, (Now()-created_on)/60000 as cal_time_lapsed
	from incident i, resolution_sla_config rsc
	where i.assignment_group = rsc.assignment_group AND
	i.priority = rsc.priority AND
	i.assigned_to is NULL AND
	status not in ('Closed', 'on Hold', 'Resolved')
	having cal_time_lapsed > rsc.time_lapse AND cal_time_lapsed < rsc.max_time_lapse;
END;

CREATE PROCEDURE getResolutionSlaConfig()
BEGIN  
	select *, (Now()-created_on)/60000 as cal_time_lapsed
	from incident i, resolution_sla_config rsc
	where i.assignment_group = rsc.assignment_group AND
	i.priority = rsc.priority AND
	status not in ('Closed', 'on Hold', 'Resolved')
	having cal_time_lapsed > rsc.time_lapse AND cal_time_lapsed < rsc.max_time_lapse;
END;


CREATE PROCEDURE testProc()
BEGIN  
	select *,(Now()-created_on)/60000 as cal_time_lapsed 
from incident i, response_sla_config rsc
where i.assignment_group = rsc.assignment_group AND
       i.priority = rsc.priority AND
       i.status not in ('Closed', 'on Hold', 'Resolved');
END;

CALL testProc;
drop PROCEDURE getResponseSlaConfig;