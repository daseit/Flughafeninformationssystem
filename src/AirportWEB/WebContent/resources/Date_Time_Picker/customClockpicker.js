$('.clockpicker').clockpicker({
	placement : 'bottom',
	align : 'left',
	donetext : 'Done',
	autoclose : true,
	'default' : 'now'
});

$(document).ready(
		function() {
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

			
			//date from "from_date" Datepicker
			//var fromDate = new Date();
			//date from "to_date" Datepicker
			var toDate = new Date();


			var fromdate = new Date();
			//var FromEndDate = new Date();
			//var ToEndDate = new Date();

			//ToEndDate.setDate(ToEndDate.getDate()+365);

			$('.from_date').datepicker({
				language : "de",
			    weekStart: 1,
			    startDate: "now",
			    //endDate: FromEndDate, 
			    autoclose: true
			})
			    .on('changeDate', function(selected){
			    	fromdate = new Date(selected.date.valueOf());
			        //startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
			    	
			        if (fromdate > toDate) {
						toDate.setDate(fromdate);
						$('.to_date').datepicker('setStartDate', fromdate);
						
					$('.to_date').data({date: fromdate});
						$('.to_date').datepicker('update');
						
						//$('.to_date').datepicker().children('h:inputText').val('2012-08-08');
						
						//$(".to_date").datepicker('update', new Date('28.06.2014'));
						
						//$('.to_date').datepicker('setValue', '2014.01.29').datepicker('update');
						
					}
					$('.to_date').datepicker('setStartDate', fromdate);
			      
			    }); 
			$('.to_date')
			    .datepicker({
			    	language : "de",
			        weekStart: 1,
			        startDate: fromdate,
			       // endDate: ToEndDate,
			        autoclose: true
			    })
			    .on('changeDate', function(selected){
			    	
			    	toDate  = new Date(selected.date.valueOf());
			        //FromEndDate = new Date(selected.date.valueOf());
			        //FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
			        //$('.from_date').datepicker('setEndDate', FromEndDate);
			    });

			  
			  

			

		});
