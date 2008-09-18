function addUpFile(){
        var str = '<br /><input class="btn" size="42" name="myFile" type="file">';
        document.getElementById('myFileDiv').insertAdjacentHTML("beforeEnd",str);
}