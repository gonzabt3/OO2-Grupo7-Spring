// /js/utils.js
function mostrarMensaje(tipo, texto) {
	const contenedor = document.getElementById('mensaje-container');
	    if (!contenedor) return; // Si no existe el contenedor, salir

	    contenedor.innerHTML = ''; // Limpia mensajes anteriores

	    const alerta = document.createElement('div');
	    alerta.className = `mensaje ${tipo}`;
	    alerta.textContent = texto;

	    contenedor.appendChild(alerta);

	    setTimeout(() => {
	        if (contenedor.contains(alerta)) contenedor.removeChild(alerta);
	    }, 5000);
}