'use strict';

let App = {};

_.extend(App, Backbone.Events);

export default App;

// export default class App {
//
//     static on(eventName, cb) {
//         eventsBus.on(eventName, cb)
//     }
//
//     static trigger(eventName, data = null) {
//         eventsBus.trigger(eventName, data)
//     }
// }