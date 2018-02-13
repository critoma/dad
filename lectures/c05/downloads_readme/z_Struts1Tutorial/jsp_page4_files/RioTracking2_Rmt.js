//*************************** RIO Tracking Manager - Rich Media Tracking *************************************
function RioTrackingManagerRmt() {
    // Initialize tracking URLs.
    this.atlasTrackingUrl = document.location.protocol + '//view.atdmt.com/jaction/mrtity_RioRichMedia_10/v3/ato.-1';
    this.crsTrackingUrl = document.location.protocol + '//www.microsoft.com/click/services/Tracking/RichMedia.ashx';
}

RioTrackingManagerRmt.prototype.processEvent = function(placementId, cellCode, eventId) {
    this.fireAtlasTag(placementId, cellCode, eventId);
    this.fireCrsTag(placementId, cellCode, eventId);
}

RioTrackingManagerRmt.prototype.fireAtlasTag = function(placementId, cellCode, eventId) {
    var scriptSrc = this.atlasTrackingUrl;
    scriptSrc += '/atc1.' + placementId.toString();
    scriptSrc += '/atc2.' + cellCode.toString();
    scriptSrc += '/atc3.' + eventId.toString();

    this.attachScript(scriptSrc);
}

RioTrackingManagerRmt.prototype.fireCrsTag = function(placementId, cellCode, eventId) {
    // Create a random number for "cache-busting".
    var timestamp = new Date();
    var rnd = Math.ceil(Math.random() * 99999999) + "" + timestamp.getUTCFullYear() + timestamp.getUTCMonth() + timestamp.getUTCDate() + timestamp.getUTCHours() + timestamp.getUTCMinutes() + timestamp.getUTCSeconds() + timestamp.getUTCMilliseconds();
    
    var scriptSrc = this.crsTrackingUrl;
    scriptSrc += '?pid=' + placementId.toString();
    scriptSrc += '&cc=' + cellCode.toString();
    scriptSrc += '&eid=' + eventId.toString();
    scriptSrc += '&rnd=' + rnd.toString();

    this.attachScript(scriptSrc);
}

RioTrackingManagerRmt.prototype.attachScript = function(scriptSrc) {
    var scriptObj = document.createElement("script");
    scriptObj.type = "text/javascript";
    scriptObj.src = scriptSrc;
    
    document.getElementsByTagName("head")[0].appendChild(scriptObj);
}

// Instantiation of RIO Tracking Object
var RioTracking = new RioTrackingManagerRmt();
