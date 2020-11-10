# Prácticas de Desarrollo de Aplicaciones Empresariales
Para las prácticas vamos a diseñar e implementar una capa de dominio para la empresa de paquetería Ujapack.

## Práctica 1
En esta primera práctica vamos a diseñar e implementar una capa de dominio lo mas real posible
para una empresa de paquetería. Principalmente trabajaremos en la definición de beans en un
contenedor IoC de Spring y en la inyección de dependencias. Como fase previa realizaremos un
diseño UML de las clases involucradas aplicando los conocimientos de ingeniería de software de
asignaturas previas.

### Diseño
El diseño UML realizado se puede consultar en el siguente enlace :
https://drive.google.com/file/d/1pJQl7SMtWMg5QCvbhpWATaff0mDwG0Q3/view?usp=sharing

### Código
El codigo de para la primera practica esta en la rama _master_.

## Práctica 2
En esta segunda práctica completaremos el prototipo añadiendo la persistencia mediante JPA.

### Base de datos
Para la base de datos usaremos MySQL ejecutado en Docker. Para ello se debe :

1. **Descargar Docker** : descargamos docker desde [aquí](https://www.docker.com/get-started), seleccionando la versión correspondiente a su sistema operativo. (Para Windows asegurate de instalar WSL 2, para la correcta ejecucion de Docker. El propio instalador lo pedira, tambien se puede descargar desde [aquí](https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi)).
2. **Registrarse en Docker** (_opcional_) : este paso es opcional pero recomendable para mayor comodidad a la hora de trabajar con Docker.
3. **Instalación de MySQL** : una vez instalado todo correctamente abrimos una consola, y ejecutamos el comando `docker pull mysql`, una vez hecho esto podemos ver que se isntalado bien en la pestaña _Images_ de la aplicación de Docker.
4. **Configuración del contenedor** : por último, configuramos los valores del contenedor para que funcione correctamente en cualquier máquina que ejecute nuestro proyecto. Para ello ejecutamos en la consola `docker run --name contenedor_mysql -e MYSQL_ROOT_PASSWORD=secreto -d mysql:latest`.

Si todo salio bien le damos al Play y comprobaremos que ejecuta (si no configuramos algo bien se parara) y que la ultima linea de la consola aparece `Version: '8.0.22' socket: '/var/run/mysqld/mysqld.sock' port: 3306 MySQL Community Server - GPL.` que nos indica la version que se esta usando de MySQL y el puerto al que debemos conectarnos. Para el desarrollo de la practica usaremos el puerto por defecto, _3306_.