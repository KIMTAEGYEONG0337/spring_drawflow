import {globalSqlNodeHandler} from "../node/SqlNode.js";
import {globalMemoNodeHandler} from "../node/MemoNode.js";

// Html이 갱신될 때 마다 Event를 Dom 요소에 리바인딩
// module을 변경할 때마다 Event를 rebinding 하는데 사용
export function rebindEvents(editor, mod) {
    globalSqlNodeHandler();
    globalMemoNodeHandler();

    editor.changeModule(mod);
    let data = editor.drawflow.drawflow[mod];

    for(let nodeId in data.data) {
        let nodeData = editor.getNodeFromId(nodeId);

        console.log(nodeId);
        console.log(nodeData);

        // node class에 따라 binding 할 event를 적절히 선택
        switch(nodeData.name) {
            case 'memo':
                // Box size는 JSON data에 포함되어 있지 않으므로 manual로 적용해줘야 함.
                var textAreaElement = document.getElementById(`textArea${nodeData.data.flowId}`);
                textAreaElement.oninput = function(event) {
                    updateMemoNodeData(editor, event);
                };
                textAreaElement.onmousedown = function(event) {
                    stopMemoPropagation(event);
                };
                break;
            case 'sql' :
                document.getElementById(`myImage${nodeData.data.flowId}`).ondblclick = function(event) {
                    showSqlModal(editor, event);
                };
                document.getElementById(`closeModal${nodeData.data.flowId}`).onclick = function(event) {
                    closeSqlModal(editor, event);
                };
                document.getElementById(`inputField${nodeData.data.flowId}`).oninput = function(event) {
                    updateSqlNodeData(editor, event);
                };
                break;
        }
    }
}
