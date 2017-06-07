$(document).ready(function() {
	$.fn.goTo = function() {
		var offset = 100;
        $('html, body').animate({
            scrollTop: $(this).offset().top + - offset + 'px'
        }, 'fast');
        return this; // for chaining...
    };
    
	$('.product-category-btn').on('click', function(event) {
        console.log(event);
		location.href = $(this).data('href');
		event.stopPropagation();
		return false;
	});
	
	$('.popover-btn').on('click', function() {
		$(this).focus();
		return false;
	});
    	
    if (gotoProduct)
        $('#product' + gotoProduct).goTo();
        
    // to avoid propagation return false
	$('.delete-product-button').on('click', function() {
		$('#deleteModal' + $(this).data('product-id')).modal('show');
		return false;
	});
});