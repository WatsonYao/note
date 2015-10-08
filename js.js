// Rx 响应式
var requestStream = Rx.Observable.just('https://api.github.com/users');
requestStream.subscribe(function(requestUrl){
	jQuery.getJSON(requestUrl, function(reponseData){
		// ...
	});
})

requestStream.subscribe(function(requestUrl){
	var responseStream = Rx.Observable.create(function(observer){
		jQuery.getJSON(requestUrl)
			.done(function(response){ observer.onNext(response);})
			.fail(function(jqXHR, status, error){observer.onError(error);})
			.always(function(){observer.onCompleted();});
	});

	responseStream.subscribe(function(response){
		// ...
	});
})

var responseMetastream = requestStream.map(function(requestUrl){
	return Rx.Observable.fromPromise(jQuery.getJSON(requestUrl));
});

var responseStream = requestStream.flatMap(function(requestUrl){
	return Rx.Observable.fromPromise(jQuery.getJSON(requestUrl));
});

// 总结一下
var requestStream = Rx.Observable.just('https://api.github.com/users');

var responseStream = requestStream.flatMap(function(requestUrl){
	return Rx.Observable.fromPromise(jQuery.getJSON(requestUrl));
})

responseStream.subscribe(function(response){
	// ...
});

// 点击刷新按钮的事件流
var refreshButton = document.querySelector('.refresh');
var refreshClickStream = Rx.Observable.fromEvent(refreshButton,'click');
var requestStream = refreshClickStream.map(function(){
	var randomOffset =  Math.floor(Math.random()*500);
	return 'https://api.github.com/users?since=' + randomOffset;
});

// 将两个合并
var requestOnRefreshStream = refreshClickStream.map(function(){
	var randomOffset = Math.floor(Math.random()*500);
	return 'http://api.github.com/users?since=' + randomOffset;
});

var startupRequestStream = Rx.Observable.just('http://api.github.com/users');

var requestStream = Rx.Observable.merge(requestOnRefreshStream,startupRequestStream);

// 简便写法
var requestStream = refreshClickStream.map(function(){
	var randomOffset = Math.floor(Math.random()*500);
	return 'http://api.github.com/users?since=' + randomOffset;
}).merge(Rx.Observable.just('https://api.github.com/users'));

// 更简洁可读
var requestStream = refreshClickStream.map(function(){
	// var ...
	// return 
}).startWith('http://api.github.com/users');

// 推荐关注的1号用户数据的事件流：
var suggestion1Stream = responseStream.map(function(listUsers){
	return listUsers[Math.floor(Math.random()*listUsers.length)];
})
suggestion1Stream.subscribe(function(suggestion){

});

//
var suggestion1Stream = responseStream.map(function(listUsers){
	return listUsers[Math.floor(Math.random()*listUsers.length)];
}).merge(
	refreshClickStream.map(function(){
		return null;
	})
).startWith(null);

// 完整代码

var refreshButton = document.querySelector('.refresh');
var refreshClickStream = Rx.Observable.fromEvent(refreshButton, 'click');

var closeButton1 = document.querySelector('.close1');
var close1ClickStream = Rx.Observable.fromEvent(closeButton1, 'click');

var requestStream = refreshClickStream.startWith('startup click')
	.map(function(){
		var randomOffset = Math.floor(Math.random()*500);
		return 'http://api.github.com/users?since=' + randomOffset;
	});

var responseStream = requestStream.flatMap(function(requestUrl){
	return Rx.Observable.fromPromise($.ajax({url:requestUrl}));
});

var suggestion1Stream = close1ClickStream.startWith('startup click')
	.combineLatest(responseStream,function(click,listUsers){
		return listUsers[Math.floor(Math.random()*listUsers.length)];
	}).merge(
		refreshClickStream.map(function(){return null});
	).startWith(null);

suggestion1Stream.subscribe(function(suggestion){
	// ...
});


// react findDOMNode()
var MyComponent = React.createClass({
	handleClick:function(){
		React.findDOMNode(this,refs.myTextInput).focus();
	},
	render:function(){
		return(
			<div>
				<input type="text" ref="myTextInput" />
				<input type="button" value="Focus the text input" onClick={this.handleClick} />
			</div>
		);
	}
});























































