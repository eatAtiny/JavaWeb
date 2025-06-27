console.log("123");

document.writeln("123");

function add(a, b) {
    return a + b;
}
// 在调用函数时，会自动将参数转换为字符串类型
console.log("function add(1, 2) = " + add(1, 2));
console.log("function add(1, 2) = " + add(1, "2")); //因为js为弱类型，所以传入的参数没有类型限制，在函数内部相当于进行了number+string的操作
// 也可以使用 + 运算符将数字和字符串拼接起来
console.log("1" + 2); // 12


// 匿名函数
// 1.函数表达式
var add1 = function (a, b) {
    return a + b;
}
// 2.箭头函数
var add2 = (a, b) => {
    return a + b;
}
console.log("function add(1, 2) = " + add1(1, 2));
console.log("function add(1, 2) = " + add2(1, 2));


// 对象
// 声明一个对象
let obj = {
    name: "张三",
    age: 18,
    sex: "男",
    // 方法
    sayHello: function () {
        console.log("Hello, " + this.name);
    },
    // 箭头函数
    sayHello2: () => {
        console.log("Hello, " + this.name);
    }
}

// 调用对象属性
console.log(obj.name);
console.log(obj.age);
console.log(obj.sex);
obj.sayHello();
obj.sayHello2();

// JSON
// 声明一个JSON对象，key必须使用双引号，value可以是任意数据类型
let json = {
    name: "张三",
    age: 18,
    sex: "男"
}
//JSON api
// 1.将对象转换为JSON字符串
let jsonStr = JSON.stringify(json);
console.log(jsonStr);
// 2.将JSON字符串转换为对象
let jsonObj = JSON.parse(jsonStr);
console.log(jsonObj);


function changeText() {
    // 1.获取元素
    let div = document.getElementById("div");
    console.log(div);
    // 2.获取元素的属性
    console.log(div.innerHTML);
    // 3.修改元素的属性
    div.innerHTML = "新的文本";
}

document.addEventListener('DOMContentLoaded', () => {
    console.log("DOM加载完成");
    document.querySelector("#btn2").onclick = function () {
        console.log("btn2被点击了");
    }

    document.querySelector("#btn3").addEventListener("click", () => {
        console.log("btn3被点击了");
    })
})


