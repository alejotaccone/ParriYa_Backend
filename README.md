# 🥩 ParriYa - Backend API

![Version](https://img.shields.io/badge/version-0.0.0-blue.svg)
![Status](https://img.shields.io/badge/status-En%20Desarrollo-orange.svg)

Este repositorio contiene el código fuente del servidor (Backend) y la API RESTful para **ParriYa**, la aplicación móvil de la mejor parrilla de Quilmes, Provincia de Buenos Aires. 

El sistema gestiona la lógica de negocio para el menú digital, la recepción de pedidos con modalidad exclusiva de retiro en el local (Takeaway) y la gestión de reservas de mesas.

## 🚀 Características y Endpoints Principales

La API soporta las siguientes funcionalidades centrales para la app móvil:

* 📖 **Gestión de Menú:** Endpoints para consultar, agregar y actualizar platos, cortes de carne, bebidas y precios.
* 🛍️ **Sistema de Pedidos (Takeaway):** Lógica para procesar carritos de compras y programar horarios de retiro.
* 📅 **Motor de Reservas:** Gestión de disponibilidad de mesas, turnos y cantidad de comensales.
* 👥 **Gestión de Usuarios:** Autenticación y perfiles de clientes.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje / Framework:** [ Java con Spring Boot ]
* **Base de Datos:** [ MySQL ]
* **Autenticación:** [ JWT (JSON Web Tokens) ]
* **Documentación de API:** [ Swagger]

## ⚙️ Instalación y Configuración Local

Si deseas correr este proyecto en tu entorno local, sigue estos pasos:

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/alejotaccone/ParriYa_Backend.git