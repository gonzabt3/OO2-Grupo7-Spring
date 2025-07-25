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
	    }, 10000);
}

window.logout = async function (redirectUrl = "/usuario/login") {
  try {
    const response = await fetch("/api/auth/logout", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      }
    });

    if (response.ok) {
      window.location.href = redirectUrl;
    } else {
      alert("Error al cerrar sesión");
    }
  } catch (error) {
    alert("Error de red: " + error.message);
  }
};

// Esto debe estar FUERA de la función logout:
document.addEventListener("DOMContentLoaded", function () {
  const logoutLinks = document.querySelectorAll('a[href="/logout"]');

  logoutLinks.forEach(link => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      logout();
    });
  });
});
