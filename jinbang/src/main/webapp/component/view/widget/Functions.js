var ChartCfg = {};
ChartCfg.typeMemu = ['line','bar'];
ChartCfg.detailSql = "select sum(drop_statistics_result) from dop_statistics where drop_kpi_id='101009' GROUP BY drop_date ORDER BY drop_date";
ChartCfg.xs = "select drop_date from dop_statistics where drop_kpi_id='101009' GROUP BY drop_date ORDER BY drop_date";
function day(num){
	var r = [];
  	for(var i=0;i<300&&i<num;i++)
      r.push('2014-10-'+i);
 	return r;
}
function rand(num){
	var data = [];
	for(var i=0;i<num;i++){
		data.push(40*(Math.random()*800).toFixed(0));
	}
	return data;
}