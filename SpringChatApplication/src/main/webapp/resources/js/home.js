$(document).ready(function() {
	var self = $(this);
	self.createUser();
	self.sendMessage();
});

(function($) {

	jQuery.fn.createUser = function() {

		var userName = prompt('Please enter your username.');
		$.get("/SpringChatApplication/creatUser", function(data, status) {
			$('#result').html(data);
		});
	}

	jQuery.fn.sendMessage = function() {

		$.ajax({
			url : "/SpringChatApplication/geteditlist",
			type : "GET",
			success : function(data, textStatus, jqXHR) {
				$.get("/LBAPortal/getads", function(data, status) {
					$('#result').html(data);
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("couldn't submit the data!")
			}
		});
	}

})(jQuery);
