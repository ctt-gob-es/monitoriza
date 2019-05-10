
$(document).ready(function() {
	
	var SC_INTERNAL_SERVER_ERROR = 500;
	var SC_NO_CONTENT = 520;
	
	// Agregamos los CSS que sean necesarios
	if (!$('link[href="css/jquery.datetimepicker.css"]').length) {
	    $('<link type="text/css" rel="stylesheet" href="css/jquery.datetimepicker.css">').appendTo('head');
	}

	// Funcion que cierra el fichero actual y vuelve al listado de ficheros
	function onClickCloseFile(event) {
		
		showLoading();
		
		$.ajax("closelogfile", {
	        type: 'POST',
	        success: function(data, status, response) {
	        	hideLoading();
	        	// Cargamos en el componente padre de la tabla el HTML recuperado
				$("#searchLog").parent().html(data);
	        },
	        error: function(err) {
	        	console.log("Error en el cierre del fichero");
	        	hideLoading();
	        	showError("Error en el cierre del fichero");
			}
		});
	}
	
	// Funcion de descarga del fichero de log
	function onClickDownloadLog(event) {
		console.log("Evento: " + event);
		
		clearAlert();
		
		var button = $('#' + event.target.id);
		var filename = event.data.filename;
		
		// Mostramos icono de espera
		button.prop('disabled', true);
		button.html('Descargando...');
		
		$.ajax("downloadlogfile", {
			data: $.param({'filename': filename}),
	        type: 'POST',
	        xhrFields: {
	            responseType: 'blob'
	        },
	        success: function(data, status, response) {
	        	
	        	var zipFilename = filename + ".zip";
	        	var type = response.getResponseHeader('Content-Type');
	        	var blob = typeof File === 'function'
		             ? new File([data], zipFilename, { type: type })
		             : new Blob([data], { type: type });
		        if (typeof window.navigator.msSaveBlob !== 'undefined') {
		             // IE workaround for "HTML7007: One or more blob URLs were revoked by closing 
		        	 //the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
		             window.navigator.msSaveBlob(blob, zipFilename);
		        } else {
		             var URL = window.URL || window.webkitURL;
		             var downloadUrl = URL.createObjectURL(blob);
		
		             if (filename) {
		                 // use HTML5 a[download] attribute to specify filename
		                 var a = document.createElement("a");
		                 // safari doesn't support this yet
		                 if (typeof a.download === 'undefined') {
		                     window.location = downloadUrl;
		                 } else {
		                     a.href = downloadUrl;
		                     a.download = zipFilename;
		                     document.body.appendChild(a);
		                     a.click();
		                     URL.revokeObjectURL(downloadUrl);
		                 }
		             } else {
		                 window.location = downloadUrl;
		             }
		        }
	        	
	        	console.log("Descarga correcta");
	        	
	    		// Se oculta el icono de espera
	        	button.html('Descargar fichero');
	        	button.prop('disabled', false);
	        },
	        error: function(err){
	        	
	        	console.log("Descarga fallida");
	        	
	    		// Se oculta el icono de espera
	        	button.html('Descargar fichero');
	        	button.prop('disabled', false);
	        	
	        	showError("Descarga fallida");
			}
		});
	}
	
	function onClickLastLines(event) {
		
		showLoading();
		
		var lastLinesFormData = {
				logFilename: $('#data-filename').val(),
				charsetName: $('#data-charset').val(),
				numLines: $('#numlines-lastlines').val(),
				more: $('#lastlines-more').val()};
		
		console.log("Solicitamos las ultimas lineas");
		
		$.ajax("loglastlines", {
			data: JSON.stringify(lastLinesFormData),
			contentType : 'application/json; charset=utf-8',
	        type: 'POST',
	        success: function(data, status, response) {

	        	console.log("Exito");
	        	hideLoading();
	        	clearAlert();
	        	
	        	if (!data) {
	        		data = '';
	        	}
	        	
	        	if ($('#lastlines-more').val() === 'true') {
	        		$('#lastlines-result').val($('#lastlines-result').val() + data);
	        	}
	        	else {
	        		$('#lastlines-result').val(data);
		        	
		        	$('#lastlines-reset-button').removeClass('hidden');
		        	$('#lastlines-more').val('true');
		        	$('#lastlines-button').html('M&aacute;s');
		        }

	        },
	        error: function(errorMsg, status, response) {
	        	
	        	console.log("Error");
	        	hideLoading();
	        	
	        	if (errorMsg.status == 401) {
	        		showError('Sesi&oacute;n caducada');
	        	} else if (errorMsg.statusText) {
	        		showError(errorMsg.statusText);
	        	}
	        	else {
	        		showError(errorMsg);
	        	}
	        }
		});
	}
	
	function onClickLastLinesReset(event) {
		
		clearAlert();
		
		$('#lastlines-reset-button').addClass('hidden');
		$('#lastlines-more').val('false');
		$('#lastlines-button').html('Consultar');
		$('#lastlines-result').val('');
	}
	
	function onClickFilterLogs(event) {
		
		var startDateValid = validate(document.getElementById('start-datetimepicker-filterlogs'));
		var endDateValid = validate(document.getElementById('end-datetimepicker-filterlogs'));
		if (!startDateValid || !endDateValid) {
			return;
		}
		
		var logFilterFormData = {
				startDate: $('#start-datetimepicker-filterlogs').val(),
				endDate: $('#end-datetimepicker-filterlogs').val(),
				level: $('#loglevel-filterlogs').val(),
				numLines: $('#numlines-filterlogs').val(),
				charsetName: $('#data-charset').val(),
				more: $('#filterlogs-more').val()};
		
		showLoading();
		
		$.ajax("logfilter", {
			data: JSON.stringify(logFilterFormData),
			contentType : 'application/json; charset=utf-8',
	        type: 'POST',
	        success: function(data, status, response) {
	        	
	        	console.log("Exito");
	        	hideLoading();
	        	clearAlert();
	        	
	        	if (status == SC_NO_CONTENT || !data) {
	        		showError('No se encuentran m&aacute;s coincidencias.');
	        		return;
	        	}
	        	
	        	if ($('#filterlogs-more').val() === 'true') {
	        		$('#filterlogs-result').val($('#filterlogs-result').val() + data);
	        	}
	        	else {
	        		$('#filterlogs-result').val(data);
		        	
		        	$('#filterlogs-reset-button').removeClass('hidden');
		        	$('#filterlogs-more').val('true');
		        	$('#filterlogs-button').html('M&aacute;s');
	        	}
	        },
	        error: function(errorMsg, status, response) {
	        	console.log("Error al filtrar el log");
	        	hideLoading();
	        	if (errorMsg.status == 401) {
	        		showError('Sesi&oacute;n caducada');
	        	} else if (errorMsg.statusText) {
	        		showError(errorMsg.statusText);
	        	} else {
	        		showError(errorMsg);
	        	}
	        }
		});
	}
	
	function onClickFilterLogsReset(event) {
		
		clearAlert();

		$('#filterlogs-reset-button').addClass('hidden');
		$('#filterlogs-more').val('false');
		$('#filterlogs-button').html('Consultar');
		$('#filterlogs-result').val('');
	}
	
	function onClickSearchText(event) {
				
		var textValid = validate(document.getElementById('text-searchtext'));
		var startDateValid = validate(document.getElementById('start-datetimepicker-searchtext'));
		if (!textValid || !startDateValid) {
			return;
		}
		
		var needMore = true;
		if ($('#searchtext-next').val() === 'true') {
			needMore = markNextText($('#searchtext-result'));
    	}
    	
		if (needMore) {
			
			console.log("Nueva peticion de busqueda");
			
			var searchTextFormData = {
					text: $('#text-searchtext').val(),
					startDate: $('#start-datetimepicker-searchtext').val(),
					numLines: $('#numlines-searchtext').val(),
					charsetName: $('#data-charset').val(),
					more: $('#searchtext-next').val()};
			
			showLoading();
			
			$.ajax("searchtext", {
				data: JSON.stringify(searchTextFormData),
				contentType : 'application/json; charset=utf-8',
		        type: 'POST',
		        success: function(data, status, response) {
		        	
		        	console.log("Exito");
		        	hideLoading();
		    		clearAlert();
		        	
		        	if (status == SC_NO_CONTENT || !data) {
		        		showError('No se encuentran m&aacute;s coincidencias.');
		        		return;
		        	}
		        	
		        	if (!!data) {
		        		var searchedText = $('#text-searchtext').val();

		        		var idx1 = 0;
		        		var idx2;
		        		var formatedText = '<div>';
		        		while ((idx2 = data.indexOf(searchedText, idx1)) > -1) {
		        			formatedText += data.substring(idx1, idx2) + "<span class='el mon-search-highlight'>" +
		        						searchedText + "</span>";
		        			idx1 = idx2 + searchedText.length;
		        		}
		        		formatedText += data.substring(idx1) + '</div>';

		        		// Si no es la primera llamada, antes de agregar el texto de busqueda, se agrega un separador
		        		// antes del texto para diferenciarlo del anterior
		        		if ($('#searchtext-next').val() === 'true') {
		        			var SEPARATOR = "<div>. . . . . . . . . .</div><div>. . . . . . . . . .</div><div>. . . . . . . . . .</div>";
		        			$('#searchtext-result').append(SEPARATOR);
		        		}
		        		
		        		$('#searchtext-result').append(formatedText);

		        		// Si no es el primer fragmento que se carga, se selecciona el siguiente elemento
		        		if ($('#searchtext-next').val() === 'true') {
		        			markNextText($('#searchtext-result'));
		        		}
		        		// Si es el primer fragmento, se selecciona el primer elemento
		        		else {
		        			markFirstText($('#searchtext-result'));
		        		}
		        	}
		        	
		        	$('#searchtext-reset-button').removeClass('hidden');
		        	$('#searchtext-next').val('true');
		        	$('#searchtext-button').html('Siguiente');
		        },
		        error: function(errorMsg, status, response) {
		        	hideLoading();
		        	if (errorMsg.status == 401) {
		        		showError('Sesi&oacute;n caducada');
		        	} else if (errorMsg.status == SC_NO_CONTENT) {
			        	showError('No se encuentran m&aacute;s resultados');
		        	} else if (errorMsg.statusText) {
		        		showError(errorMsg.statusText);
		        	}
		        	else {
		        		showError(errorMsg);
		        	}
		        }
			});
    	}
	}
	
	function onClickSearchTextReset(event) {
		
		clearAlert();
		
		$('#searchtext-reset-button').addClass('hidden');
		$('#searchtext-next').val('false');
		$('#searchtext-button').html('Consultar');
		$('#searchtext-result').html('');
	}
	
	/**
	  * Hace que deje de estar resaltado el texto actual en el panel de busqueda de texto y que pase a estarlo la
	  * siguiente ocurrencia de ese texto. Tambien controla el scroll del componente de texto para que se muestre
	  * el texto actualmente resaltado.
	  * @returns true si no se encuentran mas ocurrencias del texto, false en caso contrario.
	  */
	 function markFirstText(searchResultPanel) {

		 var allElements = searchResultPanel.find( '.el' );
		 if (allElements.length == 0) {
			 return;
		 }
		 
		 var firstElement = allElements.first();
		 
		 // Resaltamos el primer elemento
		 firstElement.removeClass("mon-search-highlight").addClass("mon-search-current-highlight");
		 
		// Movemos el scroll del panel para que se pueda ver el elemento
		 scrollToElement(searchResultPanel, firstElement);
	 }
	 
	/**
	  * Hace que deje de estar resaltado el texto actual en el panel de busqueda de texto y que
	  * pase a estarlo la siguiente ocurrencia de ese texto. Tambien controla el scroll del
	  * componente de texto para que se muestre el texto actualmente resaltado.
	  * @returns true si es necesario ampliar la busqueda para alcanzar el siguiente elemento (si
	  * lo hubiese), false en caso contrario.
	  */
	 function markNextText(searchResultPanel) {

		 var allElements = searchResultPanel.find( '.el' );
		 
		 // Indicamos que se han encontrado ocurrencias para que no se interprete que hay
		 // que hacer una nueva busqueda ya que sabemos que no se van encontrar
		 if (allElements.length == 0) {
			 return false;
		 }

		 var previousElement;
		 var newElement;
		 allElements.each(function(idx) {
			 if ($(this).hasClass("mon-search-current-highlight")) {
				 previousElement = $(this);
			 }
			 // Si la variable previousElement esta establecida, este es el siguiente elemento a seleccionar
			 else if (previousElement) {
				 previousElement.removeClass("mon-search-current-highlight").addClass("mon-search-highlight");
				 $(this).removeClass("mon-search-highlight").addClass("mon-search-current-highlight");
				 previousElement = null;
				 newElement = $(this);
			 }
		 });

		 // Si se ha seleccionado un nuevo elemento, movemos el scroll del panel para que se vea
		 if (newElement) {
			 scrollToElement(searchResultPanel, newElement);
		 }

		 // Si no hemos encontrado un nuevo elemento, indicamos que sera necesario continuar la busqueda 
		 return !newElement;
	 }

	 /**
	  * Desplaza, si es necesario, las barras de desplazamiento de un panel para que pueda verse un
	  * elemento concreto dentro del mismo.
	  */
	 function scrollToElement(panel, element) {

		 var containerOffset = panel.offset();
		 var elementOffset = element.offset();

		 // Desplazamiento vertical
		 var containerHeight = panel.height();
		 var verticalPos = elementOffset.top - containerOffset.top;

		 if (verticalPos < 0 || verticalPos > containerHeight) {
			 var VERTICAL_PADDING = 20;
			 panel.scrollTop(panel.scrollTop() + verticalPos - VERTICAL_PADDING);
		 }

		 // Desplazamiento horizontal
		 var containerWidth = panel.width();
		 var horizontalPos = elementOffset.left - containerOffset.left;

		 if (horizontalPos > containerWidth) {
			 var HORIZONTAL_PADDING = 80;
			 panel.scrollLeft(panel.scrollLeft() + horizontalPos - HORIZONTAL_PADDING);
		 }
		 else if (horizontalPos < 0) {
			 panel.scrollLeft(0);
		 }
	 }

	function resetPickerStartFilterLogs(supportDate, supportTime, maxDate) {

		$('#start-datetimepicker-filterlogs').datetimepicker({
			dayOfWeekStart: 1,
			step: 10,
			datepicker: supportDate,
			timepicker: supportTime,
			format: (supportDate && supportTime) ? "d/m/Y H:i" : (supportTime ? "H:i" : "d/m/Y"),
			formatDate: "d/m/Y",
			maxDate: maxDate ? maxDate : false,
			lang:"es",
			onChangeDateTime: function (date) {
				var textDate = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
				resetPickerEndFilterLogs(supportDate, supportTime, textDate);
			}
		});
	}

	function resetPickerEndFilterLogs(supportDate, supportTime, minDate) {
		$('#end-datetimepicker-filterlogs').datetimepicker({
			dayOfWeekStart: 1,
			step: 10,
			datepicker: supportDate,
			timepicker: supportTime,
			format: (supportDate && supportTime) ? "d/m/Y H:i" : (supportTime ? "H:i" : "d/m/Y"),
			formatDate: "d/m/Y",
			minDate: minDate ? minDate : false,
			lang:"es",
			onChangeDateTime: function (date) {
				var textDate = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
				resetPickerStartFilterLogs(supportDate, supportTime, textDate);
			}
		});
	}
	
	function resetPickerStartSearchText(supportDate, supportTime) {
		$('#start-datetimepicker-searchtext').datetimepicker({
			dayOfWeekStart: 1,
			step: 10,
			datepicker: supportDate,
			timepicker: supportTime,
			format: (supportDate && supportTime) ? "d/m/Y H:i" : (supportTime ? "H:i" : "d/m/Y"),
			formatDate: "d/m/Y",
			lang:"es"
		});
	}
	
	function assignEnterEvent(element, logAction) {
		element.keyup(function(e) {
			if (e.keyCode == 13) {
				logAction(e);
			}
		});
	}
	
	function clearAlert() {
		$('#resultLogging').removeClass("alert alert-success alert-danger");
		$('#resultLogging').empty();
	}

	function showError(errorMsg) {
		$('#resultLogging').addClass("alert alert-danger");
		$('#resultLogging').html('<strong>' + errorMsg + '</strong>');
	}

	function showLoading() {
		if (loading) {
			loading();
		}
	}
	
	function hideLoading() {
		if (hide) {
			hide();
		}
	}
	
	console.log("Iniciamos la configuracion de la pagina");
	
	// ----- EVENTOS -----
	
	// Boton de cierre de fichero
	$('#close-button').on('click', onClickCloseFile);
	
	// Boton de descarga
	$('#download-button').on('click', {filename: $('#data-filename').val()}, onClickDownloadLog);
	
	// Formulario de consulta de ultimas lineas
	$('#lastlines-button').on('click', onClickLastLines);
	$('#numlines-lastlines').on('change', onClickLastLinesReset);
	$('#lastlines-reset-button').on('click', onClickLastLinesReset);
	
	// Formulario de filtrado de logs
	$('#filterlogs-button').on('click', onClickFilterLogs);
	$('#numlines-filterlogs').on('change', onClickFilterLogsReset);
	$('#start-datetimepicker-filterlogs').on('change', onClickFilterLogsReset);
	$('#start-datetimepicker-filterlogs').on('blur', onClickFilterLogsReset);
	assignEnterEvent($('#start-datetimepicker-filterlogs'), onClickFilterLogs);
	$('#end-datetimepicker-filterlogs').on('change', onClickFilterLogsReset);
	$('#end-datetimepicker-filterlogs').on('blur', onClickFilterLogsReset);
	assignEnterEvent($('#end-datetimepicker-filterlogs'), onClickFilterLogs);
	$('#loglevel-filterlogs').on('change', onClickFilterLogsReset);
	$('#filterlogs-reset-button').on('click', onClickFilterLogsReset);
	
	// Formulario de busqueda de texto
	$('#searchtext-button').on('click', onClickSearchText);
	$('#numlines-searchtext').on('change', onClickSearchTextReset);
	$('#start-datetimepicker-searchtext').on('change', onClickSearchTextReset);
	$('#start-datetimepicker-searchtext').on('blur', onClickSearchTextReset);
	assignEnterEvent($('#start-datetimepicker-searchtext'), onClickSearchText);
	$('#text-searchtext').on('change', onClickSearchTextReset);
	assignEnterEvent($('#text-searchtext'), onClickSearchText);
	$('#searchtext-reset-button').on('click', onClickSearchTextReset);
	
	// Pestanas
	$('#lastlines-tab').on('click', onClickFilterLogsReset);
	$('#lastlines-tab').on('click', onClickSearchTextReset);
	$('#filterlogs-tab').on('click', onClickLastLinesReset);
	$('#filterlogs-tab').on('click', onClickSearchTextReset);
	$('#searchtext-tab').on('click', onClickLastLinesReset);
	$('#searchtext-tab').on('click', onClickFilterLogsReset);
	
	// --------------------

	// Configuramos los elementos de la interfaz grafica que deben aparecer
	var supportDate = $('#data-date').val() == "true";
	var supportTime = $('#data-time').val() == "true";
	var levels = $('#data-levels').val();
	
	// Niveles de log
	if (!levels || levels == 'null') {
		$('#loglevel-filterlogs').append(new Option("", 0));
		$('#loglevel-filterlogs-pane').hide();
	}
	else {
		levels = levels.split(',');
		
		for (var i = 0; i < levels.length; i++) {
			var o = new Option(levels[i], i);
			// Para el funcionamiento en IE8
			$(o).html(levels[i]);
			$('#loglevel-filterlogs').append(o);
		}
	}
	
	// Calendarios
	if (supportDate || supportTime) {
		resetPickerStartFilterLogs(supportDate, supportTime);
		resetPickerEndFilterLogs(supportDate, supportTime);
		resetPickerStartSearchText(supportDate, supportTime);
	
		// Definimos el comportamiento de los calendarios
		$('#start-datetimepicker-filterlogs').change(function (evt){
			var dateText = evt.target.value;
			if (!dateText) {
				resetPickerEndFilterLogs(supportDate, supportTime);
				return;
			}
	
			var dateFragment = dateText.substring(0, dateText.indexOf(" "));
			resetPickerEndFilterLogs(supportDate, supportTime, dateFragment);
		});
		$('#end-datetimepicker-filterlogs').change(function (evt){
			var dateText = evt.target.value;
			if (!dateText) {
				resetPickerStartFilterLogs(supportDate, supportTime);
				return;
			}
	
			var dateFragment = dateText.substring(0, dateText.indexOf(" "));
			resetPickerStartFilterLogs(supportDate, supportTime, dateFragment);
		});
	}
	else {
		$('#datetimepicker-filterlogs-pane').hide();
		$('#datetimepicker-searchtext-pane').hide();
	}

});