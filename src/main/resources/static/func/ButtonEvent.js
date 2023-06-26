import {saveProject} from "./ProgMstCRUD.js";
import {rebindEvents} from "./RebindEvent.js";

editor = window.editor;

const exportElement = document.getElementById("export");
const clearElement = document.getElementById("clear");
const playElement = document.getElementById("play");
const importElement = document.getElementById("import");

exportElement.addEventListener("click", exportJson);
clearElement.addEventListener("click", () => editor.clearModuleSelected());
playElement.addEventListener("click", play);
importElement.addEventListener("click", importJson);

var exportData = editor.export();
var jsonData = JSON.stringify(exportData);

function exportJson() {
    exportData = editor.export();
    jsonData = JSON.stringify(exportData);

    let progMst = window.tmp_prog_mst;
    progMst.viewAttr = jsonData;
    console.log(progMst.progId);
    saveProject(progMst);
}
function importJson() {
    // editor.import(JSON.parse(jsonData));
    // rebindEvents(editor);

}

/* Play Event */
/* 낮은 ID 부터 탐색한다는 맹점이 있음. 초기 Node를 설정할 마땅한 대안이 필요함 */
function play() {
    let data =editor.drawflow.drawflow.Home;
    let workflowset = [];

    let visited = {};

    for(let nodeId in data.data) {
        if (!visited[nodeId]) {
            let order = [];
            let startNodeId = nodeId;
            dfs(startNodeId, visited, order);
            workflowset.push(order);
        }
    }

    console.log(workflowset);
}

function dfs(nodeId, visited, order) {
    if (!visited[nodeId]) {
        visited[nodeId] = true;
        order.push(nodeId);

        let node = editor.getNodeFromId(nodeId);

        for(let outputId in node.outputs) {
            let connections = node.outputs[outputId].connections;
            for (let connection of connections) {
                let nextNodeId = connection.node;
                dfs(nextNodeId, visited, order);
            }
        }
    }
}

