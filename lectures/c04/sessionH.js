var http=require('http');
var options = { 
    hostname: '172.20.10.2',
    port: '8080',
    path: '/S09/ShowSession',
    method: 'GET',
    headers: {'Cookie': 'JSESSIONID=3FA3C280D9596DE5995FA4EF66FAF1F4', },
};
var results = ''; 
var req = http.request(options, function(res) {
    res.on('data', function (chunk) {
        results = results + chunk;
        console.log(results);
    }); 
    res.on('end', function () {
        console.log('end response result');
    }); 
});

req.on('error', function(e) {
        console.log('error: ' + e);
});

req.end();


