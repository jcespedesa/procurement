function confirmDelete()
{
	var agree=confirm("Are you sure you want to delete this record?");
	
	if(agree)
		return true;
					
	else
		return false;
     				
}

function protectedItem()
{
	alert("This item is protected and cannot be deleted...");
	
	return true;
	
}

function confirmDeletePic()
{
	var agree=confirm("Are you sure you want to delete this picture?");
	
	if(agree)
		return true;
					
	else
		return false;
     				
}

function confirmRetrieve()
{
	var agree=confirm("Are you sure you want to retrieve this request? This will delete all approvals in the document.");
	
	if(agree)
		return true;
					
	else
		return false;
     				
}