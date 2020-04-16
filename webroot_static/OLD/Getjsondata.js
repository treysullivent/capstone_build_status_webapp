var mysql = require('mysql');
var fs = require('fs');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database:"capstone"
});

con.connect(function(err) {
  if (err) throw err;
  con.query("SELECT * FROM Installs LIMIT 20", function (err, result, fields){
    if (err) throw err;

          
    fs.writeFile('data.json', JSON.stringify(result), function(err,) {
      if(err) throw err;
      console.log('saved');
    });
    

    con.end();
  });

  
}); 