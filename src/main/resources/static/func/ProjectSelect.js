import {newProject, loadProject} from "./ProgMstCRUD.js";
import {rebindEvents} from "./RebindEvent.js";
import {rebindModule} from "./ModuleEvent.js";

var tmp_prog_mst = window.tmp_prog_mst;
var editor = window.editor;

window.onload = function() {
    Swal.fire({
        title: 'Welcome!',
        text: 'What would you like to do?',
        showCancelButton: true,
        confirmButtonText: 'New Project',
        cancelButtonText: 'Load Project',
    }).then((result) => {
        if (result.isConfirmed) {
            newProject(tmp_prog_mst);
        } else if (result.isDismissed) {
            Swal.fire({
                title: 'Enter the project number',
                input: 'number',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Load',
                showLoaderOnConfirm: true,
                preConfirm: (number) => {
                    return loadProject(number, tmp_prog_mst)
                        .then(data => {
                            tmp_prog_mst = data;
                            return data;
                        })
                        .catch(error => {
                            Swal.showValidationMessage(`Request failed: ${error}`);
                            throw error;
                        })
                },
                allowOutsideClick: () => !Swal.isLoading()
            }).then((result) => {
                if (result.isConfirmed) {
                    tmp_prog_mst.viewAttr = JSON.parse(tmp_prog_mst.viewAttr);
                    editor.import(tmp_prog_mst.viewAttr);

                    // Next tick.
                    Promise.resolve().then(() => {
                        rebindModule(Object.keys(editor.drawflow.drawflow));
                    });

                    // why? // important!!
                    window.tmp_prog_mst = tmp_prog_mst;
                    console.log(window.tmp_prog_mst);
                    console.log("진행하세요");
                }
            })
        }
    });
}