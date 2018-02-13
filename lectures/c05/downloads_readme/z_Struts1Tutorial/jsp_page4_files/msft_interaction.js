


function ebCCustomEventHandlers()
{
	// ------------------------ GENERAL EVENTS ---------------------------------

	this.onHandleInteraction = function(objName, intName, strObjID)
	{
		// do something
		var myDU = eval(objName);
		var ebScriptElements = parent.document.getElementsByTagName("SCRIPT");
		for(var i=0;i<ebScriptElements.length;i++)
		{
			if(ebScriptElements[i].src.indexOf("RioTracking2_Rmt.js")>-1)
			{
				var ebScriptArray = ebScriptElements[i].src.split("?");
				var ebKeyArray = ebScriptArray[1].split("&");
				for(var z=0;z<ebKeyArray.length;z++){
					if(ebKeyArray[z].indexOf("cell")>-1)
					{
						var myEbCellCodeArray = ebKeyArray[z].split("=");
						var myEbCellCodeVar = myEbCellCodeArray[1];
					}	
				}
				break;
			}
		}
		
		
		var myEBVideoInteractions = new Array("ebVideoStarted","eb25Per_Played","eb50Per_Played","eb75Per_Played","ebVideoFullPlay","ebVideoPause","ebVideoReplay","ebVideoMute","ebVideoUnmute");
		
		for(var i=0;i<myEBVideoInteractions.length;i++)
		{
			//Send Video Interaction
			if(myEBVideoInteractions[i] == intName)
			{
				//check if any interactions left to report
				if(myDU.interactions[myEBVideoInteractions[i].toLowerCase()].numLeftToReport > 0)
				{
					//send interaction
					RioTracking.processEvent(myDU.adData.nFlightID, myEbCellCodeVar, i+1);
				}
			}                             
		}
	}
}


//gEbBanners[0].displayUnit.adData.customEventHandler = new ebCCustomEventHandler();









