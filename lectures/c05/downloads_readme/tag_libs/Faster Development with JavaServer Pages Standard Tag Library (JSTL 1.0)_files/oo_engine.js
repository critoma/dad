/* OnlineOpinion v4.1.4 patch 11 (sunms) */
/* This product and other products of OpinionLab, Inc. are protected by U.S. Patent No. 6606581, 6421724, 6785717 B1 and other patents pending. */

var OnlineOpinion = new Object();

// ====================
// =     UTIL         =
// ====================

OnlineOpinion.util = {
	SafeAddOnLoadEvent : function(func){
		if (!document.getElementById | !document.getElementsByTagName) 
			return;
		var oldonload = window.onload;
		if (typeof window.onload != 'function') {
			window.onload = func;
		}
		else {
			window.onload = function(){
				oldonload();
				func();
			};
		}
	},
	SafeAddOnUnLoadEvent : function(func){
		if (!document.getElementById | !document.getElementsByTagName) 
			return;
		var oldonunload = window.onunload;
		if (typeof window.onunload != 'function') {
			window.onunload = func;
		}
		else {
			window.onunload = function(){
				func();
				oldonunload();
			};
		}
	},
	popup : function(url, wname, wfeatures) {
		var wpopup = window.open(url, wname, wfeatures);
		if(typeof wpopup == 'undefined'){
			// ** try id.click() trick if IE or Opera ** //
			if (document.all) {
				// ** create new div/anchor on document ** //
				// ** assign window.open features to it ** //
				document.getElementById("test").href = url;
				document.getElementById("test").click();
			} else {
				var newWindow = window.open(url, '_blank');
				newWindow.focus();
			}
		};
		return false;
	},

	walkAnchors : function (node, depth, internal_links_re, ooObj) {
		var MAX_NODES = 1000;	// Constant to avoid taking too long
		var count = 0;
		while (node && depth > 0) {
			count ++;
			if (count >= MAX_NODES) {
				var handler = function() {
					OnlineOpinion.util.walkAnchors(node, depth, internal_links_re, ooObj);
				};
				setTimeout(handler, 50);
				return;
			}
			if(node.tagName=="A" || node.tagName=="AREA") {
				if(internal_links_re.test(node.href)) {
					node.onmousedown = function() {
						ooObj.Preferences.Plugins.Events.poX=0;
					};
				}
			}
			if(node.tagName=="INPUT") {
				if(node.type=="submit" || node.type=="image") {
					node.onmousedown = function() {
						ooObj.Preferences.Plugins.Events.poX=0;
					};
				}
			}

			if(node.tagName=="FORM") {
				//console.log((typeof node.onsubmit) +" >>" + node.action);
				if(typeof node.onsubmit!='function') {
					node.onsubmit = function() {
						ooObj.Preferences.Plugins.Events.poX=0;
					};
				} else {
					var oldonsubmit = node.onsubmit;
					node.onsubmit = function() {
						ooObj.Preferences.Plugins.Events.poX=0;
						oldonsubmit();
					};
					//console.log(node.onsubmit);
				}
			}

			if (node.nodeType == 1) { // ELEMENT_NODE
				var skipre = /^(script|style|textarea)/i;
				if (!skipre.test(node.tagName) && node.childNodes.length > 0) {
					node = node.childNodes[0];
					depth ++;
					continue;
				}
			}
			if (node.nextSibling) {
				node = node.nextSibling;
			} else {
				while (depth > 0) {
					node = node.parentNode;
					depth --;
					if(node==null)break;
					if (node.nextSibling) {
						node = node.nextSibling;
						break;
					}
				}
			}
		}
	}
};

OnlineOpinion.cookie = function() {
	// TO refactor as OnlineOpinion.utils.cookie
	// refactor tagurl / matchurl as OnlineOpinion.plugins.*
	
	this.cookie_name='oo_r';
	this.expiration = 24*60*60*1000; // 1 day
	
	this.rhex = function (num){
		var hex_chr = '0123456789abcdef', _s = '';
		for (var j = 0; j <= 3; j++) 
			_s += hex_chr.charAt((num >> (j * 8 + 4)) & 0x0F) + hex_chr.charAt((num >> (j * 8)) & 0x0F);
		return _s;
	};
	
	this.str2blks_MD5 = function (_s){
		var nblk = ((_s.length + 8) >> 6) + 1, blks = new Array(nblk * 16);
		var i = 0;
		for (; i < nblk * 16; i++) 
			blks[i] = 0;
		for (i = 0; i < _s.length; i++) 
			blks[i >> 2] |= _s.charCodeAt(i) << ((i % 4) * 8);
		blks[i >> 2] |= 0x80 << ((i % 4) * 8);
		blks[nblk * 16 - 2] = _s.length * 8;
		return blks;
	};
	
	this._fSa = function (x, y){
		var lsw = (x & 0xFFFF) + (y & 0xFFFF), msw = (x >> 16) + (y >> 16) + (lsw >> 16);
		return (msw << 16) | (lsw & 0xFFFF);
	};
	
	this.rol = function (num, cnt){
		return (num << cnt) | (num >>> (32 - cnt));
	};
	
	this.cmn = function (q, a, b, x, s, t){
		return this._fSa(this.rol(this._fSa(this._fSa(a, q), this._fSa(x, t)), s), b);
	};
	
	this._fF = function (a, b, c, d, x, s){
		return this.cmn((b & c) | ((~ b) & d), a, 0, x, s, 0);
	};
	
	this._fG = function (a, b, c, d, x, s){
		return this.cmn((b & c) | (b & d) | (c & d), a, 0, x, s, 1518500249);
	};
	
	this._fH = function (a, b, c, d, x, s){
		return this.cmn(b ^ c ^ d, a, 0, x, s, 1859775393);
	};
	
	this._MD4 = function (_s){
		var x = this.str2blks_MD5(_s), a = 1732584193, b = -271733879, c = -1732584194, d = 271733878;
		for (var i = 0; i < x.length; i += 16) {
			var olda = a, oldb = b, oldc = c, oldd = d;
			a = this._fF(a, b, c, d, x[i + 0], 3);
			d = this._fF(d, a, b, c, x[i + 1], 7);
			c = this._fF(c, d, a, b, x[i + 2], 11);
			b = this._fF(b, c, d, a, x[i + 3], 19);
			a = this._fF(a, b, c, d, x[i + 4], 3);
			d = this._fF(d, a, b, c, x[i + 5], 7);
			c = this._fF(c, d, a, b, x[i + 6], 11);
			b = this._fF(b, c, d, a, x[i + 7], 19);
			a = this._fF(a, b, c, d, x[i + 8], 3);
			d = this._fF(d, a, b, c, x[i + 9], 7);
			c = this._fF(c, d, a, b, x[i + 10], 11);
			b = this._fF(b, c, d, a, x[i + 11], 19);
			a = this._fF(a, b, c, d, x[i + 12], 3);
			d = this._fF(d, a, b, c, x[i + 13], 7);
			c = this._fF(c, d, a, b, x[i + 14], 11);
			b = this._fF(b, c, d, a, x[i + 15], 19);
			a = this._fG(a, b, c, d, x[i + 0], 3);
			d = this._fG(d, a, b, c, x[i + 4], 5);
			c = this._fG(c, d, a, b, x[i + 8], 9);
			b = this._fG(b, c, d, a, x[i + 12], 13);
			a = this._fG(a, b, c, d, x[i + 1], 3);
			d = this._fG(d, a, b, c, x[i + 5], 5);
			c = this._fG(c, d, a, b, x[i + 9], 9);
			b = this._fG(b, c, d, a, x[i + 13], 13);
			a = this._fG(a, b, c, d, x[i + 2], 3);
			d = this._fG(d, a, b, c, x[i + 6], 5);
			c = this._fG(c, d, a, b, x[i + 10], 9);
			b = this._fG(b, c, d, a, x[i + 14], 13);
			a = this._fG(a, b, c, d, x[i + 3], 3);
			d = this._fG(d, a, b, c, x[i + 7], 5);
			c = this._fG(c, d, a, b, x[i + 11], 9);
			b = this._fG(b, c, d, a, x[i + 15], 13);
			a = this._fH(a, b, c, d, x[i + 0], 3);
			d = this._fH(d, a, b, c, x[i + 8], 9);
			c = this._fH(c, d, a, b, x[i + 4], 11);
			b = this._fH(b, c, d, a, x[i + 12], 15);
			a = this._fH(a, b, c, d, x[i + 2], 3);
			d = this._fH(d, a, b, c, x[i + 10], 9);
			c = this._fH(c, d, a, b, x[i + 6], 11);
			b = this._fH(b, c, d, a, x[i + 14], 15);
			a = this._fH(a, b, c, d, x[i + 1], 3);
			d = this._fH(d, a, b, c, x[i + 9], 9);
			c = this._fH(c, d, a, b, x[i + 5], 11);
			b = this._fH(b, c, d, a, x[i + 13], 15);
			a = this._fH(a, b, c, d, x[i + 3], 3);
			d = this._fH(d, a, b, c, x[i + 11], 9);
			c = this._fH(c, d, a, b, x[i + 7], 11);
			b = this._fH(b, c, d, a, x[i + 15], 15);
			a = this._fSa(a, olda);
			b = this._fSa(b, oldb);
			c = this._fSa(c, oldc);
			d = this._fSa(d, oldd);
		}
		return this.rhex(a) + this.rhex(b) + this.rhex(c) + this.rhex(d);
	};
	
	this.read = function (n) {
		var neq = n + "=";
		var ca = document.cookie.split(';');
		for(var i=0;i < ca.length;i++) {
			var c = ca[i];
			while (c.charAt(0)==' ') c = c.substring(1,c.length);
			if (c.indexOf(neq) == 0) return unescape(c.substring(neq.length,c.length));
		}
		return null;	
	};
	
	this.write = function (n, v) {
		document.cookie = n + '=' + v + ';path=/;expires='+(new Date((new Date()).getTime()+this.expiration)).toGMTString();
	};
	
	this.matchurl = function (u, type){
		var i = 0, c = this.read(this.cookie_name);
		if(type=='domain')u=/(:\/\/)[\w\d\:\.]+/g.exec(u)[0].replace('://','');
		n = this._MD4(u);
		
		if(c == null) return false;

		while (i <c.length) {
			j = i + n.length;
			if (c.substring(i, j) == n) {
				return (unescape(c.substring(j + 1, j+2)) == 1);
			}
			i++;
		}
		return false;
	};
	
	this.tagurl = function (u, type){
		if(type=='domain')u=/(:\/\/)[\w\d\:\.]+/g.exec(u)[0].replace('://','');
		var prev_val="";
		if(this.read(this.cookie_name)!=null){prev_val=this.read(this.cookie_name).replace(eval('/' + escape(this._MD4(u)) + '~1:/g'), '');}
		this.write(this.cookie_name, prev_val+(prev_val!=''?':':'')+escape(this._MD4(u))+'~1');
	};
	
};




// ===================
// ==     CORE       =
// ===================
OnlineOpinion.ocode = function(name) {

	this.name = name;

	/* ************************** */
	/* BEGIN: Core Util functions */
	/* ************************** */
	function rematch (val, restr){
		var re = new RegExp(restr);
		var m = re.exec(val);
		if (m == null || m =='') {
			return '';
		} else {
			var s = "";
			for (i = 0; i < m.length; i++) {
				s = s + m[i];
			}
			return s;
		}
	};
	this._fC = function(_u){
		/* Util function to clean up the url and referring url along with taking care of any rewrite */
 		_aT=this._sp+',\\/,\\.,-,_,'+this._rp+',%2F,%2E,%2D,%5F';
		_aA=_aT.split(',');
		for(i=0;i<5;i++){
			eval('_u=_u.replace(/'+_aA[i]+'/g,_aA[i+5])');
		}
		return _u;
	};

	this._browser = function(){
	/* 	@engine  = ie | gecko | webkit | khtml | opera
		@version = engine version number 
	*/
		this.engine = null;
		this.version = null;		
		var useragent = navigator.userAgent.toLowerCase();

		if (window.ActiveXObject) {
			this.engine = 'ie';
			this.version = rematch(useragent, "msie\\s[0-9]\.[0-9]+").replace('msie ', '');
		}
		else {
			if (window.opera) {
				this.engine = 'opera';
				this.version = rematch(useragent, "opera.[0-9]\.[0-9]+").replace('opera', '').replace('/','');
			}
			else {
				if (document.childNodes && !document.all && !navigator.taintEnabled) {
					if (rematch(useragent, "applewebkit\/[0-9]+")!=null) {
						this.engine = 'webkit';
						this.version = rematch(useragent, "applewebkit\/[0-9]+").replace('applewebkit/', '');
					} else {
						this.engine = 'khtml';	
						this.version = rematch(useragent, "khtml\/[0-9]\.[0-9]\.[0-9]+").replace('khtml/', '');
					}
				}
				else 
					if (document.getBoxObjectFor != null) {
						this.engine = 'gecko';
						this.version = rematch(useragent, "gecko/[0-9]+").replace('gecko/', '');
					} // else can't detect
			}
		}
	};
	this.serialize = function(obj, option) {
		var str_out='', inc=0;
		for(var i in obj){
			if(typeof obj[i]!='function' && typeof obj[i]!='undefined' && obj[i]!=null && obj[i]!=''){
				if (option == 0) {
					str_out += (inc == 0 ? '' : '&') + i + '=' + escape(obj[i]);
				}
				else {
					if (option == 1) {
						str_out += (inc == 0 ? '' : '|') + escape(obj[i]);
					}
				}
				inc++;	
			}
		}
		return str_out;
	};
	/* ************************ */
	/* END: Core Util functions */
	/* ************************ */

	
	/* BEGIN: Core Object Default Preferences */
	this._hasInitialized = false;		// for internal checking of initialization.
	this.Preferences = new Object();

	this.Preferences.Persistence = {
		enabled: true,
		cookie_type: 'page', // page | domain | null
		cookie_name: 'oo_r',
		expiration: 24*60*60*1000 // 1 day
	};

	this.Preferences.Render = {
		type: 'floating',
		main_div_id: 'oo_feedback_float',
		up_div_id: 'olUp',
		over_div_id: 'olOver',
		img_path: 'onlineopinion/oo_black.gif',
		feedback_span_id: 'fbText',
		feedback_html: 'FEEDBACK',
		click_html: 'Click here to<br>rate this page',
		ty_html: '',
		float_style: 'fixed'
	};
	/*  END: Core Object Default Preferences */



	/* BEGIN: Metrics Object initialization */
	this.Metrics = new Object();
	this.Metrics.core = {
		'width'		: screen.width,
		'height'	: screen.height,
		'referer'	: window.location.href,
		'prev'		: document.referrer,
		'time1'		: (new Date()).getTime(),
		'time2'		: null
	};
	this.Metrics.custom = new Object();
	/* END: Metrics Object initialization */



	/* BEGIN: PLUGIN SECTION */
	this.Preferences.Plugins = new Object();
	
	// OnEntry/Exit Events
	this.Preferences.Plugins.Events = {
		poE: 0.0,		// Probability on Entry 
		poX: 0.0,		// Probability on Exit
		poC: {id:'', value: 0.0},		// Probability on Click
		poWC: 0.0		// Probability on Window/Tab Close
	};
	this.onEntry = function () {
		this._init();
		if(Math.random()>=1.0-this.Preferences.Plugins.Events.poE) {
			this.show();
			this.Preferences.Plugins.Events.poX=0.0;
		}
	};
	this.onExit = function() {
		this._init();
		if(Math.random()>=1.0-this.Preferences.Plugins.Events.poX) this.show();
	};
	this.OnClick = function() {
		this._init();
		if(Math.random()>=1.0-this.Preferences.Plugins.Events.poC) {
			this.show();
			this.Preferences.Plugins.Events.poX=0.0;
			this.Preferences.Plugins.Events.poC=0.0;
		}
	};

	// URL Rewrite
	this.Preferences.Plugins.URLRewrite = {
		active: false,
		regex_search_pattern: '',
		regex_replace_pattern: ''
	};
	
	// OnPage Comment Card Plugin
	this.Preferences.Plugins.CardOnPage = {
		enabled: false,
		div_id: 'onlineopinion_cc_window',
		iframe_id: 'onlineopinion_cc_frame'
	};
	
	/* END: PLUGIN */

	/* BEGIN: Core public methods */
	
	/* _init method should not be called by client code */
	this._init = function () {
		/* Don't initialize if already done. */
		if (this._hasInitialized) return true;
		this._hasInitialized = true;

		/* Trigger URL rewrite at init */
		this.resetReferer();
		
		/* Check if we have the cookie module available and enabled */
		/* Needs to be the last one in _init() because it can return false */
		if(typeof OnlineOpinion.cookie != 'undefined' && this.Preferences.Persistence.cookie_type!=null) {
			this.Cookie = new OnlineOpinion.cookie();
			if(typeof this.Preferences.Persistence.cookie_name != 'undefined') this.Cookie.cookie_name=this.Preferences.Persistence.cookie_name;
			this.Cookie.expiration = 1000*this.Preferences.Persistence.expiration; //because preference in secs
			/* If our Cookie matches, make sure we don't trigger any onEntry or onExit events! */
			if(this.Cookie.matchurl(this.Metrics.core.referer,this.Preferences.Persistence.cookie_type)==1) {
				this.Preferences.Plugins.Events.poX=0;
				this.Preferences.Plugins.Events.poE=0;
				return false;
			}	
		} else {
			this.Cookie=null;
		}
		return true;
	};
	
	
	
	this.render = function (onclick_func) {
		
		this._init();
		
		var b = new this._browser();
		var d = document;
		var de = d.documentElement;
		var db = d.body;
		var w = window;
		var divID = this.Preferences.Render.main_div_id;
		var bVersion = parseFloat(b.version);
		var compliant =  d.compatMode == 'CSS1Compat';
		var sObj = compliant ? de : db;
		// webkit has some different ways of handling document state...
		if(b.engine == "webkit") {
			sObj = db;
		}
		
		if (b.engine == null || b.version == null || isNaN(parseInt(b.version, 10)) ||	// Disables our code in the unrecognized or undetected browsers
				(b.engine == 'ie' && parseFloat(b.version) < 6) ||								// Pre IE 6.0
				(b.engine == 'opera' && parseInt(b.version, 10) < 8 ) ||						// Pre 7.0 Opera
				(b.engine == 'gecko' && parseInt(b.version, 10) < 20041107))				// Pre 1.0 FF release gecko engine < 20041107 
			return false;
		
		var mainDivObj = d.getElementById(divID);
		// create DIV layer based on wether or not it is present
		if (mainDivObj==null) {  
			if (this.Preferences.Render.type=='floating') {
				mainDivObj = db.appendChild(d.createElement("div"));
				mainDivObj.id = divID;
			} // else we don't care for now
		} 
		
		if (mainDivObj.innerHTML=="") {
			if (this.Preferences.Render.type == 'floating') {
				if (this.Preferences.Persistence.enabled && this.Preferences.Persistence.cookie_type!=null) {
					if (this.Cookie.matchurl(this.Metrics.core.referer, this.Preferences.Persistence.cookie_type)==1) return false;
				}
				
				// DOM type creation to be clean!
				var olUpObj = mainDivObj.appendChild(d.createElement("div"));
				olUpObj.id = this.Preferences.Render.up_div_id;
		
				var olOverObj = mainDivObj.appendChild(d.createElement("div"));
				olOverObj.id = this.Preferences.Render.over_div_id;
				olOverObj.style.display = 'none';

				olUpObj.onmouseover = function(){d.getElementById(olOverObj.id).style.display='block'; document.getElementById(olUpObj.id).style.display='none';};
				olOverObj.onmouseout = function(){d.getElementById(olOverObj.id).style.display='none'; document.getElementById(olUpObj.id).style.display='block';};

				var ooImg = olUpObj.appendChild(d.createElement("img"));
				ooImg.src = this.Preferences.Render.img_path;
				
				var fbText =  olUpObj.appendChild(d.createElement("span"));
				fbText.id = this.Preferences.Render.feedback_span_id;
				fbText.innerHTML = this.Preferences.Render.feedback_html;
				
				// Set the div accesibility text.
				var feedback_text = null;
				if(this.Preferences.Render.div_alt_text) {
					// property set, use that.
					feedback_text = this.Preferences.Render.div_alt_text;
				} else {
					// add the text from feedback_html to the IMG alt and title attributes.
					if(document.all) {
						feedback_text = fbText.innerText;
					} else { 
						feedback_text = fbText.textContent;
					}
				}
				mainDivObj.alt = feedback_text;
				mainDivObj.title = feedback_text;
				
				olOverObj.innerHTML = this.Preferences.Render.click_html;
			}
			
			if (this.Preferences.Render.type == 'static') {
				mainDivObj.innerHTML = this.Preferences.Render.main_html;
			}
		}
		
		
		
		// Set the floating bottom right hand corner if we are floating
		if (this.Preferences.Render.type == 'floating') {
			if(!this.Preferences.Render.float_style) this.Preferences.Render.float_style = 'fixed';
			// shorten some frequent used identifiers
			var mdoStyle = mainDivObj.style;
			var _this = this;
			var floatStyle = this.Preferences.Render.float_style;
			
			// setup render method for rightOfContent or fixedPreserveContent mode
			if(floatStyle.match(/Content/)) {
				var contentDiv = d.getElementById(_this.Preferences.Render.main_content_id || "content");
				
				// If contentDiv isn't set or not found, find the first Element node inside body.
				if(contentDiv == null) {
					floatStyle = this.Preferences.Render.float_style = 'fixed';
				}

				function getRightOfContent() {
					return contentDiv.offsetWidth + contentDiv.offsetLeft + 1;
				}
			
				// move the background image if it was placed center.
				function fixBackground() {
					if(_this.Preferences.Render.fix_background) {
						db.style.backgroundAttachment = "scroll";
						var margin = null;
						if(document.defaultView && document.defaultView.getComputedStyle) {
							margin = parseInt(document.defaultView.getComputedStyle(contentDiv, null).getPropertyValue("margin-left"), 10);
						} else {
							margin = parseInt(contentDiv.currentStyle.marginLeft, 10);
						}
						if(isNaN(margin) || margin == 0) {
							margin = contentDiv.offsetLeft || 0;
						}
						db.style.backgroundPosition = (Math.floor(contentDiv.scrollWidth * -0.5) - 2 + margin) + 'px 0';
					}
				}
			}

			var handleIEQuirks = function(e) {
				if (floatStyle.match(/^fixed/)) {
					var newLeft = sObj.scrollLeft + sObj.clientWidth -  mainDivObj.clientWidth;
					if(floatStyle == "fixedPreserveContent" && newLeft < getRightOfContent()) {
						newLeft = getRightOfContent() + 'px';
					} else if (floatStyle == "fixedContentMax" 
											&& (contentDiv.offsetWidth + mainDivObj.clientWidth) < sObj.clientWidth ) {
						newLeft = getRightOfContent() + 'px';
					}
					mdoStyle.left = newLeft;
				} else if(floatStyle == "rightOfContent") {
					mdoStyle.left = getRightOfContent() + 'px';
				}
				
				mdoStyle.top =  sObj.scrollTop + sObj.clientHeight - mainDivObj.clientHeight;
				var nullOrLoad = e == null || e.type == 'load';
				if(nullOrLoad) mdoStyle.visibility = 'visible';
				if(nullOrLoad || e.type == 'resize') fixBackground();
			};
			
			if (b.engine=='ie' && (bVersion < 7 || !compliant)) {
				// HACK: IE < 7 or IE7 "quirksmode" doesn't support fixed positioning. 
				mdoStyle.position = 'absolute';

				// Fire the handleIEQuirks reposition method on browser resize/scroll events, 
				// and onload for when IE has a previous scroll state stored.
				function mapEvents(target, events) {
					for(var idx = 0; idx < events.length; idx++) {
						var curevent = events[idx];
						target.attachEvent("on" + curevent, handleIEQuirks);
					}
				};
				
				mapEvents(mainDivObj, ["mouseover", "mouseout"]);
				mapEvents(w, ["resize", "scroll"]);
				handleIEQuirks();
			} else {
				mdoStyle.position = 'fixed';
				var resizeMove = null;
				if (floatStyle.match(/^fixed/)) {
					mdoStyle.right = '0px';
					mdoStyle.bottom = '0px';
					if (floatStyle == "fixedContentMax") {
						var maxContentWidth = contentDiv.offsetWidth + mainDivObj.offsetWidth;
						resizeMove = function(e) {
							if (sObj.clientWidth > maxContentWidth) {
								mdoStyle.left = getRightOfContent() - sObj.scrollLeft + 'px';
								mdoStyle.right = null;
							} else {
								mdoStyle.left = null;
								mdoStyle.right = '0px';
							}
						};
					}
				} else if(floatStyle == "rightOfContent") {
					var rightOfContent = getRightOfContent() - sObj.scrollLeft + 'px';
					mdoStyle.bottom = '0px';
					mdoStyle.left = rightOfContent;
				}
				
				if (floatStyle.match(/Content$/)) {
					var preserve = floatStyle == "fixedPreserveContent";
					// add right side gutter to push out horizontal scrolling.
					var gutter = d.createElement("div");
					db.replaceChild(gutter, mainDivObj);
					gutter.appendChild(mainDivObj);
					gutter.style.position = "absolute";
					gutter.style.width = mainDivObj.offsetWidth + 'px';
					gutter.style.right = '0px';
					gutter.style.top = '0px';
					gutter.style.height = sObj.scrollHeight + 'px';
					
					// add the resizing event
					resizeMove = function(e){
						var right = getRightOfContent();
						var runFix = false;
						if (preserve) {
							var left = sObj.offsetWidth - mainDivObj.offsetWidth;
							if (left <= right) runFix = true;
						} else {
							runFix = true;
						}
						if (runFix) {
							mdoStyle.left = right - sObj.scrollLeft + 'px';
							if(e == null || e.type == 'resize') {
								gutter.style.left = right + 'px';
								fixBackground();
							}
						} else {
							gutter.style.right = '0px';
							gutter.style.left = null;
							mdoStyle.right = "0px";
							mdoStyle.left = null;
						}
					};
				}
				
				if (resizeMove) {
					if(b.engine == 'ie') {
						w.attachEvent("onresize", resizeMove);
						w.attachEvent("onscroll", resizeMove);
					} else {
						w.addEventListener("resize", resizeMove, false);
						w.addEventListener("scroll", resizeMove, false);
					}
					resizeMove();
				}
				
				mainDivObj.style.visibility = 'visible';
			}
		}
		
		mainDivObj.onclick = onclick_func;
		
		// Run this if onPageCC
		
		try {
			if (this.Preferences.Plugins.CardOnPage.enabled == true) {
				var onPageCCdiv = db.appendChild(d.createElement("div"));
				onPageCCdiv.id = this.Preferences.Plugins.CardOnPage.div_id;
				var onPageCCiframe = onPageCCdiv.appendChild(d.createElement("iframe"));
				onPageCCiframe.id = this.Preferences.Plugins.CardOnPage.iframe_id;
				
				// Now set the params	
				var W = (b.engine == 'webkit') ? w.innerWidth : (b.engine == 'opera' ? db.clientWidth : de.clientWidth);
				var H = (b.engine == 'webkit') ? w.innerHeight : (b.engine == 'opera' ? db.clientHeight : de.clientHeight);
				
				//TODO: to grab from the DIV dynamically
				var wx = 585, hy = 400;
				onPageCCdiv.style.left = parseInt((W - wx) / 2, 10) + 'px';
				onPageCCdiv.style.top = parseInt((H - hy) / 2, 10) + 'px';
				if (b.engine == 'ie' && bVersion < 7) 
					onPageCCdiv.style.position = 'absolute';
			}
		} catch(e) { }
		return true;
	};
	
	this.launchCC = function () {
		try {
			/* Last check with cookies to avoid popping card if already seen */
			if(this.Cookie.matchurl(this.Metrics.core.referer, this.Preferences.Persistence.cookie_type)!=1){
				if (this.Preferences.Plugins.CardOnPage.enabled == true) {
					document.getElementById(this.Preferences.Plugins.CardOnPage.iframe_id).src = 'https://secure.opinionlab.com/ccc01/comment_card.asp?'+(this.Preferences.Render.type=='asm'?'asm=1&':'')+'static=1&' + this.serialize(this.Metrics.core, 0) + '&custom_var=' + this.serialize(this.Metrics.custom, 1);
					document.getElementById(this.Preferences.Plugins.CardOnPage.div_id).style.display = 'block';
				} else {
				/* Opens up the comment card in a new window */
				OnlineOpinion.util.popup('https://secure.opinionlab.com/ccc01/comment_card.asp?'+(this.Preferences.Render.type=='asm'?'asm=1&':'')+this.serialize(this.Metrics.core,0)+'&custom_var='+this.serialize(this.Metrics.custom,1), 'OnlineOpinion', 'resizable=yes,copyhistory=yes,scrollbars='+(this.Preferences.Render.type=='asm'?'yes':'no')+',location=no,status=no,fullscreen=no,width=545,height=200,top='+parseInt((this.Metrics.core.height-200)/2, 10)+',left='+parseInt((this.Metrics.core.width-545)/2, 10));
				}
			}
		} catch(e) {
			OnlineOpinion.util.popup('https://secure.opinionlab.com/ccc01/comment_card.asp?'+(this.Preferences.Render.type=='asm'?'asm=1&':'')+this.serialize(this.Metrics.core,0)+'&custom_var='+this.serialize(this.Metrics.custom,1), 'OnlineOpinion', 'resizable=yes,copyhistory=yes,scrollbars='+(this.Preferences.Render.type=='asm'?'yes':'no')+',location=no,status=no,fullscreen=no,width=545,height=200,top='+parseInt((this.Metrics.core.height-200)/2, 10)+',left='+parseInt((this.Metrics.core.width-545)/2, 10));
		}
	};
	
	this.show = function() {
		/* set time2 */
		this.Metrics.core.time2 = (new Date()).getTime();

		/* Show the Comment Card */
		this.launchCC();
		
		/* Hide the link then if not static */
		if (this.Preferences.Persistence.enabled) {
			document.getElementById(this.Preferences.Render.main_div_id).style.display='none';
		}
		
		/* tag URL only if cookie module available and enabled */
		
		if (this.Cookie != null && this.Preferences.Persistence.cookie_type!=null) {
			this.Cookie.tagurl(this.Metrics.core.referer,this.Preferences.Persistence.cookie_type);
		}
		
		/* reset the url referrer */
		this.resetReferer();
	};

  // Method for resetting the Referer, and have it properly rewritten if needed.
	this.resetReferer = function(alt_referer) {
		var location = window.location.href;
		if(alt_referer) location = alt_referer;
		var rewriter = this.Preferences.Plugins.URLRewrite;
		if(rewriter && rewriter.active==true) {
			this.Metrics.core.referer = location.replace(
				rewriter.regex_search_pattern,
				rewriter.regex_replace_pattern);
		} else {
			this.Metrics.core.referer = location;
		}
	};

	// TODO: Merge show & post, DRY
	this.post = function () { // Used for onPage static card posting of data
		/* set time2 */
		this.Metrics.core.time2 = (new Date()).getTime();
		var mainDivObj = document.getElementById(this.Preferences.Render.main_div_id);

		/* Post the data to the collection */
		var JSONP = mainDivObj.appendChild(document.createElement("script"));

		var odata = this.serialize(this.Metrics.core,0)+'&custom_var='+this.serialize(this.Metrics.custom,1);

		// TODO: grab all the parameteres from the form */
		var elements = document.forms[this.Preferences.Render.main_div_id+'_form'].elements;
		var formdata = [];
		for(idx=0; idx<elements.length; idx++) {
			var curElement = elements[idx];
			try{
				var name = curElement.name;
				if (name!=undefined && curElement.value!=undefined) {
					switch(curElement.type) {
						case "radio":
							if(curElement.checked)formdata.push(curElement.name+'='+encodeURIComponent(curElement.value));
							break;
						case "checkbox":
							if(curElement.checked)formdata.push(curElement.name+'='+encodeURIComponent(curElement.value));
							break;
						default:
							formdata.push(curElement.name+'='+encodeURIComponent(curElement.value));
					}
				}
			} catch(e) {}
		}
		var cdata = formdata.join('&');
		

		var osignature = this.Cookie!=null?this.Cookie._MD4(odata):"";
		JSONP.src = "https://secure.opinionlab.com/rate36s.asp?"+odata+"&"+cdata+"&signature="+osignature;
		
		/* Hide the link then if not static */
		if (this.Preferences.Persistence.enabled) {
			//mainDivObj.style.display='none';
			var mainFormDiv = document.getElementById(this.Preferences.Render.main_div_id+"_form");
			mainFormDiv.style.display='none';
		}

		
		/* Show the TY html if any! */
		if (this.Preferences.Render.ty_html!='') {
			var TYDivObj = document.getElementById(this.Preferences.Render.ty_div_id);
			TYDivObj.innerHTML = this.Preferences.Render.ty_html;
			TYDivObj.style.display='block';
		}

		
		/* tag URL only if cookie module available and enabled */
		if (this.Cookie != null && this.Preferences.Persistence.cookie_type!=null) {
			this.Cookie.tagurl(this.Metrics.core.referer,this.Preferences.Persistence.cookie_type);
		}

		
		/* reset the url referrer */
		this.resetReferer();
	};
	
	/* END: Core public methods */
};
