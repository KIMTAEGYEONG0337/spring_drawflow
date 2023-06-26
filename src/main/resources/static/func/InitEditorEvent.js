
const editor = window.editor;

editor.on('nodeCreated', function(id) {
    console.log("Node created " + id);
})

editor.on('nodeRemoved', function(id) {
    console.log("Node removed " + id);
})

editor.on('nodeSelected', function(id) {
    console.log("Node selected " + id);
})

editor.on('moduleCreated', function(name) {
    console.log("Module Created " + name);
})

editor.on('moduleChanged', function(name) {
    console.log("Module Changed " + name);
})

editor.on('connectionCreated', function(connection) {
    console.log('Connection Created');
    console.log(connection);
})

editor.on('connectionRemoved', function(connection) {
    console.log('Connection removed');
    console.log(connection);
})

editor.on('nodeMoved', function(id) {
    console.log("Node moved " + id);
})

editor.on('zoom', function(zoom) {
    console.log('Zoom level ' + zoom);
})

editor.on('translate', function(position) {
    console.log('Translate x:' + position.x + ' y:'+ position.y);
})

editor.on('addReroute', function(id) {
    console.log("Reroute added " + id);
})

editor.on('removeReroute', function(id) {
    console.log("Reroute removed " + id);
})

editor.on('mouseMove', function(position) {

});