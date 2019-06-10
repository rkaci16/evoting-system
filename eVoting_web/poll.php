<?php

//poll.php

include('database_connection.php');

if(isset($_POST["poll_option"]))
{
	$query = "
	INSERT INTO tbl_poll 
	(php_framework) VALUES (:php_framework)
	";
	$data = array(
		':php_framework'		=>	$_POST["poll_option"]
	);
	$statement = $connect->prepare($query);
	$statement->execute($data);
}

?>