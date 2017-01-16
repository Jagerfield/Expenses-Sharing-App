<?php

include'connect_db.php';

if($_SERVER["REQUEST_METHOD"]=="POST")
{
	selectPostFunctions();
}

function selectPostFunctions()
{
	define('ADD_EVENT', 'ADD_EVENT');
	define('GET_EVENT_BY_ID', 'GET_EVENT_BY_ID');
	define('GET_ALL_EVENTS_BY_ADMIN', 'GET_ALL_EVENTS_BY_ADMIN');
	define('GET_EVENTS_BETWEEN_DATES', 'GET_EVENTS_BETWEEN_DATES');
	define('GET_EVENT_PARTICIPANTS', 'GET_EVENT_PARTICIPANTS');
	
	$error;
	$post_type = null;
		
	if (isset($_POST["post_type"]))
	{
	  $post_type = $_POST["post_type"];
	} 
	else 
	{
	  $post_type = null;
	  $error ="Fail, argument post_type not posted";
	}
	
	if(IsNullOrEmptyString($post_type))
	{
		return;
	}	
	
	switch ($post_type) 
	{
		case ADD_EVENT:
			addEvent();
			break;
		case GET_EVENT_BY_ID:
			getEventById();
			break;
		case GET_ALL_EVENTS_BY_ADMIN:
			getAllEventsByAdmin();
			break;
		case GET_EVENTS_BETWEEN_DATES:
			getEventsBetweenDates();
			break;			
		case GET_EVENT_PARTICIPANTS:
			getEventParticipants();
			break;

		default:

			break;
	}
}

function getEventParticipants()
{
	global $connection;
	$msg;
	$event_participants = array();
	$remarks = array();
	$event_id;
	
	if (!IsNullOrEmptyString($_POST["event_id"]))
	{
	  $event_id = $_POST["event_id"];
	} 
	else 
	{
	  $event_id = null;
	  $remarks[]="Fail, argument event_id not posted";
	}
	
	//This gets the events information repeated and lists their participants
	if(sizeof($remarks) == 0)
	{ 
		$sql="SELECT E.event_id, EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
		   M.member_account_status , M.member_image
		   FROM events E 		   
		   JOIN events_participants EP ON E.event_Id = EP.event_id 
		   JOIN members M ON M.member_Id = EP.member_id		   
		   WHERE E.event_id = '$event_id';";
		   
		$msg = getRecords($sql, $event_participants, $remarks);	
	}	

	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "event_participants"=>$event_participants));
	mysqli_close($connection);
}	

function getAllEventsByAdmin()
{
	global $connection;
	$msg;
	$events = array();
	$remarks = array();
	$admin = null;
	
	if (isset($_POST["key_value"]))
	{
	  $admin = $_POST["key_value"];
	} 
	else 
	{
	  $admin = null;
	  $error ="Fail, argument admin not posted";
	}
	
	if(IsNullOrEmptyString($admin))
	{
	   return;
	}	
		
	$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at ,
		   EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
		   M.member_account_status , M.member_image
		   FROM events E 		   
		   JOIN events_participants EP ON E.event_Id = EP.event_id 
		   JOIN members M ON M.member_Id = EP.member_id
		   WHERE E.event_admin = $admin;";

	$msg = getRecords($sql, $events, $remarks);
	
	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "events"=>$events));
	mysqli_close($connection);
}

function getEventById()
{
	global $connection;
	$msg;
	$events = array();
	$remarks = array();
	$id = null;	
	
	if (isset($_POST["key_value"]))
	{
	  $id = $_POST["key_value"];
	} 
	else 
	{
	  $id = null;
	  $error ="Fail, argument admin not posted";
	}
	
	if(IsNullOrEmptyString($id))
	{
	   return;
	}	
	
	$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at ,
		   EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
		   M.member_account_status , M.member_image
		   FROM events E 		   
		   JOIN events_participants EP ON E.event_Id = EP.event_id 
		   JOIN members M ON M.member_Id = EP.member_id
		   WHERE E.event_id = $id;";
		   	
	$msg = getRecords($sql, $events, $remarks);
	
	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "events"=>$events));
	mysqli_close($connection);
}	

function getEventsBetweenDates()
{
	date_default_timezone_set('UTC');
	global $connection;
	$msg;
	$events = array();
	$remarks = array();
	$event_admin;
	$from_date = null;	
	$to_date = null;
	
	if (!IsNullOrEmptyString($_POST["event_admin"]))
	{
	  $event_admin = $_POST["event_admin"];
	  echo "IF event_admin : $event_admin";
	   echo "<br>";
	} 
	else 
	{
	  $event_admin = null;
	  $remarks[]="Fail, argument event_admin not posted";
	}
	
	if (!IsNullOrEmptyString($_POST['from_date']))
	{
	   $from_date = $_POST['from_date']; //Timestamp as in 	    
	} 
	else 
	{
	  $from_date = "2017-01-01 00:00:00"; //Start of the App	
	  $from_date = strtotime("2017-01-01 00:00:00");
	}
		
	if (!IsNullOrEmptyString($_POST['to_date']))
	{	  
	  $to_date = $_POST['to_date'];	  
	} 
	else 
	{
	  $to_date = strtotime(date('Y-m-d H:i:s')); //Today's date
	}
	
	//This gets the events information repeated and lists their participants
	if(sizeof($remarks) == 0)
	{ /*
		$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at ,
		   EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
		   M.member_account_status , M.member_image
		   FROM events E 		   
		   JOIN events_participants EP ON E.event_Id = EP.event_id 
		   JOIN members M ON M.member_Id = EP.member_id		   
		   WHERE E.event_admin = '$event_admin' AND E.event_created_at >= '$from_date' AND E.event_created_at <= '$to_date';";
		   
		$msg = getRecords($sql, $events, $remarks);
		
	 */
	 
	 
	 //Get Event list - you can include start and end date or not - You caninclude an event admin or not.
	 $sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at, E.event_image
		   FROM events E 		      
		   WHERE E.event_created_at >= '$from_date' AND E.event_created_at <= '$to_date' AND 
		   CASE 
			WHEN E.event_admin IS NOT NULL
			THEN
				E.event_admin = '$event_admin'				
			ELSE
				E.event_admin LIKE '%' 
			END
		   		   
		   ;";
		   
		$msg = getRecords($sql, $events, $remarks);
		
	}	
		   	
	
	
	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "events"=>$events));
	mysqli_close($connection);
}	

function addEvent()
{
	date_default_timezone_set('UTC');
	global $connection;
	$remarks = array();
	$events = array();		
	$event_name;
	$event_admin;
	$event_description;
	$event_created_at = strtotime(date('Y-m-d H:i:s'));
	$event_image_url;	
	
	if (isset($_POST["event_name"]))
	{
	  $event_name = $_POST["event_name"];
	} 
	else 
	{
	  $event_name = null;
	  $remarks[]="Fail, argument event_name not posted";
	}
	
	if (isset($_POST["event_admin"]))
	{
	  $event_admin = $_POST["event_admin"];
	} 
	else 
	{
	  $event_admin = null;
	  $remarks[]="Fail, argument event_admin not posted";
	}
	
	if (isset($_POST["event_description"]))
	{
	  $event_description = $_POST["event_description"];
	} 
	else 
	{
	  $event_description = null;
	  $remarks[]="Fail, argument event_description not posted";
	}
					
	if(isset($_FILES["event_image"]["name"]))
	{
		$event_image_url = uploadFile($erros); //Pass argument $erros by referense to gather any upload erros	
	}
	else
	{
		$event_image_url=""; //Client didn't uploaded an event_image, allow record to save
	}
		
	if(sizeof($remarks) == 0)
	{
		$sql = "Insert into `events` (`event_id`, `event_name`, `event_admin`, `event_description`, `event_created_at`, `event_image`) VALUES (NULL, '$event_name', '$event_admin', '$event_description', '$event_created_at', '$event_image_url');";
		insertRecord($sql, $events, $remarks);
	}
				
	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "events"=>$events));
	mysqli_close($connection);
}

function insertRecord($sql, &$events, &$remarks)
{
	global $connection;
	
	if ($connection->query($sql) === TRUE) 
	{
		$last_id = $connection->insert_id;		
		$sql = "Select * from `events` Where `event_id` = $last_id";
		getRecords($sql, $events, $remarks);
	} 
	else 
	{
		$remarks[] = "Fail, insert record query failed";	
		die (mysqli_error($connection));
	}
}

function getRecords($sql, &$table, &$remarks)
{
	global $connection;
	$msg = "";

	$result = $connection->query($sql);
		
	if($result)
	{
		if ($result->num_rows > 0) 
		{
			// output data of each row
			while($row = $result->fetch_assoc()) 
			{
				$table[] = $row;
			}
			
			$msg="Success";
		} 
		else 
		{
			$remarks[] = "Table empty";
			$table = array();
		}
	}
	else
	{
		$msg = "Fail";
		$remarks[] = "Fail, returned read table query is null";
	}
	
	return $msg;	
}

function uploadFile(&$remarks)
{
	$event_image_url;
	
	$full_name = $_FILES["event_image"]["name"];//Contains the event_name of the file which you have uploaded from the client
	$file_size =$_FILES["event_image"]["size"];
	$file_tmp =$_FILES["event_image"]["tmp_name"];//Contains a copy of your file content on the server
	$file_type=$_FILES["event_image"]["type"];
		
	$extensionList= array("jpeg","jpg","png");	

	$value = explode(".", $full_name);
    $file_ext = strtolower(array_pop($value)); 
	
	if(in_array($file_ext,$extensionList)=== false)
	{
		$remarks[]="Fail, extension not allowed, please choose a JPEG or PNG file.";
	}

	//Hostinger allows 8mb files. I chose 7mb to be on the safe side. 
	//Converted to byte at http://www.whatsabyte.com/P1/byteconverter.htm
	if($file_size > 7340032) 
	{
		$remarks[]='Fail, File size must be no more than 7MB';
	}
	
	if(sizeof($remarks) == 0)
	{  
		$event_image_url = getImageUrl($full_name, $file_tmp, $file_ext, $remarks);
	}
	
	return $event_image_url;
}

function getImageUrl($full_name, $file_tmp, $file_ext, &$remarks)
{
	global $base_url;
	global $image_upload_dir;
	
	$file_name = pathinfo($full_name, PATHINFO_FILENAME); //Extract the file event_name without the extension
	$file_name = str_replace(" ","_", $file_name);        //Replace all white spaces 

	$imageFileName = $file_name . '_'  . uniqid() . '.' . $file_ext; // Add a uniq id to the file event_name to avoid event_name similarities on the db
	$destination_dir = $image_upload_dir . $imageFileName;
	$event_image_url = $base_url . $destination_dir; //Will be saved in the table.
	$isImageUploaded = move_uploaded_file($file_tmp, $destination_dir);  //Upload file to server
	
	if($isImageUploaded)
	{
		return $event_image_url;	 
	}
	else
	{
		$remarks[]='Fail, event_image not uploaded, move_uploaded_file failed';
		return "";
	}
}

function IsNullOrEmptyString($value)
{
    return (!isset($value) or strlen(trim($value))== 0);
}

?>









