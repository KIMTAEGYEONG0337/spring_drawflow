const editor = window.editor;
const drawflowElement = document.getElementById("drawflow");
const dragElement = document.getElementsByClassName("drag-drawflow");

drawflowElement.addEventListener("drop", drop);
drawflowElement.addEventListener("dragover", allowDrop);

for(var i = 0; i < dragElement.length; i++) {
    dragElement[i].addEventListener("dragstart", drag);
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("node", ev.target.getAttribute('data-node'));
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("node");
    addNodeToDrawFlow(editor, data, ev.clientX, ev.clientY);
}

async function addNodeToDrawFlow(editor, name, pos_x, pos_y) {
    if(editor.editor_mode === 'fixed') {
        return false;
    }
    console.log(name);
    pos_x = pos_x * ( editor.precanvas.clientWidth / (editor.precanvas.clientWidth * editor.zoom)) - (editor.precanvas.getBoundingClientRect().x * ( editor.precanvas.clientWidth / (editor.precanvas.clientWidth * editor.zoom)));
    pos_y = pos_y * ( editor.precanvas.clientHeight / (editor.precanvas.clientHeight * editor.zoom)) - (editor.precanvas.getBoundingClientRect().y * ( editor.precanvas.clientHeight / (editor.precanvas.clientHeight * editor.zoom)));

    switch (name) {
        case 'memo':
            var module = await import('../node/MemoNode.js');
            module.createMemoNode(editor, name, pos_x, pos_y);
            break;

        case 'sql' :
            // 각 노드는 creatNode시에 생기는 ID와는 별개로 class_posx_posy를 고유 flowId로 가진다
            var module = await import('../node/SqlNode.js');
            module.createSqlNode(editor, name, pos_x, pos_y);
            break;
    }
}