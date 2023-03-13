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
