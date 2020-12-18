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
El código de para la primera práctica está en la rama _master_.

## Práctica 2
En esta segunda práctica completaremos el prototipo añadiendo la persistencia mediante JPA.

### Actualización diagrama UML
El diseño UML de la practica anterior ha sido modificado con lo requerido para esta practica, se puede consultar en el siguente enlace :
https://drive.google.com/file/d/1sniQK1NADKzh69AjrTijK3GT25YpkjHx/view?usp=sharing

### Base de datos
Para la base de datos usaremos MySQL ejecutado en Docker. Para ello se debe :

1. **Descargar Docker** : descargamos docker desde [aquí](https://www.docker.com/get-started), seleccionando la versión correspondiente a su sistema operativo. (Para Windows asegurate de instalar WSL 2, para la correcta ejecucion de Docker. El propio instalador lo pedirá, también se puede descargar desde [aquí](https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi)).
2. **Instalación de MySQL** : una vez instalado todo correctamente abrimos una consola, y ejecutamos el comando `docker pull mysql`, una vez hecho esto podemos ver que se instalado bien en la pestaña _Images_ de la aplicación de Docker.
3. **Configuración del contenedor** : por último, configuramos los valores del contenedor para que funcione correctamente en cualquier máquina que ejecute nuestro proyecto. Para ello ejecutamos en la consola:
```
docker run -p 3306:3306 --name ujapack-db -e MYSQL_ROOT_PASSWORD=secreto -e MYSQL_DATABASE=ujapack -e MYSQL_USER=ujapack -e MYSQL_PASSWORD=ujapack -d mysql
```
Este comando hace:

1. **-p 3306:3306** : mapea el puerto para nuestro ordenador.
2. **--name ujapack-db** : nombre del contenedor.
3. **-e MYSQL_ROOT_PASSWORD=secreto** : establece la contraseña del usuario root a _secreto_.
4. **-e MYSQL_DATABASE=ujapack -e MYSQL_USER=ujapack -e MYSQL_PASSWORD=ujapack** : crea una base de datos de nombre _ujapack_, a la que le asocia un nuevo usuario _ujapack_ con la contraseña _ujapack_. Es el equivalente ha hacer `GRANT ALL`.

Si todo salio bien le damos al Play y comprobaremos que ejecuta (si no configuramos algo bien se parara) y que en la pestaña _Inspect_ aparece `Port 3306/tcp localhost:3306` que nos indica el puerto al que debemos conectarnos. Para el desarrollo de la práctica usaremos el puerto por defecto, _3306_.

## Práctica 3
Para la ultima práctica haremos un [cliente web]() para acceder a la API que vamos a realizar.

### Acciones identificadas para la API
Segun lo visto en clase hemos identificado las siguientes acciones:
- POST /ujapack/administrador/envio -> Crear envio y devolver identificador y precio
- GET /ujapack/envio/{id} -> Obtiene el envio con ID. Devuelve envio
- PUT /ujapack/operario/envio/{id}/{idPuntoControl} -> Actualiza el envio con ID. Indicar punto de control y fecha
- PUT /ujapack/operario/envio/{id}/entrega -> Actualiza el envio con ID. Indicar fecha de entrega

Además de lo anterior, y completando la entrega opcional de practicas anteriores, tenemos:
- GET /ujapack/envio/extraviados/{fechas} -> Obtiene los envios extraviados. Indicar opcionalmente fecha de inicio y fin
- GET /ujapack/envio/extraviados/porcentaje/{periodo} -> Obtiene el porcentaje de envios extraviados. Indicar periodo(DIA, MES, ANIO)