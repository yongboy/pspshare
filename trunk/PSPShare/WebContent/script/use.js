String.prototype.trim = function()
		{
			return this.replace(/^\s*|\s*$/g,"");
		}
function v( id ){
	return document.getElementById( id );
}

function vi( id ){
	return v( id ).innerHTML.replace(/(^\s*)|(\s*$)/g,"");
}

function vv( id ){
	return v( id ).value.replace(/(^\s*)|(\s*$)/g,"");
}
function setcookie(name,value){
	document.cookie=name+"="+value;
}
function setcookie2( name, value ){
	document.cookie = name + "=" + escape( value ) + ";";
}
function getcookie( name ){
	var strArg=name+"=";
	var nArgLen=strArg.length;
	var nCookieLen=document.cookie.length;
	var nEnd;
	var i=0;
	var j;
	while (i<nCookieLen){
		j=i+nArgLen;
		if (document.cookie.substring(i,j)==strArg){
			nEnd=document.cookie.indexOf (";",j);
			if (nEnd==-1) nEnd=document.cookie.length;
				 return document.cookie.substring(j,nEnd);
		  }
	i=document.cookie.indexOf(" ",i)+1;
	if (i==0) break;
	}
	return '';
}

function BASEisNotNum(theNum){
    if (BASEtrim(theNum)=="")
        return true;
    for(var i=0;i<theNum.length;i++){
        oneNum=theNum.substring(i,i+1);
        if (oneNum<"0" || oneNum>"9")
          return true;
    }
    return false;
}

function BASEisNotInt(theInt){
    theInt=BASEtrim(theInt);
    if ((theInt.length>1 && theInt.substring(0,1)=="0") || BASEisNotNum(theInt)){
        return true;
    }
    return false;
}

function BASEisNotFloat(theFloat){
    len=theFloat.length;
    dotNum=0;
    if (len==0)
        return true;
    for(var i=0;i<len;i++){
        oneNum=theFloat.substring(i,i+1);
        if (oneNum==".")
            dotNum++;
        if ( ((oneNum<"0" || oneNum>"9") && oneNum!=".") || dotNum>1)
          return true;
    }
    if (len>1 && theFloat.substring(0,1)=="0"){
        if (theFloat.substring(1,2)!=".")
            return true;
    }
    return false;
}

function isdigit(s){
	var r,re;
	re = /\d*/i;
	r = s.match(re);
	return (r==s)?1:0;
}

function All(e, itemName)
{
  var aa = document.getElementsByName(itemName);
  for (var i=0; i<aa.length; i++)
   aa[i].checked = e.checked;
}
function Item(e, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!e.checked) all.checked = false;
  else
  {
    var aa = document.getElementsByName(e.name);
    for (var i=0; i<aa.length; i++)
     if(!aa[i].checked) return;
    all.checked = true;
  }
}


var cookieName = "choose";
function removecookie(cookieName,value){
	var values = getcookie(cookieName);
	if(values ==null || values == ""){
		return;
	}
	values = values.replace(value + "|","");
	setcookie(cookieName,values);
}
function saveIntoCookie(e){
	if(e.checked){
		setcookie(cookieName,getcookie(cookieName) + e.value + "|");
	}else{
		removecookie(cookieName,e.value);
	}
}
function checkhasid(id){
	var values = getcookie(cookieName);
	if(values.indexOf(id) == -1)return false;
	return true;
}
function butchSelect(e, itemName){
  var aa = document.getElementsByName(itemName);
  for (var i=0; i<aa.length; i++)
   saveIntoCookie(aa[i]);
}

var ids = "";