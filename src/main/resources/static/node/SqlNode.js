// img 태그를 사용했지만 icon을 활용할 수 있을 것
export function createSqlNode(editor, name, pos_x, pos_y) {
    const nodeId = `${name}_${pos_x}_${pos_y}`; // 노드의 고유 ID
    var inputN = `<div>
        <div class="title-box">DataBase</div>
                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGZaz1uTiKJsyaDb-hTIFPu96fFRJbhmtdeA&usqp=CAU"
                 style="width: 70px; height: 70px; padding-left: 7px; padding-right: 7px"
                 alt="My Image"
                 id="myImage${nodeId}"
                >
                <div class="modal" style="display: none">
                    <div class="modal-content">
                        <span class="close" id="closeModal${nodeId}">&times;</span>
                        Change your variable {name} !
                        <input type="text" df-flowAttr-sql id="inputField${nodeId}">
                    </div>
                </div>
    </div>`;

    var progWorkFlowMng = {
        flowId: nodeId, // 내장되어 있긴 함
        progId: -1,
        flowSeq: -1,
        flowType: -1,
        flowAttr: {
            sql : "qqq",
            col : ['']
        },
        crtdDttm: '',
        updtDttm: ''
    };

    editor.addNode(name, 1, 1, pos_x, pos_y, name, progWorkFlowMng, inputN);

    // 노드가 DOM에 추가된 후에 이벤트 핸들러를 연결합니다.
    document.getElementById(`myImage${nodeId}`).ondblclick = function(event) {
        showSqlModal(editor, event);
    };
    document.getElementById(`closeModal${nodeId}`).onclick = function(event) {
        closeSqlModal(editor, event);
    };
    document.getElementById(`inputField${nodeId}`).oninput = function(event) {
        updateSqlNodeData(editor, event);
    };

    // 이벤트 핸들러를 전역으로 하여 app에서도 다룰 수 있도록
    globalSqlNodeHandler();
}

export function globalSqlNodeHandler() {
    window.showSqlModal = showSqlModal;
    window.closeSqlModal = closeSqlModal;
    window.updateSqlNodeData = updateSqlNodeData;
}

export function showSqlModal(editor, e) {
    // 모달 창을 찾습니다.
    var modal = e.target.parentElement.querySelector(".modal");

    // 모달 창을 엽니다.
    modal.style.display = "block";

    // 이벤트가 발생한 노드의 ID를 가져옵니다.
    var nodeId = e.target.closest(".drawflow-node").id;

    // 노드 ID를 모달 창의 데이터 속성에 저장합니다.
    modal.dataset.nodeId = nodeId;

    var inputField = modal.querySelector("input");
    inputField.focus();

    console.log( e.target.closest(".drawflow-node"));

    // 에디터의 모드를 변경합니다.
    editor.editor_mode = 'fixed';
}

export function closeSqlModal(editor, e) {
    // 모달 창을 찾습니다.
    var modal = e.target.parentElement.parentElement;

    // 모달 창을 닫습니다.
    modal.style.display = "none";

    editor.editor_mode = 'edit';
}

export function updateSqlNodeData(editor, e) {
    // 입력 필드에서 데이터를 가져옵니다.
    var inputValue = e.target.value;

    // 모달 창을 찾습니다.
    var modal = e.target.closest(".modal");

    // 모달 창의 데이터 속성에서 노드 ID를 가져옵니다.
    var nodeId = modal.dataset.nodeId;

    // Uncaught TypeError, reading 'data'
    // 노드의 데이터를 업데이트
    editor.updateNodeDataFromId(nodeId, { flowAttr: {sql: inputValue} });
}
