
function cargaMiniApplet(pathToSignFolder){
	//alert("cargaMiniApplet pathToSignFolder: "+pathToSignFolder);
	
	var userUrl = window.location.href;
	userUrl = userUrl.substring(0, userUrl.lastIndexOf('/'));	
	urlBaseMonitoriza = userUrl + "/"+pathToSignFolder;
	
	//alert("functions.js - cargaMiniApplet urlBasePortafirmas: " + urlBasePortafirmas);
	
	if(typeof MiniApplet == 'undefined'){
		alert('Cargando componentes...');
	}	
	
	//alert("urlBasePortafirmas: " + urlBasePortafirmas);
	//MiniApplet.cargarMiniApplet(urlBasePortafirmas+'/sign/miniapplet/', 'DNIEJAVA');
	MiniApplet.setForceWSMode(false);
	MiniApplet.cargarMiniApplet(urlBaseMonitoriza+'../static/js/miniapplet/');
	MiniApplet.setServlets(urlBaseMonitoriza+"/StorageService", urlBaseMonitoriza+"/RetrieveService");
}



///////////////////////
//// AUTENTICACION ////
///////////////////////


function showErrorCallback (errorType, errorMessage) {	
	//alert('showErrorCallback('+errorType+' --- ' + errorMessage + ')');
	//alert('errorType: ' + typeof(errorType));
	if(errorType=='es.gob.afirma.core.AOCancelledOperationException'){
		
	} else if (errorType=='java.io.FileNotFoundException') {
		alert('Error: No se ha seleccionado un fichero de datos válido');
	} else if (errorType=='es.gob.afirma.keystores.AOCertificatesNotFoundException') {
		alert('Error: No se ha encontrado ningún certificado de firma válido');
	} else if(errorType=='es.gob.afirma.keystores.main.common.AOKeystoreAlternativeException') {
		alert('La contraseña indicada no es correcta');
	} else if(errorType=='java.lang.IllegalStateException') {
		alert('La contraseña indicada no es correcta');
	} else {
		alert('Error: Se produjo un error durante la operación de firma: ' + errorType + ' - ' + errorMessage);
	}
	//alert('Before unblock');
	setTimeout(function(){unblockScreen();},1000);	
}

function saveSignatureCallback(signatureB64) {	
	//MiniApplet.saveDataToFile(signatureB64, "Guardar firma electr\u00F3nica", null, null, null);	
	//alert("saveSignatureCallback: " + signatureB64);
	var certificate = signatureB64;
	//alert(certificate);
	eval("autenticar(certificate);");
}


function showLogCallback(errorType, errorMessage) {
	showLog("Type: " + errorType + "\nMessage: " + errorMessage);
}




function authenticate(sucessCallback, filterCert){
	//alert('authenticate('+sucessCallback+")");
	
	try{		
		var algorithm = "SHA1withRSA";
		var format = "CAdES";
		var params = 	"mode=implicit\n";
		
		//alert("Filter: " + filterCert);		
		if (filterCert != null && filterCert != "") {
			params += filterCert;
			//alert("filterCert added to Miniapplet Params: " + params);
		}
		
		//params += 'policyIdentifier!=urn:oid:1.3.6.1.4.1.5734.3.7\n';
		
		//alert("MiniApplet.sign");
		var dataToSignBase64 = MiniApplet.getBase64FromText("session");
		
		//alert("MiniApplet.sign: " + dataToSignBase64);		
		//alert("MiniApplet.params: " + params);
		
		MiniApplet.sign(dataToSignBase64, algorithm, format, params, saveSignatureCallback,showErrorCallback);
	} catch(e) {	
		//alert("Authenticate error: " + e);
		showErrorCallback(MiniApplet.getErrorType(), MiniApplet.getErrorMessage());
		setTimeout(function(){unblockScreen();},1000);
	}
}
