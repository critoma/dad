// OnlineOpinion v4.1
// This product and other products of OpinionLab, Inc. are protected by U.S. Patent No. 6606581, 6421724, 6785717 B1 and other patents pending.

// Instance new  OnlineOpinion Object
var oOobj1 = new OnlineOpinion.ocode(); 

// Configure Persitence
oOobj1.Preferences.Persistence = 	{
	enabled: true,					// Disapear onClick
	cookie_name: 'oo_r',			// cookie name 
	cookie_type: 'page',			// Remembers which page got rated
	expiration: 300				// Cookie expiration, in seconds, after icon is clicked
};

// Configure Floating params
oOobj1.Preferences.Render = {
	type: 'floating',
	main_div_id: 'oo_feedback_float',
	up_div_id: 'olUp',
	over_div_id: 'olOver',
	img_path: 'http://java.sun.com/js/op/en-US/black_oo.gif',
	feedback_span_id: 'fbText',
	feedback_html: 'Feedback',
	click_html: 'Click here to<br>rate this page',
	float_style: "fixedContentMax",
	main_content_id: "a0v0"
};

// Configure URL rewrite
oOobj1.Preferences.Plugins.URLRewrite = {
	active: false,
	regex_search_pattern: '',
	regex_replace_pattern: ''
};

// Call Backs (do not modify unless you know what you are doing!)
OnlineOpinion.util.SafeAddOnLoadEvent(function(){oOobj1.onEntry();});
OnlineOpinion.util.SafeAddOnUnLoadEvent(function(){oOobj1.onExit();});

// Call Back (on Site Exit), uncomment line below to enable
// OnlineOpinion.util.SafeAddOnLoadEvent(function(){oOobj1.onEntry();OnlineOpinion.util.walkAnchors(document.body, 10, /^(http:\/\/salesdemo\.opinionlab\.com|http:\/\/www\.opinionlab\.com)/i, oOobj1)});

// Render Onload (needed for static & floating)
OnlineOpinion.util.SafeAddOnLoadEvent(function(){
	oOobj1.render(function() {
		if (typeof(s_pageName) != 'undefined') oOobj1.Metrics.custom.s_pageName = s_pageName;
		if (typeof(s_omnitureID) != 'undefined') oOobj1.Metrics.custom.s_omnitureID = s_omnitureID;
		oOobj1.show();
	});
});

//  OnlineOpinion v4.1, Copyright 2007-2009 Opinionlab, Inc.