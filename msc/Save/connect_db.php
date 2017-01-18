<?php

//Define constants
define('server', 'localhost');
define('user', 'root');
define('password', '000000');
define('database', 'database');

// Globals
$connection;
$base_url = 'http://10.0.0.11/retrofit_tutorial/';
$image_upload_dir = 'UploadedFiles/';
 

// Create connection
$connection = new mysqli(server, user, password, database);
 
// Check connection
if ($connection->connect_error) 
 {
    die("Connection failed: " . $connection->connect_error);
 } 

?>