
function repaintEditorRedaction() {
	var browser = navigator.appName;
	var version = navigator.appVersion;
	if (document.getElementById('redactionForm:editorTextArea_tbl') != null
			&& document.getElementById('redactionForm:editorTextArea_tbl').style != null) {
		document.getElementById('redactionForm:editorTextArea_tbl').style.width = '100%';
		if (browser != 'Microsoft Internet Explorer') {
			document.getElementById('redactionForm:editorTextArea_ifr').style.height = document
					.getElementById('redactionForm:editorTextArea_ifr').contentDocument.body.offsetHeight
					+ 'px';
			document.getElementById('redactionForm:editorTextArea_ifr').style.height = document
					.getElementById('redactionForm:editorTextArea_ifr').contentDocument.body.scrollHeight
					+ 'px';
			document.getElementById('redactionForm:editorTextArea_ifr').style.height = document
					.getElementById('redactionForm:editorTextArea_ifr').contentDocument.body.scrollHeight
					+ 'px';
		} else {
			document.getElementById('redactionForm:editorTextArea_ifr').style.height = 'auto';
		}
		resizeFontSizeRedaction();
	}
}

function autofitIframe(id) {
	
	try{
		var browser = navigator.appName;
		var version = navigator.appVersion;		
		
		var opera = false;
		var mimetype = false;
		var documentAll = false;
		var documentGetElementById = false;
		try{			
			opera = window.opera;			
			mimetype = document.mimeType;		
			documentAll = document.all;
		} catch(err){
		}		
		
		documentGetElementById = document.getElementById(id);		
		if(documentGetElementById==null){			
			documentGetElementById = parent.document.getElementById(id);			
		}
		
		if (!opera && !mimetype && !documentAll && documentGetElementById) {
			documentGetElementById.style.height = this.document.body.offsetHeight + 'px';
		} else if (documentGetElementById) {
			if (browser == 'Microsoft Internet Explorer') {
				if (version.indexOf('MSIE 7.0')) {
					documentGetElementById.style.overflow = 'visible';
				}
			} else {
				// mozilla
				documentGetElementById.style.height = documentGetElementById.offsetHeight + 'px';
			}
		}
	} catch (e){
		alert('Error autofitIframe (id='+id+')' + e);
	}
}

function resizeFontSizeRedaction() {
	if (document.getElementById('redactionForm:editorTextArea_ifr') != null
			&& document.getElementById('redactionForm:editorTextArea_ifr').contentDocument != null) {
		var pageEditor = document
				.getElementById('redactionForm:editorTextArea_ifr').contentDocument;
		var contentEditor = pageEditor.getElementsByTagName("p");
		var currentFontSize = document.getElementById('redactionForm:fontSize').value
				+ 'px';
		contentEditor[0].style.fontSize = currentFontSize;
	}
}

function resizeFontSize() {	
	if (document.getElementById('requestForm:editorTextArea_ifr') != null
			&& document.getElementById('requestForm:editorTextArea_ifr').contentDocument != null) {
		var pageEditor = document.getElementById('requestForm:editorTextArea_ifr').contentDocument;
		var contentEditor = pageEditor.getElementsByTagName("p");
		var currentFontSize = document.getElementById('requestForm:fontSize').value + 'px';
		
		if(contentEditor.length>0){		
			contentEditor[0].style.fontSize = currentFontSize;
		}
	}
}

function resizeFontSizeFullScreen() {
	var currentFontSize = document.getElementById('requestForm:fontSize').value
			+ 'px';
	if (document.getElementById('requestForm:editorFullScreenTextArea_ifr') != null
			&& document
					.getElementById('requestForm:editorFullScreenTextArea_ifr').contentDocument != null) {
		var pageEditor = document
				.getElementById('requestForm:editorFullScreenTextArea_ifr').contentDocument;
		var contentEditor = pageEditor.getElementsByTagName("p");
		contentEditor[0].style.fontSize = currentFontSize;
	}
}

function repaintEditorRequest() {
	try{
		
		var browser = navigator.appName;
		var version = navigator.appVersion;
		if (document.getElementById('requestForm:editorTextArea_tbl') != null
				&& document.getElementById('requestForm:editorTextArea_tbl').style != null) {
			document.getElementById('requestForm:editorTextArea_tbl').style.width = '100%';
			if (browser != 'Microsoft Internet Explorer') {
				document.getElementById('requestForm:editorTextArea_ifr').style.height = document
						.getElementById('requestForm:editorTextArea_ifr').contentDocument.body.offsetHeight
						+ 'px';
				document.getElementById('requestForm:editorTextArea_ifr').style.height = document
						.getElementById('requestForm:editorTextArea_ifr').contentDocument.body.scrollHeight
						+ 'px';
				document.getElementById('requestForm:editorTextArea_tbl').style.height = document
						.getElementById('requestForm:editorTextArea_ifr').contentDocument.body.scrollHeight
						+ 'px';
			} else {
				document.getElementById('requestForm:editorTextArea_tbl').style.height = 'auto';
			}
			resizeFontSize();
		}	
		autofitIframe('requestForm:editor');
		
	} catch (e){
		alert("Error repaintEditorRequest: " + e);
	}
}



var waitCount = 1;

function hideElement(elementName) {

	var itemToHide = document.getElementById(elementName);
	waitCount = 1;
	itemToHide.style.display = 'none';

}

function fadeOut(elementName) {

	var browser = navigator.appName;

	var itemToFade = document.getElementById(elementName);
	if (itemToFade.style.display == 'block') {
		if (browser.indexOf('Microsoft') > -1) {
			// Microsoft Internet Explorer Case
			if (waitCount == '0') {
				itemToFade.filters[0].Apply();
				itemToFade.style.visibility = 'hidden';
				itemToFade.style.backgroundColor = 'transparent';
				itemToFade.filters[0].Play();
				setTimeout('hideElement(\'' + elementName + '\')', 2000);
			} else {
				waitCount--;
			}

		} else {

			if (waitCount != '0') {
				waitCount--;
			} else if (new Number(itemToFade.style.opacity) > 0) {
				itemToFade.style.opacity = itemToFade.style.opacity - 0.1;
				setTimeout('fadeOut(\'' + elementName + '\')', 100);
				return;
			} else {
				hideElement(elementName);
			}
		}
	} else {
		waitCount = 1;
	}
	setTimeout('fadeOut(\'' + elementName + '\')', 1000);
}

function setFocus(id1, id2) {
	if (id1 != null && document.getElementById(id1) != null) {
		document.getElementById(id1).focus();
	} else if (id2 != null && document.getElementById(id2) != null) {
		document.getElementById(id2).focus();
	}
}

function refreshHeadFormTime() {
	var date = new Date();
	var hours = date.getHours();
	var minutes = date.getMinutes();

	if (minutes < 10) {
		minutes = "0" + minutes;
	}

	document.getElementById('headForm:time').innerHTML = hours + ':' + minutes;
	setTimeout('refreshHeadFormTime()', 1000);
}

function windowSize() {
	var browser = navigator.appName;

	if (browser.indexOf('Microsoft') > -1) {
		document.getElementById("formLogin:windowHeight").value = document.documentElement.clientHeight;
		document.getElementById("formLogin:windowWidth").value = document.documentElement.clientWidth;
	} else {
		document.getElementById("formLogin:windowHeight").value = window.innerHeight;
		document.getElementById("formLogin:windowWidth").value = window.innerWidth;
	}
}

function windowSizeMain() {
	var browser = navigator.appName;

	if (browser.indexOf('Microsoft') > -1) {
		document.getElementById("messageForm:windowHeight").value = document.documentElement.clientHeight;
		document.getElementById("messageForm:windowWidth").value = document.documentElement.clientWidth;
	} else {
		document.getElementById("messageForm:windowHeight").value = window.innerHeight;
		document.getElementById("messageForm:windowWidth").value = window.innerWidth;
	}
}

function refreshFormLoginTime() {
	var date = new Date();
	var hours = date.getHours();
	var minutes = date.getMinutes();

	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	
	try{
		if(document.getElementById('formLogin:time') != null){
			document.getElementById('formLogin:time').innerHTML = hours + ':' + minutes;
		}	
	
		if (document.getElementById('headForm:doingActions') != null
				&& document.getElementById('headForm:doingActions').value == false) {
			Richfaces.hideModalPanel('mp_block');
		}
		if (document.getElementById('formLogin:doingActions') != null
				&& document.getElementById('formLogin:doingActions').value == false) {
			Richfaces.hideModalPanel('mp_block');
		}
		setTimeout('refreshFormLoginTime()', 1000);
	}catch(err){}
}

var sentReady = false;
var sessionEnd = false;

function sesion() {
	var tiempo = document.getElementById('headForm:timeSession').value;

	var tiempo_final = "";

	if (tiempo == -1) {
		tiempo_final = "0";
	} else {
		tiempo = tiempo - 1;
		tiempo_final = tiempo;
	}

	var min = Math.floor(tiempo_final / 60);
	
	document.getElementById("headForm:session_minutes").innerHTML = min;

	document.getElementById("headForm:timeSession").value = tiempo_final;

	if (tiempo_final != "0") {
		setTimeout("sesion()", 1000);
	} else {
		sessionEnd = true;
		var browser = navigator.appName;
		if (browser.indexOf('Microsoft') > -1) {
			Richfaces.showModalPanel('sessionForm:mp_session', {
				top : document.documentElement.clientHeight / 3 + 'px'
			});
		} else {
			Richfaces.showModalPanel('sessionForm:mp_session', {
				top : window.innerHeight / 3 + 'px'
			});
		}
	}
}

function reiniciarSesion() {
	var tiempo = document.getElementById('headForm:maxTimeSession').value;
	document.getElementById('headForm:session_minutes').innerHTML = Math.floor(tiempo / 60 - 1);
	document.getElementById('headForm:timeSession').value = tiempo - 1;
}

function blockScreen() {
	if (document.getElementById('headForm:doingActions') != null) {
		document.getElementById('headForm:doingActions').value = true;
	}
	if (document.getElementById('formLogin:doingActions') != null) {
		document.getElementById('formLogin:doingActions').value = true;
	}
	var browser = navigator.appName;
	if (browser.indexOf('Microsoft') > -1) {
		Richfaces.showModalPanel('blockForm:mp_block', {
			top : document.documentElement.clientHeight / 3 + 'px'
		});
	} else {
		Richfaces.showModalPanel('blockForm:mp_block', {
			top : window.innerHeight / 3 + 'px'
		});
	}
}

function unblockScreen() {
	Richfaces.hideModalPanel('blockForm:mp_block');
}

function selectAll(idCheckbox, idTable) {
	var newValue;
	if (document.getElementById(idCheckbox).checked) {
		newValue = 'checked';
	} else {
		newValue = '';
	}
	var inboxTable = document.getElementById(idTable);

	for (i = 0; i < inboxTable.tBodies.length; i++) {
		for (j = 0; j < inboxTable.tBodies[i].rows.length; j++) {
			// checkbox is in the first cell
			var cellContent = inboxTable.tBodies[i].rows[j].cells[0].innerHTML;
			var idCheckBox = getNameOrId(cellContent);
			var checkBoxInput = document.getElementsByName(idCheckBox)[0];
			if (!checkBoxInput.disabled) {
				checkBoxInput.checked = newValue;
			}
		}
	}
}
function unselectAll(idCheckbox) {
	var check = document.getElementById(idCheckbox);
	if (!check.disabled) {
		check.checked = false;
	}
}

function myTrim(str) {
	if (str.length > 0) {
		if (str.indexOf(' ') == 0) {
			return myTrim(str.substr(1));
		} else if (str.lastIndexOf(' ') == (str.length - 1)) {
			return myTrim(str.substr(0, str.length - 1));
		}
	}

	return str;
}

function checkUsersDuplicated(signers) {
	var duplicatedFound = false;
	if (signers != null && signers != '') {
		var signersArray = signers.split(';');
		for (i = 0; (!duplicatedFound && i < signersArray.length - 1); i++) {
			var first = new String(signersArray[i]);
			var firstTrim = myTrim(first);
			firstTrim = firstTrim.toUpperCase();
			if (signersArray.length > 0) {
				for (j = (i + 1); (!duplicatedFound && j < signersArray.length); j++) {
					var second = new String(signersArray[j]);
					var secondTrim = myTrim(second);
					secondTrim = secondTrim.toUpperCase();
					if (!duplicatedFound && firstTrim != '' && secondTrim != ''
							&& firstTrim == secondTrim && i != j) {
						duplicatedFound = true;
						var browser = navigator.appName;
						if (browser.indexOf('Microsoft') > -1) {
							Richfaces.showModalPanel('user_duplicated', {
								top : document.documentElement.clientHeight / 3
										+ 'px'
							});
						} else {
							Richfaces.showModalPanel('user_duplicated', {
								top : window.innerHeight / 3 + 'px'
							});
						}
						var inputSigners = document
								.getElementById('redactionForm:signersText');
						var start = inputSigners.value.lastIndexOf(second);
						var end = start + second.length;
						inputSigners.value = signers.substr(0, start)
								+ signers.substr(end + 1);
						var errorMessage = document
								.getElementById('userDuplicatedForm:user_duplicated_message');
						errorMessage.innerHTML = errorMessage.innerHTML
								.replace('$user', secondTrim);
					}
				}
			}
		}
	}
}

function removeDuplicatedFromPanel() {
	var errorMessage = document
			.getElementById('userDuplicatedForm:user_duplicated_message');
	var firstPart = errorMessage.innerHTML.substr(0, errorMessage.innerHTML
			.indexOf('"') + 1);
	var secondPart = errorMessage.innerHTML.substr(errorMessage.innerHTML
			.lastIndexOf('"'));
	errorMessage.innerHTML = firstPart + '$user' + secondPart;
}

function resizeToFullScreen() {
	var browser = navigator.appName;

	var navWidth = document.documentElement.clientWidth;
	var navHeight = document.documentElement.clientHeight;
	if (document.getElementById('requestForm') != null) {
		if (browser != 'Microsoft Internet Explorer') {

			// modal panel
			var modalPanel = document
					.getElementById('requestForm:request_text_modalContentDiv');
			modalPanel.style.width = (navWidth - 100) + 'px';
			modalPanel.style.height = (navHeight - 100) + 'px';

			// modal panel shadow
			var modalPanelShadow = document
					.getElementById('requestForm:request_text_modalShadowDiv');
			modalPanelShadow.style.width = (navWidth - 100) + 'px';
			modalPanelShadow.style.height = (navHeight - 100) + 'px';

			// modal panel rich:editor table
			var editorTable = document
					.getElementById('requestForm:editorFullScreenTextArea_tbl');
			editorTable.style.width = '100%';

			// editor iframe
			var editorIframe = document
					.getElementById('requestForm:editorFullScreenTextArea_ifr');
			editorIframe.style.height = (navHeight - 100) + 'px';

		} else {
			// Internet Explorer
			// modal panel
			var modalPanel = document
					.getElementById('requestForm:request_text_modalContentDiv');
			modalPanel.style.width = (navWidth - 100) + 'px';
			modalPanel.style.height = (navHeight - 100) + 'px';

			var editorTable = document
					.getElementById('requestForm:editorFullScreenTextArea_tbl');
			if (editorTable != null) {
				editorTable.style.width = '100%';
			}

			// editor iframe
			var editorIframe = document
					.getElementById('requestForm:editorFullScreenTextArea_ifr');
			if (editorIframe != null) {
				editorIframe.style.height = (navHeight - 200) + 'px';
			}
		}
		resizeFontSizeFullScreen();
	}
}

var auxToChange = false;

function availableCharacters(textArea, availableChars, availableTextId, aux,
		numCharacters) {
	var text = document.getElementById(textArea);
	var numSpan = document.getElementById(availableChars);
	var availableText = document.getElementById(availableTextId);
	numSpan.innerHTML = numCharacters - text.value.length;
	var numNumber = new Number(numSpan.innerHTML);
	if (numNumber < 100) {
		numSpan.style.color = 'red';
		availableText.style.color = 'red';
		if ((numNumber == 1 && !auxToChange) || (auxToChange && numNumber != 1)) {
			if (auxToChange && numNumber != 1) {
				auxToChange = false;
			} else {
				auxToChange = true;
			}
			var aux = availableText.innerHTML;
			var availableAux = document.getElementById(aux);
			availableText.innerHTML = availableAux.value;
			availableAux.value = aux;
		}
		if (numNumber < 0) {
			numSpan.innerHTML = 0;
			text.value = text.value.substr(0, numCharacters);
		}
	} else {
		numSpan.style.color = '';
		availableText.style.color = '';
	}
}

var rowCurrentStyle;

function getNameOrId(htmlText) {
	if (htmlText.indexOf('<') == 0) {
		htmlText = htmlText.substr(1);
	}
	if (htmlText.indexOf('>') == (htmlText.length - 1)) {
		htmlText = htmlText.substring(0, htmlText.length - 1);
	}
	var nameStart;
	var nameEnd;
	if (htmlText.indexOf('name=') != -1) {
		// look for name
		nameStart = htmlText.indexOf('name=') + 'name='.length;
		nameEnd = htmlText.indexOf(' ', nameStart);
		if (nameEnd == -1) {
			nameEnd = htmlText.indexOf('>', nameStart);
		}
		if (nameEnd == -1) {
			nameEnd = htmlText.length;
		}
		var name = htmlText.substring(nameStart, nameEnd);

		if (name.indexOf('"') == 0) {
			name = name.substr(1);
		}
		if (name.indexOf('"') == (name.length - 1)) {
			name = name.substring(0, name.length - 1);
		}
	} else if (htmlText.indexOf('id=') != -1) {
		// look for id
		nameStart = htmlText.indexOf('id=') + 'id='.length;
		nameEnd = htmlText.indexOf(' ', nameStart);
		if (nameEnd == -1) {
			nameEnd = htmlText.length;
		}
		var name = htmlText.substring(nameStart, nameEnd);

		if (name.indexOf('"') == 0) {
			name = name.substr(1);
		}
		if (name.indexOf('"') == (name.length - 1)) {
			name = name.substring(0, name.length - 1);
		}
	}
	return name;

}

function onlyOneSelected(current, idTable) {
	var inboxTable = document.getElementById(idTable);

	for (i = 0; i < inboxTable.tBodies.length; i++) {
		for (j = 0; j < inboxTable.tBodies[i].rows.length; j++) {
			// checkbox is in the first cell
			var cellContent = inboxTable.tBodies[i].rows[j].cells[0].innerHTML;
			var idCheckBox = getNameOrId(cellContent);
			var checkBoxInput = document.getElementsByName(idCheckBox)[0];
			if (current != idCheckBox) {
				checkBoxInput.checked = '';
			}
		}
	}
}
function alwaysOneSelectedRow(current, idTable) {
	var inboxTable = document.getElementById(idTable);
	var anySelected = false;

	for (i = 0; i < inboxTable.tBodies.length; i++) {
		for (j = 0; j < inboxTable.tBodies[i].rows[1].cells.length; j++) {
			// checkboxs are into second row
			var cellContent = inboxTable.tBodies[i].rows[1].cells[j].innerHTML;
			var idCheckBox = getNameOrId(cellContent);
			var checkBoxInput = document.getElementsByName(idCheckBox)[0];
			if (current.id != idCheckBox && checkBoxInput.checked == true) {
				anySelected = true;
			}
		}
	}
	if (!anySelected) {
		current.checked = true;
	}

}

function alwaysOneSelectedColumn(current, idTable) {
	var inboxTable = document.getElementById(idTable);

	for (i = 0; i < inboxTable.tBodies.length; i++) {
		for (j = 0; j < inboxTable.tBodies[i].rows.length; j++) {
			// checkbox is in the first cell
			var cellContent = inboxTable.tBodies[i].rows[j].cells[0].innerHTML;
			var idCheckBox = getNameOrId(cellContent);
			var checkBoxInput = document.getElementsByName(idCheckBox)[0];
			if (current != idCheckBox) {
				checkBoxInput.checked = '';
			} else {
				checkBoxInput.checked = 'checked';
			}
		}
	}
}

function blockScreenOnScroller(event) {
	if (event.target == null) {
		event.target = event.srcElement;
	}
	if (event.target.id.indexOf('TableScroller') == -1) {
		if (event.target.onclick != null) {
			blockScreen();
		}
	}
}

var clickedFilterId = '';

function paintTableRows(cellId) {
	clickedFilterId = cellId;
	var tableToPaint = document.getElementById('adminForm:jobsTable');
	var rows = tableToPaint.rows;
	for (i = 1; i < rows.length; i++) {
		var cells = rows[i].cells;
		for (j = 0; j < cells.length; j++) {
			if (cells[j].className.indexOf('reverseStyle') > -1) {
				cells[j].className = cells[j].className.replace('reverseStyle',
						'');
			}
			if (cells[j].id == cellId) {
				cells[j].className += ' reverseStyle';
			} else if (i % 2 != 0) {
				// cells[j].className += ' rich-panelbar-interior';
			} else {
				// cells[j].className += ' rich-table-subheader';
			}
		}
	}
}

function cleanField(id) {
	if (document.getElementById(id) != null) {
		document.getElementById(id).value = '';
	}
}

function updateTables(tablesIds, removeString) {
	var tablesArray = tablesIds.split(';');
	for (x = 0; x < tablesArray.length; x++) {
		var tableId = tablesArray[x];
		if (removeString != null) {
			// added to use it at server configuration table
			var removePos = tableId.lastIndexOf(removeString);
			tableId = tableId.substr(0, removePos)
					+ tableId.substr(removePos + removeString.length);
		}
		var auxTable = document.getElementById(tableId);
		// alert(tableId + ':' +activePage + '0:hiddenField');
		// alert(document.getElementById(tableId + ':' +activePage +
		// '0:hiddenField'));
		var found = false;
		if (document.getElementById(tableId + ':' + '0:hiddenField') != null
				|| document.getElementById(tableId + ':' + '5:hiddenField') != null) {
			found = true;
		} else {
			for (i = 1; i < 1000; i++) {
				if (document
						.getElementById(tableId + ':' + i + '0:hiddenField') != null
						|| document.getElementById(tableId + ':' + i
								+ '5:hiddenField') != null) {
					found = true;
					break;
				}
			}
		}
		if (found) {
			document.getElementById(tableId + ':noDataId').style.display = 'none';
		} else {
			document.getElementById(tableId + ':noDataId').style.display = 'block';
		}
		if (auxTable != null) {
			for (i = 1; i < auxTable.rows.length; i++) {
				var firstCell = auxTable.rows[i].cells[0];
				var cellContent = firstCell.innerHTML;
				var hiddenPos = cellContent.indexOf('hiddenField');
				var opening = cellContent.lastIndexOf('<', hiddenPos);
				var closing = new Number(cellContent.indexOf('>', hiddenPos)) + 1;
				var hiddenFieldString = cellContent.substring(opening, closing);
				var browser = navigator.appName;
				var namePos = null;
				var nameBegin = null;
				var nameEnd = null;
				if (browser == 'Microsoft Internet Explorer') {
					namePos = hiddenFieldString.indexOf('name=');
					nameBegin = new Number(namePos) + 'name='.length;
					nameEnd = hiddenFieldString.indexOf(' ', nameBegin)
					if (nameEnd == -1) {
						nameEnd = hiddenFieldString.length - 1;
					}
				} else {
					namePos = hiddenFieldString.indexOf('name="');
					nameBegin = new Number(namePos) + 'name="'.length;
					nameEnd = hiddenFieldString.indexOf('"', nameBegin)
				}
				var inputHidden = document.getElementById(hiddenFieldString
						.substring(nameBegin, nameEnd));
				if (inputHidden.value == 'true') {
					if (auxTable.rows[i].className == 'highLightRow') {
						rowCurrentStyle = 'updated';
					} else {
						auxTable.rows[i].className = 'updated';
					}
				}
			}
		}
	}
}

function paintRow(cellId, rowsNumber) {
	var auxArray = cellId.split(':');
	var formId = auxArray[0];
	var tableId = auxArray[1];
	var rowId = new Number(auxArray[2]) + new Number(1);
	var tableToUpdate = document.getElementById(formId + ':' + tableId);
	rowId = (rowId % rowsNumber);
	if (rowId == 0) {
		rowId = rowsNumber;
	}
	if (tableToUpdate.rows[rowId].className == 'highLightRow') {
		rowCurrentStyle = 'updated';
	} else {
		tableToUpdate.rows[rowId].className = 'updated';
	}

	// update hiddenField
	var hiddenId = formId + ':' + tableId + ':' + auxArray[2] + ':hiddenField';
	var hiddenField = document.getElementById(hiddenId);
	hiddenField.value = 'true';

}

function clearFocus() {
	document.forms["headForm"].elements[0].focus();
	document.forms["headForm"].elements[0].blur();
}

function launchLink(contentLink) {
	window.location = contentLink;
}
function launchLinkOpen(contentLink) {
	window.location = contentLink;
	// TODO: Hacer configurable
	// window.open(contentLink,"Portafirmas",
	// "toolbar=no,location=no,status=no,menubar=no");
}
function overRow(element, prefijo, pageActual, pageSize) {
	if (element.rowIndex % 2 != 0) {
		rowCurrentStyle = 'rich-panelbar-interior';
	} else {
		rowCurrentStyle = 'rich-table-subheader';
	}
	element.className = element.className.replace(rowCurrentStyle,
			'highLightRow');

	var index = (element.rowIndex - 1) + (pageActual - 1) * pageSize;
	var count = 1;
	var spanOver = document.getElementById(prefijo + index + ':spanOverflowId'
			+ count++);
	while (spanOver != undefined) {
		spanOver.className = spanOver.className.replace(rowCurrentStyle,
				'highLightRow');
		spanOver = document.getElementById(prefijo + index + ':spanOverflowId'
				+ count++);
	}
}
function leaveRow(element, prefijo, pageActual, pageSize) {
	element.className = element.className.replace('highLightRow',
			rowCurrentStyle);
	var index = (element.rowIndex - 1) + (pageActual - 1) * pageSize;
	var count = 1;
	var spanOver = document.getElementById(prefijo + index + ':spanOverflowId'
			+ count++);
	while (spanOver != undefined) {
		spanOver.className = spanOver.className.replace('highLightRow',
				rowCurrentStyle);
		spanOver = document.getElementById(prefijo + index + ':spanOverflowId'
				+ count++);
	}
}



function checkThemeMini(index) {
	
	var refreshVar = false;
	var heightWin = screen.height;
	var widthWin = screen.width;

	
	// orientation
	if (window.orientation != null
			&& (window.orientation == 90 || window.orientation == -90)) {
		heightWin = screen.width;
		widthWin = screen.height;
	}
	
	var formularioId = 'headForm';
	if(index){
		formularioId = 'formLogin';
	}

	// height	
	mini = document.getElementById(formularioId+':mini');
	if (heightWin <= 800 && mini.value.indexOf('_mini') == -1) {
		mini.value = '_mini';
		refreshVar = true;
	} else if (heightWin > 800 && mini.value.indexOf('_mini') > -1) {
		mini.value = '';
		refreshVar = true;
	}

	// width	
	panelBar = document.getElementById(formularioId+':panelBarShow');
	if (widthWin <= 800 && panelBar.value.indexOf('true') > -1) {
		panelBar.value = 'false';
		if (!index) {
			refreshVar = true;
		}
	} else if (widthWin > 800 && panelBar.value.indexOf('true') == -1) {
		panelBar.value = 'true';
		if (!index) {
			refreshVar = true;
		}
	}
	

	var IsiPhone = navigator.userAgent.indexOf("iPhone") != -1;
	var IsiPod = navigator.userAgent.indexOf("iPod") != -1;
	var IsiPad = navigator.userAgent.indexOf("iPad") != -1;
	var IsAndroid = navigator.userAgent.indexOf("Android") != -1;

	var IsiPhoneOS = IsiPhone || IsiPad || IsiPod;
	
	if (IsiPhoneOS) {
		document.getElementById('formLogin:navigator').value = 'ios';
	}
	
	if (document.getElementById('formLogin:iconSize')!=null && document.getElementById('formLogin:iconSize').value.indexOf('16') > -1
			&& IsiPhoneOS) {
		document.getElementById('formLogin:iconSize').value = '32';
		document.getElementById('formLogin:fontSize').value = '16';
		refreshVar = true;
	}
	
	if (IsAndroid) {
		// android sign client disabled
		document.getElementById('formLogin:navigator').value = 'android';//'android';
		// alert(document.getElementById('formLogin:navigator').value);
	}
	
	if (document.getElementById('formLogin:iconSize')!=null && document.getElementById('formLogin:iconSize').value.indexOf('16') > -1
			&& IsAndroid) {
		document.getElementById('formLogin:iconSize').value = '32';
		document.getElementById('formLogin:fontSize').value = '16';
		refreshVar = true;
	}

	
	// Set user url
	var userUrl = window.location.href;
	userUrl = userUrl.substring(0, userUrl.lastIndexOf('/'));
	document.getElementById('formLogin:userUrl').value = userUrl;
					
	if (refreshVar) {			
		var refreshed = document.getElementById(formularioId+':refreshed').value;
		if(refreshed=='false'){			
			document.getElementById(formularioId+':refreshed').value = 'true';
			refresh();
		}
	}
	
}




function getSignAlgorithmMiniapplet(hashAlg) {
	//alert('hashAlg: ' + hashAlg);
	var hashAlgorihtm;
	/*
	if(hashAlg=='SHA1' || hashAlg=='SHA-1' ){
		hashAlgorihtm = 'SHA1withRSA';
	} else if(hashAlg=='SHA256' || hashAlg=='SHA-256'){
		hashAlgorihtm = 'SHA256withRSA';
	} else {
		hashAlgorihtm = hashAlg.toUpperCase().replace('-','') +'withRSA';
	}
	*/

	hashAlgorihtm = hashAlg.toUpperCase().replace('-','') +'withRSA';

	
	//alert('hashAlg miniapplet: ' + hashAlg);
	return hashAlgorihtm;
}





function firmaBloqueSuccess(signatureB64) {
	//alert("firmaBloqueSuccess: " + signatureB64);
	
	var datosFirmados = signatureB64;
	document.getElementById("signForm:signatures").value = datosFirmados;
	if (datosFirmados != null) {
		document.getElementById("signForm:userId").value = null;//MiniApplet.getSignerCertificateBase64(); not exists
	}
	
	
	var signerCertificate = null;//MiniApplet.getSignerCertificateBase64(); not exists
	var signaturesForm = document.getElementById("signForm:signatures");
	var userIdForm = document.getElementById("signForm:userId");
	var signLogForm = document.getElementById("signForm:signLog");
	
	signaturesForm.value = signatureB64;
	userIdForm.value = "";//clienteFirma.getSignCertificateBase64Encoded();		
	signLogForm.value = "";//clienteFirma.getMassiveSignatureLog();
	
			
	//alert("Fin de firma: " + firmas);
	//alert("Signer Certificate: " + signerCertificate);		
	//alert("Call endSign()");
	endSign();		
	
}

function firmaBloqueError(errorType, errorMessage) {
	//alert('showErrorCallback('+errorType+' --- ' + errorMessage + ')');
	//alert('errorType: ' + typeof(errorType));
	if(errorType=='es.gob.afirma.core.AOCancelledOperationException'){
		
	} else if (errorType=='java.io.FileNotFoundException') {
		alert('Error: No se ha seleccionado un fichero de datos válido');						   
	} else if (errorType=='es.gob.afirma.keystores.common.AOCertificatesNotFoundException') {
		alert('Error: No se ha encontrado ningún certificado de firma válido');
	} else if(errorType=='es.gob.afirma.keystores.main.common.AOKeystoreAlternativeException') {
		alert('La contraseña indicada no es correcta');
	} else {
		alert('Error: Se produjo un error durante la operación de firma: ' + errorType + ' - ' + errorMessage);
	}
	//alert('Before unblock');
	setTimeout(function(){unblockScreen();},1000);	
	document.getElementById("signForm:signLog").value = errorType + " - " + errorMessage;
}


function firmaBloque(filterCert) {
	var datosFirmados;
	var datosFirma = document.getElementById("signForm:dataSign").value;
	var format = document.getElementById("signForm:format").value;
	var signatureFormat = format;
	var mode = document.getElementById("signForm:type").value;
	var algorithm = document.getElementById("signForm:algorithm").value;
	var signAlgorithm = getSignAlgorithmMiniapplet(algorithm);
	var hashAlgorithm = algorithm;
	
	if(format!=null && format=="PDF"){
		format = "PAdES";		
	}
	
	if(signatureFormat.toLowerCase().indexOf('xades') != -1){
		format = signatureFormat; //XAdES Enveloping, XAdES Enveloped or XAdES Detached
		if(format.toLowerCase().indexOf('xades enveloping') != -1){
			format = 'XAdES Enveloping';
		} else if(format.toLowerCase().indexOf('xades enveloped') != -1){
			format = 'XAdES Enveloped';
		} else if(format.toLowerCase().indexOf('xades detached') != -1){
			format = 'XAdES Detached';
		}				
		signatureFormat = "XAdES";
		//format = "Enveloping"; 
		mode = undefined;
	} else if(signatureFormat.toLowerCase().indexOf('pades') != -1){								
		signatureFormat = "PAdES";				
		format = undefined; 
		mode = undefined;
	} else if(signatureFormat.toLowerCase().indexOf('cades') != -1){
		signatureFormat = "CAdES";		
		format = undefined;
	}
	var params = '';	
	if (mode != undefined) {
		params += 'mode=' + mode + '\n';
	}
	if (format != undefined) {
		params += 'format=' + format + '\n';
	}
	
	//alert("datosFirma: " + datosFirma);
	//alert("format: " + format);
	//alert("mode: " + mode);
	//alert("algorithm: " + algorithm);

	/*
	clienteFirma.initialize();
	clienteFirma.setHash(datosFirma);
	clienteFirma.setSignatureFormat(format);
	clienteFirma.setSignatureMode(mode);
	clienteFirma.setSignatureAlgorithm(algorithm);
	//cliente 3.3. Evita la inclusion de la ruta completa de certificacion 			
	clienteFirma.addExtraParam("includeOnlySignningCertificate", "true");

	// Apply filter for default certificate.
	if (filterCert != null && filterCert != "") {
		clienteFirma.setCertFilter('"' + filterCert + '"');
	}
	//

	clienteFirma.sign();
	*/
	
	try{
		params += 'precalculatedHashAlgorithm='+hashAlgorithm+'\n';
		//alert("Envío a firma: " + params);
		MiniApplet.sign(datosFirma, signAlgorithm, signatureFormat, params, firmaBloqueSuccess, firmaBloqueError);
	} catch(e) {
		alert("Authenticate error: " + e);
		//showErrorCallback(MiniApplet.getErrorType(), MiniApplet.getErrorMessage());
		setTimeout(function(){unblockScreen();},1000);
	}	
	
	/*
	if (clienteFirma.isError()) {
		document.getElementById("signForm:signLog").value = clienteFirma
				.getErrorMessage();
	} else {
		datosFirmados = clienteFirma.getSignatureBase64Encoded();
		document.getElementById("signForm:signatures").value = datosFirmados;
		if (datosFirmados != null) {
			document.getElementById("signForm:userId").value = clienteFirma
					.getSignCertificateBase64Encoded();
		}
	}
	 */
	
}



var datosAFirmar;
var docsArray;
var signable;
var firmas = "";
var nFirma = 0;


function firmaMasivaSuccess(signatureB64) {	
	
	//alert("firmaMasivaSuccess");
	//alert("signatureB64: " + signatureB64);
	//alert("nFirma: " + nFirma);
	
	var firma = signable[1] + ':' + signatureB64;

	//alert("firma: " + firma);

	if (firmas != null && firmas != '' && firma != 'null') {
		firmas = firmas + ',';
	}
	firmas = firmas + firma;
	//alert("firmas: " + firmas);	
	//alert(nFirma+"/"+(docsArray.length-1));
	
	if(nFirma==docsArray.length-1){
		var signerCertificate = null;//MiniApplet.getSignerCertificateBase64(); not exists
		var signaturesForm = document.getElementById("signForm:signatures");
		var userIdForm = document.getElementById("signForm:userId");
		var signLogForm = document.getElementById("signForm:signLog");
		
		signaturesForm.value = firmas;
		userIdForm.value = "";//clienteFirma.getSignCertificateBase64Encoded();		
		signLogForm.value = "";//clienteFirma.getMassiveSignatureLog();
		
				
		//alert("Fin de firma: " + firmas);
		//alert("Signer Certificate: " + signerCertificate);		
		//alert("Call endSign()");
		
		debug("=========================================");
		debug("signaturesForm: " + signaturesForm.value);
		debug("userIdForm: " + userIdForm.value);
		debug("signLogForm: " + signLogForm.value);		
		debug("=========================================");
		
		endSign();		
		//alert("Called endSign(): " + error);
	}
	
	
}

function firmaMasivaError (errorType, errorMessage) {
	//alert('firmaMasivaError showErrorCallback('+errorType+' --- ' + errorMessage + ')');
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



function firmaMasiva(filterCert) {	
	//alert("Firma Masiva");
	//alert("filterCert: " + filterCert)
	
	var format = document.getElementById("signForm:format").value;
	
	var signatureFormat = format;
	var mode = document.getElementById("signForm:type").value;	
		
	datosAFirmar = document.getElementById("signForm:dataSign").value;
	docsArray = datosAFirmar.split(',');	
	//alert("docsArray: " + docsArray);
	//alert('datosAFirmar: ' + datosAFirmar);
	
	if(format!=null && format=="PDF"){
		format = "PAdES";		
	}	
	
	if(signatureFormat.toLowerCase().indexOf('xades') != -1){
		format = signatureFormat; //XAdES Enveloping, XAdES Enveloped or XAdES Detached
		if(format.toLowerCase().indexOf('xades enveloping') != -1){
			format = 'XAdES Enveloping';
		} else if(format.toLowerCase().indexOf('xades enveloped') != -1){
			format = 'XAdES Enveloped';
		} else if(format.toLowerCase().indexOf('xades detached') != -1){
			format = 'XAdES Detached';
		}
		signatureFormat = "XAdES";
		//format = "Enveloping"; 
		mode = undefined;
	} else if(signatureFormat.toLowerCase().indexOf('pades') != -1){								
		signatureFormat = "PAdES";				
		format = undefined; 
		mode = undefined;
	} else if(signatureFormat.toLowerCase().indexOf('cades') != -1){
		signatureFormat = "CAdES";		
		format = undefined;
	}
	var params = '';	
	if (mode != undefined) {
		params += 'mode=' + mode + '\n';
	}
	if (format != undefined) {
		params += 'format=' + format + '\n';
	}
	
	if (filterCert != null && filterCert != "") {		
		params += filterCert + '\n';
		//alert("filterCert added to Miniapplet Params: " + params);
	}
	
	//params += 'policyIdentifier!=urn:oid:1.3.6.1.4.1.5734.3.7\n';
	//alert("MiniApplet.params: " + params);
	//MiniApplet.echo();
	
	/* POLITICA DE FIRMA 
	params += "policyIdentifier=2.16.724.1.3.1.1.2.1.8\n";
    params += "policyIdentifierHash=7SxX3erFuH31TvAw9LZ70N7p1vA=\n";
    params += "policyIdentifierHashAlgorithm=1.3.14.3.2.26\n";
    params += "policyQualifier=http://administracionelectronica.gob.es/es/ctt/politicafirma/politica_firma_AGE_v1_8.pdf\n";
    params += "mode=implicit\n";
    */
	
	try{
		if(docsArray.length>1){
			MiniApplet.setStickySignatory(true);
		}
		
		nFirma = 0;
		for (i = 0; i < docsArray.length; i++) {			
			//alert("i : " + i);
			nFirma = i;			
			signable = docsArray[i].split(':');
			//alert("signable: " + signable);
			var contenidoAFirmar = signable[2];
			var algorithm = signable[3];			
			var signAlgorithm = getSignAlgorithmMiniapplet(algorithm);
			var hashAlgorithm = algorithm;
			
			/*
			alert("ToSign: " + contenidoAFirmar);
			alert("Algorithm: " + algorithm);
			alert("signatureFormat: " + signatureFormat);
			alert("Params: " + params);
			*/
			
			if (signable[0].indexOf('HASH') != -1) {
				var paramsDocN = params;				
				//alert("Call: sign HASH");
				paramsDocN += 'precalculatedHashAlgorithm='+hashAlgorithm+'\n';				
				//alert(paramsDocN);
				MiniApplet.sign(contenidoAFirmar, signAlgorithm, signatureFormat, paramsDocN, firmaMasivaSuccess, firmaMasivaError);
			} else if (signable[0].indexOf('BINARY') != -1) {
				//alert("Call: sign BINARY");
				MiniApplet.sign(contenidoAFirmar, signAlgorithm, signatureFormat, params, firmaMasivaSuccess, firmaMasivaError);
			} else if (signable[0].indexOf('MULTISIGN') != -1) {
				//alert("Call: sign MULTISIGN coSign");
				MiniApplet.counterSign(contenidoAFirmar, signAlgorithm, signatureFormat, params, firmaMasivaSuccess, firmaMasivaError);				
			}			
		}
		
	} catch(e) {
		alert("Se ha producido un error: " + e);
		//showErrorCallback(MiniApplet.getErrorType(), MiniApplet.getErrorMessage());
		setTimeout(function(){unblockScreen();},1000);
	}	
}

function firmaMasivaIdazki() {
	var datosAFirmar = document.getElementById("signForm:dataSign").value;
	var format = document.getElementById("signForm:format").value;
	var formatCosign = document.getElementById("signForm:formatCosign").value;
	var algorithm = document.getElementById("signForm:algorithm").value;
	var docsArray = datosAFirmar.split(',');
	var multi = false;
	var simple = false;
	var firmas = "";
	var firma = "";

	// alert(format + "," + formatCosign + "," + algorithm);

	clienteFirmaIdazki.clearInputs();

	// simple
	for (i = 0; i < docsArray.length; i++) {
		var signable = docsArray[i].split(':');
		if (signable[0].indexOf('HASH') != -1) {
			clienteFirmaIdazki.addInput('inline-hash', signable[2], 'inline',
					null);
			simple = true;
		} else if (signable[0].indexOf('BINARY') != -1) {
			clienteFirmaIdazki.addInput('inline-binary', signable[2], 'inline',
					null);
			simple = true;
		} else if (signable[0].indexOf('MULTISIGN') != -1) {
			multi = true;
		}
	}

	if (simple) {
		clienteFirmaIdazki.setOption('signature-hashalgo', algorithm);
		// clienteFirmaIdazki.setOption('dlgsign-show', 'false');
		clienteFirmaIdazki.setCryptoStoreAuto();
		clienteFirmaIdazki.signSetAdESLevel('bes');
		clienteFirmaIdazki.sign(format);
		var simpleCount = clienteFirmaIdazki.getOutputCount();
		// alert("Simple:" + simpleCount);
		var count = 0;
		for (i = 0; i < docsArray.length && count <= simpleCount; i++) {
			var signable = docsArray[i].split(':');
			if (signable[0].indexOf('MULTISIGN') == -1) {
				firma = clienteFirmaIdazki.getOutputContent(count++, true);
				if (firma != null && firma != '' && firma != 'null'
						&& firma != 'error') {
					firma = signable[1] + ':' + firma;
					firmas = firmas + firma;
				} else if (firma == 'error') {
					firmas = firmas + '#ERROR#';
				} else {
					// nada
				}
				if (i < docsArray.length - 1 && firma != ''
						&& count < simpleCount) {
					firmas = firmas + ',';
				}
			}
		}
	}
	// multi
	if (multi) {
		clienteFirmaIdazki.clearInputs();
		for (i = 0; i < docsArray.length; i++) {
			var signable = docsArray[i].split(':');
			if (signable[0].indexOf('MULTISIGN') != -1) {
				clienteFirmaIdazki.addInput('inline-binary', signable[2],
						'inline', null);
			}
		}
		clienteFirmaIdazki.setOption('signature-hashalgo', algorithm);
		// clienteFirmaIdazki.setOption('dlgsign-show', 'false');
		clienteFirmaIdazki.setCryptoStoreAuto();
		clienteFirmaIdazki.signSetAdESLevel('bes');
		clienteFirmaIdazki.sign(formatCosign);

		var multiCount = clienteFirmaIdazki.getOutputCount();
		// alert("Multi:" + multiCount);
		count = 0;
		for (i = 0; i < docsArray.length && count <= multiCount; i++) {
			var signable = docsArray[i].split(':');
			if (signable[0].indexOf('MULTISIGN') > -1) {
				firma = clienteFirmaIdazki.getOutputContent(count++, true);
				// alert(firma);
				if (firma != null && firma != '' && firma != 'null'
						&& firma != 'error') {
					firma = signable[1] + ':' + firma;
					firmas = firmas + firma;
				} else if (firma == 'error') {
					firmas = firmas + '#ERROR#';
				} else {
					// nada
				}
				if (i < docsArray.length - 1 && firma != ''
						&& count < multiCount) {
					firmas = firmas + ',';
				}
			}
		}
	}
	document.getElementById("signForm:signatures").value = firmas;
}

function firmaIOS(sessionId) {
	var datosAFirmar = document.getElementById("signForm:dataSign").value;
	var url = window.location.href;
	if (url.indexOf("/request") > -1) {
		url = url.substring(0, url.indexOf('/request')) + '/mobile/sign';
	} else {
		url = url.substring(0, url.lastIndexOf('/')) + '/mobile/sign';
	}
	/*portafirmasjda*/
	launchLink("portafirmasjda://sign?datos=" + datosAFirmar + "&sessionId="+ sessionId + "&url=" + url);
}

function authIOS(sessionId) {
	var url = window.location.href;
	if (url.indexOf("/request") > -1) {
		url = url.substring(0, url.indexOf('/request')) + '/mobile/auth';
	} else {
		url = url.substring(0, url.lastIndexOf('/')) + '/mobile/auth';
	}
	/*portafirmasjda*/
	launchLink("portafirmasjda://auth?datos=auth&sessionId=" + sessionId + "&url="
			+ url);
}

// Android
function firmaAndroid(sessionId) {
	var datosAFirmar = document.getElementById("signForm:dataSign").value;
	var url = window.location.href;
	if (url.indexOf("/request") > -1) {
		url = url.substring(0, url.indexOf('/request')) + '/mobile/sign';
	} else {
		url = url.substring(0, url.lastIndexOf('/')) + '/mobile/sign';
	}
	/*portafirmasjda*/
	launchLink("portafirmasjda://sign/" + datosAFirmar + "/" + sessionId + "/"
			+ url);
}

function authAndroid(sessionId) {
	var url = window.location.href;
	if (url.indexOf("/request") > -1) {
		url = url.substring(0, url.indexOf('/request')) + '/mobile/auth';
	} else {
		url = url.substring(0, url.lastIndexOf('/')) + '/mobile/auth';
	}
	/*portafirmasjda*/
	launchLink("portafirmasjda://auth/auth/" + sessionId + "/" + url);
}

// Fin Android




function cargarClienteDeFirma(index, path){
	//alert("cargarClienteDeFirma("+index+", "+path+")");
	var agent = navigator.userAgent;
	//alert(agent);
	if (agent.toLowerCase().indexOf('ipad') > -1
			|| agent.toLowerCase().indexOf('iphone') > -1
			|| agent.toLowerCase().indexOf('ipod') > -1) {
		//alert("IOS");
		cargarAppletFirmaPfirma(index);
	} else if (agent.toLowerCase().indexOf('android') > -1) {
		//alert("Android");
		cargaMiniApplet(path);
	} else {		
		//alert("PC");
		cargaMiniApplet(path);		 	
	}
}

function cargarAppletFirmaPfirma(index) {
	//alert("cargarAppletFirmaPfirma("+index+")");
	var agent = navigator.userAgent;

	// only block android
	if (agent.toLowerCase().indexOf('android') > -1) {
		// android sign client disabled
		document.getElementById("formLogin:navigator").value = 'notsupport';
		document.getElementById("formLogin:access").disabled = true;
	}
	// afirma client doesn´t loaded
	else if (agent.toLowerCase().indexOf('ipad') > -1
			|| agent.toLowerCase().indexOf('iphone') > -1
			|| agent.toLowerCase().indexOf('ipod') > -1) {
		// nothing
	} else if (agent.toLowerCase().indexOf('firefox') > -1
			|| agent.toLowerCase().indexOf('msie') > -1
			|| agent.toLowerCase().indexOf('chrome') > -1
			|| agent.toLowerCase().indexOf('safari') > -1
			|| agent.toLowerCase().indexOf('opera') > -1) {
		if (index == true) {
			document.getElementById("formLogin:navigator").value = 'support';
		}
		cargarAppletFirma(index);
	} else {
		if (index == true) {
			document.getElementById("formLogin:navigator").value = 'notsupport';
			document.getElementById("formLogin:access").disabled = true;
		}
	}

}

function selectCert() {
	try{
		var textArea = document
				.getElementById('configurationForm:subjectCertTextArea');
		var subjecthidden = document
				.getElementById('configurationForm:subjectCertHidden');
		var serialhidden = document
				.getElementById('configurationForm:serialCertHidden');
		var serialText = document
				.getElementById('configurationForm:serialCertText');
	
		var alias = clienteFirma.showCertSelectionDialog();
		clienteFirma.setData("");
		clienteFirma.sign();
	
		var subject = clienteFirma.getSignCertificate().getSubjectX500Principal();
		var serial = clienteFirma.getSignCertificate().getSerialNumber();
	
		textArea.innerHTML = subject;
		serialText.value = serial;
		textArea.title = subject;
		serialText.title = serial;
		subjecthidden.value = subject;
		serialhidden.value = serial;
	
		clienteFirma.initialize();
	} catch (err){
		alert("No es posible seleccionar el certificado");
	}
}

function clearDefaultCert() {
	var textArea = document
			.getElementById('configurationForm:subjectCertTextArea');
	var subjecthidden = document
			.getElementById('configurationForm:subjectCertHidden');
	var serialhidden = document
			.getElementById('configurationForm:serialCertHidden');
	var serialText = document
			.getElementById('configurationForm:serialCertText');
	textArea.innerHTML = "";
	subjecthidden.value = "";
	serialhidden.value = "";
	serialText.value = "";
	textArea.title = "";
	serialText.title = "";
}

function isTouchDevice() {
	if ((navigator.userAgent.match(/android 3/i))
			|| (navigator.userAgent.match(/honeycomb/i)))
		return false;
	try {
		document.createEvent("TouchEvent");
		return true;
	} catch (e) {
		return false;
	}
}

function touchScroll(id) {
	if (isTouchDevice()) {
		var element = document.getElementById(id);
		var scrollStartPosY = 0;
		var scrollStartPosX = 0;

		element.addEventListener("touchstart", function(event) {
			scrollStartPosY = this.scrollTop + event.touches[0].pageY;
			scrollStartPosX = this.scrollLeft + event.touches[0].pageX;
		}, false);

		element
				.addEventListener(
						"touchmove",
						function(event) {
							if ((this.scrollTop < this.scrollHeight
									- this.offsetHeight && this.scrollTop
									+ event.touches[0].pageY < scrollStartPosY - 5)
									|| (this.scrollTop != 0 && this.scrollTop
											+ event.touches[0].pageY > scrollStartPosY + 5))
								event.preventDefault();
							if ((this.scrollLeft < this.scrollWidth
									- this.offsetWidth && this.scrollLeft
									+ event.touches[0].pageX < scrollStartPosX - 5)
									|| (this.scrollLeft != 0 && this.scrollLeft
											+ event.touches[0].pageX > scrollStartPosX + 5))
								event.preventDefault();
							this.scrollTop = scrollStartPosY
									- event.touches[0].pageY;
							this.scrollLeft = scrollStartPosX
									- event.touches[0].pageX;
						}, false);
	}
}


function setCaretToEnd (e) {
    var control = $((e.target ? e.target : e.srcElement).id);
    if (control.createTextRange) {
        var range = control.createTextRange();
        range.collapse(false);
        range.select();
    }
    else if (control.setSelectionRange) {
        control.focus();
        var length = control.value.length;
        control.setSelectionRange(length, length);
    }
    control.selectionStart = control.selectionEnd = control.value.length;
} 



function debug(message){
	try{
		var log = true;
		if(log){
			console.log(message);
		}
	}catch(e){}
}




