<?php

	// JSON array
	$response = array();
	 
	// importando desde Interfaz...
	require_once __DIR__ . '/conect.php';

	// building
	$db = new CONNEX();
	$result = mysqli_query(mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE),"SELECT * FROM news");
	 
	// make sure your db isn´t empty :)
	if (mysqli_num_rows($result) > 0) {
	    // loopeando
	    $response["news"] = array();
	 
	    while ($row = mysqli_fetch_array($result)) {
			// temp user array
			$notice = array();
			$notice["ID"] = $row["ID"];
			$notice["Titulo"] = $row["Titulo"];
			$notice["Subtitulo"] = $row["Subtitulo"];
			$notice["Fecha"] = $row["Fecha"];
			$notice["Categoria"] = $row["Categoria"];
			$notice["Noticia"] = $row["Noticia"];
			$notice["Imagen"] = $row["Imagen"];
			// push single product into final response array

			array_push($response["news"], $notice);
	    }
	    // yey
	    $response["success"] = 1;
	 
	    echo json_encode($response);
	} else {
	    // oh no!!
	    $response["demonios"] = 0;
	    $response["message"] = "No hay notices!!! A trabajar";
	 
	    echo json_encode($response);
	}
?>