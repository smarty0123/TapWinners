'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notifications/{user_id}/{notification_id}').onWrite(event => {
	const user_id = event.params.user_id;
  	const notification_id = event.params.notification_id;
  	console.log('We have a notification from : ', user_id);

  	if(!event.data.val()){

    return console.log('A Notification has been deleted from the database : ', notification_id);

  	}

  	const fromUser = admin.database().ref(`/notifications/${user_id}/${notification_id}`).once('value');
  	return fromUser.then(fromUserResult =>{
  		const from_user_name = fromUserResult.val().from;
  		console.log('You have notification from ', from_user_name);


  	const deviceToken = admin.database().ref(`/Scoreboard/${user_id}/token-id`).once('value');

  	return deviceToken.then(result =>{

  		const token_id = result.val();

  		const payload = {
  			notification: {
  				title : "Tap Winner",
  				body: `${from_user_name} has tap more than you.`,
  				sound: "default"
  			}
  		};

  		return admin.messaging().sendToDevice(token_id, payload).then(response =>{
  			console.log('This was the notification Feature');
  		});
  	});
  	});



 });