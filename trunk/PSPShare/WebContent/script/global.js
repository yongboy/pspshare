document.onkeydown = pageHandler;

var prevPage = null;
var nextPage = null;

function pageHandler(event) {

	event = event ? event : (window.event ? window.event : null);

	if (event.keyCode == 39) {
		if(nextPage){
			location = nextPage;
		}
	}

	if (event.keyCode == 37) {
		if(prevPage){
			location = prevPage;
		}
	}

}