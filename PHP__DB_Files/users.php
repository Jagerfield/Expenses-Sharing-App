<?php

include'connect_db.php';

if($_SERVER["REQUEST_METHOD"]=="GET")
{
	getUsers();
}
else if($_SERVER["REQUEST_METHOD"]=="POST")
{
	addUser();
}

function getUsers()
{
	global $connection;
	$msg;
	$users = array();
	$errors = array();
		
	$sql="SELECT * FROM users ORDER BY name ASC;";
	
	$msg = getRecords($sql, $users, $errors);
	
	header('Connect-Type: application/json');
	echo json_encode(array("errors"=>$errors, "users"=>$users));
	mysqli_close($connection);
}

function addUser()
{
	global $connection;
	$errors = array();
	$users = array();
		
	$name;
	$email;
	$mobile;
	$image_url;
	
	if (isset($_POST["name"]))
	{
	  $name = $_POST["name"];
	} 
	else 
	{
	  $name = null;
	  $errors[]="Fail, argument name not posted";
	}
	
	if (isset($_POST["email"]))
	{
	  $email = $_POST["email"];
	} 
	else 
	{
	  $email = null;
	  $errors[]="Fail, argument email not posted";
	}
	
	if (isset($_POST["mobile"]))
	{
	  $mobile = $_POST["mobile"];
	} 
	else 
	{
	  $mobile = null;
	  $errors[]="Fail, argument mobile not posted";
	}
				
	if(isset($_FILES["image"]["name"]))
	{
		$image_url = uploadFile($erros); //Pass argument $erros by referense to gather any upload erros	
	}
	else
	{
		$image_url=""; //Client didn't uploaded an image, allow record to save
	}
		
	if(sizeof($errors) == 0)
	{
		$sql = "Insert into `users` (`id`, `name`, `email`, `mobile`, `image`) VALUES (NULL, '$name', '$email', '$mobile', '$image_url');";											
		insertRecord($sql, $users, $errors);
	}
				
	header('Connect-Type: application/json');
	echo json_encode(array("errors"=>$errors, "users"=>$users));
	mysqli_close($connection);
}

function insertRecord($sql, &$users, &$errors)
{
	global $connection;
	
	if ($connection->query($sql) === TRUE) 
	{
		$last_id = $connection->insert_id;		
		$sql = "Select * from `users` Where `id` = $last_id";		
		getRecords($sql, $users, $errors);
	} 
	else 
	{
		$errors[] = "Fail, insert record query failed";	
		die (mysqli_error($connection));
	}
}

function getRecords($sql, &$users, &$errors)
{
	global $connection;
	$msg;

	$result = $connection->query($sql);
	
	//$result = mysqli_query($connection, $sql);
	
	if($result)
	{
		if ($result->num_rows > 0) 
		{
			// output data of each row
			while($row = $result->fetch_assoc()) 
			{
				$users[] = $row;
			}
			
			$msg="Success";
		} 
		else 
		{
			$errors[] = "Fail, Can't retrieve last row";
			$users = array();
		}
	}
	else
	{
		$msg = "Fail";
		$errors[] = "Fail, returned read table query is null";
	}
	
	return $msg;	
}

function uploadFile(&$errors)
{
	$image_url;
	
	$full_name = $_FILES["image"]["name"];//Contains the name of the file which you have uploaded from the client 
	$file_size =$_FILES["image"]["size"];
	$file_tmp =$_FILES["image"]["tmp_name"];//Contains a copy of your file content on the server 
	$file_type=$_FILES["image"]["type"];
		
	$extensionList= array("jpeg","jpg","png");	

	$value = explode(".", $full_name);
    $file_ext = strtolower(array_pop($value)); 
	
	if(in_array($file_ext,$extensionList)=== false)
	{
		$errors[]="Fail, extension not allowed, please choose a JPEG or PNG file.";
	}

	//Hostinger allows 8mb files. I chose 7mb to be on the safe side. 
	//Converted to byte at http://www.whatsabyte.com/P1/byteconverter.htm
	if($file_size > 7340032) 
	{
		$errors[]='Fail, File size must be no more than 7 MB';
	}
	
	if(sizeof($errors) == 0)
	{  
		$image_url = getImageUrl($full_name, $file_tmp, $file_ext, $errors);
	}	
	
	
	return $image_url;
}

function getImageUrl($full_name, $file_tmp, $file_ext, &$errors)
{
	global $base_url;
	global $image_upload_dir;
	
	$file_name = pathinfo($full_name, PATHINFO_FILENAME); //Extract the file name without the extension
	$file_name = str_replace(" ","_", $file_name);        //Replace all white spaces 

	$imageFileName = $file_name . '_'  . uniqid() . '.' . $file_ext; // Add a uniq id to the file name to avoid name similarities on the db
	$destination_dir = $image_upload_dir . $imageFileName;
	$image_url = $base_url . $destination_dir; //Will be saved in the table.
	$isImageUploaded = move_uploaded_file($file_tmp, $destination_dir);  //Upload file to server
	

	if($isImageUploaded)
	{
		return $image_url;	 
	}
	else
	{
		$errors[]='Fail, image not uploaded, move_uploaded_file failed';
		return "";
	}
}

function IsNullOrEmptyString($value)
{
    return (!isset($value) or strlen(trim($value))== 0);
}

?>









