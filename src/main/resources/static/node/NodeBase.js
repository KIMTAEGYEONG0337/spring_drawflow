export function createBaseNode(editor, name, pos_x, pos_y) {
    const nodeId = `${name}_${pos_x}_${pos_y}`; // 노드의 고유 ID
    var inputN = `<div>
        <div class="title-box">Memo</div>               
        <div class="box" style="box-sizing: content-box;">
        </div>
    </div>`;

    var progWorkFlowMng = {
        flowId: nodeId, // 내장되어 있긴 함
        progId: -1,
        flowSeq: -1,
        flowType: -1,
        flowAttr: {
            test:''
        },
        crtdDttm: '',
        updtDttm: ''
    };

    editor.addNode(name, 0, 0, pos_x, pos_y, name, progWorkFlowMng, inputN);


    window.updateBaseNodeData = updateBaseNodeData;
}

export function updateBaseNodeData(editor, e) {
    // 입력 필드에서 데이터를 가져옵니다.
    var inputValue = e.target.value;

    // var textarea = e.target.closest(".textarea");
    //
    // // 모달 창의 데이터 속성에서 노드 ID를 가져옵니다.
    // var nodeId = textarea.dataset.nodeId;

    // 노드의 데이터를 업데이트합니다.
    editor.updateNodeDataFromId(nodeId, { flowAttr: {test : inputValue} });
}