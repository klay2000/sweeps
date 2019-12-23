function getMap() {
    getSector(1, 1, function(err, data) {
	renderMap(data);
	drawTheBoiz(data.entities);
    });
}

document.onload = function() {
    getMap();
}

function getSector(x, y, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', '/api/getSector', true);
    xhr.responseType = 'json';
    xhr.setRequestHeader('content-type', 'application/json');
    xhr.send(JSON.stringify({"x": x, "y": y }));
    xhr.onload = function() {
	var status = xhr.status;
	if (status === 200) {
	    callback(null, xhr.response);
	} else {
	    callback(status, xhr.response);
	}
    };
}

var getJSON = function(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
	var status = xhr.status;
	if (status === 200) {
	    callback(null, xhr.response);
	} else {
	    callback(status, xhr.response);
	}
    };
    xhr.send();
};


function renderMap(data) {
    console.log(data);
    var grid = document.getElementsByClassName("map-container");
    for (var y=0; y < 25; y++) {
	for (var x=0; x < 25; x++) {
	    var el = document.createElement("div");
	    el.className="cell-container"
	    var c = document.createElement("img");
	    c.id = x + " " + y;
	    c.class = "cell-block";
	    el.appendChild(c);
	    grid[0].appendChild(el);

	    if (data['map'][x][y] == 1) {
		el.className += " active-cell";
	    } else {
		el.className += " inactive-cell";
	    }
	}
    }
}

function drawTheBoiz(entities) {
    console.log(entities);
    for (var i=0; i<entities.length; i++) {
	var img = document.getElementById(entities[i].xposition + " " + entities[i].yposition);
	if (entities[i].type == "energy_source") {
	    img.src = "assets/bolt.svg";
	}
	if (entities[i].type == "boi") {
	    img.style.height = "30px";
	    img.style.width = "30px";
	    img.src = "assets/boi.svg";
	}
    }
}



function getApiKey() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/getNewAPIKey', true);
    xhr.responseType = 'json';
    xhr.send();
    xhr.onload = function() {
	var p = document.getElementById("display");
	p.innerHTML += xhr.response;
    }
}
