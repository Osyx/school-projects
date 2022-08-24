function includeHTML(cb) {
	var z, i, elmnt, file, xhttp;
	z = document.getElementsByTagName("*");
	for (i = 0; i < z.length; i++) {
		elmnt = z[i];
		file = elmnt.getAttribute("include-html");
		if (file) {
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4) {
			if (this.status == 200) {elmnt.innerHTML = this.responseText;}
			if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
				elmnt.removeAttribute("include-html");
				includeHTML(cb);
			}
		}      
		xhttp.open("GET", file, true);
		xhttp.send();
		return;
		}
	}
	if (cb) cb();
};
includeHTML();

$(document).ready(function(e){
    $( document ).on( 'click', '.bs-dropdown-to-select-group .dropdown-menu li', function( event ) {
    	var $target = $( event.currentTarget );
		$target.closest('.bs-dropdown-to-select-group')
			.find('[data-bind="bs-drp-sel-value"]').val($target.attr('data-value'))
			.end()
			.children('.dropdown-toggle').dropdown('toggle');
		$target.closest('.bs-dropdown-to-select-group')
    		.find('[data-bind="bs-drp-sel-label"]').text($target.context.textContent);
		return false;
	});

	var working = false;
    $("#submit").click(function(){
    	$.ajax({
			type: 'POST',
			url: "mysubmitpage.php",
			data: $('#login-form').serialize(), 
			success: function(response) {
					alert("Submitted comment"); 
				},
				error: function() {
					alert("There was an error submitting comment");
				}
		});
	});
});

function login() {
	let form = document.getElementById('login-form');
	let navItems = document.getElementById('navbar-items');
	let div = document.createElement('div');
	div.innerHTML = '<li class="nav-item"><a class="nav-link" href="user.html">User</a></li>'; 
	navItems.appendChild(div.firstChild);
	div = document.createElement('div');
	div.innerHTML = '<p id="login-text" class="navbar-text navbar-right">Bob Hund</p>';
	form.replaceWith(div.firstChild);
}