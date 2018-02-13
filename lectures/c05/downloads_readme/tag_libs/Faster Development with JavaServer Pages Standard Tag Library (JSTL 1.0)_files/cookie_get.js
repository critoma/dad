function getCookie(CookieName) {
if (document.cookie.length > 0) { 
mycookie = document.cookie.indexOf(CookieName+"=");
if (mycookie != -1) { mycookie += CookieName.length+1;
end = document.cookie.indexOf(";", mycookie);
if (end == -1) end = document.cookie.length;
return unescape(document.cookie.substring(mycookie, end));
}
}
return null;
} 