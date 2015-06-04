# taller_de_prog_2_android

Para instalar la aplicación, buscar el apk deseado en el repositorio (/instalables/primer_entrega, etc) y copiarlo al dispositivo android donde se instalará. Luego buscar el archivo en el dispositivo, ejecutarlo, aceptar los permisos, y el programa se instalará. El mismo quedará en la lista de programas del dispositivo bajo el nombre de "MensajerO".


NOTA - ANTES DE EJECUTAR LA APP

El móvil desde donde se ejecute la app debe estar conectado a la misma red wifi que la pc donde está corriendo el backend. Para poder encontrar el servidor, se debe agregar un alias a la lista de hosts del SO. Dicho alias debera tener el nombre de "mensajero". A continuación las indicaciones para hacerlo.

Configurar el host

1 - Verificar cual es la ip de la computadora en la red wifi local con ifconfig
2 - Agregar un alias a dicha ip en la tabla de hosts del sistema operativo. En ubuntu dicho archivo se encuentra en /etc/hosts (ref: https://www.weg.ucar.edu/documentation/hostfile-unix.html) . El alias a agregar para la ip encontrada en el paso 1 será “mensajero”, sin las comillas. La linea a agregar se vera de la siguiente forma: (reemplazar 111.111.111.111 por la ip del punto 1) 

111.111.111.111 mensajero