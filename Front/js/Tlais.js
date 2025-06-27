document.addEventListener('DOMContentLoaded', ()=>{
    console.log("DOM加载完成");
    const trs = document.querySelectorAll("tr");
    for (let i = 1; i < trs.length; i++) {
        trs[i].addEventListener('mouseenter', ()=>{
            trs[i].style.backgroundColor = 'rgba(0, 255, 55, 0.4)';
        });
        trs[i].addEventListener('mouseleave', ()=>{
            trs[i].style.backgroundColor = '';
        });
    }

    // 4.获取所有的按钮
    const btns = document.querySelectorAll("#delete");
    // 5.遍历所有的按钮
    for (let i = 0; i < btns.length; i++) {
        // 6.给每个按钮添加点击事件
        btns[i].addEventListener('click', function() {
            if (confirm("确定要删除吗？")){
                this.parentNode.parentNode.remove();
            }
        });
    }

})
