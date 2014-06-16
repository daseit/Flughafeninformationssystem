$(".closeModal").click(function() {
	$('.modal.in').modal('hide');
});

$('body').on('click','#myReset',function() {
    $('#myModal').modal('hide').remove();

});