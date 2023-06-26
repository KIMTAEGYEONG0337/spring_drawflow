import {rebindEvents} from "./RebindEvent.js";

editor = window.editor;

var addElement = document.getElementById("add");
var deleteElement = document.getElementById("delete");
var changeHomeElement = document.getElementById("change");

var myModal = document.getElementById("myModal");
var modalSubmit = document.getElementById("modal-submit");
var moduleNameInput = document.getElementById("module-name");

addElement.addEventListener("click", function() { openModal(addModule) });
deleteElement.addEventListener("click", function() { openModal(deleteModule) });
changeHomeElement.addEventListener("click", changeModule);

function openModal(callback) {
    myModal.style.display = "block";
    modalSubmit.onclick = function() {
        var name = moduleNameInput.value;
        callback(name);
        myModal.style.display = "none";
        moduleNameInput.value = "";
    }
}

function addModule(name) {
    editor.addModule(name);
    if (name) {
        var ul = document.querySelector('.col ul');
        var li = document.createElement('li');
        li.textContent = name;
        li.onclick = function() {
            rebindEvents(editor, name);
            changeModule(event);
        };
        ul.appendChild(li);
    }
}

export function rebindModule(moduleList) {
    for (let i = 0 ; i < moduleList.length; i++) {
        if (moduleList[i] == 'Home') {
            rebindEvents(editor, 'Home');
            continue;
        }

        console.log(moduleList[i]);
        let ul = document.querySelector('.col ul');
        let li = document.createElement('li');
        li.textContent = moduleList[i];
        li.onclick = function() {
            rebindEvents(editor, moduleList[i]);
            changeModule(event);
        };
        ul.appendChild(li);
    }
}


function deleteModule(name) {
    if (name) {
        var ul = document.querySelector('.col ul');
        var lis = ul.getElementsByTagName('li');
        for (var i = 0; i < lis.length; i++) {
            if (lis[i].textContent === name) {
                editor.removeModule(name);
                ul.removeChild(lis[i]);
                break;
            }
        }
    }
}

function changeModule(event) {
    var all = document.querySelectorAll(".col ul li");
    for (var i = 0; i < all.length; i++) {
        all[i].classList.remove('selected');
    }
    event.target.classList.add('selected');

    var moduleName = event.target.textContent;
    rebindEvents(editor, moduleName);
}
