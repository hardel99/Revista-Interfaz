<?php
	/**
	 * hay clases wauo
	 **/

	class CONNEX {

	    // constructor
	    function __construct() {
			// connecting to database
			$this -> connect();
	    }

	    function __destruct() {
			$this -> close();
	    }

	    function connect() {
			// import database connection variables
			require_once __DIR__ . '/espesifications.php';

			$conoba = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

			// equivalente de cursor
			return $conoba;
	    }

	    function close() {
			mysqli_close();
	    }
	}
?>