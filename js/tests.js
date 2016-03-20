function stackTraceToString(stackTrace) {
    return stackTrace.map(function(s){
        return 'at '+ s.className + '.' + s.methodName + '('+ s.fileName +':' + s.lineNumber+')';
    }).join('\n');
}

function ScalaJSReporter(tc) {

    var testCount = 0;

    this.testFinished = function(event) {
        var result = {
            description: event.selector.testName,
            id: event.fullyQualifiedName + '.' + event.selector.testName,
            log: event.throwable ? [event.throwable.toString].concat([stackTraceToString(event.throwable.stackTrace)]) : [],
            skipped: event.status === 'Skipped',
            success: event.status === 'Success',
            suite: [event.fullyQualifiedName],
            time: event.durationLS
        };
        tc.result(result);
    };

    this.testStarted = function() {

    };
    this.suiteCompleted = function() {

    };
    this.suiteStarting = function() {

    };
    this.onMessage = function(msg) {
        var split = msg.indexOf(':');
        var cmd = msg.substring(0, split);
        if (cmd === 'event') {
            var event = JSON.parse(msg.substr(split+1));
            this.testFinished(event);
        } else if (cmd === 'msg') {
            var message = msg.substr(split+1);
            if (message === 'org.scalatest.events.TestStarting') { this.testStarted(); }
            else if(message === 'org.scalatest.events.SuiteCompleted') { this.suiteCompleted(); }
            else if(message === 'org.scalatest.events.SuiteStarting') { this.suiteStarting(); }
        } else if (cmd === 'fail') {
            this.testFailed(JSON.parse(msg.substr(split+1)));
        } else if (cmd.substring(0,2) === 'ok') {
        } else {
            console.log('UNKNOWN', msg);
        }
    };
}

var pseudoKarma = {
    result : function ( msg) {
        console.log("result:"  + JSON.stringify(msg));
    }
};

var stupidCallback =  function(msg) {
        console.log(msg);
};

window.scalajsCom = {
    reporter: function(tc) {
        console.log("reported...");
        testReporter = new ScalaJSReporter(tc);
    },
    init: function(recvCB) {
        console.log("init...");
        callback = recvCB;

    },
    receive: function(msg) {
        console.log("receive...");
        callback(msg);
    },
    send: function(msg) {
        console.log("send...");
        testReporter.onMessage(msg);
    },
    close: function() {
    }
};
//scalajsCom.init(stupidCallback);


var sampleTask = {
    fullyQualifiedName: 'pl.setblack.lsa.cryptotpyrc.rsa.js.RSACryptoAlgTest',
    fingerprint: {
        fpType: 'SubclassFingerprint',
        superclassName: 'org.scalatest.Suite',
        isModule: false,
        requireNoArgConstructor: true
    },
    explicitlySpecified: true,
    selectors: [{selType: 'SuiteSelector'}]
};

var sampleCmd = {
    loggerColorSupport: [],
    serializedTask: JSON.stringify(sampleTask)
};

scalajsCom.reporter(pseudoKarma);