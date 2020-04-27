//$.session.get('funcright');
var aaa='<%=session.getAttribute("funcright")%>'
console.log(aaa)
var example1 = new Vue({
    el: '#menu-list',
    data: {
        items: [
            { menuName: '修改资料'},
            { menuName: '修改密码'}
        ]
    }
})