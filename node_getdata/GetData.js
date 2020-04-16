var mysql = require('mysql');
var fs = require('fs');


queryDB(wait5sec);


function queryDB(callback){
	getInstalls();
	getRecentFails();
	getRedGreen();
	getStatusCounts();
	callback();
}

function wait5sec(){
	setTimeout(function(){
		queryDB(wait5sec)
	}, 100);
}

function getInstalls()
{
	var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "root",
	database:"pci_database"
	});
		
	con.connect(function(err) {
		if (err) throw err;
		con.query("SELECT * FROM (SELECT * FROM install_jobs ORDER BY ID DESC LIMIT 20) AS T ORDER BY ID;", function (err, result, fields){
			if (err) throw err;
			
			fs.writeFile("json/installTableData.json", JSON.stringify(result), function(err,) {
				if(err) throw err;
				//console.log("Updated " + filename);
			});	
		});
		con.end();
	}); 
}

function getRecentFails()
{
	var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "root",
	database:"pci_database"
	});
	
	con.connect(function(err) {
		if (err) throw err;
		con.query("SELECT * FROM install_jobs WHERE Status='FAILED' ORDER BY EndTime DESC LIMIT 30;", function (err, result, fields){
			if (err) throw err;
			
			fs.writeFile("json/recentFails.json", JSON.stringify(result), function(err,) {
				if(err) throw err;
				//console.log("Updated " + filename);
			});	
		});
		con.end();
	}); 
}

function getRedGreen()
{
	var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "root",
	database:"pci_database"
	});
	
	con.connect(function(err) {
		if (err) throw err;
		con.query("SELECT * FROM (SELECT * FROM install_jobs ORDER BY ID DESC LIMIT 100) AS T ORDER BY ID;", function (err, result, fields){
			if (err) throw err;
			
			fs.writeFile("json/redGreen.json", JSON.stringify(result), function(err,) {
				if(err) throw err;
				//console.log("Updated " + filename);
			});	
		});
		con.end();
	}); 
}

function getStatusCounts()
{
	var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "root",
	database:"pci_database"
	});
	
	con.connect(function(err) {
		if (err) throw err;
		con.query("SELECT Status,COUNT(*) as count FROM install_jobs GROUP BY Status ORDER BY count DESC;", function (err, result, fields){
			if (err) throw err;
			
			fs.writeFile("json/statusCounts.json", JSON.stringify(result), function(err,) {
				if(err) throw err;
				//console.log("Updated " + filename);
			});	
		});
		con.end();
	}); 
}



