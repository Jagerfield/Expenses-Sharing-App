<?php

//Define constants
define('server', 'localhost');
define('user', 'root');
define('password', '000000');
define('database', 'database');

// Globals
$connection;
$base_url = 'http://localhost/expenses_sharing_app/';
$image_upload_dir = 'UploadedFiles/';
 

// Create connection
$connection = new mysqli(server, user, password, database);
 
// Check connection
if ($connection->connect_error) 
 {
    die("Connection failed: " . $connection->connect_error);
 } 

?>