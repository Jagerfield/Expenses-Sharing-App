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
	define('GET_EVENTS', 'GET_EVENTS');
	define('GET_EVENT_PARTICIPANTS', 'GET_EVENT_PARTICIPANTS');
	define('GET_EVENT_TRANSACTIONS', 'GET_EVENT_TRANSACTIONS');
	
	$error;
	$action_type = null;
		
	if (isset($_POST["action_type"]))
	{
	  $action_type = $_POST["action_type"];
	} 
	else 
	{
	  $action_type = null;
	  $error ="Fail, argument action_type value is wrong";
	}
	
	if(IsNullOrEmptyString($action_type))
	{
		return;
	}	
	
	switch ($action_type) 
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
		case GET_EVENTS:
			getEvents();
			break;			
		case GET_EVENT_PARTICIPANTS:
			getEventParticipants();
			break;
		case GET_EVENT_TRANSACTIONS:
			getEventTransactions();
			break;	

		default:

			break;
	}
}

function getEventTransactions()
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
		$remarks[]="Fail, argument event_id value is wrong";
	}
		
	//This gets the events information repeated and lists their participants
	if(sizeof($remarks) == 0)
	{ 
		$sql="SELECT T.transaction_id , T.event_id , T.transaction_issuer , T.transaction_receiver , T.transaction_amount , T.transaction_description , 
		T.transaction_image , T.transaction_created_at , T.transaction_updated_at
		   FROM transactions T 		   
		   WHERE T.event_id = '$event_id';";
		   
		$msg = getRecords($sql, $event_participants, $remarks);	
	}	

	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "event_participants"=>$event_participants));
	mysqli_close($connection);
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
		$remarks[]="Fail, argument event_id value is wrong";
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
	  $error ="Fail, argument admin value is wrong";
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
	  $error ="Fail, argument admin value is wrong";
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

//Get events by Admin, date, or event id
function getEvents()
{
	date_default_timezone_set('UTC');
	global $connection;
	$msg;
	$events = array();
	$event_participants = array();
	$event_transactions = array();
	$event_chat = array();
	$remarks = array();
	$event_admin;
	$event_id;
	$from_date = null;	
	$to_date = null;
	
	if (!IsNullOrEmptyString($_POST["event_admin"]))
	{
	  $event_admin = $_POST["event_admin"];
	} 
	else 
	{
	  $event_admin = null;
	  $remarks[]="Fail, argument event_admin value is wrong";
	}
	
	if (!IsNullOrEmptyString($_POST["event_id"]))
	{
	  $event_id = $_POST["event_id"];
	} 
	else 
	{
	  $event_id = null;
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
	{ 	 
		if (IsNullOrEmptyString($_POST["event_id"]))
		{
			 //Get Event list by an admin - you can include start and end date or not - You caninclude an event admin or not.  WORKS
			$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at, E.event_image
			   FROM events E 		      
			   WHERE E.event_admin = '$event_admin' AND E.event_created_at >= '$from_date' AND E.event_created_at <= '$to_date' 
				;";		
				
				$msg = getRecords($sql, $events, $remarks);
				//header('Connect-Type: application/json');
				//echo json_encode(array("remarks"=>$remarks, "events"=>$events));
		}
		else
		{
			//Get a specific event
			$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at, E.event_image
			   FROM events E 		      
			   WHERE E.event_id = '$event_id'
				;";	
								
			$result = getRecords($sql, $events, $remarks);
			
			//Get participants
			if($result == "Success")
			{
				$sql="SELECT EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
				   M.member_account_status , M.member_image
				   FROM events_participants EP 		    
				   JOIN members M ON M.member_Id = EP.member_id	
				   WHERE $event_id = EP.event_id
				   ;";	
				 
				$result = "";				 
				$result = getRecords($sql, $event_participants, $remarks);
				
				//Get event transactions 
				if($result == "Success")
				{
					$sql="SELECT T.transaction_id , T.transaction_issuer , T.transaction_receiver , T.transaction_amount , T.transaction_description , 
					   T.transaction_image , T.transaction_created_at , T.transaction_updated_at
					   FROM transactions T 		    
					   WHERE T.event_id = $event_id
					   ;";	
					   
					$result = "";				 
					$result = getRecords($sql, $event_transactions, $remarks);
					 
					//Get event chats 
					if($result == "Success")
					{
						$sql="SELECT C.chat_id , C.chat_issuer , C.event_id , C.chat_message
						   FROM chat C 		    
						   WHERE C.event_id = $event_id
						   ;";	
						 
						$result = "";				 
						$result = getRecords($sql, $event_chat, $remarks);								
						
					}						
				}		
			}
		}
	}	
	
	header('Connect-Type: application/json');
	echo json_encode(array("remarks"=>$remarks, "events"=>$events, "event_participants"=>$event_participants, "event_transactions"=>$event_transactions, "event_chat"=>$event_chat));
					
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
	  $remarks[]="Fail, argument event_name value is wrong";
	}
	
	if (isset($_POST["event_admin"]))
	{
	  $event_admin = $_POST["event_admin"];
	} 
	else 
	{
	  $event_admin = null;
	  $remarks[]="Fail, argument event_admin value is wrong";
	}
	
	if (isset($_POST["event_description"]))
	{
	  $event_description = $_POST["event_description"];
	} 
	else 
	{
	  $event_description = null;
	  $remarks[]="Fail, argument event_description value is wrong";
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
	$result = "";

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
			
			$result="Success";
		} 
		else 
		{
			$remarks[] = "Table empty";
			$table = array();
		}
	}
	else
	{
		$result = "Fail";
		$remarks[] = "Fail, returned read table query is null";
	}
	
	return $result;	
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



/*
			//Original
			$sql="SELECT E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at ,
			   EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
			   M.member_account_status , M.member_image
			   FROM events E 		   
			   JOIN events_participants EP ON E.event_Id = EP.event_id 
			   JOIN members M ON M.member_Id = EP.member_id		   
			   WHERE E.event_admin = '$event_admin' AND E.event_created_at >= '$from_date' AND E.event_created_at <= '$to_date';";
			   
			$msg = getRecords($sql, $events, $remarks);
			
		 */
		 
		   /*
		   //Not working well
			$sql="SELECT DISTINCT  E.event_id, E.event_name, E.event_admin , E.event_description , E.event_created_at ,
			   EP.ep_id , EP.event_id , EP.member_id , EP.ep_status , M.member_id , M.member_name , M.gmail , M.udid ,
			   M.member_account_status , M.member_image, T.transaction_id , T.transaction_issuer , T.transaction_receiver , 
			   T.transaction_amount , T.transaction_description , T.transaction_image , T.transaction_created_at , T.transaction_updated_at
			   FROM events E 		   
			   JOIN events_participants EP ON E.event_Id = EP.event_id 
			   JOIN members M ON M.member_Id = EP.member_id	
			   JOIN transactions T ON E.event_id = T.event_Id		   
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
		 
		 */
		 
?>









