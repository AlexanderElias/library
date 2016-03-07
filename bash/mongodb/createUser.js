var UN = 'username';
var DB = 'database';
var PW = 'password';

db.createUser({
	user: UN,
	pwd: PW,
	roles: [
		{
			'role' : 'readWrite',
			'db' : DB
		},
	]
});
