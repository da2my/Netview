const videos = document.getElementsByClassName('col-sm-3');
var DOMAIN = location.href.replace(/[^/]*$/, "");
const botSeguir = document.getElementById('seguir');

for (const child of videos) {
  
 const video = child.querySelector('video');

	video.onplay = (event) => {
	
		history.pushState(null,"", "favs/"+child.innerText);
		boton = document.getElementById('seguir');
		boton.style.display = '';
	
	};

	video.onpause = (event) => {
		
		history.replaceState(null,"", DOMAIN+"favs");
	 	boton = document.getElementById('seguir');
	    boton.style.display = 'none';
	
	};

}

botSeguir.addEventListener('click', () =>
window.location =  window.location);