$(document).ready(function() {
	var self = $(this);
	//self.createUser();
	//self.sendMessage();
	self.getUsers();
});

(function($) {

	jQuery.fn.getUsers = function() {
		$(".listname")
				.click(
						function() {
							var chatroomName = ($(this).text()); // gets text contents of clicked li

							$
									.ajax({
										url : "/SpringChatApplication/getChatroom?chatroomName="
												+ chatroomName,
										type : "GET",
										success : function(data, textStatus,
												jqXHR) {
											var length = data.users.length;
											var html = "";
											for (var i = 0; i < length; i++) {
												html += "<p>"
														+ data.users[i].name
														+ "</p>"
											}
											$("#listusersforchatroom").html(

											html);
											var messagesLength = data.messages.length;
											var messagesHtml = "";
											for (var j = 0; j < messagesLength; j++) {

												messagesHtml += "<p>"
														+ data.messages[j].text
														+ "</p>"
											}
											$("#content").html(messagesHtml);

										}
									});
						});
	}

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
