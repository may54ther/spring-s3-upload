/** =======================
 * Config
 * ======================= */
const FILE_CONFIG = {
    "MIN_SIZE": 1024,
    "MAX_SIZE": 50 * 1024 * 1024
}

/** =======================
 * Event
 * ======================= */
function onChecked() {
    const checkAll = document.getElementById("checkAll")
    const total = document.querySelectorAll('input[name=checkInput]').length;
    const count = document.querySelectorAll('input[name=checkInput]:checked').length;
    checkAll.checked = total === count;
}

function onCheckedAll() {
    const checkAll = document.getElementById("checkAll")
    const checkboxes = document.getElementsByName("checkInput");

    for (const checkbox of checkboxes) {
        checkbox.checked = checkAll.checked;
    }
}

/** =======================
 * Fetch
 * ======================= */
const FILE_ACTION = {
    "fileList": function () {
        fetch("/api/files", {
            method: "GET",
        })
            .then(response => response.json())
            .then(result => {
                const listElement = document.getElementById("fileListBody");
                const files = result.response;

                if (files.length < 1) {
                    listElement.innerHTML = "<tr><td colspan='3' class='form-text text-center'>업로드 된 파일이 없습니다.</td></tr>";
                }

                files.forEach((file) => {
                    const createElement = document.createElement("tr");
                    createElement.innerHTML = `
                    <th scope="row"><input type="checkbox" id="file_${file.id}" name="checkInput" value="${file.id}" onchange="onChecked()" class="form-check-input"></th>
                    <td>
                        <label for="file_${file.id}">${file.name}</label>
                    </td>
                    <td class="list-btn text-center">
                        <button class="btn text-primary" onclick="FILE_ACTION.fileDownload('${file.id}')"><i class="bi bi-download"></i></button>
                        <button class="btn text-danger" onclick="FILE_ACTION.singleFileDelete(event,'${file.id}')"><i class="bi bi-trash3"></i></button>
                    </td>`;

                    listElement.append(createElement);
                });
            })
            .catch(error => console.warn(error))
    },
    "fileUpload": function () {
    },
    "fileDownload": function () {
    },
    "singleFileDelete": function () {
    },
    "multipleFileDelete": function () {
    }
}