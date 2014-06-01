var input = $('#input-a');
input.clockpicker({
	autoclose : true,
	'default' : 'now',

});
$(document).ready(function() {
	// adding todays date as the value to the datepickers.
	var d = new Date();
	var curr_day = d.getDate();
	var curr_month = d.getMonth() + 1; // Months are zero based
	var curr_year = d.getFullYear();
	var eutoday = curr_day + "." + curr_month + "." + curr_year;
	var ustoday = curr_month + "." + curr_day + "." + curr_year;
	$("div.datepicker input").attr('value', eutoday);
	$("div.usdatepicker input").attr('value', ustoday);

	// calling the datepicker for bootstrap plugin
	// https://github.com/eternicode/bootstrap-datepicker
	// http://eternicode.github.io/bootstrap-datepicker/
	$('.datepicker').datepicker({
		language : "de",
		autoclose : true,
		startDate : new Date()
	});
});

$('.clockpicker').clockpicker({
	placement : 'bottom',
	align : 'left',
	donetext : 'Done',
	autoclose : true,
	'default' : 'now'
});
