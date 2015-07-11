class TV{
	var keyCount = "12"
	var keyName = "Power"

	func openTV()->String{
		return "Success"
	}

	init(name:String){
		self.name = name
	}

	deinit{
		name=""
	}
}

class MiTV:TV{
	
	override var name:String{
		get{
			return self.name
		}
		set{

		}
	}
}

var miTV = TV()
miTV.keyName

struct TV{
	var keyName = "xxx"
	var keyNumber = 12
	func getKeyNumber()->Int{
		return keyNumber
	}
}

// 值类型
var myTV = TV(keyName:"xxx",keyNumber:12)

// 类和结构体
// 类可以继承，可以析构，可以多重引用 ,结构不可


//协议，协议是可以继承的
protocol Action{
	var myName:String{
	get set
	}

	var myAge:Int{
	get
	}

	func getMyName()->String
}

class InvokeAction:Action{
	
}

class XXXX:基类,协议{
	
}

// 协议合并 同时实现协议
func hello(s:protocol<Action,ChildAction>){
	
}


// 泛型
func swapValue<T>(inout a:T, inout b:T){
	let temp = a
	a = b
	b = temp
}

var a = "a"
var b = "b"
swapValue(&a, &b)

class Vclass<T>{
	
	func getName(s:T){

	}

}

var vv = Vclass<Int>()

// 类，枚举，结构 都可以采用协议