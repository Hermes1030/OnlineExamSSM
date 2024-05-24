document.getElementById("toggleFullScreen").addEventListener('click', function() {
    if (screenfull.isFullscreen) {
        // 退出全屏
        screenfull.exit();
        alert('已退出全屏，请进入全屏后考试。');
    } else {
        // 进入全屏
        if (screenfull.request()) {
        } else {
            alert('全屏失败，请进入全屏后考试。');
        }
    }
});
