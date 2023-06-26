// img 태그를 사용했지만 icon을 활용할 수 있을 것
export function createSqlNode(editor, name, pos_x, pos_y) {
    const nodeId = `${name}_${pos_x}_${pos_y}`; // 노드의 고유 ID
    let inputN = `<div>
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

    let progWorkFlowMng = {
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
    let modal = e.target.parentElement.querySelector(".modal");

    e.target.closest(".drawflow-node").style.zIndex = "9999";
    modal.style.display = "block";
    window.transform = editor.precanvas.style.transform;
    editor.precanvas.style.transform = '';
    editor.precanvas.style.left = editor.canvas_x + 'px';
    editor.precanvas.style.top = editor.canvas_y + 'px';

    let nodeId = e.target.closest(".drawflow-node").id;

    // 노드 ID를 모달 창의 데이터 속성에 저장합니다.
    modal.dataset.nodeId = nodeId;

    let inputField = modal.querySelector("input");
    inputField.focus();

    // console.log( e.target.closest(".drawflow-node"));

    // 에디터의 모드를 변경합니다.
    editor.editor_mode = 'fixed';
}

export function closeSqlModal(editor, e) {
    // 모달 창을 찾습니다.
    let modal = e.target.parentElement.parentElement;
    e.target.closest(".drawflow-node").style.zIndex = "2";
    // 모달 창을 닫습니다.
    modal.style.display = "none";
    editor.precanvas.style.transform = transform;
    editor.precanvas.style.left = '0px';
    editor.precanvas.style.top = '0px';
    editor.editor_mode = "edit";
}

export function updateSqlNodeData(editor, e) {
    // 입력 필드에서 데이터를 가져옵니다.
    let inputValue = e.target.value;

    // 모달 창을 찾습니다.
    let modal = e.target.closest(".modal");

    // 모달 창의 데이터 속성에서 노드 ID를 가져옵니다.
    let nodeId = modal.dataset.nodeId;

    // Uncaught TypeError, reading 'data'
    // 노드의 데이터를 업데이트
    editor.updateNodeDataFromId(nodeId, { flowAttr: {sql: inputValue} });
}
